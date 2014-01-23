package RPGLevelEditor;

import java.awt.Graphics2D;

/**
 *
 * @author ManIkWeet
 */
public class Tile {

    int art = 0;
    int x;
    int y;
    public boolean collides = false;
    public boolean overlaps = false;
    public boolean overlapsLoad = false;

    public Tile(int art, int x, int y, boolean collides, boolean overlaps) {
        this.overlapsLoad = false;
        this.art = art;
        this.x = x;
        this.y = y;
        this.collides = collides;
        this.overlaps = overlaps;
    }
    
    public Tile(int art, int x, int y, boolean collides, boolean overlaps, boolean overlapsLoad) {
        this.overlapsLoad = overlapsLoad;
        this.art = art;
        this.x = x;
        this.y = y;
        this.collides = collides;
        this.overlaps = overlaps;
    }

    public void render(Graphics2D g) {
        if (overlaps) {
            if(overlapsLoad){
            g.drawImage(Art.overlapTiles.get(art), x, y - 8, null);               
            }else{
            g.drawImage(Art.overlapTiles.get(255 - art), x, y - 8, null);
            }
        } else {
            g.drawImage(Art.squareTiles.get(art), x, y, null);
        }
    }
}
