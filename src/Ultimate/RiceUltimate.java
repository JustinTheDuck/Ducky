package Ultimate;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.BufferOverflowException;
import java.util.Objects;

public class RiceUltimate {
    GamePanel gp;
    KeyHandler keyH;

    int ultimateDecrement = 0;
    int ultimateDecrement2 = 0;

    BufferedImage ultimateLeft, ultimateRight;
    BufferedImage image;

    public RiceUltimate(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getImages();
    }

    public void getImages() {
        ultimateLeft = getImage("Left");
        ultimateRight = getImage("Right");
    }

    public void update(){
        ultimateDecrement++;
        if(Ultimate.playerInfo[1] && ultimateDecrement >= 2) {
            gp.player.ultimateProgress--; ultimateDecrement = 0;
            if(gp.player.ultimateProgress == 0) {gp.player.ultimateCounting = true; Ultimate.playerInfo[1] = false;}
            if(keyH.spclPressed && !gp.player.alive) {gp.projectiles.getCharacter("Rice", gp.player.x, gp.player.y, gp.player.direction, "player");}
        }
        ultimateDecrement2++;
        if(Ultimate.player2Info[1] && ultimateDecrement2 >= 2) {
            gp.player2.ultimateProgress--; ultimateDecrement2 = 0;
            if(gp.player2.ultimateProgress == 0) {gp.player2.ultimateCounting = true; Ultimate.player2Info[1] = false;}
            if(keyH.spclPressed2 && !gp.player2.alive) {gp.projectiles.getCharacter("Rice", gp.player2.x, gp.player2.y, gp.player2.direction, "player2");}
        }
    }

    public void ultimateAttack(String player) {
        if(player.equals("player")){Ultimate.playerInfo[1] = true; gp.player.ultimateCounting = false;}
        if(player.equals("player2")){Ultimate.player2Info[1] = true; gp.player2.ultimateCounting = false;}
    }

    public void drawP1(Graphics2D g2) {
        if(Ultimate.playerInfo[1] && gp.player.direction.equals("right")){g2.drawImage(ultimateRight, gp.player.x, gp.player.y - 1, gp.TileSize, gp.TileSize, null);}
        if(Ultimate.playerInfo[1] && gp.player.direction.equals("left")){g2.drawImage(ultimateLeft, gp.player.x, gp.player.y - 1, gp.TileSize, gp.TileSize, null);}
    }

    public void drawP2(Graphics2D g2) {
        if(Ultimate.player2Info[1] && gp.player2.direction.equals("right")){g2.drawImage(ultimateRight, gp.player2.x, gp.player2.y - 1, gp.TileSize, gp.TileSize, null);}
        if(Ultimate.player2Info[1] && gp.player2.direction.equals("left")){g2.drawImage(ultimateLeft, gp.player2.x, gp.player2.y - 1, gp.TileSize, gp.TileSize, null);}
    }

    public BufferedImage getImage(String filePath) {
        try {image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/Rice/ultimate/UltimateOverlay_" + filePath + ".png")));}
        catch (IOException e) {e.printStackTrace();}
        return image;
    }
}