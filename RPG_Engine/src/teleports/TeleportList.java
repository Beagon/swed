package teleports;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import game.Game;

/**
 *
 * @author ManIkWeet
 */
public class TeleportList {
    
    public static ArrayList<Teleport> teleports = new ArrayList<Teleport>();
    
    public static void setTeleports() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(Game.class.getResourceAsStream("/teleports.txt")));
            String text;
            String[] data;

// repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                if (!text.startsWith("//")) {
                    data = text.split("\t");
                    teleports.add(new Teleport(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4])));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
