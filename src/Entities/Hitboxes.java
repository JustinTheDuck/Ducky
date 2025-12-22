package Entities;

import Main.GamePanel;
import Objects.Projectiles;

import java.awt.*;

public class Hitboxes {
    GamePanel gp;

    final int borderSize = 2;
    public Hitboxes(GamePanel gp) {
        this.gp = gp;
    }

    public void drawHitboxes(Graphics2D g2) {
        g2.setColor(Color.RED);
        for(int i = 0; i < Projectiles.attacks.size(); i++) {
            if(Projectiles.attacks.size() < 5) {break;}
            if(i % 14 == 0) {
                g2.fillRect((int) Projectiles.attacks.get(i + 1), (int) Projectiles.attacks.get(i + 2),(int) ((Rectangle) Projectiles.attacks.get(i + 12)).getWidth(), borderSize);
                g2.fillRect((int) Projectiles.attacks.get(i + 1), (int) Projectiles.attacks.get(i + 2), borderSize,(int) ((Rectangle) Projectiles.attacks.get(i + 12)).getHeight());
                g2.fillRect((int) Projectiles.attacks.get(i + 1) + (int) ((Rectangle) Projectiles.attacks.get(i + 12)).getWidth() - borderSize, (int) Projectiles.attacks.get(i + 2), borderSize,(int) ((Rectangle) Projectiles.attacks.get(i + 12)).getHeight());
                g2.fillRect((int) Projectiles.attacks.get(i + 1) , (int) Projectiles.attacks.get(i + 2) + (int) ((Rectangle) Projectiles.attacks.get(i + 12)).getHeight() - borderSize,(int) ((Rectangle) Projectiles.attacks.get(i + 12)).getWidth(), borderSize);
            }
        }
        g2.fillRect(gp.player.x + gp.player.solidArea.x, gp.player.y + gp.player.solidArea.y, gp.player.solidArea.width, borderSize);
        g2.fillRect(gp.player.x + gp.player.solidArea.x,gp.player.y + gp.player.solidArea.y, borderSize, gp.player.solidArea.height);
        g2.fillRect(gp.player.x + gp.player.solidArea.x + gp.player.solidArea.width - borderSize, gp.player.y + gp.player.solidArea.y, borderSize, gp.player.solidArea.height);
        g2.fillRect(gp.player.x + gp.player.solidArea.x, gp.player.y + 2 * gp.player.solidArea.y + gp.player.solidArea.height - borderSize,gp.player.solidArea.width, borderSize);
        g2.fillRect(gp.player2.x + gp.player2.solidArea.x, gp.player2.y + gp.player2.solidArea.y, gp.player2.solidArea.width, borderSize);
        g2.fillRect(gp.player2.x + gp.player2.solidArea.x,gp.player2.y + gp.player2.solidArea.y, borderSize, gp.player2.solidArea.height);
        g2.fillRect(gp.player2.x + gp.player2.solidArea.x + gp.player2.solidArea.width - borderSize, gp.player2.y + gp.player2.solidArea.y, borderSize, gp.player2.solidArea.height);
        g2.fillRect(gp.player2.x + gp.player2.solidArea.x, gp.player2.y + 2 * gp.player2.solidArea.y + gp.player2.solidArea.height - borderSize,gp.player2.solidArea.width, borderSize);
        if(gp.player.Character.equals("Guy")) {
            g2.fillRect(gp.player.x + gp.player.solidArea.x, gp.player.y + gp.player.solidArea.y, gp.player.solidArea.width, borderSize);
            g2.fillRect(gp.player.x + gp.player.solidArea.x,gp.player.y + gp.player.solidArea.y, borderSize, gp.player.solidArea.height);
            g2.fillRect(gp.player.x + gp.player.solidArea.x + gp.player.solidArea.width - borderSize, gp.player.y + gp.player.solidArea.y, borderSize, gp.player.solidArea.height);
            g2.fillRect(gp.player.x + gp.player.solidArea.x, gp.player.y + 2 * gp.player.solidArea.y + gp.player.solidArea.height - borderSize,gp.player.solidArea.width, borderSize);
        }
    }
}
