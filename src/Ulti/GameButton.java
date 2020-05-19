package Ulti;

import static Main.BattleShip.soundOn;
import static Ulti.Entity.click_mp3;
import Ulti.PublicMethod;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameButton extends StackPane {
    
    private PublicMethod pm = new PublicMethod();
    public GameButton(String name, int weidth, int height) {
        Text text = new Text(name);
        text.setFont(Font.font("Calibri", FontWeight.BOLD, 25));
        text.setFill(Color.WHITE);

        Rectangle bg = new Rectangle(weidth, height);
        bg.setOpacity(0.6);
        bg.setFill(Color.BLACK);
        bg.setEffect(new GaussianBlur(3.5));

        setAlignment(Pos.CENTER);
        setRotate(-0.5);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            bg.setTranslateX(0);
            text.setTranslateX(0);
            bg.setFill(Color.WHITE);
            text.setFill(Color.BLACK);
        });

        setOnMouseExited(event -> {
            bg.setTranslateX(0);
            text.setTranslateX(0);
            bg.setFill(Color.BLACK);
            text.setFill(Color.WHITE);
        });

        DropShadow drop = new DropShadow(50, Color.WHITE);
        drop.setInput(new Glow());

        setOnMousePressed(event -> {
            MediaPlayer click;
            try {
                click = pm.addSound(click_mp3, false);
                if (soundOn) {
                    click.play();
                }
                setEffect(drop);
            } catch (IOException ex) {
                Logger.getLogger(GameButton.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        setOnMouseReleased(event -> setEffect(null));
    }
}
