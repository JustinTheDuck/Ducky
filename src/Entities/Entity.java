package Entities;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    public GamePanel gp;
    //Entity Data
    public int x, y;
    public int xSpeed;
    public double ySpeed;
    public BufferedImage right1, right2, right3, right4, left1, left2, left3, left4, upRight, upLeft, downRight, downLeft, image;
    public String direction;
    public String upDown;
    public boolean touchingX;
    public boolean touchingY;
    public int jump;
    public int fall;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int health, maxHealth;
    public String Character;
    String user;
    BufferedImage[] characterImages = new BufferedImage[12];

    //Ultimate data
    public int ultimateProgress;
    public int ultimateDELAY;
    public int ultimateNum = 0;
    public boolean ultimateCounting;

    //Basic Attack data
    public int basicCOOLDOWN;
    public int basicCOUNTER;

    //Constants
    public final double gravity = 0.5;

    //animations
    public int spriteCounter = 0;
    public int spriteNum = 1;

    //Projectile data
    public String name;
    public int life, maxLife;
    public int damage;
    public boolean alive;


    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}


