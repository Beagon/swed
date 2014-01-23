package entity;

import inputhandling.KeyBoard;
import java.awt.Graphics2D;
import java.awt.Point;
import game.Art;
import teleports.TeleportList;

/**
 *
 * @author ManIkWeet
 */
public class Player extends Entity {

    public static final int DIRECTION_UP = 0;
    public static final int DIRECTION_RIGHT = 1;
    public static final int DIRECTION_DOWN = 2;
    public static final int DIRECTION_LEFT = 3;
    int direction;
    int tickCounter = 0;
    public static final int mySquareSize = 16;
    int toMove = 0;
    boolean willTeleport = false;
    Point teleportTo = null;
    boolean moving = false;

    public Player(int x, int y, int direction) {
        super(x, y, mySquareSize);
        this.direction = direction;
    }

    public void tick() {
        oldX = x;//deze variablen komen van de Entity, dat is wat extends doet
        oldY = y;//deze variablen komen van de Entity, dat is wat extends doet
        int stepSize = 2;
        KeyBoard.tick();
        if (toMove == 0) {
            if (KeyBoard.up.isDown || KeyBoard.w.isDown || KeyBoard.i.isDown) {
                direction = DIRECTION_UP;
                toMove = mySquareSize;
            } else if (KeyBoard.down.isDown || KeyBoard.s.isDown || KeyBoard.k.isDown) {
                direction = DIRECTION_DOWN;
                toMove = mySquareSize;
            } else if (KeyBoard.left.isDown || KeyBoard.a.isDown || KeyBoard.j.isDown) {
                direction = DIRECTION_LEFT;
                toMove = mySquareSize;
            } else if (KeyBoard.right.isDown || KeyBoard.d.isDown || KeyBoard.l.isDown) {
                direction = DIRECTION_RIGHT;
                toMove = mySquareSize;
            }
            if (KeyBoard.space.isDown) {
                System.out.println("x=" + x / 16 + "y=" + y / 16);
            }
        }
        if (toMove != 0) {
            moving = true;
            if (toMove == mySquareSize) {
                Point newPlace = null;
                switch (direction) {
                    case DIRECTION_UP:
                        newPlace = new Point(x / 16, (y - 16) / 16);
                        break;
                    case DIRECTION_DOWN:
                        newPlace = new Point(x / 16, (y + 16) / 16);
                        break;
                    case DIRECTION_LEFT:
                        newPlace = new Point((x - 16) / 16, y / 16);
                        break;
                    case DIRECTION_RIGHT:
                        newPlace = new Point((x + 16) / 16, y / 16);
                        break;
                }
                for (int i = 0; i < TeleportList.teleports.size(); i++) {
                    if (TeleportList.teleports.get(i).getFrom().equals(newPlace)) {
                        System.out.println("teleport!");
                        willTeleport = true;
                        teleportTo = TeleportList.teleports.get(i).getTo();
                    }
                }
                // <editor-fold defaultstate="collapsed" desc="collision detection">
                switch (direction) {
                    case DIRECTION_UP:
                        if (collidesWithTile(x, y - mySquareSize)) {
                            toMove = 0;
                        } else {
                            y -= stepSize;
                            toMove -= stepSize;
                        }
                        break;
                    case DIRECTION_DOWN:
                        if (collidesWithTile(x, y + mySquareSize)) {
                            toMove = 0;
                        } else {
                            y += stepSize;
                            toMove -= stepSize;
                        }
                        break;
                    case DIRECTION_LEFT:
                        if (collidesWithTile(x - mySquareSize, y)) {
                            toMove = 0;
                        } else {
                            x -= stepSize;
                            toMove -= stepSize;
                        }
                        break;
                    case DIRECTION_RIGHT:
                        if (collidesWithTile(x + mySquareSize, y)) {
                            toMove = 0;
                        } else {
                            x += stepSize;
                            toMove -= stepSize;
                        }
                        break;
                }//</editor-fold>


            } else {
                switch (direction) {
                    case DIRECTION_UP:
                        y -= stepSize;
                        toMove -= stepSize;
                        break;
                    case DIRECTION_DOWN:
                        y += stepSize;
                        toMove -= stepSize;
                        break;
                    case DIRECTION_LEFT:
                        x -= stepSize;
                        toMove -= stepSize;
                        break;
                    case DIRECTION_RIGHT:
                        x += stepSize;
                        toMove -= stepSize;
                        break;
                }
                if (toMove == 0 && willTeleport) {
                    x = teleportTo.x * 16;
                    y = teleportTo.y * 16;
                    oldX = x;
                    oldY = y;
                    direction = DIRECTION_DOWN;
                    willTeleport = false;
                }
            }
            tickCounter++;
            if (tickCounter >= 16) {
                tickCounter = 0;
            }//!collidesWithTile(x, y + stepSize)
        } else {
            moving = false;
        }
    }

    public void render(Graphics2D g2d, double interpolation) {
        if (!moving) {
            super.render(g2d, interpolation, Art.character.get(direction * 3));
        } else {
            if (tickCounter < 4) {
                super.render(g2d, interpolation, Art.character.get(direction * 3 + 1));
            } else if (tickCounter < 8) {
                super.render(g2d, interpolation, Art.character.get(direction * 3));
            } else if (tickCounter < 12) {
                super.render(g2d, interpolation, Art.character.get(direction * 3 + 2));
            } else {
                super.render(g2d, interpolation, Art.character.get(direction * 3));
            }
        }
    }
}
