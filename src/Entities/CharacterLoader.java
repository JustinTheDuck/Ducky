package Entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class CharacterLoader {
    static int xSpeed, maxHealth, ultimateDELAY, basicCOOLDOWN;
    static BufferedImage[] characterImages = new BufferedImage[12];
    static BufferedImage image;


    public static int getCharacterSpeed(String Character) {
        xSpeed = 1;
        switch(Character){
            case "Duck", "Rice", "Zombie":
                xSpeed = 4;
                break;
        }
        return xSpeed;
    }

    public static int getBasicCOODOWN(String Character) {
        switch(Character){
            case"Duck", "Zombie":
                basicCOOLDOWN = 60;
                break;
            case"Rice":
                basicCOOLDOWN = -1;
                break;
        }
        return basicCOOLDOWN;
    }

    public static int getCharacterMaxHealth(String Character) {
        maxHealth = 1000;
        switch(Character){
            case "Duck", "Rice", "Zombie":
                maxHealth = 100;
                break;
        }
        return maxHealth;
    }

    public static int getCharacterUltimateDelay(String Character) {
        switch(Character){
            case "Duck", "Zombie":
                ultimateDELAY = 5;
                break;
            case("Rice"):
                ultimateDELAY = 10;
                break;
        }
        return ultimateDELAY;
    }

    public static BufferedImage[] getPlayerImages(String character){
        characterImages[0] = getImage(character + "/character_images/" + character + "_RightWalk1");
        characterImages[1] = getImage(character + "/character_images/" + character + "_RightWalk2");
        characterImages[2] = getImage(character + "/character_images/" + character + "_RightWalk3");
        characterImages[3] = getImage(character + "/character_images/" + character + "_RightWalk4");
        characterImages[4] = getImage(character + "/character_images/" + character + "_LeftWalk1");
        characterImages[5] = getImage(character + "/character_images/" + character + "_LeftWalk2");
        characterImages[6] = getImage(character + "/character_images/" + character + "_LeftWalk3");
        characterImages[7] = getImage(character + "/character_images/" + character + "_LeftWalk4");
        characterImages[8] = getImage(character + "/character_images/" + character + "_RightJump");
        characterImages[9] = getImage(character + "/character_images/" + character + "_LeftJump");
        characterImages[10] = getImage(character + "/character_images/" + character + "_RightDown");
        characterImages[11] = getImage(character + "/character_images/" + character + "_LeftDown");
        return characterImages;
    }

    public static BufferedImage getImage(String filePath) {
        try {
            image = ImageIO.read((CharacterLoader.class.getClassLoader().getResourceAsStream("player/" + filePath + ".png")));
        } catch (Exception e) {
            try {image = ImageIO.read(Objects.requireNonNull(CharacterLoader.class.getClassLoader().getResourceAsStream("menu/error/Missing_Tile.png")));} catch (
                    IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return image;
    }
}
