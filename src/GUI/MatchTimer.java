package GUI;

import Main.GamePanel;

import java.awt.*;

public class MatchTimer {
    GamePanel gp;

    String text;
    int COUNTER;
    public int matchLength = 120;
    public int timeLeft = matchLength;
    public int textLength, textHeight;

    int[] triangleXPoints = new int[3];
    int[] triangleYPoints = new int[3];

    final int triangleWidth = 10;
    final int nPoints = 3;

    public MatchTimer(GamePanel gp) {
        this.gp = gp;
    }
    public void draw(Graphics2D g2) {
        if(gp.gameState == gp.playState && !gp.title.characterSelection.starting) {
            COUNTER++;
            if (COUNTER >= 60) {timeLeft--; COUNTER = 0;}
        }
        //Time Left Text Data
        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        text = "Time Left:";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();

        //Time Left Data
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
        String text2 = Integer.toString(timeLeft);
        int textLength2 = (int) g2.getFontMetrics().getStringBounds(text2, g2).getWidth();
        int textHeight2 = (int) g2.getFontMetrics().getStringBounds(text2, g2).getHeight();

        //Drawing Box for text to go inside of
        g2.setColor(Color.BLACK);
        g2.fillRect(gp.ui.x - textLength / 2, 0, textLength, textHeight + textHeight2);
        drawRightTriangle(gp.ui.x - textLength / 2 - triangleWidth, 0, triangleWidth, textHeight + textHeight2, "left", g2);
        drawRightTriangle(gp.ui.x + textLength / 2, 0, triangleWidth, textHeight + textHeight2, "right", g2);

        //Draw Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.drawString(text, gp.ui.x - textLength / 2, textHeight);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
        g2.drawString(text2, gp.ui.x - textLength2 / 2, textHeight + textHeight2 - 10);
    }
    private void drawRightTriangle(int x, int y, int width, int height, String direction, Graphics2D g2) {
        if(direction.equals("left")) {
            triangleXPoints[0] = x;
            triangleXPoints[1] = x + width;
            triangleXPoints[2] = triangleXPoints[1];
            triangleYPoints[0] = y;
            triangleYPoints[1] = triangleYPoints[0];
            triangleYPoints[2] = triangleYPoints[0] + height;
        }
        if(direction.equals("right")) {
            triangleXPoints[0] = x;
            triangleXPoints[1] = x + width;
            triangleXPoints[2] = triangleXPoints[0];
            triangleYPoints[0] = y;
            triangleYPoints[1] = triangleYPoints[0];
            triangleYPoints[2] = triangleYPoints[0] + height;
        }
        g2.fillPolygon(triangleXPoints, triangleYPoints, nPoints);
    }
}
