package Game;

import Model.Shot;
import static Main.BattleShip.*;
import Ulti.Entity;
import Ulti.GameButton;
import Ulti.PublicMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class GameResult extends PublicMethod implements Entity {

    private static String battleResult;
    private static int totalShots, hit, miss, totalScore;
    private static ArrayList<String> stringHistory = new ArrayList<>();
    MediaPlayer endingPlayer;

    public GameResult() throws IOException {
        Pane root = new Pane();
        // Background
        ImageView imgView = addBackground(gameresult_bg,1120,630);
        root.getChildren().addAll(imgView);
        //Hiển thị kết quả
        Label game_result = addLabel(GamePlay.strResult, 45);
        game_result.setTextFill(GamePlay.strResult.contains("WIN") ? Color.YELLOW : Color.RED);
        game_result.setTranslateX(480);
        game_result.setTranslateY(40);
        root.getChildren().add(game_result);

        //Sound
        endingPlayer = addSound(ending_mp3, false);
        if (soundOn) {
            endingPlayer.play();
        }
        //Danh sách các nước đi
        VBox vbox1 = new VBox(20);
        vbox1.setTranslateX(190);
        vbox1.setTranslateY(80);
        Label historylb = addLabel("Move", 28);
        historylb.setTextFill(Color.WHITE);
        historylb.setAlignment(Pos.CENTER);
        ListView myListView = new ListView();
         myListView.setPrefSize(400, 400);
        ListProperty<String> listProperty = new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList(stringHistory));
        myListView.itemsProperty().bind(listProperty);
        myListView.setCellFactory(cell -> {
            return new ListCell<String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item);
                        setTextFill(item.contains("hit") ? Color.GREEN : Color.RED);
                        setFont(Font.font(20));
                    }
                }
            };
        });
        vbox1.getChildren().addAll(historylb, myListView);
        root.getChildren().add(vbox1);

        //Bảng điểm
        GridPane gridPane = new GridPane();
        gridPane.setTranslateX(595);
        gridPane.setTranslateY(150);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(2);
        gridPane.setVgap(2);
        gridPane.setPickOnBounds(false);
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints();
            gridPane.getRowConstraints().add(row);
        }
        for (int i = 0; i < 2; i++) {
            ColumnConstraints col = new ColumnConstraints();
            gridPane.getColumnConstraints().add(col);
        }

        Label totalShotlb = addLabel("Total shot\t:", 28);
        totalShotlb.setPrefSize(250, 40);
        totalShotlb.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(totalShotlb, 0, 0);

        Label totalShotlb1 = addLabel(String.valueOf(totalShots), 28);
        totalShotlb1.setPrefSize(250, 40);
        totalShotlb1.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(totalShotlb1, 1, 0);

        Label hitlb = addLabel("Hit\t\t\t:", 28);
        hitlb.setPrefSize(250, 40);
        hitlb.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(hitlb, 0, 1);

        Label hitlb1 = addLabel(String.valueOf(hit), 28);
        hitlb1.setPrefSize(250, 40);
        hitlb1.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(hitlb1, 1, 1);

        Label misslb = addLabel("Miss\t\t:", 28);
        misslb.setPrefSize(250, 40);
        misslb.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(misslb, 0, 2);

        Label misslb1 = addLabel(String.valueOf(miss), 28);
        misslb1.setPrefSize(250, 40);
        misslb1.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(misslb1, 1, 2);

        Label totalScorelb = addLabel("Total score\t:", 28);
        totalScorelb.setPrefSize(250, 40);
        totalScorelb.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(totalScorelb, 0, 3);

        Label totalScorelb1 = addLabel(String.valueOf(totalScore), 28);
        totalScorelb1.setPrefSize(250, 40);
        totalScorelb1.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(totalScorelb1, 1, 3);

        //Khung nền xám
        Rectangle bg = new Rectangle(300, 400);
        bg.setTranslateX(595);
        bg.setTranslateY(132);
        bg.setFill(Color.GREY);
        bg.setOpacity(0.6);
        root.getChildren().addAll(bg, gridPane);

        //Nút Return
        GameButton btnReturn = new GameButton("RETURN TO MAIN MENU", 250, 40);
        btnReturn.setOnMouseClicked(sukien -> {
            try {
                endingPlayer.dispose();
                resume = false;
                new GameMenu();
            } catch (IOException ex) {
                Logger.getLogger(GameResult.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnReturn.setAlignment(Pos.CENTER);
        HBox close = new HBox(btnReturn);
        close.setAlignment(Pos.CENTER);
        close.setTranslateX(440);
        close.setTranslateY(570);
        root.getChildren().add(close);

        Scene scene = new Scene(root);
        window.setScene(scene);
    }

    //---Phần cần sửa---//
    //Thay thế bằng setPlayerData và đưa vào trong Player.java
    public static void transferStat(String result, int tShots, int nHit, int tScore) {
        battleResult = result;
        totalShots = tShots;
        hit = nHit;
        miss = tShots - nHit;
        totalScore = tScore;
    }
    
    //Hiển thị các bước đi ra ListView
    //Thay thế bằng setHistoryShot và đưa vào trong Shot.java
    public static void transferHistory(ArrayList<Shot> historyList) {
        stringHistory.clear();
        int numLoops = historyList.size();
        for (int i = 1; i <= numLoops; i++) {
            stringHistory.add(String.format("%3d. %s", i, historyList.get(i - 1).shotToString()));
        }
    }
}
