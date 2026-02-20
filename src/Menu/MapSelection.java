package Menu;

import Main.GamePanel;

import java.awt.*;

public class MapSelection {
    GamePanel gp;
    public MapSelection(GamePanel gp) {
        this.gp = gp;
    }

    public void drawMapSelection(Graphics2D g2) {
        gp.title.currentMenuState = gp.title.characterState;
    }
}
