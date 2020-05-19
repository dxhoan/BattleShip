/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

    private boolean soundOn = true;

    public boolean getSoundState() {
        return soundOn;
    }

    public void setSoundState(boolean soundOn) {
        this.soundOn = soundOn;
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
