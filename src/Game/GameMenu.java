package Game;

import Model.Player;
import static Main.BattleShip.*;
import Ulti.Entity;
import Ulti.GameButton;
import Ulti.PublicMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
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
import javafx.scene.text.FontWeight;

public class GameMenu extends PublicMethod implements Entity {

    public static String playername;
    private ArrayList<Player> playerData = new ArrayList<>();
    private MediaPlayer openingPlayer;

    public GameMenu() throws IOException {
        Pane root = new Pane();
        ImageView imgView = addBackground(gamemenu_bg, 1120, 630);

        openingPlayer = addSound(opening_mp3, true);

        if (soundOn) {
            openingPlayer.play();
        }

        openingPlayer.setVolume(50);
        openingPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        Label title = addLabel("BATTLE SHIP", 65);
        title.setTranslateX(400);
        title.setTranslateY(60);
        title.setEffect(new DropShadow(30, Color.BLACK));

        //Nhập tên người chơi nếu ko thì mặc định Unknown
        TextField name = addTextField("Enter your name");
        name.setOnKeyReleased(sukien -> {
            playername = name.getText();
        });
        if (playername == null) {
            playername = name.getText();
            if (playername.equals("") || playername.equals("Enter your name")) {
                playername = "Unknown";
            }
        }

        VBox mainPanel = new VBox(10);        //Panel menu : chọn các tính năng
        VBox customPanel = new VBox(10);      //Panel custom : chọn chế độ chơi
        Pane scorePanel = new Pane();         //Panel score : hiển thị bảng điểm
        VBox settingPanel = new VBox(10);     //Panel setting : thiết lập âm thanh và cấu hình
        VBox soundPanel = new VBox(10);       //Panel sound : âm thanh
        VBox dimensionPanel = new VBox(10);   //Panel dimension : kích thước chương trình

        mainPanel.setTranslateX(440); // Tọa độ X
        mainPanel.setTranslateY(200); // Tọa độ Y

        customPanel.setTranslateX(440);
        customPanel.setTranslateY(200);

        scorePanel.setTranslateX(300);
        scorePanel.setTranslateY(150);

        settingPanel.setTranslateX(440);
        settingPanel.setTranslateY(230);

        soundPanel.setTranslateX(440);
        soundPanel.setTranslateY(230);

        dimensionPanel.setTranslateX(440);
        dimensionPanel.setTranslateY(230);

        ////////////////////////////////////////////////////////////////////
        //Menu -> Custom
        GameButton btnStart = new GameButton("START", 250, 40);
        btnStart.setOnMouseClicked(event -> {
            root.getChildren().add(customPanel);
            root.getChildren().remove(mainPanel);
        });
        GameButton btnResume = new GameButton("RESUME", 250, 40);
        btnResume.setOnMouseClicked(event -> {
            openingPlayer.dispose();
            window.setScene(gamePlayScene); //Sử dụng setScene thay cho new GamePlay để không xóa dữ liệu đang có và sử dụng được chức năng Resume
        });
        
        GameButton btnNewgame = new GameButton("NEW GAME", 250, 40);
        btnNewgame.setOnMouseClicked(event -> {
            root.getChildren().add(customPanel);
            root.getChildren().remove(mainPanel);
        });
        
        GameButton btnEasy = new GameButton("Easy", 250, 40);
        btnEasy.setOnMouseClicked(event -> {
            try {
                isEasy = true; //cần thay thế bằng getLevel để lấy chế độ game
                new GamePlay(); //new GamePlay thì tạo mới nên ko Resume được
                openingPlayer.dispose();
            } catch (IOException ex) {
                Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        GameButton btnHard = new GameButton("Hard", 250, 40);
        btnHard.setOnMouseClicked(event -> {
            try {
                isEasy = false; //cần thay thế bằng getLevel để lấy chế độ game
                new GamePlay();
                openingPlayer.dispose();
            } catch (IOException ex) {
                Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Custom -> Menu
        GameButton btnCustomBack = new GameButton("BACK", 250, 40);
        btnCustomBack.setOnMouseClicked(event -> {
            root.getChildren().add(mainPanel);
            root.getChildren().remove(customPanel);
        });

        ////////////////////////////////////////////////////////////////////
        //Menu -> High Score
        GameButton btnScore = new GameButton("HIGH SCORE", 250, 40);
        btnScore.setOnMouseClicked(event -> {
            root.getChildren().add(scorePanel);
            root.getChildren().remove(mainPanel);
        });

        VBox listVBox = new VBox(20);
        listVBox.setTranslateX(50);
        listVBox.setTranslateY(0);

        HBox listHBox = new HBox(120);
        Label ranklb = new Label("Rank");
        ranklb.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        ranklb.setTextFill(Color.WHITE);
        ranklb.setAlignment(Pos.CENTER);
        Label playerlb = new Label("Player");
        playerlb.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        playerlb.setTextFill(Color.WHITE);
        playerlb.setAlignment(Pos.CENTER);
        Label scorelb = new Label("Score");
        scorelb.setFont(Font.font("Verdana", FontWeight.BOLD, 22));
        scorelb.setTextFill(Color.WHITE);
        scorelb.setAlignment(Pos.CENTER);
        listHBox.getChildren().addAll(ranklb, playerlb, scorelb);

        GridPane scoreBoard = new GridPane();
        //Giới hạn số hàng
        for (int i = 0; i < 4; i++) {
            RowConstraints row = new RowConstraints();
            scoreBoard.getRowConstraints().add(row);
        }
        //Giới hạn số cột
        for (int i = 0; i < 3; i++) {
            ColumnConstraints col = new ColumnConstraints();
            scoreBoard.getColumnConstraints().add(col);
        }
        playerData.clear(); //xóa dữ liệu người chơi trước khi đọc file
        playerData = SaveData.Player_Data();
        int num_of_players = playerData.size();
        for (int i = 0; i < num_of_players; i++) {

            //Hiển thị rank
            Label rank = new Label(Integer.toString(i + 1) + ".");
            rank.setFont(new Font(30));
            rank.setTextFill(Color.WHITE);
            rank.setPrefSize(65, 40);
            rank.setAlignment(Pos.CENTER);
            scoreBoard.add(rank, 0, i);

            //Hiển thị tên người chơi
            Label nameLabel = new Label(playerData.get(i).getName());
            nameLabel.setFont(new Font(30));
            nameLabel.setTextFill(Color.WHITE);
            nameLabel.setPrefSize(320, 40);
            nameLabel.setAlignment(Pos.CENTER);
            scoreBoard.add(nameLabel, 1, i);

            //Hiển thị điểm số
            Label scoreLabel = new Label(String.valueOf(playerData.get(i).getScore()));
            scoreLabel.setFont(new Font(30));
            scoreLabel.setTextFill(Color.WHITE);
            scoreLabel.setPrefSize(60, 40);
            scoreLabel.setAlignment(Pos.CENTER);
            scoreBoard.add(scoreLabel, 2, i);
        }

        //Khung xám đặt sau bảng điểm
        Rectangle bg = new Rectangle(550, 265);
        bg.setFill(Color.GREY);
        bg.setOpacity(0.4);

        //Xóa dữ liệu người chơi
        GameButton btnReset = new GameButton("RESET", 250, 40);
        btnReset.setOnMouseClicked(event -> {
            scoreBoard.getChildren().clear();
            SaveData.ClearResultFile();
        });
        //High Score -> Menu
        GameButton btnScoreBack = new GameButton("BACK", 250, 40);
        btnScoreBack.setOnMouseClicked(event -> {
            root.getChildren().add(mainPanel);
            root.getChildren().remove(scorePanel);
        });

        HBox btnHBox = new HBox(40);
        btnHBox.setTranslateX(5);
        btnHBox.setTranslateY(280);
        btnHBox.getChildren().addAll(btnScoreBack, btnReset);
        listVBox.getChildren().addAll(listHBox, scoreBoard);

        ////////////////////////////////////////////////////////////////////
        //Menu -> Setting
        GameButton btnSetting = new GameButton("SETTING", 250, 40);
        btnSetting.setOnMouseClicked(event -> {
            root.getChildren().add(settingPanel);
            root.getChildren().remove(mainPanel);
        });

        //Setting -> Sound
        GameButton btnSound = new GameButton("SOUND", 250, 40);
        btnSound.setOnMouseClicked(event -> {
            root.getChildren().add(soundPanel);
            root.getChildren().remove(settingPanel);
        });

        GameButton btnOn = new GameButton("ON", 250, 40);
        btnOn.setOnMouseClicked(event -> {
            openingPlayer.setMute(false);
            if (soundOn) {
                openingPlayer.play();
            }
            soundOn = true;
        });

        GameButton btnOff = new GameButton("OFF", 250, 40);
        btnOff.setOnMouseClicked(event -> {
            openingPlayer.setMute(true);
            //sound.setSoundState(false);
            soundOn = false;
        });
        //Sound -> Setting
        GameButton btnSoundBack = new GameButton("BACK", 250, 40);
        btnSoundBack.setOnMouseClicked(event -> {
            root.getChildren().add(settingPanel);
            root.getChildren().remove(soundPanel);

        });

        //Setting -> Menu
        GameButton btnSettingBack = new GameButton("BACK", 250, 40);
        btnSettingBack.setOnMouseClicked(event -> {
            root.getChildren().add(mainPanel);
            root.getChildren().remove(settingPanel);
        });
        ////////////////////////////////////////////////////////////////////
        //Nút Exit
        GameButton btnExit = new GameButton("EXIT", 250, 40);
        btnExit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        if (!resume) { //Kiểm tra xem có đang resume hay ko
            mainPanel.getChildren().addAll(name, btnStart, btnScore, btnSetting, btnExit);
        } else {
            //Hiển thị nút Resume
            mainPanel.getChildren().addAll(name, btnResume, btnNewgame, btnScore, btnSetting, btnExit);
            name.setVisible(false);
        }
        customPanel.getChildren().addAll(btnEasy, btnHard, btnCustomBack);
        scorePanel.getChildren().addAll(bg, listVBox, btnHBox);
        settingPanel.getChildren().addAll(btnSound, btnSettingBack);
        soundPanel.getChildren().addAll(btnOn, btnOff, btnSoundBack);

        root.getChildren().addAll(imgView, title, mainPanel);
        gameMenuScene = new Scene(root);
        window.setScene(gameMenuScene);
    }
}
