package tiles;

import java.awt.Graphics2D;
import game.Art;

/**
 *
 * @author ManIkWeet
 */
public class Tile {

    public static final int tileSize = 16;
    int art;
    public boolean collides = false;
    public boolean overlaps = false;
    int overlapSize = 8;

    public Tile(int art, boolean collides, boolean overlaps) {
        this.art = art;
        this.collides = collides;
        this.overlaps = overlaps;
    }

    public void paint(Graphics2D g, int x, int y) {

        if (overlaps) {
            g.drawImage(Art.overlapTiles.get(art), x, y - overlapSize, null);
        } else {
            g.drawImage(Art.floorTiles.get(art), x, y, null);
        }
    }
}
