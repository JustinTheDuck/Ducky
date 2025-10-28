package GUI;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import static GUI.GUIConstants.*;


public class UltimateBar {
    GamePanel gp;
    public UltimateBar(GamePanel gp) {
        this.gp = gp;
        Images();
    }

    BufferedImage image, ultBarRight, ultEmpty, ultFull, ultIndicatorRight, ultBarLeft, ultIndicatorLeft;
    final int yLocal = yLocation + ySize;

    public void Images() {
        ultBarRight = getImage("UI/UltBar/UltBarRight");
        ultBarLeft = getImage("UI/UltBar/UltBarLeft");
        ultEmpty = getImage("UI/UltBar/UltEmpty");
        ultFull = getImage("UI/UltBar/UltFull");
        ultIndicatorRight = getImage("UI/UltBar/UltIndicatorRight");
        ultIndicatorLeft = getImage("UI/UltBar/UltIndicatorLeft");
    }

    public BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(filePath + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void player1Ult(Graphics2D g2) {
        g2.drawImage(ultIndicatorRight, xLocation + xOffset/2, yLocal + yOffset/2, 100, 7, null);
        //Covers or adds to Ult size
        if(gp.player.ultimateProgress > 50) {g2.drawImage(ultFull, xLocation + xOffset/2 + (healthSize * gp.player.ultimateProgress/100) - healthSize/2, yLocal + yOffset/2, 50, 7, null);}
        if(gp.player.ultimateProgress <= 50) {g2.drawImage(ultEmpty, xLocation + xOffset/2 + (healthSize * gp.player.ultimateProgress/100), yLocal + yOffset/2, 50, 7, null);}
        //Ult bar overlay
        g2.drawImage(ultBarRight, xLocation, yLocal, xSize/2, ySize/2, null);
    }

    public void player2Ult(Graphics2D g2) {
        //Ult Indicator Layer first
        g2.drawImage(ultIndicatorLeft, gp.ScreenWidth - 4 - xSize/2, yLocal + yOffset/2, 100, 7, null);
        //Covers or adds to Ult size
        if(gp.player2.ultimateProgress > 50) {g2.drawImage(ultFull, gp.ScreenWidth - (healthSize * gp.player2.ultimateProgress/100) - xOffset/2 - xLocation, yLocal + yOffset/2, 50, 7, null);}
        if(gp.player2.ultimateProgress <= 50) {g2.drawImage(ultEmpty, gp.ScreenWidth - (healthSize * gp.player2.ultimateProgress/100) - healthSize/2 - xOffset/2 - xLocation, yLocal + yOffset/2,50, 7, null);}
        //Health bar overlay
        g2.drawImage(ultBarLeft, gp.ScreenWidth - xLocation - xSize/2, yLocal, xSize/2, ySize/2, null);
    }

    public void draw(Graphics2D g2){
        player1Ult(g2);
        player2Ult(g2);
    }

}
