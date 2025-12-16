package Menu;

import Entities.CharacterLoader;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Title {
    GamePanel gp;

    public CharacterSelection characterSelection;

    Font ComicSans;
    String text;
    int textLength, textHeight;

    int[] smashHitbox = new int[4];

    final int creditSpacing = 5;
    int[] creditsHitbox = new int[4];

    int[] returnHitbox = new int[4];

    int currentMenuState = 1;
    final int titleState = 1;
    final int characterState = 2;
    final int mapState = 3;
    final int creditsState = 4;

    Color c;

    BufferedImage image;
    BufferedImage return_arrow, return_arrowHover;

    public Title(GamePanel gp) {
        this.gp = gp;
        InputStream is = getClass().getResourceAsStream("/fonts/comic.ttf");
        try {ComicSans = Font.createFont(Font.TRUETYPE_FONT, is);}
        catch (FontFormatException | IOException e) {e.printStackTrace();}
        characterSelection = new CharacterSelection(this.gp);
        return_arrow = getImage("return/Return_Arrow");
        return_arrowHover = getImage("return/Return_ArrowHover");
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.BLACK); g2.fillRect(0,0,gp.ScreenWidth, gp.ScreenHeight);
        if(currentMenuState == titleState) {drawTitleScreen(g2);}
        else if(currentMenuState == characterState) {characterSelection.drawCharacterSelection(g2);}
        else if(currentMenuState == mapState) {drawMapSelection(g2);}
        else if(currentMenuState == creditsState) {drawCredits(g2);}
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
        text = "Smash";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        smashHitbox[0] = (int) ((gp.ui.x - (double) textLength /2) * gp.ui.widthFactor);
        smashHitbox[1] = (int) ((gp.ui.x + (double) textLength /2) * gp.ui.widthFactor);
        smashHitbox[2] = (int) ((gp.ui.y - (double) textHeight /2) * gp.ui.heightFactor);
        smashHitbox[3] = (int) ((gp.ui.y + (double) textHeight /2) * gp.ui.heightFactor);
        if(gp.mouseH.x > smashHitbox[0] && gp.mouseH.x < smashHitbox[1] && gp.mouseH.y > smashHitbox[2] && gp.mouseH.y < smashHitbox[3]) {
            c = new Color(247, 215, 109); g2.setColor(c);
            if(gp.mouseH.pressed) {currentMenuState = characterState;}
        }
        else{g2.setColor(Color.WHITE);}
        g2.drawString(text, gp.ui.x - textLength/2, gp.ui.y + textHeight/2);

        text = "Credits";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        creditsHitbox[0] = (int) ((gp.ui.x - (double) textLength /2 + textHeight) * gp.ui.widthFactor);
        creditsHitbox[1] = (int) ((gp.ui.x + (double) textLength /2 + textHeight) * gp.ui.widthFactor);
        creditsHitbox[2] = (int) ((gp.ui.y - (double) textHeight /2 + textHeight) * gp.ui.heightFactor);
        creditsHitbox[3] = (int) ((gp.ui.y + (double) textHeight /2 + textHeight) * gp.ui.heightFactor);
        if(gp.mouseH.x > creditsHitbox[0] && gp.mouseH.x < creditsHitbox[1] && gp.mouseH.y > creditsHitbox[2] && gp.mouseH.y < creditsHitbox[3]) {
            c = new Color(247, 215, 109); g2.setColor(c);
            if(gp.mouseH.pressed) {currentMenuState = creditsState;}
        }
        else{g2.setColor(Color.WHITE);}
        g2.drawString(text, gp.ui.x - textLength/2, gp.ui.y + 3 * textHeight/2);
    }

    public void drawMapSelection(Graphics2D g2) {

    }

    public void drawCredits(Graphics2D g2) {
        returnHitbox[0] = 0;
        returnHitbox[1] = (int) (gp.TileSize * gp.ui.widthFactor);
        returnHitbox[2] = 0;
        returnHitbox[3] = (int) (gp.TileSize * gp.ui.heightFactor);
        if(gp.mouseH.x > returnHitbox[0] && gp.mouseH.x < returnHitbox[1] && gp.mouseH.y > returnHitbox[2] && gp.mouseH.y < returnHitbox[3]) {
            if(gp.mouseH.pressed) {currentMenuState = titleState;} g2.drawImage(return_arrowHover, 0,0, gp.TileSize, gp.TileSize, null);}
        else {g2.drawImage(return_arrow, 0,0, gp.TileSize, gp.TileSize, null);}
        g2.setColor(Color.WHITE);
        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 50F));
        text = "Credits";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text,gp.ui.x - textLength / 2, textHeight);
        int spacing = textHeight;

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        text = "Master Procrastinator:";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text,gp.ui.x - textLength / 2, spacing + 5 + textHeight);
        text = "Andy Vo";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, spacing + 5 + 2 * textHeight);
        text = "Golden Gooner:";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, creditSpacing + spacing + 5 + 3 * textHeight);
        text = "Yibo Peng";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, creditSpacing + spacing + 5 + 4 * textHeight);
        text = "Self Proclaimed #1 Ducky Fighters Player:";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, 2 * creditSpacing + spacing + 5 + 5 * textHeight);
        text = "Aidan Lee";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, 2 * creditSpacing + spacing + 5 + 6 * textHeight);
        text = "Self Proclaimed #1 Kirby Player:";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, 3 * creditSpacing + spacing + 5 + 7 * textHeight);
        text = "The Big Q";
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text,gp.ui.x - textLength / 2, 3 * creditSpacing + spacing + 5 + 8 * textHeight);
    }

    private BufferedImage getImage(String filePath) {
        try {image = ImageIO.read((CharacterLoader.class.getClassLoader().getResourceAsStream("menu/" + filePath + ".png")));}
        catch (Exception e) {
            try {image = ImageIO.read(Objects.requireNonNull(CharacterLoader.class.getClassLoader().getResourceAsStream("menu/error/Missing_Tile.png"))); System.out.println("Error: " + filePath + " not found");} catch (
                    IOException ex) {throw new RuntimeException(ex);}}
        return image;
    }
}
