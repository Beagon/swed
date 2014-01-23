package entity;

import inputhandling.KeyBoard;
import java.awt.Graphics2D;
import game.*;
import database.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;

/**
 *
 * @author Robin
 */
public class TalkActions {
    Graphics2D g2d;
    Font font = new Font("System", Font.PLAIN, 12);
    Game game;
    Sqlite db;
    public static String Pokemon = "";
    
    public TalkActions(Graphics2D g2d, Game game) {
                this.g2d = g2d;
                this.game = game;
                try{
                this.db = new Sqlite("main");
                }catch (Exception ignore){
                    System.out.println(ignore.getMessage());                
                }
    }
    
    public void Tick(){  
    }
    
     public void Draw() {
         this.g2d.setColor(Color.WHITE);
         this.g2d.setFont(font);
         this.g2d.drawString(Pokemon, 40, 80);  
         this.DrawText(1);
     }
     
     public void DrawText(int TextID){
        this.g2d.drawString(String.format(db.GetText(2, "TalkActions", "text")), 5, game.GAME_HEIGHT - 20);
     }
}
