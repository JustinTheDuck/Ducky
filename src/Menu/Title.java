package Menu;

import Main.GamePanel;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Title {
    GamePanel gp;

    Font ComicSans;
    String text;
    int textLength, textHeight;

    int[] smashHitbox = new int[4];

    int[] triangleXPoints = new int[3];
    int[] triangleYPoints = new int[3];
    final int triangleWidth = 48;
    final int nPoints = 3;
    final int offset = 20;

    int characterWidth;

    int currentMenuState = 1;
    final int titleState = 1;
    final int characterState = 2;
    final int mapState = 3;

    Color c;
    public Title(GamePanel gp) {
        this.gp = gp;
        InputStream is = getClass().getResourceAsStream("/fonts/comic.ttf");
        try {ComicSans = Font.createFont(Font.TRUETYPE_FONT, is);}
        catch (FontFormatException | IOException e) {e.printStackTrace();}
        characterWidth = (gp.ScreenWidth - triangleWidth)/2 - 3 * offset;
    }

    public void draw(Graphics2D g2) {
        if(currentMenuState == titleState) {drawTitleScreen(g2);}
        else if(currentMenuState == characterState) {drawCharacterSelection(g2);}
        else if(currentMenuState == mapState) {drawMapSelection(g2);}
    }

    public void drawTitleScreen(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(ComicSans);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
        text = "Ducky Fighters";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, gp.ui.x - textLength/2, textHeight);

        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
        text = "Start";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        smashHitbox[0] = (int) ((gp.ui.x - (double) textLength /2) * gp.ui.widthFactor);
        smashHitbox[1] = (int) ((gp.ui.x + (double) textLength /2) * gp.ui.widthFactor);
        smashHitbox[2] = (int) ((gp.ui.y - (double) textHeight /2) * gp.ui.heightFactor);
        smashHitbox[3] = (int) ((gp.ui.y + (double) textHeight /2) * gp.ui.heightFactor);
        if(gp.mouseH.x > smashHitbox[0] && gp.mouseH.x < smashHitbox[1] && gp.mouseH.y > smashHitbox[2] && gp.mouseH.y < smashHitbox[3]) {
            c = new Color(247, 215, 109); g2.setColor(c);
            if(gp.mouseH.pressed) {currentMenuState = characterState; g2.setColor(Color.BLACK); g2.fillRect(0,0,gp.ScreenWidth, gp.ScreenHeight);}
        }
        else{g2.setColor(Color.WHITE);}
        g2.drawString(text, gp.ui.x - textLength/2, gp.ui.y + textHeight/2);
    }

    public void drawCharacterSelection(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 5 * gp.ui.y / 4, gp.ScreenWidth, gp.ScreenHeight);
        c = new Color(255, 50,50, 200);
        g2.setColor(c);
        triangleXPoints[0] = offset;
        triangleXPoints[1] = offset + triangleWidth;
        triangleXPoints[2] = triangleXPoints[1];
        triangleYPoints[0] = 5 * gp.ui.y / 4 + offset + triangleWidth;
        triangleYPoints[1] = triangleYPoints[0] - triangleWidth;
        triangleYPoints[2] = triangleYPoints[0];
        g2.fillPolygon(triangleXPoints,triangleYPoints, nPoints);
        g2.fillRect(triangleXPoints[1], triangleYPoints[1], characterWidth, gp.ScreenHeight - triangleYPoints[1]);
        g2.fillRect(offset, triangleYPoints[0], triangleWidth, gp.ScreenHeight - triangleYPoints[0]);
        c = new Color(50, 50,255, 200);
        g2.setColor(c);
        triangleXPoints[0] = triangleWidth + characterWidth + 2 * offset;
        triangleXPoints[1] = triangleWidth + characterWidth + 2 * offset + triangleWidth;
        triangleXPoints[2] = triangleXPoints[1];
        g2.fillPolygon(triangleXPoints,triangleYPoints, nPoints);
        g2.fillRect(triangleXPoints[1], triangleYPoints[1], characterWidth, gp.ScreenHeight - triangleYPoints[1]);
        g2.fillRect(triangleXPoints[0], triangleYPoints[0], triangleWidth, gp.ScreenHeight - triangleYPoints[0]);
    }

    public void drawMapSelection(Graphics2D g2) {

    }
}
