package game;

import inputhandling.KeyBoard;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import teleports.TeleportList;
import entity.TalkActions;
import game.Console;

public class Game extends Canvas implements Runnable {
   // private static boolean ConsoleEnabled = true;
    public static final int GAME_WIDTH = 256;
    public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
    public static final int SCALE = 2;
    private Level level;
    private int fps = 0;
    private int frameCount = 0;
    private int gts = 0;
    private int gameTickCount = 0;
    private final int GAME_TICKS_SECOND = 30;
    //Calculate how many ns each frame should take for our target game tick.
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_TICKS_SECOND;
    //At the very most we will update the game this many times before a new render.
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    //If we are able to get as high as this FPS, don't render again.
    final double TARGET_FPS = 60;
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    private TalkActions talkactions;
    

    public Game() {
        this.setPreferredSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        this.setMinimumSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        this.setMaximumSize(new Dimension(GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE));
        this.setIgnoreRepaint(true);
        this.addKeyListener(new KeyBoard());
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     //   if(ConsoleEnabled){
    //    Console console = new Console();
     //   console.show();
     //   }
        
        Game mt = new Game();
        JFrame frame = new JFrame("The Game");
        frame.setBackground(Color.red);
        frame.add(mt);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        mt.start();
    }

    private void start() {
        Thread t = new Thread(this);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    @Override
    public void run() {
        TeleportList.setTeleports();
        createBufferStrategy(2);
        BufferStrategy buffer = getBufferStrategy();
        // Get graphics configuration...
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        // Create off-screen drawing surface
        BufferedImage bi = gc.createCompatibleImage(GAME_WIDTH, GAME_HEIGHT);

        // Objects needed for rendering...
        Graphics graphics = null;
        Graphics2D g2d = bi.createGraphics();
        level = Level.loadLevel("/hoi.png", g2d, this);
         this.talkactions = new TalkActions(g2d, this); 
        //We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime = System.nanoTime();

        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (true) {
            double now = System.nanoTime();
            int updateCount = 0;

            //Do as many game updates as we need to, potentially playing catchup.
            while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                level.tick();
                talkactions.Tick();
                lastUpdateTime += TIME_BETWEEN_UPDATES;
                updateCount++;
                gameTickCount++;
            }
            //Render. To do so, we need to calculate interpolation for a smooth render.
            double interpolation = ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES);

            render(g2d, interpolation);//interpolation representeerd hoever we bij de volgende TICK zijn...
            // Blit image and flip...
            graphics = buffer.getDrawGraphics();
            graphics.drawImage(bi, 0, 0, GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE, null);
            //if (!buffer.contentsLost()) {
            buffer.show();
            //}
            lastRenderTime = now;

            //Update the frames we got.
            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime) {
                fps = frameCount;
                frameCount = 0;
                gts = gameTickCount;
                gameTickCount = 0;
                lastSecondTime = thisSecond;
            }

            //Yield until it has been at least the target time between renders. This saves the CPU from hogging.

            while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                Thread.yield();

                //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it. 
                //You can remove this line and it will still work (better), your CPU just climbs on certain OSes. 
                //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this. 
                try {
                    Thread.sleep(0, 50000);
                    //Thread.sleep(10); 
                } catch (Exception e) {
                }

                now = System.nanoTime();
            }

        }
    }

    public void render(Graphics2D g2d, double interpolation) {
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);    
        
        g2d.setColor(Color.black);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        level.paint(g2d, interpolation);
        //g.drawImage(buffer, 0, 0, GAME_WIDTH * SCALE, GAME_HEIGHT * SCALE, this);
        // display frames per second...
        g2d.setColor(Color.GREEN);
        g2d.drawString(String.format("FPS: %s; GTS: %s", fps, gts), 20, 20);
        talkactions.Draw();
        frameCount++;
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
