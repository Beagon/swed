package RPGLevelEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.xml.bind.util.JAXBResult;

/**
 *
 * @author ManIkWeet
 */
public class GUI extends JFrame implements ItemListener, ActionListener {

    private JComboBox squareTileComboBox;
    private JComboBox overlapTileComboBox;
    private JCheckBox collides = new JCheckBox("Collides");
    private JButton save = new JButton("Save");
    private JButton load = new JButton("Load");
    int width = 8, height = 6;
    public Object[] squareTileArray, overlapTileArray;
    boolean rightClick = false;
    boolean mouseEntered = false;
    ArrayList<Tile> tiles = new ArrayList<Tile>();

    public GUI() {
        super("RPG Level Editor");
        width = Integer.parseInt(JOptionPane.showInputDialog("The width of the tiles (Min. 10): "));
        height = Integer.parseInt(JOptionPane.showInputDialog("The heigt of the tiles (Min. 10): "));
        if(width < 10 || height < 10){
                 JOptionPane.showMessageDialog(null, "Your width or height is lower then 10, please try again.");
                 width = Integer.parseInt(JOptionPane.showInputDialog("The width of the tiles (Min. 10): "));
                 height = Integer.parseInt(JOptionPane.showInputDialog("The heigt of the tiles (Min. 10): "));
        }
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProcedure();
            }
        });
        
        setLayout(null);
        setDefaultCloseOperation(0);//3 voor sluiten, 0 voor niks doen
        setSize(width * 16, height * 16);
        setVisible(true);
        squareTileArray = new Object[Art.squareTiles.size()];
        for (int i = 0; i < squareTileArray.length; i++) {
            squareTileArray[i] = new ImageIcon(Art.squareTiles.get(i));
        }
        squareTileComboBox = new JComboBox(squareTileArray);
        squareTileComboBox.setBounds(5, height * 16 + 16, width * 16, 20);
        squareTileComboBox.addItemListener(this);
        overlapTileArray = new Object[Art.overlapTiles.size()];
        for (int i = 0; i < overlapTileArray.length; i++) {
            overlapTileArray[i] = new ImageIcon(Art.overlapTiles.get(i));
        }
        overlapTileComboBox = new JComboBox(overlapTileArray);
        overlapTileComboBox.setBounds(5, height * 16 + 48, width * 16, 32);
        overlapTileComboBox.addItemListener(this);

        save.setBounds(5, height * 16 + 150, width * 16, 32);
        save.addActionListener(this);
        load.setBounds(5, height * 16 + 113, width * 16, 32);
        load.addActionListener(this);
        collides.setBounds(1, height * 16 + 80, 110, 32);
        Level l = new Level(width, height);
        l.setBounds(5, 0, width * 16, height * 16);
        l.setVisible(true);
        add(l);
        add(squareTileComboBox);
        add(overlapTileComboBox);
        add(save);
        add(load);
        add(collides);
        getContentPane().setPreferredSize(new Dimension(width * 16, height * 16 + 180));
        pack();
        setResizable(false);
    }

    public void loadLevel(File name) {
        try {
            tiles.clear();
            int X = 0, Y = 0;
            BufferedImage levelImage = Art.getFromLocation(name);
            Tile[][] newTiles = new Tile[levelImage.getWidth()][levelImage.getHeight()];
            for (int x = 0; x < newTiles.length; x++) {
                X += 1;
                for (int y = 0; y < newTiles[x].length; y++) {
                    Y += 1;
                    int tileColor = levelImage.getRGB(x, y);
                    int red = (tileColor & 0x00ff0000) >> 16;
                    int green = (tileColor & 0x0000ff00) >> 8;
                    int blue = tileColor & 0x000000ff;
                    if (red != 0) {
                        switch (green) {
                            case 0:
                                tiles.add(new Tile(red, x * 16, y * 16, false, false));
                                break;
                            case 1:
                                tiles.add(new Tile(red, x * 16, y * 16, true, false));
                                break;
                            case 2:
                                tiles.add(new Tile(red, x * 16, y * 16, false, true, true));
                                break;
                            case 3:
                                tiles.add(new Tile(red, x * 16, y * 16, true, true, true));
                                break;
                        }
                    }
                }
            }
            System.out.println("X: " + X + " Y: " + Y);
        } catch (NumberFormatException e) {
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
    }

    private void exitProcedure() {
        System.out.println("Exit");
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(save)) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.createGraphics();
            for (int i = 0; i < tiles.size(); i++) {

                Tile t = (Tile) tiles.get(i);
                if (!t.collides && !t.overlaps) {
                    g.setColor(new Color(t.art, 0, 0));
                } else if (t.collides && !t.overlaps) {
                    g.setColor(new Color(t.art, 1, 0));
                } else if (!t.collides && t.overlaps) {
                    g.setColor(new Color(255 - t.art, 2, 0));
                } else if (t.collides && t.overlaps) {
                    g.setColor(new Color(255 - t.art, 3, 0));
                }
                g.fillRect(t.x / 16, t.y / 16, 1, 1);

            }
            File f = new File("hoi.png");
            try {
                ImageIO.write(bi, "png", f);
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        } else {
            final JFileChooser fc = new JFileChooser();
            fc.showOpenDialog(null);

            try {
                File file = new File(fc.getSelectedFile().toString());
                loadLevel(file);
                repaint();
            } catch (NumberFormatException LoadImageError) {
            }
        }
    }

    private class Level extends JPanel implements MouseMotionListener, MouseListener {

        int x = 0, y = 0, x2 = 0, y2 = 0;
        int width, height;

        public Level(int width, int height) {
            addMouseMotionListener(this);
            addMouseListener(this);
            this.width = width * 16;
            this.height = height * 16;
        }

        @Override
        public void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.black);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.white);
            for (int i = 0; i < width; i += 16) {
                g2d.drawLine(i, 0, i, height);
            }
            for (int i = 0; i < height; i += 16) {
                g2d.drawLine(0, i, width, i);
            }
            for (int i = 0; i < tiles.size(); i++) {
                Tile t = tiles.get(i);
                t.render(g2d);
            }

            if (mouseEntered) {
                g2d.setColor(Color.GREEN);
                g2d.fillRect(x2 * 16, y2 * 16, 16, 16);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            handle(e);
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if (x > width - 16) {
                x = width - 16;
            }
            if (y > height - 16) {
                y = height - 16;
            }
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            x2 = (int) (x / 16);
            y2 = (int) (y / 16);
            repaint();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            handle(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                rightClick = true;
            } else {
                rightClick = false;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            mouseEntered = true;
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            mouseEntered = false;
            repaint();
        }

        private void handle(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            if (x > width - 16) {
                x = width - 16;
            }
            if (y > height - 16) {
                y = height - 16;
            }
            if (x < 0) {
                x = 0;
            }
            if (y < 0) {
                y = 0;
            }
            x2 = (int) (x / 16);
            y2 = (int) (y / 16);
            removeTileAt(x2, y2);
            if (rightClick) {
                if (overlapTileComboBox.getSelectedIndex() != 0) {
                    if (collides.isSelected()) {
                        tiles.add(new Tile(255 - overlapTileComboBox.getSelectedIndex(), x2 * 16, y2 * 16, true, true));
                    } else {
                        tiles.add(new Tile(255 - overlapTileComboBox.getSelectedIndex(), x2 * 16, y2 * 16, false, true));
                    }
                }
            } else {
                if (squareTileComboBox.getSelectedIndex() != 0) {
                    if (collides.isSelected()) {
                        tiles.add(new Tile(squareTileComboBox.getSelectedIndex(), x2 * 16, y2 * 16, true, false));
                    } else {
                        tiles.add(new Tile(squareTileComboBox.getSelectedIndex(), x2 * 16, y2 * 16, false, false));
                    }
                }
            }

            repaint();
        }

        public void removeTileAt(int x, int y) {
            for (int i = 0; i < tiles.size(); i++) {
                Tile t = (Tile) tiles.get(i);
                if (t.x == x * 16 && t.y == y * 16) {
                    tiles.remove(i--);
                    i = tiles.size();
                }
            }
        }
    }
}
