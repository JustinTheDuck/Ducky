package GUI;

import Main.GamePanel;
import Main.KeyHandler;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    Controls controls;
    Settings settings;

    public Boolean pressed = false;

    Font arial_80, TimesNewRoman;
    GamePanel gp;
    KeyHandler keyH;
    int x, y;
    final int yOffset = 5;
    int textLength, textHeight;
    int width, height;
    final int borderThickness = 5;
    String text;

    public int currentPauseState;
    public final int baseState = 1;
    public final int controlState = 2;
    public final int settingsState = 3;

    public double heightFactor, widthFactor;

    public int[] quitHitbox = new int[4];
    int[] optionHitbox = new int[4];
    int[] settingsHitbox = new int[4];

    public UI(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        InputStream is = getClass().getResourceAsStream("/fonts/times.ttf");
        try {TimesNewRoman = Font.createFont(Font.TRUETYPE_FONT, is);}
        catch (FontFormatException | IOException e) {e.printStackTrace();}

        arial_80 = new Font("Arial", Font.PLAIN, 80);
        x = gp.ScreenWidth / 2;
        y = gp.ScreenHeight / 2;
        width = gp.TileSize * 9;
        height = gp.TileSize * 11;

        controls = new Controls(this.gp);
        settings = new Settings(this.gp);
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.pauseState && currentPauseState == baseState) {
            Color c = new Color(0,0,0, 100);
            g2.setColor(c);
            g2.fillRoundRect(x - width / 2, y - height / 2, width, height, 35, 35);

            c = new Color(255,255,255);
            g2.setColor(c);
            g2.setStroke(new BasicStroke(borderThickness));
            g2.drawRoundRect(x - width / 2, y - height / 2, width, height, 25, 25);

            g2.setFont(TimesNewRoman);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
            g2.setColor(Color.white);
            text = "Paused";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            g2.drawString(text, x - textLength / 2, textHeight + yOffset);

            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
            text = "Quit";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            quitHitbox[0] = Math.toIntExact(Math.round((x - textLength / 2) * widthFactor));
            quitHitbox[1] = Math.toIntExact(Math.round((x + textLength / 2) * widthFactor));
            quitHitbox[2] = Math.toIntExact(Math.round((gp.ScreenHeight - 2 * textHeight - yOffset) * heightFactor));
            quitHitbox[3] = Math.toIntExact(Math.round((gp.ScreenHeight - textHeight - yOffset) * heightFactor));
            if(gp.mouseH.x > quitHitbox[0] && gp.mouseH.x < quitHitbox[1] && gp.mouseH.y > quitHitbox[2] && gp.mouseH.y < quitHitbox[3]) {
                if (gp.mouseH.pressed) {gp.closeWindow();}
                c = new Color(247, 215, 109); g2.setColor(c);
            } else {g2.setColor(Color.WHITE);}
            g2.drawString(text, x - textLength / 2, gp.ScreenHeight - textHeight - yOffset);

            text = "Controls";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            optionHitbox[0] = Math.toIntExact(Math.round((x - textLength / 2) * widthFactor));
            optionHitbox[1] = Math.toIntExact(Math.round((x + textLength / 2) * widthFactor));
            optionHitbox[2] = Math.toIntExact(Math.round((gp.ScreenHeight - 3 * textHeight - yOffset) * heightFactor));
            optionHitbox[3] = Math.toIntExact(Math.round((gp.ScreenHeight - 2 * textHeight - yOffset) * heightFactor));
            if(gp.mouseH.x > optionHitbox[0] && gp.mouseH.x < optionHitbox[1] && gp.mouseH.y > optionHitbox[2] && gp.mouseH.y < optionHitbox[3]) {
                if(gp.mouseH.pressed) {currentPauseState = controlState;}
                c = new Color(247, 215, 109); g2.setColor(c);
            } else {g2.setColor(Color.WHITE);}
            g2.drawString(text, x - textLength / 2, gp.ScreenHeight - 2 * textHeight - yOffset);

            text = "Settings";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            settingsHitbox[0] = Math.toIntExact(Math.round((x - textLength / 2) * widthFactor));
            settingsHitbox[1] = Math.toIntExact(Math.round((x + textLength / 2) * widthFactor));
            settingsHitbox[2] = Math.toIntExact(Math.round((gp.ScreenHeight - 4 * textHeight - yOffset) * heightFactor));
            settingsHitbox[3] = Math.toIntExact(Math.round((gp.ScreenHeight - 3 * textHeight - yOffset) * heightFactor));
            if(gp.mouseH.x > settingsHitbox[0] && gp.mouseH.x < settingsHitbox[1] && gp.mouseH.y > settingsHitbox[2] && gp.mouseH.y < settingsHitbox[3]) {
                if(gp.mouseH.pressed) {currentPauseState = settingsState;}
                c = new Color(247, 215, 109); g2.setColor(c);
            } else {g2.setColor(Color.WHITE);}
            g2.drawString(text, x - textLength / 2, gp.ScreenHeight - 3 * textHeight - yOffset);
        }
        if(gp.gameState == gp.pauseState && currentPauseState == controlState) {controls.draw(g2);}
        if(gp.gameState == gp.pauseState && currentPauseState == settingsState) {settings.draw(g2);}
    }
}