package Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;


public class Sound {

    Clip music, sfx;
    URL[] musicURL = new URL[30];
    URL[] sfxURL = new URL[30];

    URL sound;

    int tracks = 0;

    public Sound() {
        musicURL[0] = getSound("music/DuckSong");
        musicURL[1] = getSound("music/ChickenDanceSong");

        sfxURL[0] = getSound("sfx/fireball_launch");
        sfxURL[1] = getSound("sfx/bag_throw");
        sfxURL[2] = getSound("sfx/fart");
        sfxURL[3] = getSound("sfx/meth");
        for (URL url : musicURL) {if (url != null) {tracks++;}}
    }

    public void setFile(int i, String type) {
        if(type.equals("music")) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(musicURL[i]);
                music = AudioSystem.getClip();
                music.open(ais);
            } catch (Exception e) {System.out.println("File not found");}
        }
        if(type.equals("sfx")) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(sfxURL[i]);
                sfx = AudioSystem.getClip();
                sfx.open(ais);
            } catch (Exception e) {System.out.println("File not found");}
        }
    }
    
    public void stopMusic() {
        music.stop();
    }

    public void playMusic() {
        int i = (int) (Math.random() * tracks);
        setFile(i, "music");
        music.start();
    }

    public void playSFX(String reason) {
        int i = 5;
        if(reason.equals("fireball")) {i = 0;}
        if(reason.equals("rice_bag")) {i = 1;}
        if(reason.equals("fart")) {i = 2;}
        if(reason.equals("meth")) {i = 3;}
        setFile(i, "sfx");
        sfx.start();
    }

    public URL getSound(String filepath) {
        sound = getClass().getResource("/sounds/" + filepath + ".wav");
        return sound;
    }
}
