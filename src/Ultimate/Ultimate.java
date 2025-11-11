package Ultimate;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;

public class Ultimate {
    GamePanel gp;
    KeyHandler keyH;

    DuckUltimate duckUltimate;
    RiceUltimate riceUltimate;
    ZombieUltimate zombieUltimate;
    GuyUltimate guyUltimate;

    public static boolean[] playerInfo = new boolean[2];
    public static boolean[] player2Info = new boolean[2];

    public static int[] playerHitbox = new int[4];
    public static int[] player2Hitbox = new int[4];

    public static int SpriteNum;
    public static int SpriteCounter;

    public Ultimate(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        duckUltimate = new DuckUltimate(gp, keyH);
        riceUltimate = new RiceUltimate(gp, keyH);
        zombieUltimate = new ZombieUltimate(gp, keyH);
        guyUltimate = new GuyUltimate(gp, keyH);
        defaultValues();
    }

    public void updateHitbox() {
        playerHitbox[0] = gp.player.x + gp.player.solidArea.x;
        playerHitbox[1] = gp.player.x + gp.player.solidArea.x + gp.player.solidArea.width;
        playerHitbox[2] = gp.player.y + gp.player.solidArea.y;
        playerHitbox[3] = gp.player.y + gp.player.solidArea.y + gp.player.solidArea.height;
        player2Hitbox[0] = gp.player2.x + gp.player2.solidArea.x;
        player2Hitbox[1] = gp.player2.x + gp.player2.solidArea.x + gp.player2.solidArea.width;
        player2Hitbox[2] = gp.player2.y + gp.player2.solidArea.y;
        player2Hitbox[3] = gp.player2.y + gp.player2.solidArea.y + gp.player2.solidArea.height;
    }

    public void defaultValues() {
        playerInfo[0] = false;
        playerInfo[1] = false;
    }

    public void update() {
        SpriteCounter++;
        if (SpriteCounter >= 15) {
            SpriteNum++;
        }
        updateHitbox();
        switch (gp.player.Character) {
            case "Duck":
                if (!playerInfo[0] && gp.player.basicCOOLDOWN <= gp.player.basicCOUNTER && keyH.basicPressed) {duckUltimate.basicAttack("player");}
                if (!playerInfo[1] && gp.player.ultimateProgress == 100 && keyH.downPressed) {duckUltimate.ultimateAttack("player");}
                break;
            case "Rice":
                if (!playerInfo[1] && gp.player.ultimateProgress == 100 && keyH.basicPressed) {riceUltimate.ultimateAttack("player");}
                break;
            case "Zombie":
                if (!playerInfo[0] && gp.player.basicCOOLDOWN <= gp.player.basicCOUNTER && keyH.basicPressed) {zombieUltimate.basicAttack("player");}
                if (!playerInfo[1] && gp.player.ultimateProgress == 100 && keyH.downPressed) {zombieUltimate.ultimateAttack("player");}
                break;
            case "Guy":
                if (!playerInfo[0] && gp.player.basicCOOLDOWN <= gp.player.basicCOUNTER && keyH.basicPressed) {guyUltimate.basicAttack("player");}
                break;
        }
        switch (gp.player2.Character) {
            case "Duck":
                if (!player2Info[0] && gp.player2.basicCOOLDOWN <= gp.player2.basicCOUNTER && keyH.basicPressed2) {duckUltimate.basicAttack("player2");}
                if (!player2Info[1] && gp.player2.ultimateProgress == 100 && keyH.downPressed2) {duckUltimate.ultimateAttack("player2");}
                break;
            case "Rice":
                if (!player2Info[1] && gp.player2.ultimateProgress == 100 && keyH.basicPressed2) {riceUltimate.ultimateAttack("player2");}
                break;
            case "Zombie":
                if (!player2Info[0] && gp.player2.basicCOOLDOWN <= gp.player2.basicCOUNTER && keyH.basicPressed2) {zombieUltimate.basicAttack("player2");}
                if (!player2Info[1] && gp.player2.ultimateProgress == 100 && keyH.downPressed2) {zombieUltimate.ultimateAttack("player2");}
                break;
            case "Guy":
                if (!player2Info[0] && gp.player2.basicCOOLDOWN <= gp.player2.basicCOUNTER && keyH.basicPressed2) {guyUltimate.basicAttack("player2");}
                if (!player2Info[1] && gp.player2.ultimateProgress == 100 && keyH.downPressed) {guyUltimate.ultimateAttack("player2");}
                break;
        }
        if (gp.player.Character.equals("Duck") || gp.player2.Character.equals("Duck")) {duckUltimate.update();}
        if (gp.player.Character.equals("Rice") || gp.player2.Character.equals("Rice")) {riceUltimate.update();}
        if (gp.player.Character.equals("Zombie") || gp.player2.Character.equals("Zombie")) {zombieUltimate.update();}
        if (gp.player.Character.equals("Guy") || gp.player2.Character.equals("Guy")) {guyUltimate.update();}
    }

    public void drawP1(Graphics2D g2) {
        if (gp.player.Character.equals("Duck")) {duckUltimate.drawP1(g2);}
        if (gp.player.Character.equals("Rice")) {riceUltimate.drawP1(g2);}
        if (gp.player.Character.equals("Zombie")) {zombieUltimate.drawP1(g2);}
        if (gp.player.Character.equals("Guy")) {guyUltimate.drawP1(g2);}
    }

    public void drawP2(Graphics2D g2) {
        if (gp.player2.Character.equals("Duck")) {duckUltimate.drawP2(g2);}
        if (gp.player2.Character.equals("Rice")) {riceUltimate.drawP2(g2);}
        if (gp.player2.Character.equals("Zombie")) {zombieUltimate.drawP2(g2);}
        if (gp.player2.Character.equals("Guy")) {guyUltimate.drawP2(g2);}
    }
}