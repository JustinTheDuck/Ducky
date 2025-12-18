package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.net.URL;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/music/TheDuckSong.wav");

    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {}
    }

    public void stopMusic() {
        clip.stop();
    }

    public void playMusic(int i) {
        setFile(i);
        clip.start();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void playSE(int i) {
        setFile(i);
        clip.start();
    }

}
