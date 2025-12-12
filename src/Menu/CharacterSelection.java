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

    int characterWidth;

    final int characterRows = 2;
    final int characterColumns = 8;
    final int characterOffset = 5;

    Color c;

    BufferedImage image, tempImage;
    BufferedImage[][] characterImages = new BufferedImage[characterColumns][characterRows];
    String[][] characterNames = new String[characterColumns][characterRows];

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
            if(gp.mouseH.pressed) {gp.title.currentMenuState = gp.title.titleState;} g2.drawImage(gp.title.return_arrowHover, 0,0, gp.TileSize, gp.TileSize, null);}
        else {g2.drawImage(gp.title.return_arrow, 0,0, gp.TileSize, gp.TileSize, null);}

        c = new Color(179, 229, 252);
        g2.setColor(c);
        g2.fillRect(0, 5 * gp.ui.y / 4, gp.ScreenWidth, gp.ScreenHeight);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        int width = (gp.ScreenWidth - 2 * offset) / characterColumns - characterOffset;
        int height = width / 2;
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
            }
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

    private BufferedImage getImage(String filePath) {
        try {image = ImageIO.read((CharacterLoader.class.getClassLoader().getResourceAsStream("player/" + filePath + ".png")));} catch (Exception e) {
            try {image = ImageIO.read(Objects.requireNonNull(CharacterLoader.class.getClassLoader().getResourceAsStream("menu/error/Missing_Tile.png")));System.out.println("Error: " + filePath + " not found");
            } catch (IOException ex) {throw new RuntimeException(ex);}}
        return image;
    }
}
