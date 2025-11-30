package GUI;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static GUI.GUIConstants.*;

public class HealthBar {
    GamePanel gp;
    BufferedImage image, healthBarRight, healthEmpty, healthFull, healthIndicatorRight, healthBarLeft, healthIndicatorLeft;

    //Calls this instance of gp
    public HealthBar(GamePanel gp) {
        this.gp = gp;
        Images();
    }

    public BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void Images() {
        healthBarRight = getImage("UI/HealthBar/HealthBarRight");
        healthBarLeft = getImage("UI/HealthBar/HealthBarLeft");
        healthEmpty = getImage("UI/HealthBar/HealthEmpty");
        healthFull = getImage("UI/HealthBar/HealthFull");
        healthIndicatorRight = getImage("UI/HealthBar/HealthIndicatorRight");
        healthIndicatorLeft = getImage("UI/HealthBar/HealthIndicatorLeft");
    }

    public void player1Health(Graphics2D g2) {
        //Health Indicator Layer first
        g2.drawImage(healthIndicatorRight, xLocation + xOffset, yLocation + yOffset, 200, 14, null);
        //Covers or adds to health size
        if(gp.player.health > gp.player.maxHealth/2) {g2.drawImage(healthFull, xLocation + xOffset + (2 * healthSize * gp.player.health/gp.player.maxHealth) - healthSize, yLocation + yOffset, 100, 14, null);}
        if(gp.player.health <= gp.player.maxHealth/2 && gp.player.health >= 0) {g2.drawImage(healthEmpty, xLocation + xOffset + (2 * healthSize * gp.player.health/gp.player.maxHealth), yLocation + yOffset, 100, 14, null);}
        if(gp.player.health < 0) {g2.drawImage(healthEmpty, xLocation + xOffset, yLocation + yOffset, 100, 14, null);}
        //Health bar overlay
        g2.drawImage(healthBarRight, xLocation, yLocation, xSize, ySize, null);
    }

    public void player2Health(Graphics2D g2) {
        //Health Indicator Layer first
        g2.drawImage(healthIndicatorLeft, gp.ScreenWidth + 2 - xSize, yLocation + yOffset, 200, 14, null);
        //Covers or adds to health size
        if(gp.player2.health > gp.player2.maxHealth/2) {g2.drawImage(healthFull, gp.ScreenWidth - (2 * healthSize * gp.player2.health/gp.player2.maxHealth) - xOffset - xLocation, yLocation + yOffset, 100, 14, null);}
        if(gp.player2.health <= gp.player2.maxHealth/2 && gp.player2.health >= 0) {g2.drawImage(healthEmpty, gp.ScreenWidth - (2 * healthSize * gp.player2.health/gp.player2.maxHealth) - healthSize - xOffset - xLocation, yLocation + yOffset,100, 14, null);}
        if(gp.player2.health < 0) {g2.drawImage(healthEmpty, gp.ScreenWidth - healthSize - xOffset - xLocation, yLocation + yOffset, 100, 14, null);}
        //Health bar overlay
        g2.drawImage(healthBarLeft, gp.ScreenWidth - xLocation - xSize, yLocation, xSize, ySize, null);
    }

    public void checkDead() {
        if(gp.player.health <= 0) {gp.dead = 1; gp.gameState = gp.lossState;}
        if(gp.player2.health <= 0) {gp.dead = 2; gp.gameState = gp.lossState;
        }
    }

    //Draw
    public void draw(Graphics2D g2){
        player1Health(g2);
        player2Health(g2);
    }
}