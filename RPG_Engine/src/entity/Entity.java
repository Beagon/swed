package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import game.Art;
import game.Level;
import tiles.Tile;

/**
 *
 * @author ManIkWeet
 */
public class Entity {

    public int oldX;
    public int oldY;
    public int x;
    public int y;
    public int squareSize;

    public Entity(int x, int y, int squareSize) {
        oldX = x;
        oldY = y;
        this.x = x;
        this.y = y;
        this.squareSize = squareSize;
    }

    public boolean collidesWithTile(int nextX, int nextY) {

        int entityTileX = nextX / Tile.tileSize;
        int entityTileY = nextY
                / Tile.tileSize;
        try {
            if (nextX > x) {
                int entityTopRightTileX =
                        (nextX + squareSize - 1) / Tile.tileSize;
                int entityTopRightTileY =
                        entityTileY;
                int entityBottomRightTileX = entityTopRightTileX;
                int entityBottomRightTileY = (nextY + squareSize - 1) / Tile.tileSize;
                if (Level.tiles[entityTopRightTileX][entityTopRightTileY].collides || Level.tiles[entityBottomRightTileX][entityBottomRightTileY].collides) {
                    return true;
                }
            } else if (nextX < x) {
                int entityTopLeftTileX = entityTileX;
                int entityTopLeftTileY =
                        entityTileY;
                int entityBottomLeftTileX = entityTopLeftTileX;
                int entityBottomLeftTileY = (nextY + squareSize - 1) / Tile.tileSize;
                if (Level.tiles[entityTopLeftTileX][entityTopLeftTileY].collides || Level.tiles[entityBottomLeftTileX][entityBottomLeftTileY].collides) {
                    return true;
                }
            }
            if (nextY > y) {
                int entityBottomLeftTileX = entityTileX;
                int entityBottomLeftTileY =
                        (nextY + squareSize - 1) / Tile.tileSize;
                int entityBottomRightTileX = (nextX + squareSize - 1) / Tile.tileSize;;
                int entityBottomRightTileY = entityBottomLeftTileY;
                if (Level.tiles[entityBottomLeftTileX][entityBottomLeftTileY].collides || Level.tiles[entityBottomRightTileX][entityBottomRightTileY].collides) {
                    return true;
                }
            } else if (nextY < y) {
                int entityTopLeftTileX = entityTileX;
                int entityTopLeftTileY =
                        entityTileY;
                int entityTopRightTileX = (nextX + squareSize - 1) / Tile.tileSize;
                int entityTopRightTileY = entityTopLeftTileY;
                if (Level.tiles[entityTopLeftTileX][entityTopLeftTileY].collides || Level.tiles[entityTopRightTileX][entityTopRightTileY].collides) {
                    return true;
                }
            }
        } catch (Exception e) {
            return true;
        }

        return false;
        // <editor-fold defaultstate="collapsed" desc="old collision code">

//        int characterTileX = nextX / Tile.tileSize;
//        int characterTileY = nextY / Tile.tileSize;
//        System.out.println("X: " + characterTileX + "Y:" + characterTileY);
//        if (nextX % OverlapTile.tileSize != 0 && nextY % OverlapTile.tileSize != 0) {
//            if (Level.tiles[characterTileX][characterTileY].collides) {
//                return true;
//            }
//            if (Level.tiles[characterTileX + 1][characterTileY].collides) {
//                return true;
//            }
//            if (Level.tiles[characterTileX][characterTileY + 1].collides) {
//                return true;
//            }
//            if (Level.tiles[characterTileX
//                    + 1][characterTileY + 1].collides) {
//                return true;
//            }
//        } else if (nextX % OverlapTile.tileSize == 0) {
//            if (nextY
//                    % OverlapTile.tileSize != 0) {//als die false is, betekend het dat de entity PRECIES op één tile staat 
//                if (Level.tiles[characterTileX][characterTileY].collides) {
//                    return true;
//                }
//                if (Level.tiles[characterTileX][characterTileY + 1].collides) {//dit is nodig omdat een entity altijd net zo groot is als een tile 
//                    return true;
//                }
//            } else {
//                if (Level.tiles[characterTileX][characterTileY].collides) {
//                    return true;
//                }
//            }
//        } else if (nextY % OverlapTile.tileSize == 0) {
//            if (Level.tiles[characterTileX][characterTileY].collides) {
//                return true;
//            }
//            if (Level.tiles[characterTileX + 1][characterTileY].collides) {
//                return true;
//            }
//        }
//        return false;

        // </editor-fold>
    }

    public void updatePosition(int x, int y) {
        oldX = this.x;
        oldY = this.y;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics2D g2d, double interpolation, BufferedImage art) {
        int currentX = (int) ((x - oldX) * interpolation + oldX);
        int currentY = (int) ((y - oldY) * interpolation + oldY);
        g2d.drawImage(art, currentX - Level.currentX, currentY - Level.currentY, squareSize, squareSize, null);
    }
}
