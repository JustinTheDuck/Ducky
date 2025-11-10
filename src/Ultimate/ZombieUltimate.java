package Ultimate;

import Main.GamePanel;
import Main.KeyHandler;
import Objects.Projectiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class ZombieUltimate {
    GamePanel gp;
    KeyHandler keyH;

    BufferedImage image;
    BufferedImage basicLeft, basicRight, basicLeftJump, basicLeftDown, basicRightJump, basicRightDown;
    BufferedImage ultimate;

    //Basic
    boolean basicHit = false;
    boolean basicHit2 = false;
    int basicCounter, basicCounter2;
    final int life = 30;
    Rectangle BasicHitbox = new Rectangle(16, 16, 16, 16);
    int[] basicHitbox = new int[4];
    int[] basicHitbox2 = new int[4];

    //Fart
    Rectangle FartHitbox = new Rectangle(0, 0, 3 * 48, 3 * 48);
    int[] fartHitbox = new int[4];
    int[] fartHitbox2 = new int[4];
    final int DAMAGE = 5;

    //Ultimate
    int bottomRow, leftColumn, rightColumn;
    int bottomRow2, leftColumn2, rightColumn2;
    Rectangle AppleHitbox = new Rectangle(0, 0, 48, 48);
    //X and Y Locations of the apple
    int ultX, ultY;
    int ultX2, ultY2;
    //YSpeed (not gravity affected because I'm lazy)
    int ySpeed = 6;
    //Tiles the apple is colliding with
    int tileNum1, tileNum2;
    //How much poison affects the player
    int PoisonDAMAGE = 0, PoisonDAMAGE2 = 0;

    public ZombieUltimate(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        //Basic && Ultimate Images
        getImages();
    }

    public void updateHitbox() {
        //Basic Hitbox of Player 1
        basicHitbox[0] = gp.player.x + BasicHitbox.x;
        basicHitbox[1] = gp.player.x + BasicHitbox.x + BasicHitbox.width;
        basicHitbox[2] = gp.player.y + BasicHitbox.y;
        basicHitbox[3] = gp.player.y + BasicHitbox.y + BasicHitbox.height;
        //Basic Hitbox of Player 2
        basicHitbox2[0] = gp.player2.x + BasicHitbox.x;
        basicHitbox2[1] = gp.player2.x + BasicHitbox.x + BasicHitbox.width;
        basicHitbox2[2] = gp.player2.y + BasicHitbox.y;
        basicHitbox2[3] = gp.player2.y + BasicHitbox.y + BasicHitbox.height;
        //Fart Hitbox (if player inside of it take damage)
        for(int i = 0; i < Projectiles.attacks.size(); i++) {
            if(Projectiles.attacks.get(i).equals("fart") && Projectiles.attacks.get(i + 6).equals("player")) {
                fartHitbox[0] = (int) Projectiles.attacks.get(i + 1);
                fartHitbox[1] = (int) Projectiles.attacks.get(i + 1) + FartHitbox.width;
                fartHitbox[2] = (int) Projectiles.attacks.get(i + 2);
                fartHitbox[3] = (int) Projectiles.attacks.get(i + 2) + FartHitbox.height;
            }
            if(Projectiles.attacks.get(i).equals("fart") && Projectiles.attacks.get(i + 6).equals("player2")) {
                fartHitbox2[0] = (int) Projectiles.attacks.get(i + 1);
                fartHitbox2[1] = (int) Projectiles.attacks.get(i + 1) + FartHitbox.width;
                fartHitbox2[2] = (int) Projectiles.attacks.get(i + 2);
                fartHitbox2[3] = (int) Projectiles.attacks.get(i + 2) + FartHitbox.height;
            }
        }
        //Tile locations of the apple for player 1
        leftColumn = ultX/gp.TileSize;
        rightColumn = (ultX + AppleHitbox.width)/gp.TileSize;
        bottomRow = (ultY + ySpeed)/gp.TileSize;
        //Tile locations of the apple for player 2
        leftColumn2 = ultX2/gp.TileSize;
        rightColumn2 = (ultX2 + AppleHitbox.width)/gp.TileSize;
        bottomRow2 = (ultY2 + 2 * ySpeed)/gp.TileSize;
    }

    public void update() {
        updateHitbox();
        //Player 1 Punch
        if(Ultimate.playerInfo[0] && gp.player.Character.equals("Zombie")) {
            basicCounter++;
            if(!basicHit && Ultimate.player2Hitbox[1] >= basicHitbox[0] && Ultimate.player2Hitbox[0] <= basicHitbox[1] && Ultimate.player2Hitbox[3] >= basicHitbox[2] && Ultimate.player2Hitbox[2] <= basicHitbox[3]) {gp.player2.health -= 50; basicHit = true;}
            //When to kill Punch
            if (life <= basicCounter) {gp.player.basicCOUNTER = 0; Ultimate.playerInfo[0] = false; basicCounter = 0; basicHit = false;}
        }
        //Player 2 Punch
        if(Ultimate.player2Info[0] && gp.player2.Character.equals("Zombie")) {
            basicCounter2++;
            if(!basicHit2 && Ultimate.playerHitbox[1] >= basicHitbox2[0] && Ultimate.playerHitbox[0] <= basicHitbox2[1] && Ultimate.playerHitbox[3] >= basicHitbox2[2] && Ultimate.playerHitbox[2] <= basicHitbox2[3]) {gp.player.health -= 50; basicHit2 = true;}
            //When to kill Punch
            if (life <= basicCounter2) {gp.player2.basicCOUNTER = 0; Ultimate.player2Info[0] = false; basicCounter2 = 0; basicHit2 = false;}
        }
        //Check it players collide with the fart hitbox
        if(Ultimate.player2Hitbox[1] >= fartHitbox[0] && Ultimate.player2Hitbox[0] <= fartHitbox[1] && Ultimate.player2Hitbox[3] >= fartHitbox[2] && Ultimate.player2Hitbox[2] <= fartHitbox[3]) {gp.player2.health -= DAMAGE;}
        if(Ultimate.playerHitbox[1] >= fartHitbox2[0] && Ultimate.playerHitbox[0] <= fartHitbox2[1] && Ultimate.playerHitbox[3] >= fartHitbox2[2] && Ultimate.playerHitbox[2] <= fartHitbox2[3]) {gp.player.health -= DAMAGE;}
        //Apple data/When to calculate damage
        if(Ultimate.playerInfo[1]) {
            ultY += ySpeed;
            tileNum1 = gp.tileM.mapTileNum[leftColumn][bottomRow]; tileNum2 = gp.tileM.mapTileNum[rightColumn][bottomRow];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {Ultimate.playerInfo[1] = false; calculateDamage("player"); PoisonDAMAGE++;}
        }
        if(Ultimate.player2Info[1]) {
            ultY2 += ySpeed;
            tileNum1 = gp.tileM.mapTileNum[leftColumn2][bottomRow2]; tileNum2 = gp.tileM.mapTileNum[rightColumn2][bottomRow2];
            if(gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {Ultimate.player2Info[1] = false; calculateDamage("player2"); PoisonDAMAGE2++;}
        }
        gp.player2.health -= PoisonDAMAGE;
        gp.player.health -= PoisonDAMAGE2;
    }

    public void getImages() {
        basicLeft = getImage("basic_attack/Zombie_PunchLeft");
        basicLeftDown = getImage("basic_attack/Zombie_PunchLeftDown");
        basicLeftJump = getImage("basic_attack/Zombie_PunchLeftJump");
        basicRight = getImage("basic_attack/Zombie_PunchRight");
        basicRightDown = getImage("basic_attack/Zombie_PunchRightDown");
        basicRightJump = getImage("basic_attack/Zombie_PunchRightJump");
        ultimate = getImage("ultimate/Apple");
    }

    public void calculateDamage(String player) {
        int distance = (int) sqrt(pow(ultX - gp.player2.x, 2) + pow(ultY - gp.player2.y, 2));
        int distance2 = (int) sqrt(pow(ultX2 - gp.player.x, 2) + pow(ultY2 - gp.player.y, 2));
        int damage = 100 - distance;
        int damage2 = 100 - distance2;
        if(damage > 0 && player.equals("player")) {gp.player2.health -= damage;}
        if(damage2 > 0 && player.equals("player2")) {gp.player.health -= damage;}
    }

    public void basicAttack(String player) {
        if(player.equals("player")) {Ultimate.playerInfo[0] = true; basicHit = false;}
        if(player.equals("player2")) {Ultimate.player2Info[0] = true; basicHit2 = false;}
    }

    public void ultimateAttack(String player) {
        if(player.equals("player")) {Ultimate.playerInfo[1] = true; ultX = gp.player.x; ultY = 0; gp.player.ultimateProgress = 0;}
        if(player.equals("player2")) {Ultimate.player2Info[1] = true; ultX2 = gp.player2.x; ultY2 = 0; gp.player2.ultimateProgress = 0;}
    }

    public void drawP1(Graphics2D g2) {
        if(Ultimate.playerInfo[0]) {
            if(gp.player.direction.equals("left") && gp.player.upDown.equals("up")) {g2.drawImage(basicLeftJump, gp.player.x, gp.player.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player.direction.equals("left") && gp.player.upDown.equals("null")) {g2.drawImage(basicLeft, gp.player.x, gp.player.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player.direction.equals("left") && gp.player.upDown.equals("down")) {g2.drawImage(basicLeftDown, gp.player.x, gp.player.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player.direction.equals("right") && gp.player.upDown.equals("up")) {g2.drawImage(basicRightJump, gp.player.x, gp.player.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player.direction.equals("right") && gp.player.upDown.equals("null")) {g2.drawImage(basicRight, gp.player.x, gp.player.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player.direction.equals("right") && gp.player.upDown.equals("down")) {g2.drawImage(basicRightDown, gp.player.x, gp.player.y, gp.TileSize, gp.TileSize, null);}
        }
        if(Ultimate.playerInfo[1]) {g2.drawImage(ultimate, ultX, ultY, gp.TileSize, gp.TileSize, null);}
    }

    public void drawP2(Graphics2D g2) {
        if(Ultimate.player2Info[0]) {
            if(gp.player2.direction.equals("left") && gp.player2.upDown.equals("up")) {g2.drawImage(basicLeftJump, gp.player2.x, gp.player2.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player2.direction.equals("left") && gp.player2.upDown.equals("null")) {g2.drawImage(basicLeft, gp.player2.x, gp.player2.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player2.direction.equals("left") && gp.player2.upDown.equals("down")) {g2.drawImage(basicLeftDown, gp.player2.x, gp.player2.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player2.direction.equals("right") && gp.player2.upDown.equals("up")) {g2.drawImage(basicRightJump, gp.player2.x, gp.player2.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player2.direction.equals("right") && gp.player2.upDown.equals("null")) {g2.drawImage(basicRight, gp.player2.x, gp.player2.y, gp.TileSize, gp.TileSize, null);}
            if(gp.player2.direction.equals("right") && gp.player2.upDown.equals("down")) {g2.drawImage(basicRightDown, gp.player2.x, gp.player2.y, gp.TileSize, gp.TileSize, null);}
        }
        if(Ultimate.player2Info[1]) {g2.drawImage(ultimate, ultX2, ultY2, gp.TileSize, gp.TileSize, null);}
    }

    public BufferedImage getImage(String filePath) {
        try {image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("player/Zombie/" + filePath + ".png")));}
        catch (IOException e) {e.printStackTrace();}
        return image;
    }
}