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
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int ScreenWidth = maxScreenCol * TileSize;
    public final int ScreenHeight = maxScreenRow * TileSize;

    //For Full Screen
    int ScreenWidth2 = ScreenWidth;
    int ScreenHeight2 = ScreenHeight;
    BufferedImage tempScreen;
    Graphics g2;

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

    public void setupGame() {
        tempScreen = new BufferedImage(ScreenWidth, ScreenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = tempScreen.getGraphics();
        setFullScreen();
    }

    public void setFullScreen() {
        //Get Device Data
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //Get Full screen width and height
        ScreenHeight2 = Main.window.getHeight();
        ScreenWidth2 = Main.window.getWidth();
    }

    public void startGameThread() {
        game = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void closeProgram() {
        Main.window.dispose();
        game = false;
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
                drawToTempScreen();
                drawToScreen();
                delta--;
            }
        }
        System.out.println("Game Terminated");
        if (dead == 1) {System.out.print(" Player 1 died");}
        if (dead == 2) {System.out.print(" Player 2 died");}
    }

    public void update() {
        if(keyH.gameClosePressed) {closeProgram();}
        //Update Character position
        player.update();
        player2.update();
        projectiles.update();
        healthBar.checkDead();
        ultimate.update();
    }

    public void drawToTempScreen() {
        //Layer 1
        tileM.draw((Graphics2D) g2);
        //Layer2
        healthBar.draw((Graphics2D) g2);
        ultimateBar.draw((Graphics2D) g2);
        specialAttackBar.draw((Graphics2D) g2);
        //Layer 3
        player.draw((Graphics2D) g2);
        ultimate.drawP1((Graphics2D) g2);
        player2.draw((Graphics2D) g2);
        ultimate.drawP2((Graphics2D) g2);
        //Layer 4
        projectiles.draw((Graphics2D) g2);
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        if(g != null) {
            g.drawImage(tempScreen, 0, 0, ScreenWidth2, ScreenHeight2, null);
            g.dispose();
        }
    }
}
