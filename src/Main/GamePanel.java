package Main;

import Entities.Player;
import Entities.Player2;
import GUI.SpecialAttackBar;
import GUI.UltimateBar;
import Objects.Projectiles;
import GUI.HealthBar;

import Ultimate.Ultimate;
import Tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    final int originalTileSize = 24;
    final int Scale = 2;

    public boolean game;
    public int dead;

    public final int TileSize = originalTileSize * Scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int ScreenWidth = maxScreenCol * TileSize;
    public final int ScreenHeight = maxScreenRow * TileSize;

    BufferedImage end, image;

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);

    public Player player = new Player(this, keyH);
    public Player2 player2 = new Player2(this, keyH);
    public Projectiles projectiles = new Projectiles(this);
    public HealthBar healthBar = new HealthBar(this);
    public UltimateBar ultimateBar = new UltimateBar(this);
    public SpecialAttackBar specialAttackBar = new SpecialAttackBar(this);
    public Ultimate ultimate = new Ultimate(this, keyH);

    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        game = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null && game) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
        System.out.println("Game Terminated");
        if (dead == 1) {System.out.print(" Player 1 died");}
        if (dead == 2) {System.out.print(" Player 2 died");}
    }

    public void update() {
        //Update Character position
        player.update();
        player2.update();
        projectiles.update();
        healthBar.checkDead();
        ultimate.update();
    }

    public void paintComponent(Graphics g) {
        //Repaint screen with the updated information
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Layer 1
        tileM.draw(g2);
        //Layer2
        healthBar.draw(g2);
        ultimateBar.draw(g2);
        specialAttackBar.draw(g2);
        //Layer 3
        player.draw(g2);
        ultimate.drawP1(g2);
        player2.draw(g2);
        ultimate.drawP2(g2);
        //Layer 4
        projectiles.draw(g2);
        g2.dispose();
    }
}
