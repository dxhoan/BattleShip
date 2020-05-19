package Model;

import Game.GameController;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Point2D;

public class Board extends Parent {

    private VBox col = new VBox();
    public boolean enemyBoard = false;
    public int ships = 5;
    public int playerHit = 0;
    public int move = 0;
    private int Score;
    private GameController gc = new GameController();

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getMove() {
        return move;
    }

    public int getPlayerHit() {
        return playerHit;
    }

    public Board(boolean enemyBoard, EventHandler<? super MouseEvent> handler) {
        this.enemyBoard = enemyBoard;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            col.getChildren().add(row);
        }
        getChildren().add(col);
    }

    public boolean setShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) {
            int length = ship.type;

            if (ship.vertical) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    if (!enemyBoard) {
                        cell.setFill(Color.GREEN);
                        cell.setStroke(Color.WHITE);
                    }
                }
            } else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    if (!enemyBoard) {
                        cell.setFill(Color.GREEN);
                        cell.setStroke(Color.WHITE);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;

        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!gc.isValidPoint(x, i)) {
                    return false;
                }

                Cell cell = getCell(x, i);
                if (cell.ship != null) {
                    return false;
                }

                // Không thể đặt tàu ở sát nhau
                for (Cell neighbor : checkNeighbor(x, i)) {
                    if (!gc.isValidPoint(x, i)) {
                        return false;
                    }

                    if (neighbor.ship != null) {
                        return false;
                    }
                }
            }
        } else {
            for (int i = x; i < x + length; i++) {
                if (!gc.isValidPoint(i, y)) {
                    return false;
                }

                Cell cell = getCell(i, y);
                if (cell.ship != null) {
                    return false;
                }

                for (Cell tau : checkNeighbor(i, y)) {
                    if (!gc.isValidPoint(i, y)) {
                        return false;
                    }

                    if (tau.ship != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public class Cell extends Rectangle {

        public int x, y; // Tọa độ của ô vuông
        public Ship ship = null; // Khởi tạo object tàu
        public boolean wasShot = false; // Biến kiểm tra tàu bị bắn chưa
        public Image image;
        private Board board;

        public Cell(int x, int y, Board board) {
            super(40, 40);	// Chiều dài ô vuông extends từ Rectangle
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTBLUE);
            setStroke(Color.BLACK);
        }

        //Kiểm tra bắn trúng hay không
        public boolean isHit() {
            wasShot = true;

            if (ship != null) {
                ship.hit();
                if (board.enemyBoard) {
                    board.playerHit++;
                    board.move++;
                }
                setFill(Color.RED);
                image = new Image(new File("img/explode.png").toURI().toString());
                setFill(new ImagePattern(image));
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }
            image = new Image(new File("img/missed.png").toURI().toString());
            setFill(new ImagePattern(image));
            if (board.enemyBoard) {
                board.move++;
            }
            return false;
        }

    }

    public Cell getCell(int x, int y) {
        return (Cell) ((HBox) col.getChildren().get(y)).getChildren().get(x); // Lấy 1 hàng từ biến "cột" sau đó convert sang HBox để lấy children, rồi lấy children của x ra được node convert qua Elements
    }

    private Cell[] checkNeighbor(int x, int y) {
        Point2D[] array = new Point2D[]{
            new Point2D(x - 1, y),
            new Point2D(x + 1, y),
            new Point2D(x, y - 1),
            new Point2D(x, y + 1),
            new Point2D(x + 1, y + 1),
            new Point2D(x - 1, y - 1),
            new Point2D(x - 1, y + 1),
            new Point2D(x + 1, y - 1)
        };

        List<Cell> neighbor = new LinkedList<Cell>();
        for (Point2D a : array) {
            if (gc.isValidPoint(a)) {
                neighbor.add(getCell((int) a.getX(), (int) a.getY()));
            }
        }
        return neighbor.toArray(new Cell[0]);
    }

}
