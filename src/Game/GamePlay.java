package Game;

import Model.Shot;
import Model.Ship;
import Model.Board;
import static Main.BattleShip.*;
import Ulti.Entity;
import Ulti.GameButton;
import Ulti.PublicMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GamePlay extends PublicMethod implements Entity {

    Board myBoard, enemyBoard;
    MediaPlayer shipPlayer, startPlayer, wonPlayer, lostPlayer;
    private boolean running = false;
    private boolean mark = false;
    private int shipsToPlace = 5;
    public static String strResult;
    private boolean enemyTurn = false;
    private int[] shipList = {2, 3, 3, 4, 5};
    private static ArrayList<Shot> history = new ArrayList<>(); // Danh sách ô đã bắn
    private Stack<Point2D> adjacentPoints = new Stack<Point2D>(); // Stack chứa tọa độ xung quanh ô bắn trúng

    private GameController gc = new GameController();

    public GamePlay() throws IOException {

        BorderPane root = new BorderPane();
        ImageView imgView = addBackground(gameplay_bg, 1120, 630);
        root.getChildren().add(imgView);

        Text name = addText("BATTLE SHIP", 32);
        HBox top = new HBox(name);
        top.setAlignment(Pos.CENTER);
        root.setTop(top);

        Label me = addLabel(GameMenu.playername, 25);
        Label enemy = addLabel("Enemy", 25);

        
        Pane result = new Pane();
        shipPlayer = addSound(ship_mp3, true);
        startPlayer = addSound(start_mp3, true);
        wonPlayer = addSound(victory_mp3, true);
        lostPlayer = addSound(defeat_mp3, true);
        if (soundOn) {
            shipPlayer.play();
        }

        ImageView victory = addBackground(victory_bg, 600, 400);
        ImageView defeat = addBackground(defeat_bg, 300, 290);
        defeat.setTranslateX(150);
        defeat.setTranslateY(55);

        Rectangle result_bg = new Rectangle(600, 400);
        result_bg.setFill(Color.GREY);
        result_bg.setOpacity(0.5);

        GameButton btnContinue = new GameButton("Continue", 100, 40);
        btnContinue.setTranslateX(252);
        btnContinue.setTranslateY(300);

        result.setTranslateX(250);
        result.setTranslateY(115);

        ////////////////////////////////////////////////////////////////////////
        // GamePlay -> Menu
        GameButton btnReturn = new GameButton("RETURN TO MAIN MENU", 250, 40);
        btnReturn.setOnMouseClicked(sukien -> {
                startPlayer.dispose();
                shipPlayer.dispose();
                wonPlayer.dispose();
                lostPlayer.dispose();
                resume = true;
            try {
                new GameMenu();
            } catch (IOException ex) {
                Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
            }   
        });
        btnReturn.setAlignment(Pos.CENTER);
        HBox close = new HBox(btnReturn);
        close.setAlignment(Pos.CENTER);
        root.setBottom(close);

        ////////////////////////////////////////////////////////////////////////
        history.clear(); //xóa dữ liệu tọa độ bắn trước khi chơi
        enemyBoard = new Board(true, sukien -> {
            if (!running) {
                return;
            }
            Board.Cell cell = (Board.Cell) sukien.getSource();
            if (cell.wasShot) {
                return;
            }
            enemyTurn = !cell.isHit();
            gc.addHistoryShot(cell, !enemyTurn, history); // Thêm vào danh sách turn bắn
            

            if (enemyBoard.ships == 0) {
                strResult = "YOU WIN";
                startPlayer.dispose();
                shipPlayer.dispose();
                if (soundOn) {
                    wonPlayer.play();
                }
                running = false;
                result.getChildren().addAll(result_bg, victory, btnContinue);
                root.getChildren().remove(close);
                root.getChildren().add(result);
                btnContinue.setOnMouseClicked(e -> {
                    try {
                        wonPlayer.dispose();
                        gc.Result(myBoard, enemyBoard, strResult, history);
                        new GameResult();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
            }
            if (enemyTurn) {
                if (isEasy) {
                    gc.EasyAITurn(enemyBoard, myBoard, adjacentPoints, enemyTurn, mark);
                } else {
                    gc.HardAITurn(enemyBoard, myBoard, adjacentPoints, enemyTurn, mark);
                }
                if (myBoard.ships == 0) {
                    strResult = "YOU LOSE";
                    startPlayer.dispose();
                    shipPlayer.dispose();
                    if (soundOn) {
                        lostPlayer.play();
                    }
                    running = false;
                    result.getChildren().addAll(result_bg, defeat, btnContinue);
                    root.getChildren().remove(close);
                    root.getChildren().add(result);
                    btnContinue.setOnMouseClicked(e -> {
                        try {
                            lostPlayer.dispose();
                            gc.Result(myBoard, enemyBoard, strResult, history);
                            new GameResult();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });
                }
            }
        });

        //Khu vực đặt tàu người chơi
        myBoard = new Board(false, sukien -> {
            if (running) {
                return;
            }
            Board.Cell cell = (Board.Cell) sukien.getSource();
            if (myBoard.setShip(new Ship(shipList[shipsToPlace - 1], sukien.getButton() == MouseButton.PRIMARY),
                    cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    gc.CreateShipForAI(enemyBoard, shipList); //Nếu người chơi đặt tàu xong thì đặt tàu cho AI và chơi
                    running = true;
                    if (soundOn) {
                        shipPlayer.stop();
                        startPlayer.play();
                    }
                }
            }
        });
        VBox Board1 = new VBox(30, me, myBoard);
        VBox Board2 = new VBox(30, enemy, enemyBoard);
        HBox Board = new HBox(100, Board1, Board2);

        Board.setAlignment(Pos.CENTER);
        VBox center = new VBox(30, top, Board);
        root.setCenter(center);

        gamePlayScene = new Scene(root);
        window.setScene(gamePlayScene);
    }
}
