package inputhandling;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ManIkWeet
 */
public class KeyBoard implements KeyListener {

    public static List<Key> keys = new ArrayList<Key>();
    public static Key up = new Key();
    public static Key down = new Key();
    public static Key left = new Key();
    public static Key right = new Key();
    public static Key space = new Key();
    public static Key w = new Key();
    public static Key a = new Key();
    public static Key s = new Key();
    public static Key d = new Key(); 
    public static Key i = new Key();
    public static Key j = new Key();
    public static Key k = new Key();
    public static Key l = new Key();  
    public static Key enter = new Key();  
    public static Key ctrl = new Key();
    
    public KeyBoard() {
        keys.add(up);
        keys.add(down);
        keys.add(left);
        keys.add(right);
        keys.add(space);
        keys.add(w);
        keys.add(a);
        keys.add(s);
        keys.add(d);
        keys.add(i);
        keys.add(j);
        keys.add(k);
        keys.add(l);
        keys.add(ctrl);
        keys.add(enter);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            
            case KeyEvent.VK_CONTROL:
                ctrl.nextState = true;
                break;
                
            case KeyEvent.VK_W:
                w.nextState = true;
                break;
                
            case KeyEvent.VK_ENTER:
                enter.nextState = true;
                break;

            case KeyEvent.VK_A:
                a.nextState = true;
                break;

             case KeyEvent.VK_S:
                s.nextState = true;
                break;
                
            case KeyEvent.VK_D:
                d.nextState = true;
                break;

            case KeyEvent.VK_I:
                i.nextState = true;
                break;

            case KeyEvent.VK_J:
                j.nextState = true;
                break;

             case KeyEvent.VK_K:
                k.nextState = true;
                break;
                
            case KeyEvent.VK_L:
                l.nextState = true;
                break;
                
            case KeyEvent.VK_UP:
                up.nextState = true;
                break;

            case KeyEvent.VK_DOWN:
                down.nextState = true;
                break;

            case KeyEvent.VK_LEFT:
                left.nextState = true;
                break;

            case KeyEvent.VK_RIGHT:
                right.nextState = true;
                break;

            case KeyEvent.VK_SPACE:
                space.nextState = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_CONTROL:
                ctrl.nextState = false;
                break;
                
            case KeyEvent.VK_ENTER:
                enter.nextState = false;
                break;
                
            case KeyEvent.VK_W:
                w.nextState = false;
                break;

            case KeyEvent.VK_A:
                a.nextState = false;
                break;

             case KeyEvent.VK_S:
                s.nextState = false;
                break;
                
            case KeyEvent.VK_D:
                d.nextState = false;
                break;
  
            case KeyEvent.VK_I:
                i.nextState = false;
                break;

            case KeyEvent.VK_J:
                j.nextState = false;
                break;

             case KeyEvent.VK_K:
                k.nextState = false;
                break;
                
            case KeyEvent.VK_L:
                l.nextState = false;
                break;                
                
            case KeyEvent.VK_UP:
                up.nextState = false;
                break;

            case KeyEvent.VK_DOWN:
                down.nextState = false;
                break;

            case KeyEvent.VK_LEFT:
                left.nextState = false;
                break;

            case KeyEvent.VK_RIGHT:
                right.nextState = false;
                break;

            case KeyEvent.VK_SPACE:
                space.nextState = false;
                break;
        }
    }

    public static void releaseAll() {
        for (Key key : keys) {
            key.release();
        }
    }

    public static void tick() {
        for (Key key : keys) {
            key.tick();
        }
    }
}
