package Ulti;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.*;
import javafx.scene.paint.Color;

public class PublicMethod {

    public TextField addTextField(String str) throws IOException {
        TextField textfield = new TextField(str);
        textfield.setFont(Font.font(15));
        textfield.setPrefHeight(30);
        textfield.setPrefWidth(250);
        textfield.setAlignment(Pos.CENTER);
        return textfield;
    }

    public Text addText(String str, int size) {
        Text text = new Text(str);
        text.setFont(Font.font("Calibri", FontWeight.BOLD, size));
        text.setFill(Color.WHITE);
        return text;
    }

    public Label addLabel(String str, int size) {
        Label label = new Label(str);
        label.setFont(Font.font("Calibri", FontWeight.BOLD, size));
        label.setTextFill(Color.WHITE);
        return label;
    }

    public Image addIcon(String filename) throws IOException {
        InputStream icon = Files.newInputStream(Paths.get("img/" + filename));
        Image iconImg = new Image(icon);
        icon.close();
        return iconImg;
    }

    public ImageView addBackground(String filename, int width, int height) throws IOException {
        InputStream is = Files.newInputStream(Paths.get(("img/" + filename)));
        Image img = new Image(is);
        is.close();
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(width);
        imgView.setFitHeight(height);
        return imgView;
    }
    public MediaPlayer addSound(String filename, boolean loop) throws IOException {
        String sound = "sound/" + filename;
        Media sound_mp3 = new Media(new File(sound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound_mp3);
        if (loop) {
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        mediaPlayer.setVolume(0.5);
        return mediaPlayer;
    }

}
