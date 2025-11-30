package Main;

import Entities.Player;
import Entities.Player2;
import GUI.SpecialAttackBar;
import GUI.UI;
import GUI.UltimateBar;
import Objects.Projectiles;
import GUI.HealthBar;

import Ultimate.Ultimate;
import Tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable {
    //Screen Settings
    final int originalTileSize = 24;
    final int Scale = 2;

    public int dead;

    public final int TileSize = originalTileSize * Scale; //48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int ScreenWidth = maxScreenCol * TileSize;
    public final int ScreenHeight = maxScreenRow * TileSize;

    //For Full Screen
    public int ScreenWidth2 = ScreenWidth;
    public int ScreenHeight2 = ScreenHeight;
    BufferedImage tempScreen;
    Graphics g2;

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);

    public Player player = new Player(this, keyH);
    public Player2 player2 = new Player2(this, keyH);
    public Projectiles projectiles = new Projectiles(this);
    public HealthBar healthBar = new HealthBar(this);
    public UltimateBar ultimateBar = new UltimateBar(this);
    public SpecialAttackBar specialAttackBar = new SpecialAttackBar(this);
    public Ultimate ultimate = new Ultimate(this, keyH);
    public UI ui = new UI(this, keyH);

    //GAME STATE
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int lossState = 3;

    public GamePanel() {
        this.setPreferredSize(new Dimension(ScreenWidth, ScreenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
        this.addMouseMotionListener(mouseH);
    }

    public void setupGame() {
        gameState = playState;
        tempScreen = new BufferedImage(ScreenWidth, ScreenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = tempScreen.getGraphics();

        ui.widthFactor = ScreenWidth2/ScreenWidth;
        ui.heightFactor = ScreenHeight2/ScreenHeight;

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

        ui.widthFactor = (double) Main.window.getWidth() /ScreenWidth;
        ui.heightFactor = (double) Main.window.getHeight() /ScreenHeight;
    }

    public void restoreOriginalSize() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(null);
        ScreenHeight2 = ScreenHeight;
        ScreenWidth2 = ScreenWidth;

        ui.widthFactor = 1;
        ui.heightFactor = 1;

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void closeWindow() {Main.window.dispose(); System.exit(0);}

    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
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
    }

    public void update() {
        if(gameState == playState) {
            //Update Character position
            player.update();
            player2.update();
            projectiles.update();
            healthBar.checkDead();
            ultimate.update();
        } else if (gameState == lossState && keyH.gameClosePressed) {closeWindow();}
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
        //Layer 5
        ui.draw((Graphics2D) g2);
    }

    public void drawToScreen() {
        Graphics g = getGraphics();
        if(g != null) {
            g.drawImage(tempScreen, 0, 0, ScreenWidth2, ScreenHeight2, null);
            g.dispose();
        }
    }
}
