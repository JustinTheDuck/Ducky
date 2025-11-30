package GUI;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Settings {
    GamePanel gp;

    BufferedImage image;
    BufferedImage option_no, option_yes, option;

    String text;
    int textLength, textHeight;
    final int xOffset = 40;
    final int fullScreen = 1;
    final int minScreen = 2;
    int ScreenSize = fullScreen;
    int COUNTER = 0;

    int[] optionHitbox = new int[4];
    public Settings(GamePanel gp) {
        this.gp = gp;
        option_no = getImage("options/No");
        option_yes = getImage("options/Yes");
    }

    public BufferedImage getImage(String filePath) {
        try {image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("UI/Settings/" + filePath + ".png")));
        } catch (IOException e) {e.printStackTrace();}
        return image;}

    public void draw(Graphics2D g2) {
        Color c = new Color(0,0,0, 100);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x - gp.ui.width / 2, gp.ui.y - gp.ui.height / 2, gp.ui.width, gp.ui.height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(gp.ui.borderThickness));
        g2.drawRoundRect(gp.ui.x - gp.ui.width / 2, gp.ui.y - gp.ui.height / 2, gp.ui.width, gp.ui.height, 25, 25);

        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        text = "Settings";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, gp.ui.x - textLength / 2, textHeight + gp.ui.yOffset);


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        text = "Settings";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, gp.ui.x - textLength / 2, textHeight + gp.ui.yOffset);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
        text = "Full Screen";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        optionHitbox[0] = (int) ((gp.ui.x - textHeight / 2 - textLength / 2 - xOffset / 2) * gp.ui.widthFactor);
        optionHitbox[1] = (int) ((gp.ui.x - textHeight / 2 - textLength / 2 - xOffset / 2 + textHeight) * gp.ui.widthFactor);
        optionHitbox[2] = (int) ((gp.ScreenHeight - 2 * textHeight - gp.ui.yOffset + 10) * gp.ui.heightFactor);
        optionHitbox[3] = (int) ((gp.ScreenHeight - 2 * textHeight - gp.ui.yOffset + 10 + textHeight) * gp.ui.heightFactor);

        COUNTER++;
        if(COUNTER > 10 && gp.mouseH.pressed && gp.mouseH.x >= optionHitbox[0] && gp.mouseH.x <= optionHitbox[1] && gp.mouseH.y >= optionHitbox[2] && gp.mouseH.y <= optionHitbox[3]) {
            COUNTER = 0;
            if(ScreenSize == fullScreen) {ScreenSize = minScreen; gp.restoreOriginalSize();}
            else if(ScreenSize == minScreen) {ScreenSize = fullScreen; gp.setFullScreen();}
        }

        if(ScreenSize == fullScreen) {option = option_yes;}
        else if(ScreenSize == minScreen) {option = option_no;}
        g2.drawString(text, gp.ui.x - textLength / 2 + textHeight / 2 + xOffset / 2, gp.ScreenHeight - textHeight - gp.ui.yOffset);
        g2.drawImage(option, gp.ui.x - textHeight / 2- textLength / 2 - xOffset / 2, gp.ScreenHeight - 2 * textHeight - gp.ui.yOffset + 10, textHeight, textHeight, null);

    }
}
