package Objects;

import Entities.Entity;
import Main.GamePanel;

import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;

import Ultimate.Ultimate;

public class Projectiles extends Entity {
    public double g;

    int spriteCounter2;
    int spriteNum2 = 1;

    int ySpeedo;
    boolean hit;
    String user;

    boolean hitOnce;

    public static ArrayList<Object> attacks = new ArrayList<>();

    public Object[] fireball = new Object[8];
    public Object[] sprites = new Object[8];
    public Object[] rice_bag = new Object[8];
    public Object[] slash = new Object[8];
    public Object[] fart = new Object[8];
    public Object[] meatball = new Object[8];

    public Projectiles(GamePanel gp) {
        super(gp);
        this.gp = gp;
        Images();
    }

    public Rectangle getData(String type) {
        Rectangle r = new Rectangle();
        switch(type) {
            case "fireball":
                r.setSize(gp.TileSize, gp.TileSize); name = "fireball"; xSpeed = 10; ySpeedo = 0; maxLife = 80; life = maxLife; damage = 20; g = 0; hitOnce = true; break;
            case "slash":
                r.setSize(gp.TileSize, gp.TileSize); name = "slash"; xSpeed = 15; ySpeedo = 0; maxLife = 30; life = maxLife; damage = 10; g = 0; hitOnce = true; break;
            case "rice_bag":
                r.setSize(gp.TileSize, gp.TileSize); name = "rice_bag"; xSpeed = 10; ySpeedo = 0; maxLife = 60; life = maxLife; g = -0.5; hitOnce = true; break;
            case "fart":
                r.setSize(3 * gp.TileSize,3 * gp.TileSize); name = "fart"; xSpeed = 5; ySpeedo = 0; maxLife = 100; life = maxLife; g = 0; hitOnce = false; damage = 1; break;
            case "meatball":
                r.setSize(gp.TileSize, gp.TileSize); name = "meatball"; xSpeed = 0; ySpeedo = -5; maxLife = 100; life = maxLife; g = 0; damage = 10; hitOnce = true; break;
        }
        return r;
    }

    public void getCharacter(String character, int x, int y, String direction, String user) {
        switch(character){
            case"Duck":
                set(x, y, direction, true, user, "fireball"); gp.sound.playSFX("fireball");
                break;
            case"Rice":
                if(!Ultimate.playerInfo[1] && user.equals("player")) {set(x, y, direction, true, user, "rice_bag"); gp.sound.playSFX("rice_bag");}
                if(Ultimate.playerInfo[1] && user.equals("player")) {set(x, y, direction, true, user, "slash");}
                if(!Ultimate.player2Info[1] && user.equals("player2")) {set(x, y, direction, true, user, "rice_bag"); gp.sound.playSFX("rice_bag");}
                if(Ultimate.player2Info[1] && user.equals("player2")) {set(x, y, direction, true, user, "slash");}
                break;
            case "Zombie":
                if(direction.equals("right")) {set(x, y - 50, direction, true, user, "fart");}
                if(direction.equals("left")) {set(x - 60, y - 50, direction, true, user, "fart");}
                gp.sound.playSFX("fart");
                break;
            case "Guy":
                set(x, y, direction, true, user, "meatball");
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
        Rectangle newHitbox = getData(type);
        addAttack(name, x, y, xSpeed, ySpeedo, direction, user, damage, life, hit, g, maxLife, newHitbox, hitOnce);
    }

    public void update() {
        //Cycles through Array List to update
        for(int i = 0; i < attacks.size(); i++) {
            //Gets data from array list
            if(i % 14 == 0) {
                if(attacks.get(i + 5) == "right") {attacks.set(i + 1, (int)attacks.get(i + 1) + (int)attacks.get(i + 3));}
                if(attacks.get(i + 5) == "left") {attacks.set(i + 1, (int)attacks.get(i + 1) - (int)attacks.get(i + 3));}
                int[] hitbox = new int[4];
                hitbox[0] = (int) attacks.get(i + 1);
                hitbox[1] = (int) attacks.get(i + 1) + (int) ((Rectangle) attacks.get(i + 12)).getWidth();
                hitbox[2] = (int) attacks.get(i + 2);
                hitbox[3] = (int) attacks.get(i + 2) + (int) ((Rectangle) attacks.get(i + 12)).getHeight();
                if(attacks.get(i + 6) == "player") {
                    if(Ultimate.player2Hitbox[1] >= hitbox[0] && Ultimate.player2Hitbox[0] <= hitbox[1] && Ultimate.player2Hitbox[3] >= hitbox[2] && Ultimate.player2Hitbox[2] <= hitbox[3] && (!(boolean)attacks.get(i + 9) || !(boolean)attacks.get(i + 13))) {
                        gp.player2.health -=(int) attacks.get(i + 7);
                        attacks.set(i + 9, true);
                    }
                }
                if(attacks.get(i + 6) == "player2") {
                    if(Ultimate.playerHitbox[1] >= hitbox[0] && Ultimate.playerHitbox[0] <= hitbox[1] && Ultimate.playerHitbox[3] >= hitbox[2] && Ultimate.playerHitbox[2] <= hitbox[3] && (!(boolean)attacks.get(i + 9) || !(boolean)attacks.get(i + 13))) {
                        gp.player.health -= (int) attacks.get(i + 7);
                        attacks.set(i + 9, true);
                    }
                }
                attacks.set(i+4, (double)attacks.get(i + 4) + (double)attacks.get(i + 10));
                attacks.set(i+2, (int)attacks.get(i + 2) - (int) Math.round((double)attacks.get(i + 4)));
                if(attacks.get(i) == "rice_bag") {attacks.set(i+7, - 10 * ((int) Math.round((double) attacks.get(i + 4))));}

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
        for(int i = 0; i <= 3; i++) {meatball[i] = getImage("player/Guy/ultimate/Guy_Meatball" + (i + 1)); meatball[i + 4] = getImage("player/Guy/ultimate/Guy_Meatball" + (i + 1));}
    }

    public void getSprites(String object) {
        for(int i = 0; i <= 7; i++){sprites[i] = null;}
        switch(object) {
            case "rice_bag": System.arraycopy(rice_bag, 0, sprites, 0, 8); break;
            case "fireball": System.arraycopy(fireball, 0, sprites, 0, 8); break;
            case "slash": System.arraycopy(slash, 0, sprites, 0, 8); break;
            case "fart": System.arraycopy(fart, 0, sprites, 0, 8); break;
            case "meatball": System.arraycopy(meatball, 0, sprites, 0, 8); break;
        }
    }

public void draw(Graphics2D g2) {
    BufferedImage image = null;
    //Change picture based on direction
        for (int i = 0; i < attacks.size(); i++) {
            if(attacks.size() < 5){break;}
            if (i % 14 == 0) {
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
                int size = (int) ((Rectangle) attacks.get(i + 12)).getWidth();
                g2.drawImage(image, (int) attacks.get(i + 1), (int) attacks.get(i + 2), size, size, null);
            }
    }
}

    public void addAttack(String name, int x, int y, int xVelocity, double yVelocity, String direction, String user, int damage, int life, boolean hit, double g, int maxLife, Rectangle hitbox, boolean hitOnce) {
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
        attacks.add(hitbox);
        attacks.add(hitOnce);
    }

    //Removes projectile from Array list once dead
    public void remove(int i){
        //Tells both players when their projectile is dead
        if(attacks.get(i+6) == "player") {gp.player.alive = false;}
        if(attacks.get(i+6) == "player2") {gp.player2.alive = false;}
        for(int a = 1; a < 15; a++) {
            attacks.remove(i);
        }
    }
}