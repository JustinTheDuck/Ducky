package GUI;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Controls {
    GamePanel gp;

    //IMAGES
    BufferedImage image;
    BufferedImage key_w, key_d, key_s, key_a, key_up, key_down, key_left, key_right, key_e, key_q, key_n, key_m;
    BufferedImage up, down, left, right;
    BufferedImage key_basic, key_ultimate, key_special;
    BufferedImage basic, ultimate, special, ultimate2;
    BufferedImage characterImage;
    BufferedImage[] duckImages = new BufferedImage[4];
    BufferedImage[] riceImages = new BufferedImage[4];
    BufferedImage[] zombieImages = new BufferedImage[4];
    BufferedImage[] guyImages = new BufferedImage[4];
    int specialScale;

    //IMAGE LOCATION OFFSETS FOR CHARACTER SPECIFIC ATTACKS
    int basicXOffset = 0, specialXOffset = 0, ultimateXOffset = 0, basicYOffset = 0, specialYOffset = 0, ultimateYOffset = 0;
    int ultimateXOffset2 = 0, ultimateYOffset2 = 0;

    //SCREEN DATA
    final int xOffset = 15;
    final int yOffset = 30;
    final int ultWidth = 300;

    //KEY DRAW INFO
    final int keyHeight = 9;
    final int keyWidth = 13;
    final int factor = 3;
    final int xKeyOffset = 5;

    //ATTACK IMAGE SCALING
    final int imageSize = 48;
    final int imageWidth = 120;

    //ATTACK DESCRIPTIONS
    String text, ultimateName;
    int textLength, textHeight;
    String basicText = "N/A";
    String specialText = "N/A";
    String ultimateText = "N/A";

    //CHARACTER CONTROL SELECTION
    int controlState;
    final int duckState = 1;
    final int riceState = 2;
    final int zombieState = 3;
    final int guyState = 4;

    //PLAYER SELECTION
    final int playerSelectionWidth = 48 * 7;
    int playerState = 1;
    BufferedImage leftArrow, rightArrow;
    
    public Controls(GamePanel gp) {
        this.gp = gp;
        controlState = duckState;
        getImages();
    }

    //LOADS ALL IMAGES (AT THE LAUNCH OF GAME)
    public void getImages() {
        key_w = getImage("keyboard_icons/key_w");
        key_a = getImage("keyboard_icons/key_a");
        key_s = getImage("keyboard_icons/key_s");
        key_d = getImage("keyboard_icons/key_d");
        key_e = getImage("keyboard_icons/key_e");
        key_q = getImage("keyboard_icons/key_q");
        key_up = getImage("keyboard_icons/key_up");
        key_left = getImage("keyboard_icons/key_left");
        key_right = getImage("keyboard_icons/key_right");
        key_down = getImage("keyboard_icons/key_down");
        key_n = getImage("keyboard_icons/key_n");
        key_m = getImage("keyboard_icons/key_m");
        up = getImage("movement/ArrowUp");
        left = getImage("movement/ArrowLeft");
        right = getImage("movement/ArrowRight");
        down = getImage("movement/ArrowDown");
        duckImages[0] = gp.ultimate.duckUltimate.basicRight;
        duckImages[1] = (BufferedImage) gp.projectiles.fireball[0];
        duckImages[2] = gp.ultimate.duckUltimate.ultimate_1;
        riceImages[1] = (BufferedImage) gp.projectiles.rice_bag[0];
        riceImages[2] = gp.ultimate.riceUltimate.ultimateRight;
        riceImages[3] = (BufferedImage) gp.projectiles.slash[0];
        zombieImages[0] = gp.ultimate.zombieUltimate.basicRight;
        zombieImages[1] = (BufferedImage) gp.projectiles.fart[0];
        zombieImages[2] = gp.ultimate.zombieUltimate.ultimate;
        guyImages[1] = gp.ultimate.guyUltimate.meth;
        guyImages[2] = (BufferedImage) gp.projectiles.meatball[0];
        leftArrow = getImage("character_arrows/next_left");
        rightArrow = getImage("character_arrows/next_right");
    }

    public void draw(Graphics2D g2) {
        if(playerState == 1) {key_basic = key_e; key_special = key_q; key_ultimate = key_s; characterImage = gp.player.right1;} else if(playerState == 2) {key_basic = key_n; key_special = key_m; key_ultimate = key_down; characterImage = gp.player2.right1;}
        switch(controlState) {
            case duckState:
                basic = duckImages[0]; special = duckImages[1]; ultimate = duckImages[2]; ultimate2 = duckImages[3];
                basicXOffset = 10; basicYOffset = 0; specialXOffset = 20; specialYOffset = 0; ultimateXOffset = -5; ultimateYOffset = 20; specialScale = 1;
                basicText = "Punches enemy player\n(If in range)";
                specialText = "Launches a Fireball\nin direction player is facing";
                ultimateName = "Smash Attack"; ultimateText = "Duck SMASHES down\ndoes damage based on how fast\nand how close Duck is to enemy";
                break;
            case riceState:
                basic = riceImages[0]; special = riceImages[1]; ultimate = riceImages[2]; ultimate2 = riceImages[3];
                specialXOffset = 20; specialYOffset = 0; ultimateXOffset = 0; ultimateYOffset = 0; ultimateXOffset2 = imageSize * 2; ultimateYOffset2 = 0; specialScale = 1;
                specialText = "Launches a Rice Bag\nin direction player is facing";
                ultimateName = "Ching Chong"; ultimateText = "Rice Farmer unlocks full potential\nand uses scythe to attack\ninstead of rice bags\n(Launched with special key)";
                break;
            case zombieState:
                basic = zombieImages[0]; special = zombieImages[1]; ultimate = zombieImages[2]; ultimate2 = zombieImages[3];
                basicXOffset = 0; basicYOffset = 0; specialXOffset = 5; specialYOffset = 0; ultimateXOffset = 0; ultimateYOffset = 0; specialScale = 2;
                basicText = "Punches enemy player\n(If in range)";
                specialText = "Launches a Poison Cloud (Fart)\nin direction player is facing";
                ultimateName = "Poison Apple"; ultimateText = "Zombie spawns a poisonous apple\nwhich does deals \npermanent poison damage\nand how close the apple\nlands to the enemy";
                break;
            case guyState:
                basic = guyImages[0]; special = guyImages[1]; ultimate = guyImages[2]; ultimate2 = guyImages[3];
                specialXOffset = 0; specialYOffset = 10; ultimateYOffset = 20; ultimateXOffset = 0;
                specialText = "Places a spike\ndeals damage if enemy lands on it";
                ultimateName = "Meatball Rain"; ultimateText = "Rains fireballs from the sky\ndeals damage if it hits enemy";
                break;
        }

        //BACKGROUND
        Color c = new Color(0,0,0, 100);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x - gp.ui.width, gp.ui.y - gp.ui.height / 2, gp.ui.width * 2, gp.ui.height, 70, 70);
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(gp.ui.borderThickness));
        g2.drawRoundRect(gp.ui.x - gp.ui.width, gp.ui.y - gp.ui.height / 2, gp.ui.width * 2, gp.ui.height, 50, 50);

        //PLAYER SELECTION
        c = new Color(36, 52, 71);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x - playerSelectionWidth / 2, gp.ui.y - gp.ui.height / 2 - yOffset / 2 + yOffset, playerSelectionWidth, 2 * yOffset, 30, 30);
        g2.drawImage(leftArrow, gp.ui.x - playerSelectionWidth / 2, gp.ui.y - gp.ui.height / 2 + yOffset / 2 + 5, 2 * yOffset, 2 * (yOffset - 5), null);
        g2.drawImage(rightArrow, gp.ui.x - playerSelectionWidth / 2 + playerSelectionWidth - 2 * yOffset, gp.ui.y - gp.ui.height / 2 + yOffset / 2 + 5, 2 * yOffset, 2 * (yOffset - 5), null);
        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 35F));
        g2.setColor(Color.white);
        if(playerState == 1){text = "Player 1";}
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text, gp.ui.x - textLength / 2, gp.ui.y - gp.ui.height / 2 - yOffset / 2 + yOffset + textHeight);

        //ULTIMATE BOX
        c = new Color(255, 10, 255);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x + gp.ui.width - ultWidth - xOffset, gp.ui.y - gp.ui.height / 2 + 3 * yOffset, ultWidth, gp.ui.height - 4 * yOffset, 30, 30);

        //BASIC BOX
        c = new Color(0, 100, 255);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x - gp.ui.width + xOffset, gp.ui.y - gp.ui.height / 2 + 3 * yOffset, gp.ui.width * 2 - 3 * xOffset - ultWidth, (gp.ui.height - 5 * yOffset) / 4, 30, 30);

        //SPECIAL BOX
        c = new Color(255, 50, 50);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x - gp.ui.width + xOffset, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4, gp.ui.width * 2 - 3 * xOffset - ultWidth, (gp.ui.height - 5 * yOffset) / 4, 30, 30);

        //MOVEMENT BOX
        c = new Color(255, 200, 0);
        g2.setColor(c);
        g2.fillRoundRect(gp.ui.x - gp.ui.width + xOffset, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4, gp.ui.width * 2 - 3 * xOffset - ultWidth, (gp.ui.height - 5 * yOffset) / 2, 30, 30);
        //KEYS FOR MOVEMENT
        if(playerState == 1) {
            g2.drawImage(key_a, gp.ui.x - gp.ui.width + xOffset + xKeyOffset, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor / 2 - keyHeight * factor, keyWidth * factor, keyHeight * factor, null);
            g2.drawImage(key_w, gp.ui.x - gp.ui.width + xOffset + xKeyOffset + keyWidth * factor / 2, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 - keyHeight * factor / 2 - keyHeight * factor, keyWidth * factor, keyHeight * factor, null);
            g2.drawImage(key_d, gp.ui.x - gp.ui.width + xOffset + xKeyOffset + keyWidth * factor, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor / 2 - keyHeight * factor, keyWidth * factor, keyHeight * factor, null);
        } else if (playerState == 2) {
            g2.drawImage(key_left, gp.ui.x - gp.ui.width + xOffset + xKeyOffset, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor / 2 - keyHeight * factor, keyWidth * factor, keyHeight * factor, null);
            g2.drawImage(key_up, gp.ui.x - gp.ui.width + xOffset + xKeyOffset + keyWidth * factor / 2, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 - keyHeight * factor / 2 - keyHeight * factor, keyWidth * factor, keyHeight * factor, null);
            g2.drawImage(key_right, gp.ui.x - gp.ui.width + xOffset + xKeyOffset + keyWidth * factor, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor / 2 - keyHeight * factor, keyWidth * factor, keyHeight * factor, null);
        }

        //AREA FOR ATTACK IMAGES
        c = new Color(179, 229, 252);
        g2.setColor(c);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4, imageWidth, (gp.ui.height - 5 * yOffset) / 2);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4, imageWidth, (gp.ui.height - 5 * yOffset) / 4);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor, gp.ui.y - gp.ui.height / 2 + 3 * yOffset, imageWidth, (gp.ui.height - 5 * yOffset) / 4);
        g2.fillRect(gp.ui.x + gp.ui.width - ultWidth - xOffset, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + textHeight + 5, ultWidth, (gp.ui.height + 5 * yOffset / 2) / 4);

        //ULTIMATE
        g2.setFont(gp.ui.TimesNewRoman);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        g2.setColor(Color.white);
        text = "Ultimate";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        g2.drawString(text, gp.ui.x + gp.ui.width - ultWidth + xOffset + factor * keyWidth, gp.ui.y - gp.ui.height / 2 + 3 * yOffset + textHeight);
        g2.drawImage(characterImage, gp.ui.x + gp.ui.width - ultWidth - xOffset + ultWidth/2 - imageSize, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + textHeight + (gp.ui.height + 5 * yOffset / 2) / 8 - imageSize, imageSize * 2, imageSize * 2, null);

        textHeight = (int) g2.getFontMetrics().getStringBounds(ultimateName, g2).getHeight();
        g2.drawString(ultimateName, gp.ui.x + gp.ui.width - ultWidth - xOffset, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + 2 * textHeight + (gp.ui.height + 5 * yOffset / 2) / 4);
        g2.fillRoundRect(gp.ui.x + gp.ui.width - ultWidth - xOffset, 5 + gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + 2 * textHeight + (gp.ui.height + 5 * yOffset / 2) / 4 , ultWidth, gp.ui.height - 4 * yOffset - (gp.ui.height + 5 * yOffset / 2) / 4 - 2 * textHeight - 20, 30, 30);
        g2.fillRect(gp.ui.x + gp.ui.width - ultWidth - xOffset, 5 + gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + 2 * textHeight + (gp.ui.height + 5 * yOffset / 2) / 4, ultWidth, 30);


        text = "Basic Movement";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth + xOffset, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + yOffset);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + textHeight, gp.ui.width * 2 - 3 * xOffset - ultWidth - imageWidth - 2 * factor * keyWidth - 2 * xKeyOffset, (gp.ui.height - 5 * yOffset) / 2 - textHeight - 15);
        g2.fillRoundRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + textHeight + (gp.ui.height - 5 * yOffset) / 2 - textHeight - 30, gp.ui.width * 2 - 3 * xOffset - ultWidth - imageWidth - 2 * factor * keyWidth - 2 * xKeyOffset, 30, 30, 30);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + textHeight + (gp.ui.height - 5 * yOffset) / 2 - textHeight - 30, 30, 30);

        g2.drawImage(characterImage, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - imageSize/2, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor/2 - keyHeight * factor - imageSize / 2, imageSize, imageSize, null);
        g2.drawImage(left, gp.ui.x - gp.ui.width + xOffset/2 + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - imageSize, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor/2 - keyHeight * factor - imageSize / 4, imageSize / 2, imageSize / 2, null);
        g2.drawImage(right, gp.ui.x - gp.ui.width - xOffset/2 + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 + imageSize, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor/2 - keyHeight * factor - imageSize / 4, imageSize / 2, imageSize / 2, null);
        g2.drawImage(up, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - imageSize/4, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 4 + keyHeight * factor/2 - keyHeight * factor - imageSize / 2 - yOffset, imageSize / 2, imageSize / 2, null);

        text = "Basic Attack";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth + xOffset, gp.ui.y - gp.ui.height / 2 + 4 * yOffset);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + textHeight + 5, gp.ui.width * 2 - 3 * xOffset - ultWidth - imageWidth - 2 * factor * keyWidth - 2 * xKeyOffset, (gp.ui.height - 5 * yOffset) / 4 - textHeight - 15);
        g2.fillRoundRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + textHeight + 5, gp.ui.width * 2 - 3 * xOffset - ultWidth - imageWidth - 2 * factor * keyWidth - 2 * xKeyOffset, 25, 30, 30);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + textHeight + 5, 30, 25);
        g2.drawImage(characterImage, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - imageSize/2, gp.ui.y - gp.ui.height / 2 + 3 * yOffset + imageSize/2, imageSize, imageSize, null);

        text = "Special Attack";
        textHeight = (int) g2.getFontMetrics().getStringBounds(text, g2).getHeight();
        g2.drawString(text, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth + xOffset, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + yOffset);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 5, gp.ui.width * 2 - 3 * xOffset - ultWidth - imageWidth - 2 * factor * keyWidth - 2 * xKeyOffset, (gp.ui.height - 5 * yOffset) / 4 - textHeight - 15);
        g2.fillRoundRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + textHeight + 5 + (gp.ui.height - 5 * yOffset) / 4 - textHeight - 30, gp.ui.width * 2 - 3 * xOffset - ultWidth - imageWidth - 2 * factor * keyWidth - 2 * xKeyOffset, 25, 30, 30);
        g2.fillRect(gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + textHeight + 5 + (gp.ui.height - 5 * yOffset) / 4 - textHeight - 30, 30, 25);
        g2.drawImage(characterImage, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - imageSize/2, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + ((gp.ui.height - 5 * yOffset) / 4 - imageSize) / 2, imageSize, imageSize, null);

        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 20F));
        drawStringWithNewLines("Strafe Up\nStrafe Left\nStrafe Right", gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth + xOffset, gp.ui.y - gp.ui.height / 2 + 4 * yOffset + 2 * (gp.ui.height - 5 * yOffset) / 4 + textHeight + yOffset, g2);
        drawStringWithNewLines(specialText, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth + xOffset,gp.ui.y - gp.ui.height / 2 + 8 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + textHeight + 5, g2);
        drawStringWithNewLines(basicText, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth + xOffset,gp.ui.y - gp.ui.height / 2 + 9 * yOffset / 2 + 5, g2);
        drawStringWithNewLines(ultimateText, gp.ui.x + gp.ui.width - ultWidth - xOffset / 2, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + 2 * 3 * textHeight / 2 + (gp.ui.height + 5 * yOffset / 2) / 4, g2);

        //DRAWS KEYS AND ATTACK IMAGES
        g2.drawImage(basic, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - imageSize/2 + basicXOffset, gp.ui.y - gp.ui.height / 2 + 3 * yOffset + imageSize/2 + basicYOffset, imageSize, imageSize, null);
        g2.drawImage(key_basic, gp.ui.x - gp.ui.width + xOffset + xKeyOffset + keyWidth * factor / 2, gp.ui.y - gp.ui.height / 2 + 3 * yOffset + (gp.ui.height - 5 * yOffset) / 8 - keyHeight * factor / 2, keyWidth * factor, keyHeight * factor, null);
        g2.drawImage(special, gp.ui.x - gp.ui.width + xOffset + 2 * xKeyOffset + 2 * keyWidth * factor + imageWidth/2 - (specialScale * imageSize) /2 + specialXOffset, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + ((gp.ui.height - 5 * yOffset) / 4 - specialScale * imageSize) / 2 + specialYOffset, specialScale * imageSize, specialScale * imageSize, null);
        g2.drawImage(key_special, gp.ui.x - gp.ui.width + xOffset + xKeyOffset + keyWidth * factor / 2, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + (gp.ui.height - 5 * yOffset) / 4 + (gp.ui.height - 5 * yOffset) / 8 - keyHeight * factor / 2, keyWidth * factor, keyHeight * factor, null);
        g2.drawImage(ultimate, gp.ui.x + gp.ui.width - ultWidth - xOffset + ultWidth/2 - imageSize + ultimateXOffset, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + textHeight + (gp.ui.height + 5 * yOffset / 2) / 8 - imageSize + ultimateYOffset, 2 * imageSize, 2 * imageSize, null);
        g2.drawImage(ultimate2, gp.ui.x + gp.ui.width - ultWidth - xOffset + ultWidth/2 - imageSize + ultimateXOffset2, gp.ui.y - gp.ui.height / 2 + 7 * yOffset / 2 + textHeight + (gp.ui.height + 5 * yOffset / 2) / 8 - imageSize + ultimateYOffset2, 2 * imageSize, 2 * imageSize, null);
        g2.drawImage(key_ultimate, gp.ui.x + gp.ui.width - ultWidth + factor * keyWidth / 2 - xOffset, gp.ui.y - gp.ui.height / 2 + 3 * yOffset + textHeight / 2 - factor * keyHeight / 4, factor * keyWidth, factor * keyHeight, null);
    }

    public BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("UI/Controls/" + filePath + ".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void drawStringWithNewLines(String string, int x, int y, Graphics2D g2) {
        String[] lines = string.split("\n");

        FontMetrics fm = g2.getFontMetrics();

        for (String line : lines) {
            g2.drawString(line, x, y);
            y += fm.getHeight(); // Move down for next line
        }
    }
}
