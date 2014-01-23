package inputhandling;

/**
 *
 * @author ManIkWeet
 */
public class Key {

    public boolean isDown = false;
    public boolean wasDown = false;
    public boolean nextState = false;
    
    public void tick() {
        wasDown=isDown;
        isDown=nextState;
    }
    
    public boolean wasPressed() {
        return !wasDown && isDown;
    }
    
    public boolean wasReleased() {
        return wasDown && !isDown;
    }
    
    public void release() {
        nextState = false;
    }
}
