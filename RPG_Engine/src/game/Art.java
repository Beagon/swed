package game;

import entity.Entity;
import entity.Player;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author ManIkWeet
 */
public class Art {

    public static ArrayList<BufferedImage> floorTiles = cut("/squaretiles.png", 16, 16);
    public static ArrayList<BufferedImage> overlapTiles = cut("/overlaptiles.png", 16, 24);
    public static ArrayList<BufferedImage> character = cut("/hero.png", 16, 16);
    //public static ArrayList<BufferedImage> npcs = cut("/npc.png", 16, 16);

    public static BufferedImage get(String path) {
        try {
            return ImageIO.read(Game.class.getResource(path));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public static ArrayList<BufferedImage> cut(String path, int width, int height) {

        BufferedImage fullImage = get(path);
        ArrayList<BufferedImage> toReturn = new ArrayList<BufferedImage>();

        for (int y = 0; y < fullImage.getHeight(); y += height) {
            for (int x = 0; x < fullImage.getWidth(); x += width) {
                toReturn.add(fullImage.getSubimage(x, y, width, height));
            }
        }
        return toReturn;
    }
}
