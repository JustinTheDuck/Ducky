package GUI;

import Main.GamePanel;
import Ultimate.GuyUltimate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static GUI.GUIConstants.*;


import static Objects.Projectiles.attacks;

public class SpecialAttackBar {
    GamePanel gp;
    BufferedImage image, spclBarRight_0, spclBarRight_1, spclBarRight_2, spclBarRight_3, spclBarRight_4, spclBarLeft_0, spclBarLeft_1, spclBarLeft_2, spclBarLeft_3, spclBarLeft_4;

    final int yLocal = 2 * yLocation + ySize;

    public int percent;

    public SpecialAttackBar(GamePanel gp) {
        this.gp = gp;
        Images();
    }

    public BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("UI/SpclBar/SpclBar" + filePath + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void Images() {
        spclBarRight_0 = getImage("Right_0");
        spclBarRight_1 = getImage("Right_1");
        spclBarRight_2 = getImage("Right_2");
        spclBarRight_3 = getImage("Right_3");
        spclBarRight_4 = getImage("Right_4");
        spclBarLeft_0 = getImage("Left_0");
        spclBarLeft_1 = getImage("Left_1");
        spclBarLeft_2 = getImage("Left_2");
        spclBarLeft_3 = getImage("Left_3");
        spclBarLeft_4 = getImage("Left_4");
    }

    public void draw(Graphics2D g2){
        for(int i = 0; i < attacks.size(); i++) {
            if(i%12 == 0){
                percent = 100 * (int) attacks.get(i+8)/ (int) attacks.get(i+11);
                if(attacks.get(i + 6) == "player"){
                    if(percent >= 75) {g2.drawImage(spclBarRight_0, xLocation, yLocal, xSize/2, ySize/2, null);}
                    if(percent < 75 && percent >= 50) {g2.drawImage(spclBarRight_1, xLocation, yLocal, xSize/2, ySize/2, null);}
                    if(percent < 50 && percent >= 25) {g2.drawImage(spclBarRight_2, xLocation, yLocal, xSize/2, ySize/2, null);}
                    if(percent < 25) {g2.drawImage(spclBarRight_3, xLocation, yLocal, xSize/2, ySize/2, null);}
                }
                if(attacks.get(i + 6) == "player2"){
                    if(percent >= 75) {g2.drawImage(spclBarLeft_0, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
                    if(percent < 75 && percent >= 50) {g2.drawImage(spclBarLeft_1, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
                    if(percent < 50 && percent >= 25) {g2.drawImage(spclBarLeft_2, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
                    if(percent < 25) {g2.drawImage(spclBarLeft_3, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
                }
            }
        }
        if(gp.player.Character.equals("Guy")) {
            percent = 100 * (gp.player.basicCOOLDOWN - gp.player.basicCOUNTER) / gp.player.basicCOOLDOWN;
            if(percent >= 75) {g2.drawImage(spclBarRight_0, xLocation, yLocal, xSize/2, ySize/2, null);}
            if(percent < 75 && percent >= 50) {g2.drawImage(spclBarRight_1, xLocation, yLocal, xSize/2, ySize/2, null);}
            if(percent < 50 && percent >= 25) {g2.drawImage(spclBarRight_2, xLocation, yLocal, xSize/2, ySize/2, null);}
            if(percent < 25 && percent > 0) {g2.drawImage(spclBarRight_3, xLocation, yLocal, xSize/2, ySize/2, null);}
            if(percent <= 0) {g2.drawImage(spclBarRight_4, xLocation, yLocal, xSize/2, ySize/2, null);}
        }
        if(gp.player2.Character.equals("Guy")) {
            percent = 100 * (gp.player2.basicCOOLDOWN - gp.player2.basicCOUNTER) / gp.player2.basicCOOLDOWN;
            if(percent >= 75) {g2.drawImage(spclBarLeft_0, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
            if(percent < 75 && percent >= 50) {g2.drawImage(spclBarLeft_1, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
            if(percent < 50 && percent >= 25) {g2.drawImage(spclBarLeft_2, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
            if(percent < 25 && percent > 0) {g2.drawImage(spclBarLeft_3, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
            if(percent <= 0 ) {g2.drawImage(spclBarLeft_3, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}

        }
        if(!gp.player.alive && !gp.player.Character.equals("Guy")) {g2.drawImage(spclBarRight_4, xLocation, yLocal, xSize/2, ySize/2, null);}
        if(!gp.player2.alive && !gp.player2.Character.equals("Guy")) {g2.drawImage(spclBarLeft_4, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);}
    }
}
