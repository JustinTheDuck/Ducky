package Objects;

import Entities.Entity;
import Main.GamePanel;

import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;

import Ultimate.Ultimate;

public class Projectiles extends Entity {
    //For Player1 hitbox
    public int xRightPlayerHitbox, xLeftPlayerHitbox, yTopPlayerHitbox, yBottomPlayerHitbox;
    //For Player2 hitbox
    public int xRightPlayer2Hitbox, xLeftPlayer2Hitbox, yTopPlayer2Hitbox, yBottomPlayer2Hitbox;

    public double g;

    int spriteCounter2;
    int spriteNum2 = 1;

    int ySpeedo;
    boolean hit;
    String user;

    int size;

    public static ArrayList<Object> attacks = new ArrayList<>();

    Object[] fireball = new Object[8];
    Object[] sprites = new Object[8];
    Object[] rice_bag = new Object[8];
    Object[] slash = new Object[8];
    Object[] fart = new Object[8];

    public Projectiles(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Images();
    }

    public void getData(String type) {
        switch(type) {
            case "fireball":
                name = "fireball"; xSpeed = 10; ySpeedo = 0; maxLife = 80; life = maxLife; damage = 20; g = 0; break;
            case "slash":
                name = "slash"; xSpeed = 15; ySpeedo = 0; maxLife = 30; life = maxLife; damage = 10; g = 0;
                break;
            case "rice_bag":
                name = "rice_bag"; xSpeed = 10; ySpeedo = 0; maxLife = 60; life = maxLife; g = -0.5;
                break;
            case "fart":
                name = "fart"; xSpeed = 5; ySpeedo = 0; maxLife = 100; life = maxLife; g = 0; damage = 0;
                break;
        }
    }

    public void getCharacter(String character, int x, int y, String direction, String user) {
        switch(character){
            case"Duck":
                set(x, y, direction, true, user, "fireball");
                break;
            case"Rice":
                if(!Ultimate.playerInfo[1] && user.equals("player")) {set(x, y, direction, true, user, "rice_bag");}
                if(Ultimate.playerInfo[1] && user.equals("player")) {set(x, y, direction, true, user, "slash");}
                if(!Ultimate.player2Info[1] && user.equals("player2")) {set(x, y, direction, true, user, "rice_bag");}
                if(Ultimate.player2Info[1] && user.equals("player2")) {set(x, y, direction, true, user, "slash");}
                break;
            case "Zombie":
                if(user.equals("player") && gp.player.direction.equals("right")) {set(x, y - 50, direction, true, user, "fart");}
                if(user.equals("player") && gp.player.direction.equals("left")) {set(x - 60, y - 50, direction, true, user, "fart");}
                if(user.equals("player2") && gp.player2.direction.equals("right")) {set(x, y - 50, direction, true, user, "fart");}
                if(user.equals("player2") && gp.player2.direction.equals("left")) {set(x - 60, y - 50, direction, true, user, "fart");}
                break;
        }
        if(user.equals("player")) {gp.player.alive = true;}
        if(user.equals("player2")) {gp.player2.alive = true;}
    }

    public void set(int x, int y, String direction, boolean alive, String user, String type) {
        this.x = x + 24;
        this.y = y;
        this.direction = direction;
        this.alive = alive;
        this.life = maxLife;
        this.hit = false;
        this.user = user;
        getData(type);
        addAttack(name, x, y, xSpeed, ySpeedo, direction, user, damage, life, hit, g, maxLife);
    }

    public void updatePlayer2Hitbox() {
        xRightPlayer2Hitbox = gp.player2.x + gp.player2.solidArea.x + gp.player2.solidArea.width;
        xLeftPlayer2Hitbox = gp.player2.x + gp.player2.solidArea.x;
        yTopPlayer2Hitbox = gp.player2.y + gp.player2.solidArea.y;
        yBottomPlayer2Hitbox = gp.player2.y + gp.player2.solidArea.y + gp.player2.solidArea.height;
    }

    public void updatePlayerHitbox() {
        xRightPlayerHitbox = gp.player.x + gp.player.solidArea.x + gp.player.solidArea.width;
        xLeftPlayerHitbox = gp.player.x + gp.player.solidArea.x;
        yTopPlayerHitbox = gp.player.y + gp.player.solidArea.y;
        yBottomPlayerHitbox = gp.player.y + gp.player.solidArea.y + gp.player.solidArea.height;
    }

    public void update() {
        //Cycles through Array List to update
        for(int i = 0; i < attacks.size(); i++) {
            //Gets data from array list
            if(i % 12 == 0) {
                if(attacks.get(i + 5) == "right") {attacks.set(i + 1, (int)attacks.get(i + 1) + (int)attacks.get(i + 3));}
                if(attacks.get(i + 5) == "left") {attacks.set(i + 1, (int)attacks.get(i + 1) - (int)attacks.get(i + 3));}
                if(attacks.get(i + 6) == "player") {
                    updatePlayer2Hitbox();
                    if(xLeftPlayer2Hitbox <= (int)attacks.get(i + 1) + 24 && xRightPlayer2Hitbox >= (int)attacks.get(i + 1) + 24 && yTopPlayer2Hitbox <= (int)attacks.get(i + 2) + 24 && yBottomPlayer2Hitbox >= (int)attacks.get(i + 2) + 24 && !(boolean)attacks.get(i + 9)) {
                        gp.player2.health -= 10 * (int) attacks.get(i + 7);
                        attacks.set(i + 9, true);
                    }
                }
                if(attacks.get(i + 6) == "player2") {
                    updatePlayerHitbox();
                    if(xLeftPlayerHitbox <= (int)attacks.get(i + 1) && xRightPlayerHitbox >= (int)attacks.get(i + 1) && yTopPlayerHitbox <= (int)attacks.get(i + 2) && yBottomPlayerHitbox >= (int)attacks.get(i + 2) && !(boolean)attacks.get(i + 9)) {
                        gp.player.health -= 10 * (int) attacks.get(i + 7);
                        attacks.set(i + 9, true);
                    }
                }
                attacks.set(i+4, (double)attacks.get(i + 4) + (double)attacks.get(i + 10));
                attacks.set(i+2, (int)attacks.get(i + 2) - (int) Math.round((double)attacks.get(i + 4)));
                if(attacks.get(i) == "rice_bag") {attacks.set(i+7, -((int) Math.round((double) attacks.get(i + 4))));}

                if(attacks.get(i).equals("fart")) {attacks.set(i + 9, false);}

                //Update life of projectile
                attacks.set(i+8, (int)attacks.get(i+8) - 1);
                if((int)attacks.get(i+8) <= 0) {remove(i);}
            }

            spriteCounter2++;
            if(spriteCounter2 > 10){
                spriteNum2++;
                if(spriteNum2 == 8) {spriteNum2 = 1;}
                spriteCounter2 = 0;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {spriteNum = 2;}
                else if (spriteNum == 2) {spriteNum = 3;}
                else if (spriteNum == 3) {spriteNum = 4;}
                else if (spriteNum == 4) {spriteNum = 1;}
                spriteCounter = 0;
            }
        }
    }
    //Loads Images
    public void Images() {
        for (int i = 0; i <= 3; i++) {
            fireball[i] = getImage("projectiles/fireball/Fireball_Right" + (i + 1));
            fireball[i + 4] = getImage("projectiles/fireball/Fireball_Left" + (i + 1));
        }
        for(int i = 0; i <= 7; i++) {rice_bag[i] = getImage("projectiles/rice_bag/RiceBag_" + (i + 1));}
        for (int i = 0; i <= 3; i++) {
            slash[i] = getImage("projectiles/slash/Slash_Right" + (i + 1));
            slash[i + 4] = getImage("projectiles/slash/Slash_Left" + (i + 1));
        }
        for(int i = 0; i <= 7; i++) {fart[i] = getImage("projectiles/fart/Fart");}
    }

    public void getSprites(String object) {
        for(int i = 0; i <= 7; i++){
            sprites[i] = null;
        }
        switch(object) {
            case "rice_bag":
                System.arraycopy(rice_bag, 0, sprites, 0, 8);
                break;
            case "fireball":
                System.arraycopy(fireball, 0, sprites, 0, 8);
                break;
            case "slash":
                System.arraycopy(slash, 0, sprites, 0, 8);
            case "fart":
                System.arraycopy(fart, 0, sprites, 0, 8);
        }
    }

public void draw(Graphics2D g2) {
    BufferedImage image = null;
    //Change picture based on direction
        for (int i = 0; i < attacks.size(); i++) {
            if(attacks.size() < 5){break;}
            if (i % 12 == 0) {
                //Gets the images required for projectile named i
                getSprites((String) attacks.get(i));
                if(attacks.get(i) == "rice_bag"){
                    if (spriteNum == 1) {image = (BufferedImage) sprites[0];}
                    if (spriteNum == 2) {image = (BufferedImage) sprites[1];}
                    if (spriteNum == 3) {image = (BufferedImage) sprites[2];}
                    if (spriteNum == 4) {image = (BufferedImage) sprites[3];}
                    if (spriteNum == 5) {image = (BufferedImage) sprites[4];}
                    if (spriteNum == 6) {image = (BufferedImage) sprites[5];}
                    if (spriteNum == 7) {image = (BufferedImage) sprites[6];}
                    if (spriteNum == 8) {image = (BufferedImage) sprites[7];}
                }
                else {
                    switch ((String) attacks.get(i + 5)) {
                        case "right":
                            if (spriteNum == 1) {image = (BufferedImage) sprites[0];}
                            if (spriteNum == 2) {image = (BufferedImage) sprites[1];}
                            if (spriteNum == 3) {image = (BufferedImage) sprites[2];}
                            if (spriteNum == 4) {image = (BufferedImage) sprites[3];}
                            break;
                        case "left":
                            if (spriteNum == 1) {image = (BufferedImage) sprites[4];}
                            if (spriteNum == 2) {image = (BufferedImage) sprites[5];}
                            if (spriteNum == 3) {image = (BufferedImage) sprites[6];}
                            if (spriteNum == 4) {image = (BufferedImage) sprites[7];}
                            break;
                    }
                }
                if(attacks.get(i).equals("fart")) {size = gp.TileSize * 3;}
                else{size = gp.TileSize;}
                g2.drawImage(image, (int) attacks.get(i + 1), (int) attacks.get(i + 2), size, size, null);
            }
    }
}

    public void addAttack(String name, int x, int y, int xVelocity, double yVelocity, String direction, String user, int damage, int life, boolean hit, double g, int maxLife) {
        attacks.add(name);
        attacks.add(x);
        attacks.add(y);
        attacks.add(xVelocity);
        attacks.add(yVelocity);
        attacks.add(direction);
        attacks.add(user);
        attacks.add(damage);
        attacks.add(life);
        attacks.add(hit);
        attacks.add(g);
        attacks.add(maxLife);
    }

    //Removes projectile from Array list once dead
    public void remove(int i){
        //Tells both players when their projectile is dead
        if(attacks.get(i+6) == "player") {gp.player.alive = false;}
        if(attacks.get(i+6) == "player2") {gp.player2.alive = false;}
        for(int a = 1; a < 13; a++) {
            attacks.remove(i);
        }
    }
}