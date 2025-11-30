package Ultimate;

import Entities.CharacterLoader;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GuyUltimate {
    GamePanel gp;
    KeyHandler keyH;

    public BufferedImage image, meth;

    //Ultimate data
    int ultimateDecrement;
    final int ultimateDecrementCondition = 2;
    int meatball;
    final int meatballSpawnCondition = 5;

    //Meth data
    static int maxLife = 120;
    static int basicCounter, basicCounter2;
    int x, y, x2, y2;
    int[] methHitbox = new int[4];
    int[] methHitbox2 = new int[4];
    public int methDAMAGE = 5;
    Rectangle MethHitbox = new Rectangle(0, 0, 48, 48);

    public GuyUltimate(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getImages();
    }

    public void getImages() {
        meth = getImage("basic_attack/Guy_Meth");
    }

    public void basicAttack(String player) {
        if(player.equals("player")) {
            Ultimate.playerInfo[0] = true; basicCounter = 0;
            x = gp.player.x; y = gp.player.y;
            methHitbox[0] = x; methHitbox[1] = x + MethHitbox.width; methHitbox[2] = y; methHitbox[3] = y + MethHitbox.height;}
        if(player.equals("player2")) {
            Ultimate.player2Info[0] = true; basicCounter2 = 0;
            x2 = gp.player2.x; y = gp.player2.y;
            methHitbox2[0] = x2; methHitbox2[1] = x2 + MethHitbox.width; methHitbox2[2] = y2; methHitbox2[3] = y2 + MethHitbox.height;}
    }

    public void ultimateAttack(String player) {
        if (player.equals("player")) {Ultimate.playerInfo[1] = true; ultimateDecrement = 0; gp.player.ultimateCounting = false;}
        if (player.equals("player2")) {Ultimate.player2Info[1] = true;}
    }

    public void update() {
        if(gp.player.Character.equals("Guy")) {gp.player.alive = true;}
        if(gp.player2.Character.equals("Guy")) {gp.player2.alive = true;}
        if (Ultimate.playerInfo[0]) {
            basicCounter++;
            if (Ultimate.player2Hitbox[1] >= methHitbox[0] && Ultimate.player2Hitbox[0] <= methHitbox[1] && Ultimate.player2Hitbox[3] >= methHitbox[2] && Ultimate.player2Hitbox[2] <= methHitbox[3]) {gp.player2.health -= methDAMAGE;}
            if (maxLife <= basicCounter) {Ultimate.playerInfo[0] = false; gp.player.basicCOUNTER = 0;}
        }
        if (Ultimate.playerInfo[1]) {
            ultimateDecrement++;
            if (ultimateDecrement >= ultimateDecrementCondition) {
                meatball++; ultimateDecrement = 0; gp.player.ultimateProgress--;
                if (meatball >= meatballSpawnCondition) {gp.projectiles.getCharacter("Guy", gp.player.x, gp.player.y, "right", "player");meatball = 0;}
                if (gp.player.ultimateProgress <= 0) {gp.player.ultimateCounting = true; Ultimate.playerInfo[1] = false;}}}
    }

    public void drawP1(Graphics2D g2) {
        if(Ultimate.playerInfo[0]) {g2.drawImage(meth, x, y, gp.TileSize, gp.TileSize, null);}
    }

    public void drawP2(Graphics2D g2) {}

    public BufferedImage getImage(String filePath) {
        try {image = ImageIO.read((CharacterLoader.class.getClassLoader().getResourceAsStream("player/Guy/" + filePath + ".png")));}
        catch (Exception e) {
            try {image = ImageIO.read(Objects.requireNonNull(CharacterLoader.class.getClassLoader().getResourceAsStream("menu/error/Missing_Tile.png"))); System.out.println("Error: " + filePath + " not found");} catch (
                    IOException ex) {throw new RuntimeException(ex);}}
        return image;
    }
}

