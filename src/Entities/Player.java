package Entities;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity{
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        //Calling superclass of constructor
        super(gp);

        this.keyH = keyH;
        //Hitbox Data
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 0;
        solidArea.width = 32;
        solidArea.height = 32;

        ultimateProgress = 0;

        setDefaultValues();
        getPlayerImages();
    }

    public void setDefaultValues(){
        //Get default starting values for character
        Character = "Zombie";
        user = "player";
        x = 100;
        y = 100;
        direction = "right";
        upDown = "null";
        touchingX = false;
        touchingY = false;
        xSpeed = CharacterLoader.getCharacterSpeed(Character);
        maxHealth = CharacterLoader.getCharacterMaxHealth(Character);
        ultimateDELAY = CharacterLoader.getCharacterUltimateDelay(Character);
        basicCOOLDOWN = CharacterLoader.getBasicCOODOWN(Character);
        health = maxHealth;
        ultimateCounting = true;
    }

    public void getPlayerImages() {
        characterImages = CharacterLoader.getPlayerImages(Character);
        right1 = characterImages[0];
        right2 = characterImages[1];
        right3 = characterImages[2];
        right4 = characterImages[3];
        left1 = characterImages[4];
        left2 = characterImages[5];
        left3 = characterImages[6];
        left4 = characterImages[7];
        upRight = characterImages[8];
        upLeft = characterImages[9];
        downRight = characterImages[10];
        downLeft = characterImages[11];
    }

    public void update(){
        gp.cChecker.checkTile(this);
        //change y pos based on ySpeed
        if (!touchingY && fall <= 9) {
            ySpeed -= gravity;
            y -= (int) Math.round(ySpeed);
        }

        //Delay between g effect
        fall++;
        if(fall <= 10) {fall = 0;}

        //set all values speed + jump when touching ground
        if (touchingY) {
            ySpeed = 0;
            jump = 0;
            y = Math.round((float) y/gp.TileSize) * gp.TileSize;
        }

        //Controls for player1
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed && jump <= 1 && ySpeed <= 0) {
                ySpeed = 10;
                jump++;
            }
            if (keyH.leftPressed) {
                direction = "left";
                if (!touchingX) {x -= xSpeed;}
            }
            if (keyH.rightPressed) {
                direction = "right";
                if (!touchingX) {x += xSpeed;}
            }
            //updates player image every 5 frames
            spriteCounter++;
            if (spriteCounter > 5) {
                if (spriteNum == 1) {spriteNum = 2;}
                else if (spriteNum == 2) {spriteNum = 3;}
                else if (spriteNum == 3) {spriteNum = 4;}
                else if (spriteNum == 4) {spriteNum = 1;}
                spriteCounter = 0;
            }
        }

        basicCOUNTER++;

        //increment ultimate meter based on DELAY and completion time
        if(ultimateCounting) {
            ultimateNum++;
            if(ultimateNum >= ultimateDELAY){
                if(ultimateProgress < 100) {ultimateProgress++;}
                ultimateNum = 0;
            }
        }

        //Default up/down
        if (!keyH.downPressed && !keyH.upPressed) {upDown = "null";}

        //When to fire projectile
        if (keyH.spclPressed && !alive) {
            gp.projectiles.getCharacter(Character, x, y, direction, "player");
        }

        //Kill Player after falling off ledge
        if(y >= 500) {
            y = 0;
            ySpeed = 0;
            gp.player.health = 0;
        }
   }

   //draw player
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        //Change picture based on direction
        switch (direction) {
            case "right":
                if (spriteNum == 1) {image = right1;}
                if (spriteNum == 2) {image = right2;}
                if (spriteNum == 3) {image = right3;}
                if (spriteNum == 4) {image = right4;}
                break;
            case "left":
                if (spriteNum == 1) {image = left1;}
                if (spriteNum == 2) {image = left2;}
                if (spriteNum == 3) {image = left3;}
                if (spriteNum == 4) {image = left4;}
                break;
        }
        if (ySpeed > 0.5) {upDown = "up";}
        else if(ySpeed < -0.5) {upDown = "down";}
        else {upDown = "null";}

        if (ySpeed > 2) {
            if (direction.equals("right")) {image = upRight;}
            if (direction.equals("left")) {image = upLeft;}
        }
        if (ySpeed < -2) {
            if (direction.equals("right")) {image = downRight;}
            if (direction.equals("left")) {image = downLeft;}
        }
        g2.drawImage(image, x, y, gp.TileSize, gp.TileSize, null);
    }
}