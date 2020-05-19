package Main;

import Game.GameMenu;
import Ulti.PublicMethod;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BattleShip extends Application {

    public static Stage window; //Khai báo static để lưu giá trị chạy trong quá trình game, sử dụng import static Main.BattleShip.*; để lấy giá trị cố định
    public static Scene gameMenuScene; //Dùng cho chức năng resume
    public static Scene gamePlayScene;
    //public static Sound sound; //cần thay soundOn bằng class Sound
    public static boolean soundOn = true;
    //public static GameLevel level; //cần thay isEasy bằng class GameLevel
    public static boolean isEasy = true;
    public static boolean resume = false;
    
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        new GameMenu();
        
        window.setWidth(1120);
        window.setHeight(660);
        window.setResizable(true);
        window.setTitle("Battleship");
        window.setAlwaysOnTop(true);
        
        // Icon
        Image icon = new PublicMethod().addIcon("icon.jpg");
        window.getIcons().add(icon);
        window.show();
    }
}
