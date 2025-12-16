package Menu;

import Entities.CharacterLoader;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CharacterSelection {
    GamePanel gp;

    String text;
    int textHeight, textLength;

    int[] triangleXPoints = new int[3];
    int[] triangleYPoints = new int[3];
    final int triangleWidth = 48;
    final int nPoints = 3;
    final int offset = 20;

    final int borderSize = 5;

    int[] returnHitbox = new int[4];
    int[] startHitbox = new int[4];

    int characterWidth;

    final int characterRows = 2;
    final int characterColumns = 8;
    final int characterOffset = 5;

    Color c;

    BufferedImage image, tempImage;
    BufferedImage[][] characterImages = new BufferedImage[characterColumns][characterRows];
    String[][] characterNames = new String[characterColumns][characterRows];
    BufferedImage player1Image, player2Image;

    int currentPlayerSelection = 1;
    int delay = 0;
    boolean player1Selected = false, player2Selected = false;
    int xLocation1 = 0, yLocation1 = 0, xLocation2 = -67, yLocation2 = 67;
    String character1, character2;
    final int imageSize = 192;

    int startDelay;
    int COUNTDOWN;
    public boolean starting = false;

    public CharacterSelection(GamePanel gp) {
        this.gp = gp;
        characterWidth = (gp.ScreenWidth - triangleWidth) / 2 - 3 * offset;
        getImages();
    }

    private void getImages() {
        characterNames[0][0] = "Duck";
        tempImage = getImage(characterNames[0][0] + "/character_images/" + characterNames[0][0] + "_RightWalk1");
        characterImages[0][0] = tempImage.getSubimage(0, 0, 24, 12);
        characterNames[1][0] = "Rice";
        tempImage = getImage(characterNames[1][0] + "/character_images/" + characterNames[1][0] + "_RightWalk1");
        characterImages[1][0] = tempImage.getSubimage(12, 0, 24, 12);
        characterNames[2][0] = "Zombie";
        tempImage = getImage(characterNames[2][0] + "/character_images/" + characterNames[2][0] + "_RightWalk1");
        characterImages[2][0] = tempImage.getSubimage(12, 0, 24, 12);
        characterNames[3][0] = "Guy";
        tempImage = getImage(characterNames[3][0] + "/character_images/" + characterNames[3][0] + "_RightWalk1");
        characterImages[3][0] = tempImage.getSubimage(0, 0, 24, 12);
    }

    public void drawCharacterSelection(Graphics2D g2) {
        returnHitbox[0] = 0;
        returnHitbox[1] = (int) (gp.TileSize * gp.ui.widthFactor);
        returnHitbox[2] = 0;
        returnHitbox[3] = (int) (gp.TileSize * gp.ui.heightFactor);
        if(gp.mouseH.x > returnHitbox[0] && gp.mouseH.x < returnHitbox[1] && gp.mouseH.y > returnHitbox[2] && gp.mouseH.y < returnHitbox[3]) {
            if(gp.mouseH.pressed && delay >= 10) {
                delay = 0;
                if(!player1Selected && !player2Selected) {gp.title.currentMenuState = gp.title.titleState;}
                if(player1Selected && !player2Selected) {player1Selected = false; currentPlayerSelection = 1; xLocation1 = 0; yLocation1 = 0; player1Image = null;}
                if(player1Selected) {player2Selected = false;  currentPlayerSelection = 2; xLocation2 = -67; yLocation2 = 67; player2Image = null;}
            }
            g2.drawImage(gp.title.return_arrowHover, 0,0, gp.TileSize, gp.TileSize, null);}
        else {g2.drawImage(gp.title.return_arrow, 0,0, gp.TileSize, gp.TileSize, null);}

        c = new Color(179, 229, 252);
        g2.setColor(c);
        g2.fillRect(0, 5 * gp.ui.y / 4, gp.ScreenWidth, gp.ScreenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        int width = (gp.ScreenWidth - 2 * offset) / characterColumns - characterOffset;
        int height = width / 2;
        delay++;
        for (int a = 0; a < characterRows; a++) {
            for (int b = 0; b < characterColumns; b++) {
                c = new Color(179, 229, 252);
                g2.setColor(c);
                g2.fillRect(offset + (width + characterOffset) * b, gp.TileSize + offset + (width + characterOffset) * a, width, 2 * height);
                g2.setColor(Color.BLACK);
                if (characterNames[b][a] != null) {text = characterNames[b][a];}
                else {text = "Andy's fault";}
                c = new Color(36, 52, 71);
                g2.setColor(c);
                textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight() + 6;
                g2.fillRect(offset + (width + characterOffset) * b, gp.TileSize + offset + width - textHeight + (width + characterOffset) * a, width, textHeight);
                g2.setColor(Color.WHITE);
                g2.drawString(text, offset + (width + characterOffset) * b + 5, gp.TileSize + offset + width + (width + characterOffset) * a - 7);
                g2.drawImage(characterImages[b][a], offset + (width + characterOffset) * b, gp.TileSize + offset + width - height - textHeight + (width + characterOffset) * a, width, height, null);
                int[] hitbox = new int[4];
                hitbox[0] = (int) ((offset + (width + characterOffset) * b) * gp.ui.widthFactor);
                hitbox[1] = (int) (hitbox[0] + width * gp.ui.widthFactor);
                hitbox[2] = (int) ((gp.TileSize + offset + (width + characterOffset) * a) * gp.ui.heightFactor);
                hitbox[3] = (int) (hitbox[2] + 2 * height * gp.ui.heightFactor);
                if(gp.mouseH.pressed && gp.mouseH.x > hitbox[0] && gp.mouseH.x < hitbox[1] && gp.mouseH.y > hitbox[2] && gp.mouseH.y < hitbox[3]) {
                    if(currentPlayerSelection == 2 && delay >= 10) {
                        player2Selected = true;
                        xLocation2 = offset + (width + characterOffset) * b + textHeight / 2;
                        yLocation2 = gp.TileSize + offset + (width + characterOffset) * a;
                        currentPlayerSelection = 3; delay = 0;
                        player2Image = characterImages[b][a];
                        character2 = characterNames[b][a];
                    }
                    if(currentPlayerSelection == 1 && delay >= 10) {
                        player1Selected = true;
                        xLocation1 = offset + (width + characterOffset) * b + textHeight / 2;
                        yLocation1 = gp.TileSize + offset + (width + characterOffset) * a;
                        currentPlayerSelection = 2; delay = 0;
                        player1Image = characterImages[b][a];
                        character1 = characterNames[b][a];
                    }
                }
            }
        }

        if(xLocation1 != xLocation2 || yLocation1 != yLocation2) {
            if(player1Selected) {drawSelection(xLocation1, yLocation1, width - textHeight, "player", g2);}
            if(player2Selected) {drawSelection(xLocation2, yLocation2, width - textHeight, "player2", g2);}
        } else {
            drawSelection(xLocation1, yLocation1, width - textHeight, "shared", g2);
        }

        if(player1Selected && player2Selected) {
            g2.setFont(gp.ui.TimesNewRoman);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
            text = "R e a d y  to  F i g h t !";
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            GradientPaint gradient = new GradientPaint(0, gp.ui.y - textHeight / 2 - borderSize, Color.WHITE, 0, gp.ui.y - textHeight / 2 + borderSize, Color.GRAY);
            g2.setPaint(gradient);
            g2.fillRect(0, gp.ui.y - textHeight / 2 - borderSize, gp.ScreenWidth, textHeight + 3 * borderSize);
            gradient = new GradientPaint(0, 0, Color.RED, gp.ScreenWidth, 0 , Color.YELLOW);
            g2.setPaint(gradient);
            g2.fillRect(0, gp.ui.y - textHeight / 2, gp.ScreenWidth, textHeight + borderSize);
            g2.setColor(Color.BLACK);
            g2.fillRect(gp.ui.x - textLength / 2 - borderSize, gp.ui.y - textHeight / 2 - 3 * borderSize / 4, textLength + 2 * borderSize, textHeight);
            startHitbox[0] = (int) ((gp.ui.x - textLength / 2 - borderSize) * gp.ui.widthFactor);
            startHitbox[1] = (int) (startHitbox[0] + (textLength + borderSize * 2) * gp.ui.widthFactor);
            startHitbox[2] = (int) ((gp.ui.y - textHeight / 2 - 3 * borderSize / 4) * gp.ui.heightFactor);
            startHitbox[3] = (int) (startHitbox[2] + textHeight * gp.ui.heightFactor);
            gradient = new GradientPaint(gp.ui.x - textLength / 2, gp.ui.y + textHeight / 2 - 5, new Color(255, 85, 7), gp.ui.x + textLength / 2, gp.ui.y + textHeight / 2 - 5, new Color(254, 254, 77));
            g2.setPaint(gradient);
            g2.drawString(text, gp.ui.x - textLength / 2, gp.ui.y + textHeight / 2 - 13 - 3 * borderSize / 4);
            if(gp.mouseH.x > startHitbox[0] && gp.mouseH.x < startHitbox[1] && gp.mouseH.y > startHitbox[2] && gp.mouseH.y < startHitbox[3] && gp.mouseH.pressed) {gp.gameState = gp.playState; gp.player.Character = character1; gp.player2.Character = character2; startGame();}
        }

        g2.setColor(Color.BLACK);
        drawRightTriangle(offset, 5 * gp.ui.y / 4 + offset - borderSize, triangleWidth, triangleWidth, g2);
        g2.fillRect(offset, 5 * gp.ui.y / 4 + offset + triangleWidth - borderSize, borderSize, borderSize);
        g2.fillRect(offset + triangleWidth, 5 * gp.ui.y / 4 + offset - borderSize, characterWidth, borderSize);
        drawRightTriangle(triangleWidth + characterWidth + 2 * offset, 5 * gp.ui.y / 4 + offset - borderSize, triangleWidth, triangleWidth, g2);
        g2.fillRect(triangleWidth + characterWidth + 2 * offset, 5 * gp.ui.y / 4 + offset + triangleWidth - borderSize, borderSize, borderSize);
        g2.fillRect(triangleWidth + characterWidth + 2 * offset + triangleWidth, 5 * gp.ui.y / 4 + offset - borderSize, characterWidth, borderSize);

        c = new Color(179, 229, 252);
        g2.setColor(c);
        drawRightTriangle(triangleWidth + characterWidth + 2 * offset, 5 * gp.ui.y / 4 + offset, triangleWidth, triangleWidth, g2);
        drawRightTriangle(offset, 5 * gp.ui.y / 4 + offset, triangleWidth, triangleWidth, g2);

        c = new Color(255, 50, 50, 125);
        g2.setColor(c);
        drawRightTriangle(offset, 5 * gp.ui.y / 4 + offset, triangleWidth, triangleWidth, g2);
        g2.fillRect(triangleXPoints[1], triangleYPoints[1], characterWidth / 2, gp.ScreenHeight - triangleYPoints[1]);
        g2.fillRect(offset, triangleYPoints[0], triangleWidth, gp.ScreenHeight - triangleYPoints[0]);
        c = new Color(125, 125, 125, 100);
        g2.setColor(c);
        drawRightTriangle(offset + characterWidth / 2 - borderSize, 5 * gp.ui.y / 4 + offset, triangleWidth, gp.ScreenHeight - triangleYPoints[1], g2);
        g2.fillRect(offset + characterWidth / 2 - borderSize + triangleWidth, 5 * gp.ui.y / 4 + offset, borderSize, 4 * borderSize);
        c = new Color(255, 50, 50);
        g2.setColor(c);
        drawRightTriangle(offset + characterWidth / 2, 5 * gp.ui.y / 4 + offset, triangleWidth, gp.ScreenHeight - triangleYPoints[1], g2);
        g2.fillRect(offset + characterWidth / 2 + triangleWidth, 5 * gp.ui.y / 4 + offset, characterWidth / 2, gp.ScreenHeight - triangleYPoints[1]);
        g2.drawImage(player1Image, offset + triangleWidth / 2, gp.ScreenHeight - imageSize/2 , imageSize, imageSize / 2, null);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        text = "Player 1";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        c = new Color(250,150,150);
        g2.setColor(c);
        g2.fillRect(offset + characterWidth / 2 + triangleWidth + characterWidth / 12, 5 * gp.ui.y / 4 + offset + gp.ScreenHeight - triangleYPoints[1] - characterWidth/4, characterWidth / 3, textHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(offset + characterWidth / 2 + triangleWidth + characterWidth / 12, 5 * gp.ui.y / 4 + offset + gp.ScreenHeight - triangleYPoints[1] - characterWidth/4, characterWidth / 3, textHeight);
        g2.drawString(text, offset + characterWidth / 2 + triangleWidth + characterWidth / 6 - textLength / 8, 5 * gp.ui.y / 4 + offset + gp.ScreenHeight - triangleYPoints[1] - characterWidth/4 + textHeight - 5);

        c = new Color(50, 50, 255, 125);
        g2.setColor(c);
        drawRightTriangle(triangleWidth + characterWidth + 2 * offset, 5 * gp.ui.y / 4 + offset, triangleWidth, triangleWidth, g2);
        g2.fillRect(triangleXPoints[1], triangleYPoints[1], characterWidth, gp.ScreenHeight - triangleYPoints[1]);
        g2.fillRect(triangleXPoints[0], triangleYPoints[0], triangleWidth, gp.ScreenHeight - triangleYPoints[0]);
        c = new Color(125, 125, 125, 100);
        g2.setColor(c);
        drawRightTriangle(triangleWidth + 3 * characterWidth / 2 + 2 * offset - borderSize, 5 * gp.ui.y / 4 + offset, triangleWidth, gp.ScreenHeight - triangleYPoints[1], g2);
        g2.fillRect(triangleWidth + 3 * characterWidth / 2 + 2 * offset- borderSize + triangleWidth, 5 * gp.ui.y / 4 + offset, borderSize, 4 * borderSize);
        c = new Color(50, 50, 255);
        g2.setColor(c);
        drawRightTriangle(triangleWidth + 3 * characterWidth / 2 + 2 * offset, 5 * gp.ui.y / 4 + offset, triangleWidth, gp.ScreenHeight - triangleYPoints[1], g2);
        g2.fillRect(2 * offset + 3 * characterWidth / 2 + 2 * triangleWidth, 5 * gp.ui.y / 4 + offset, characterWidth / 2, gp.ScreenHeight - triangleYPoints[1]);
        g2.drawImage(player2Image,2 * offset + characterWidth + 3 * triangleWidth / 2, gp.ScreenHeight - imageSize/2 , imageSize, imageSize / 2, null);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        text = "Player 2";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        c = new Color(150,150,250);
        g2.setColor(c);
        g2.fillRect(2 * offset + 3 * characterWidth / 2 + 2 * triangleWidth + characterWidth / 12, 5 * gp.ui.y / 4 + offset + gp.ScreenHeight - triangleYPoints[1] - characterWidth/4, characterWidth / 3, textHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(2 * offset + 3 * characterWidth / 2 + 2 * triangleWidth + characterWidth / 12, 5 * gp.ui.y / 4 + offset + gp.ScreenHeight - triangleYPoints[1] - characterWidth/4, characterWidth / 3, textHeight);
        g2.drawString(text, 2 * offset + 3 * characterWidth / 2 + 2 * triangleWidth + characterWidth / 6 - textLength / 8, 5 * gp.ui.y / 4 + offset + gp.ScreenHeight - triangleYPoints[1] - characterWidth/4 + textHeight - 5);
    }

    private void drawRightTriangle(int x, int y, int width, int height, Graphics2D g2) {
        triangleXPoints[0] = x;
        triangleXPoints[1] = x + width;
        triangleXPoints[2] = triangleXPoints[1];
        triangleYPoints[0] = y + height;
        triangleYPoints[1] = triangleYPoints[0] - height;
        triangleYPoints[2] = triangleYPoints[0];
        g2.fillPolygon(triangleXPoints, triangleYPoints, nPoints);
    }

    private void drawSelection(int x, int y, int width, String Player, Graphics2D g2) {
        Color a = Color.WHITE, b = Color.WHITE;
        final int borderSize = 10;
        if(Player.equals("player")) {
            a = new Color(170, 20, 20);
            b = new Color(255, 20, 20);
            text = "P1";
        } else if(Player.equals("player2")){
            a = new Color(20, 20, 170);
            b = new Color(20, 20, 255);
            text = "P2";
        }
        if(Player.equals("player") || Player.equals("player2")) {
            g2.setColor(a);
            g2.fillOval(x, y, width, width);
            g2.setColor(b);
            g2.fillOval(x + borderSize / 2, y + borderSize / 2, width - borderSize, width - borderSize);
            g2.setColor(Color.WHITE);
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            g2.drawString(text, x + width / 2 - textLength / 2, y + width / 2 + textHeight / 2);
        } else if(Player.equals("shared")) {
            g2.setColor(new Color(170, 20, 20));
            g2.fillArc(x, y, width, width, 90, 180);
            g2.setColor(new Color(255, 20, 20));
            g2.fillArc(x + borderSize / 2, y + borderSize / 2, width - borderSize, width - borderSize, 90, 180);
            g2.setColor(new Color(20, 20, 170));
            g2.fillArc(x, y, width, width, 270, 180);
            g2.setColor(new Color(20, 20, 255));
            g2.fillArc(x + borderSize / 2, y + borderSize / 2, width - borderSize, width - borderSize, 270, 180);
            g2.setColor(Color.WHITE);
            text = "P1/2";
            textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            g2.drawString(text, x + width / 2 - textLength / 2, y + width / 2 + textHeight / 2);
        }
    }

    private void startGame() {
        starting = true;
        gp.player.startGame();
        gp.player2.startGame();
        COUNTDOWN = 3;
    }

    public void draw(Graphics2D g2) {
        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 60F));
        g2.setColor(Color.WHITE);
        startDelay++;
        text = Integer.toString(COUNTDOWN);
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text, gp.ui.x - textLength / 2, gp.ui.y + textHeight / 2 - 13);
        if(startDelay >= 60) {
            COUNTDOWN--;
            startDelay = 0;
        }
        if(COUNTDOWN <= 0) {starting = false;}
    }

    private BufferedImage getImage(String filePath) {
        try {image = ImageIO.read((CharacterLoader.class.getClassLoader().getResourceAsStream("player/" + filePath + ".png")));} catch (Exception e) {
            try {image = ImageIO.read(Objects.requireNonNull(CharacterLoader.class.getClassLoader().getResourceAsStream("menu/error/Missing_Tile.png")));System.out.println("Error: " + filePath + " not found");
            } catch (IOException ex) {throw new RuntimeException(ex);}}
        return image;
    }
}
