

import java.util.Random;
import java.util.Stack;
import javafx.geometry.Point2D;

public class GameLevel {
    private GameController gc = new GameController();
    private static Random rand = new Random();
    private boolean isEasy;
    
    public void setLevel(boolean isEasy) {
        this.isEasy=isEasy;
    }
    public boolean getLevel() {
        return isEasy;
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
                    gc.getAdjacentByDirection(adjacentPoints, x, y, cell.ship.vertical);
                } else {
                    gc.getAdjacentPoint(adjacentPoints, x, y);
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
                    gc.getAdjacentByDirection(adjacentPoints, x, y, cell.ship.vertical);
                } else {
                    gc.getAdjacentPoint(adjacentPoints, x, y);
                }
            }
            enemyTurn = mark;
        }
    }
}
