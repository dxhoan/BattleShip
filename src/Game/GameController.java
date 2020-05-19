package Game;

import Model.Shot;
import Model.Ship;
import Model.Board;
import Model.Player;
import static Main.BattleShip.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import javafx.geometry.Point2D;

public class GameController {

    private static Random rand = new Random();

    public void CreateShipForAI(Board enemyBoard, int[] shipList) {
        int type = 5;
        while (type > 0) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if (enemyBoard.setShip(new Ship(shipList[type - 1], Math.random() < 0.5), x, y)) {
                type--;
            }
        }
    }

    public void EasyAITurn(Board enemyBoard, Board myBoard, Stack<Point2D> adjacentPoints, boolean enemyTurn, boolean mark) {
        while (enemyTurn) {
            int x, y;
            x = rand.nextInt(10);
            y = rand.nextInt(10);
            Board.Cell cell = myBoard.getCell(x, y);
            if (cell.wasShot) {
                continue;
            }
            mark = cell.isHit();
            if (mark) {
                if (!cell.ship.isAlive()) {
                    while (!adjacentPoints.empty()) {
                        adjacentPoints.pop();
                    }
                    continue;
                }
                if (cell.ship.getHealth() < cell.ship.type - 1) {
                    getAdjacentByDirection(adjacentPoints, x, y, cell.ship.vertical);
                } else {
                    getAdjacentPoint(adjacentPoints, x, y);
                }
            }
            enemyTurn = mark;
        }
    }

    public void HardAITurn(Board enemyBoard, Board myBoard, Stack<Point2D> adjacentPoints, boolean enemyTurn, boolean mark) {
        while (enemyTurn) {
            int x, y;
            if (adjacentPoints.empty()) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
            } else {
                System.out.println("STACK SIZE : " + adjacentPoints.size());
                Point2D p = adjacentPoints.pop();
                x = (int) p.getX();
                y = (int) p.getY();
            }
            Board.Cell cell = myBoard.getCell(x, y);
            if (cell.wasShot) {
                continue;
            }
            mark = cell.isHit();
            if (mark) {
                if (!cell.ship.isAlive()) {
                    while (!adjacentPoints.empty()) {
                        adjacentPoints.pop();
                    }
                    continue;
                }
                if (cell.ship.getHealth() < cell.ship.type - 1) {
                    getAdjacentByDirection(adjacentPoints, x, y, cell.ship.vertical);
                } else {
                    getAdjacentPoint(adjacentPoints, x, y);
                }
            }
            enemyTurn = mark;
        }
    }

    //Kiểm tra toa độ có hợp lệ hay koQ
    public boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public boolean isValidPoint(Point2D a) {
        return isValidPoint(a.getX(), a.getY());
    }

    //Thêm tọa độ điểm vừa bắn vào danh sách
    public void addHistoryShot(Board.Cell cell, boolean accurate, ArrayList<Shot> history) {
        Shot mShot = new Shot(cell.x, cell.y, accurate);
        history.add(mShot);
    }

    // Kiểm tra 4 điểm xung quanh
    public void getAdjacentPoint(Stack<Point2D> adjacentPoints, int x, int y) {
        Point2D[] points = new Point2D[]{new Point2D(x - 1, y), new Point2D(x + 1, y), new Point2D(x, y - 1),
            new Point2D(x, y + 1)};
        for (Point2D p : points) {
            if (isValidPoint(p.getX(), p.getY())) { // kiểm tra xem ô xung quanh hợp lệ ko, nếu ok thì đẩy vào push
                adjacentPoints.push(p);
            }
        }
    }

    // Xác định hướng bắn
    public void getAdjacentByDirection(Stack<Point2D> adjacentPoints, int x, int y, boolean vertical) {
        Point2D[] points;
        if (vertical) {
            points = new Point2D[]{new Point2D(x, y - 1), new Point2D(x, y + 1)};
        } else {
            points = new Point2D[]{new Point2D(x - 1, y), new Point2D(x + 1, y),};
        }
        for (Point2D p : points) {
            if (isValidPoint(p.getX(), p.getY())) {
                adjacentPoints.push(p);
            }
        }
    }

    //Chuyển sang panel hiển thị kết quả
    public void Result(Board myBoard, Board enemyBoard, String strResult, ArrayList<Shot> history) throws IOException {
        if (strResult == "YOU WIN") {
            if (isEasy) {
                myBoard.setScore((int) ((float) enemyBoard.getPlayerHit() / enemyBoard.getMove() * 100));
            } else {
                myBoard.setScore((int) ((float) enemyBoard.getPlayerHit() / enemyBoard.getMove() * 100) * 2);
            }
            if (myBoard != null) {
                ArrayList<Player> playerData = new ArrayList<>();
                playerData = SaveData.Player_Data();
                playerData = SaveData.InputPlayer(GameMenu.playername, myBoard.getScore(), playerData);
                SaveData.InputDataToFile(playerData);
            }
        } else {
            myBoard.setScore(0);
        }
        GameResult.transferHistory(history);
        GameResult.transferStat(strResult, enemyBoard.getMove(), enemyBoard.getPlayerHit(), myBoard.getScore());
        System.out.println("Player hits : " + enemyBoard.getPlayerHit());
        System.out.println("Moves : " + enemyBoard.getMove());
        System.out.println("SCORE : " + myBoard.getScore());
    }

    //---Phần cần sửa---///
    private static ArrayList<String> stringHistory = new ArrayList<>();
    //Chuyển transferHistory(history) về setHistoryShot và đưa vào Shot.java
    public void setHistoryShot(ArrayList<Shot> historyList) {
        stringHistory.clear();
        int numLoops = historyList.size();
        for (int i = 1; i <= numLoops; i++) {
            stringHistory.add(String.format("%3d. %s", i, historyList.get(i - 1).toString()));
        }
    }
    //thay thế stringHistory trong GameResult line 65 (shot.getHistoryList())
    public ArrayList<String> getHistoryShot() {
        return stringHistory;
    }
}
