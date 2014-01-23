package game;

import entity.Player;
import entity.TalkActions;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import tiles.Tile;

/**
 *
 * @author ManIkWeet
 */
public class Level {

    public static Tile[][] tiles;
    private Player player = new Player(16, 16, 0);
    private TalkActions talkactions;
    Graphics2D g2d;
    Game game;
    public static int currentX;
    public static int currentY;

    public Level(Tile[][] floorTiles, Graphics2D g2d, Game game) {
        Level.tiles = floorTiles;
        this.g2d = g2d;
        this.talkactions = new TalkActions(g2d, game);
    }

    public void tick() {
        talkactions.Tick();
        player.tick();        
    }

    public static Level loadLevel(String name, Graphics2D g2d, Game game) {
        BufferedImage levelImage = Art.get(name);
        Tile[][] newTiles = new Tile[levelImage.getWidth()][levelImage.getHeight()];
        for (int x = 0; x < newTiles.length; x++) {
            for (int y = 0; y < newTiles[x].length; y++) {
                int tileColor = levelImage.getRGB(x, y);
                int red = (tileColor & 0x00ff0000) >> 16;
                int green = (tileColor & 0x0000ff00) >> 8;
                int blue = tileColor & 0x000000ff;
                if (red != 0) {
                    switch (green) {
                        case 0:
                            newTiles[x][y] = new Tile(red, false, false);
                            break;
                        case 1:
                            newTiles[x][y] = new Tile(red, true, false);
                            break;
                        case 2:
                            newTiles[x][y] = new Tile(red, false, true);
                            break;
                        case 3:
                            newTiles[x][y] = new Tile(red, true, true);
                            break;
                    }
                }
            }
        }

        return new Level(newTiles, g2d, game);
    }

    void paint(Graphics2D g2d, double interpolation) {
        currentX = (int) ((player.x - player.oldX) * interpolation + player.oldX) - Game.GAME_WIDTH / 2 + (Player.mySquareSize / 2);
        currentY = (int) ((player.y - player.oldY) * interpolation + player.oldY) - Game.GAME_HEIGHT / 2 + (Player.mySquareSize / 2);
        int cameraTileX = currentX / Tile.tileSize;
        int cameraTileY = currentY / Tile.tileSize;
        int maxXTilesOnScreen = Game.GAME_WIDTH / Tile.tileSize + 1;
        int maxYTilesOnScreen = Game.GAME_HEIGHT / Tile.tileSize + 1;
        int smoothX = currentX % Tile.tileSize;
        int smoothY = currentY % Tile.tileSize;
        for (int x = 0; x < maxXTilesOnScreen; x++) {
            for (int y = 0; y < maxYTilesOnScreen; y++) {
                try {
                    if (tiles[x + cameraTileX][y + cameraTileY] != null) {
                        if (!tiles[x + cameraTileX][y + cameraTileY].overlaps) {
                            Tile ft = (Tile) tiles[x + cameraTileX][y + cameraTileY];
                            ft.paint(g2d, x * Tile.tileSize - smoothX, y * Tile.tileSize - smoothY);
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                }
            }
        }
        player.render(g2d, interpolation);
        maxYTilesOnScreen++;//want walltiles zijn wat hoger
        for (int x = 0; x < maxXTilesOnScreen; x++) {
            for (int y = 0; y < maxYTilesOnScreen; y++) {
                try {
                    if (tiles[x + cameraTileX][y + cameraTileY] != null) {
                        if (tiles[x + cameraTileX][y + cameraTileY].overlaps) {
                            tiles[x + cameraTileX][y + cameraTileY].paint(g2d, x * Tile.tileSize - smoothX, y * Tile.tileSize - smoothY);;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                }
            }
        }
    }
}
