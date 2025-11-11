package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, leftPressed, rightPressed, downPressed, upPressed2, leftPressed2, rightPressed2, downPressed2;
    public boolean spclPressed, spclPressed2, basicPressed, basicPressed2;
    public boolean gameClosePressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {upPressed = true;}
        if (code == KeyEvent.VK_A) {leftPressed = true;}
        if (code == KeyEvent.VK_S) {downPressed = true;}
        if (code == KeyEvent.VK_D) {rightPressed = true;}
        if (code == KeyEvent.VK_UP) {upPressed2 = true;}
        if (code == KeyEvent.VK_LEFT) {leftPressed2 = true;}
        if (code == KeyEvent.VK_DOWN) {downPressed2 = true;}
        if (code == KeyEvent.VK_RIGHT) {rightPressed2 = true;}
        if (code == KeyEvent.VK_Q) {spclPressed = true;}
        if (code == KeyEvent.VK_N) {spclPressed2 = true;}
        if (code == KeyEvent.VK_E) {basicPressed = true;}
        if (code == KeyEvent.VK_M) {basicPressed2 = true;}
        if (code == KeyEvent.VK_ESCAPE) {
            if(gp.gameState == gp.playState) {gp.gameState = gp.pauseState;}
            else if(gp.gameState == gp.pauseState) {gp.gameState = gp.playState;}
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {upPressed = false;}
        if (code == KeyEvent.VK_A) {leftPressed = false;}
        if (code == KeyEvent.VK_S) {downPressed = false;}
        if (code == KeyEvent.VK_D) {rightPressed = false;}
        if (code == KeyEvent.VK_UP) {upPressed2 = false;}
        if (code == KeyEvent.VK_LEFT) {leftPressed2 = false;}
        if (code == KeyEvent.VK_DOWN) {downPressed2 = false;}
        if (code == KeyEvent.VK_RIGHT) {rightPressed2 = false;}
        if (code == KeyEvent.VK_Q) {spclPressed = false;}
        if (code == KeyEvent.VK_N) {spclPressed2 = false;}
        if (code == KeyEvent.VK_E) {basicPressed = false;}
        if (code == KeyEvent.VK_M) {basicPressed2 = false;}
        if (code == KeyEvent.VK_ESCAPE) {gameClosePressed = false;}

    }
}
