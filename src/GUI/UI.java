package GUI;

import Main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    Font arial_80, TimesNewRoman_60;
    GamePanel gp;
    int x, y;
    final int yOffset = 5;
    int textLength, textHeight;
    String text;

    public UI(GamePanel gp) {
        this.gp = gp;

        InputStream is = getClass().getResourceAsStream("/fonts/times.ttf");
        try {TimesNewRoman_60 = Font.createFont(Font.TRUETYPE_FONT, is);}
        catch (FontFormatException | IOException e) {e.printStackTrace();}
        arial_80 = new Font("Arial", Font.PLAIN, 80);
        x = gp.ScreenWidth/2;
        y = gp.ScreenWidth/4;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(TimesNewRoman_60);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        g2.setColor(Color.white);

        if(gp.gameState == gp.pauseState) {
            text = "Paused";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            g2.drawString(text, x - textLength/2 , textHeight + yOffset);}
    }
}
