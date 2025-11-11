package Ultimate;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static Ultimate.Ultimate.SpriteNum;
import static java.lang.Math.*;

public class DuckUltimate {
    GamePanel gp;
    KeyHandler keyH;

    //Basic Information for player 1 and 2 if(applicable)
    int basicCounter, basicCounter2;
    boolean basicHit = false, basicHit2 = false;
    final int life = 30;
    Rectangle BasicHitbox = new Rectangle(16, 16, 16, 16);
    int[] basicHitbox = new int[4];
    int xOffset, xOffset2;
    int[] basicHitbox2 = new int[4];
    BufferedImage basicLeft, basicRight;
    final int DAMAGE = 5;

    //Ultimate Information for player 1 and 2 if(applicable)
    BufferedImage ultimate_1, ultimate_2, ultimate_3, ultimate_4, ultimate;
    BufferedImage image;
    int damage, maxYSpeed, maxYSpeed2;

    public DuckUltimate(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        //Basic && Ultimate Images
        getImages();
    }

    public void getImages() {
        basicLeft = getImage("basic_attack/Duck_LeftPunch");
        basicRight = getImage("basic_attack/Duck_RightPunch");
        ultimate_1 = getImage("ultimate/Duck_Ultimate1");
        ultimate_2 = getImage("ultimate/Duck_Ultimate2");
        ultimate_3 = getImage("ultimate/Duck_Ultimate3");
        ultimate_4 = getImage("ultimate/Duck_Ultimate4");
    }

    //Calculate damage based on distance + maxYSpeed
    public int damage(String player) {
        int distance = (int) sqrt(pow(gp.player.x - gp.player2.x, 2) + pow(gp.player.y - gp.player2.y, 2));
        if(player.equals("player")) {damage = (100 - distance) * abs(maxYSpeed)/10;}
        if(player.equals("player2")) {damage = (100 - distance) * abs(maxYSpeed2)/10;}
        if(damage <= 0)  {damage = 0;}
        return damage;
    }

    public void updateHitbox() {
        //Adjust where the Basic Hitbox is based on direction of the player
        if(gp.player.direction.equals("left")) {xOffset = -15;}
        if(gp.player.direction.equals("right")) {xOffset = 20;}
        if(gp.player2.direction.equals("left")) {xOffset2 = -15;}
        if(gp.player2.direction.equals("right")) {xOffset2 = 20;}
        //Basic Hitbox of Player 1
        basicHitbox[0] = xOffset + gp.player.x + BasicHitbox.x;
        basicHitbox[1] = xOffset + gp.player.x + BasicHitbox.x + BasicHitbox.width;
        basicHitbox[2] = gp.player.y + BasicHitbox.y;
        basicHitbox[3] = gp.player.y + BasicHitbox.y + BasicHitbox.height;
        //Basic Hitbox of Player 2
        basicHitbox2[0] = xOffset + gp.player2.x + BasicHitbox.x;
        basicHitbox2[1] = xOffset + gp.player2.x + BasicHitbox.x + BasicHitbox.width;
        basicHitbox2[2] = gp.player2.y + BasicHitbox.y;
        basicHitbox2[3] = gp.player2.y + BasicHitbox.y + BasicHitbox.height;
    }

    public void update() {
        updateHitbox();
        if(Ultimate.playerInfo[0]) {
            basicCounter++;
            if(!basicHit && Ultimate.player2Hitbox[0] <= basicHitbox[0] && Ultimate.player2Hitbox[1] >= basicHitbox[1] && Ultimate.player2Hitbox[2] <= basicHitbox[2] && Ultimate.player2Hitbox[3] >= basicHitbox[3]) {gp.player2.health -= DAMAGE; basicHit = true; gp.player.basicCOUNTER = 0;}
            //When to kill Punch
            if (life <= basicCounter) {gp.player.basicCOUNTER = 0; Ultimate.playerInfo[0] = false; basicCounter = 0; basicHit = false;}
        }
        if(Ultimate.playerInfo[1]) {
            //Update the max possible velocity of the player during ultimate
            if(gp.player.ySpeed <= maxYSpeed) {maxYSpeed = (int) gp.player.ySpeed;}
            if(gp.player.ySpeed == 0 || gp.player.touchingY) {Ultimate.playerInfo[1] = false; gp.player.ultimateProgress = 0; gp.player2.health -= damage("player");}
        }
        //Player 2
        if(Ultimate.player2Info[0]) {
            basicCounter2++;
            if(!basicHit2 && Ultimate.playerHitbox[0] <= basicHitbox2[0] && Ultimate.playerHitbox[1] >= basicHitbox2[1] && Ultimate.playerHitbox[2] <= basicHitbox2[2] && Ultimate.playerHitbox[3] >= basicHitbox2[3]) {gp.player.health -= DAMAGE; basicHit2 = true; gp.player2.basicCOUNTER = 0;}
            //When to kill Punch
            if (life <= basicCounter2) {gp.player2.basicCOUNTER = 0; Ultimate.player2Info[0] = false; basicCounter2 = 0; basicHit2 = false;}
        }
        if(Ultimate.player2Info[1]) {
            //Update the max possible velocity of the player during ultimate
            if(gp.player2.ySpeed <= maxYSpeed2) {maxYSpeed2 = (int) gp.player2.ySpeed;}
            if(gp.player2.ySpeed == 0 || gp.player2.touchingY) {Ultimate.player2Info[1] = false; gp.player2.ultimateProgress = 0; gp.player.health -= damage("player2");}
        }
    }

    public void basicAttack(String player) {
        if(player.equals("player")) {Ultimate.playerInfo[0] = true; basicHit = false;}
        if(player.equals("player2")) {Ultimate.player2Info[0] = true; basicHit2 = false;}
    }

    public void ultimateAttack(String player) {
        if(player.equals("player")) {
            Ultimate.playerInfo[1] = true; maxYSpeed = 0;
            //Ensures that the player cannot jump during ultimate and does not prematurely stop midair
            gp.player.ySpeed = -6; gp.player.jump = 2;
        }
        if(player.equals("player2")) {
            Ultimate.player2Info[1] = true; maxYSpeed2 = 0;
            //Ensures that the player cannot jump during ultimate and does not prematurely stop midair
            gp.player2.ySpeed = -6; gp.player2.jump = 2;
        }
    }

    public void drawP1(Graphics2D g2) {
        //Animation pictures
        if(SpriteNum % 4 == 0) {ultimate = ultimate_1;}
        if(SpriteNum % 4 == 1) {ultimate = ultimate_2;}
        if(SpriteNum % 4 == 2) {ultimate = ultimate_3;}
        if(SpriteNum % 4 == 3) {ultimate = ultimate_4;}
        if(Ultimate.playerInfo[0] && gp.player.direction.equals("left")){g2.drawImage(basicLeft, gp.player.x - 10, gp.player.y, gp.TileSize + 5, gp.TileSize + 5, null);}
        if(Ultimate.playerInfo[0] && gp.player.direction.equals("right")){g2.drawImage(basicRight, gp.player.x + 10, gp.player.y, gp.TileSize + 5, gp.TileSize + 5, null);}
        if(Ultimate.playerInfo[1]){g2.drawImage(ultimate, gp.player.x, gp.player.y, gp.TileSize + 10, gp.TileSize + 5, null);}
    }

    public void drawP2(Graphics2D g2) {
        if(Ultimate.player2Info[0] && gp.player2.direction.equals("left")){g2.drawImage(basicLeft, gp.player2.x - 10, gp.player2.y, gp.TileSize + 5, gp.TileSize + 5, null);}
        if(Ultimate.player2Info[0] && gp.player2.direction.equals("right")){g2.drawImage(basicRight, gp.player2.x + 10, gp.player2.y, gp.TileSize + 5, gp.TileSize + 5, null);}
        if(Ultimate.player2Info[1]){g2.drawImage(ultimate, gp.player2.x, gp.player2.y, gp.TileSize + 10, gp.TileSize + 5, null);}
    }

    public BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/Duck/" + filePath + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
