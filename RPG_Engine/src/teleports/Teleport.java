package teleports;

import java.awt.Point;

/**
 *
 * @author ManIkWeet
 */
public class Teleport {

    int fromx, fromy, tox, toy, effect;

    public Teleport(int fromx, int fromy, int tox, int toy, int effect) {
        this.fromx = fromx;
        this.fromy = fromy;
        this.tox = tox;
        this.toy = toy;
        this.effect = effect;
    }

    public Point getFrom() {
        return new Point(fromx, fromy);
    }

    public Point getTo() {
        return new Point(tox, toy);
    }
    
    public int getEffect() {
        return effect;
    }
}
