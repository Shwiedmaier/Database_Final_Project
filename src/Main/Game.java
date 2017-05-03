package Main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package test;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author malory, john, scott
 */
public class Game extends Canvas implements Runnable, ActionListener {

    JComboBox LevelDisplay;
    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 150;
    public static final int HEIGHT = WIDTH / 12 * 9 + 9;
    public static final int SCALE = 3;
    public static final String NAME = "Daisy Imports";


    private final JFrame frame;
    
 
    Connection connection = null;
    private JPanel buttonPanel;

    public boolean running = false;
    public int tickCount = 0;
    public String username;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage image2 = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

    private JButton clickAdd;
    private JButton clickSubtract;
    private JButton clickMultiply;
    private JButton clickDivide;
    private JLabel label1;
    private JLabel label2;
    private boolean addClick = false;
    private boolean subtractClick = false;
    private boolean multiplyClick = false;
    private boolean divideClick = false;

    public Game() {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        clickAdd = new JButton("Current Orders");
        clickSubtract = new JButton("Subtraction");
        clickMultiply = new JButton("Multiplication");
        clickDivide = new JButton("Division");
        clickAdd.addActionListener((ActionListener) this);
        clickSubtract.addActionListener((ActionListener) this);
        clickMultiply.addActionListener((ActionListener) this);
        clickDivide.addActionListener((ActionListener) this);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setOpaque(false);

        buttonPanel.add(clickAdd);
        buttonPanel.add(clickSubtract);
        buttonPanel.add(clickMultiply);
        buttonPanel.add(clickDivide);
        label1 = new JLabel(" ");
        buttonPanel.add(label1);
        label2 = new JLabel(" ");
        buttonPanel.add(label2);

        try {
            URL url = new URL("http://orig09.deviantart.net/879c/f/2012/119/1/0/pixel_waterfall_bg__by_isohei-d4xntof.gif");
            Icon icon = new ImageIcon(url);
            JLabel label = new JLabel(icon);
            // JFrame f = new JFrame("Animation");
            frame.getContentPane().add(label);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            //f.setVisible(true);
        } catch (Exception e) {
            System.out.println("OMG WTF");
        }

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(this, BorderLayout.CENTER); //adds canvas to jframe

        frame.pack();//keeps everything sized correctly (>= PrefferedSize)
        frame.setSize(560, 330);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();//thread is instance of runnable, will run "run()" on start
    }

    public synchronized void stop() {
        running = false;
    }

    public void run() {

        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0; //how many nano seconds in 1 tick

        int ticks = 0;
        int frames = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0; //how many nano-seconds have gone by so far. Once we hit 1, we will minus 1 from it



        int width = 420;
     
        frame.setVisible(true);

        while (running) { //DA GAME
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true; //TEMP tru
            while (delta >= 1) {
                ticks++;
                tick();
                delta--;
                shouldRender = true;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };
            if (shouldRender) {
                frames++;
                render();
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000; //add another second
                //System.out.println(frames + ", " + ticks);
                frames = 0;
                ticks = 0;
            }

            //load up game 
        }
    }

    public void tick() { //updates per second
        tickCount++;

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = (i + tickCount);
        }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy(); //object to organize data , on canvas
        if (bs == null) {
            createBufferStrategy(3);//higher number will increase image performance
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        try {
            image2 = ImageIO.read(new File("name.png"));
        } catch (IOException ex) {
            // handle exception...
        }

        g.drawImage(image2, 0, 0, getWidth(), getHeight(), null);
        g.drawImage(image2, 0, 0, null);

        g.dispose(); //free up memory
        bs.show(); //show contents of buffer
    }

    public static void main(String[] args) {
        new Game().start();

    }

    public void connect() {

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://mysql.winnerdigital.net:3306/scott_412", "scott1044", "ohyesdaddy!");
            JOptionPane.showMessageDialog(frame, "WE MADE IT", "Daisy Imports", 3);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "FAILED TO CONNECT!", "Daisy Imports", 3);
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (e.getSource() == clickAdd) {
            addClick = true;

 
        }
        if (e.getSource() == clickSubtract) {
            subtractClick = true;
            connect();
            try (PreparedStatement ps = connection.prepareStatement(" SELECT * FROM DAISYEMPLOYEE")) {
                // In the SQL statement being prepared, each question mark is a placeholder
                // that must be replaced with a value you provide through a "set" method invocation.
                // The following two method calls replace the two placeholders; the first is
                // replaced by a string value, and the second by an integer value.
                //ps.setString(1, "scott");

                // The ResultSet, rs, conveys the result of executing the SQL statement.
                // Each time you call rs.next(), an internal row pointer, or cursor,
                // is advanced to the next row of the result.  The cursor initially is
                // positioned before the first row.
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int numColumns = rs.getMetaData().getColumnCount();
                        for (int i = 1; i <= numColumns; i++) {
                            JOptionPane.showMessageDialog(frame, "COLUMN " + i + " = " + rs.getObject(i), "Daisy Imports", 3);
                            // Column numbers start at 1.
                            // Also there are many methods on the result set to return
                            // the column as a particular type. Refer to the Sun documentation
                            // for the list of valid conversions.
                           // System.out.println("COLUMN " + i + " = " + rs.getObject(i));
                        } // for
                    } // while
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "COMPLETELY FUCKED", "Daisy Imports", 3);
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                } // try
            } catch (SQLException ex) {
                 JOptionPane.showMessageDialog(frame, "TOTALLY FUCKED", "Daisy Imports", 3);
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } // try

        }
        if (e.getSource() == clickMultiply) {
            multiplyClick = true;

        }
        if (e.getSource() == clickDivide) {
            divideClick = true;

        }
    }
}
