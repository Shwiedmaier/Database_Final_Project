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
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Rectangle;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author malory, john, scott
 */
public class DaisyGui extends Canvas implements Runnable, ActionListener {

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

    private JButton clickButton1;
    private JButton clickButton2;
    private JButton clickButton3;
    private JButton clickButton4;
    private JButton clickButton5;
    private JButton clickButton6;
    private JButton clickButton7;
    private JButton clickButton8;
    private JButton clickButton9;
    private JButton clickButton10;
    private JButton clickButton11;
    private JButton clickButton12;
    private JButton clickButton13;
    private JButton clickButton14;
    private JButton clickButton15;
    private JButton clickButton16;

    public DaisyGui() {
        connect();
        frame = new JFrame(NAME);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        clickButton1 = new JButton("Connection TEST");
        clickButton2 = new JButton("Employee MGMT");
        clickButton3 = new JButton("Car MGMT");
        clickButton4 = new JButton("WorkOrder MGMT");
        clickButton1.addActionListener((ActionListener) this);
        clickButton2.addActionListener((ActionListener) this);
        clickButton3.addActionListener((ActionListener) this);
        clickButton4.addActionListener((ActionListener) this);

        clickButton5 = new JButton("Client MGMT");
        clickButton6 = new JButton("Manage WrkOrd");
        clickButton7 = new JButton("PART MGMT");
        clickButton8 = new JButton("META DATA");
        clickButton5.addActionListener((ActionListener) this);
        clickButton6.addActionListener((ActionListener) this);
        clickButton7.addActionListener((ActionListener) this);
        clickButton8.addActionListener((ActionListener) this);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBackground(Color.white);
        buttonPanel.setOpaque(false);
        buttonPanel.add(clickButton1);
        buttonPanel.add(clickButton2);
        buttonPanel.add(clickButton3);
        buttonPanel.add(clickButton4);
        buttonPanel.add(clickButton5);
        buttonPanel.add(clickButton6);
        buttonPanel.add(clickButton7);
        buttonPanel.add(clickButton8);
        buttonPanel.setLayout(new GridLayout(2, 4));

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
            System.out.println("Failure");
        }

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(this, BorderLayout.CENTER); //adds canvas to jframe

        frame.pack();//keeps everything sized correctly (>= PrefferedSize)
        frame.setSize(560, 350);
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
        new DaisyGui().start();

    }

    public void execution(String Query, String[] headers) {
        //connect();
        try (PreparedStatement ps = connection.prepareStatement(Query)) {

            try (ResultSet rs = ps.executeQuery()) {
                ArrayList<String[]> result = new ArrayList<String[]>();
                int columnCount = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        row[i] = rs.getString(i + 1);
                    }
                    result.add(row);
                }
                String[][] data = result.toArray(new String[][]{});
                //headers for the table
                String[] columns = headers;

                //actual data for the table in a 2d array
                //create table with data
                JFrame frame2 = new JFrame("Results Table");
                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                JTable table = new JTable(data, columns);
                JScrollPane tableContainer = new JScrollPane(table);
                panel.add(tableContainer, BorderLayout.CENTER);
                //add the table to the frame
                frame2.getContentPane().add(panel);

                frame2.pack();
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                frame2.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
                frame2.setSize(800, 400);
                frame2.setVisible(true);

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
            } // try // try
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
            Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
        } // try // try
    }

    public void connect() {

        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://mysql.winnerdigital.net:3306/scott_412", "scott1044", "ohyesdaddy!");
            //JOptionPane.showMessageDialog(frame, "Connection Successful", "Daisy Imports", 3);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(frame, "FAILED TO CONNECT!", "Daisy Imports", 3);
            Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == clickButton1) {
            try {
                connection = DriverManager
                        .getConnection("jdbc:mysql://mysql.winnerdigital.net:3306/scott_412", "scott1044", "ohyesdaddy!");
                JOptionPane.showMessageDialog(frame, "Connection Successful", "Daisy Imports", 3);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "FAILED TO CONNECT!", "Daisy Imports", 3);
                Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == clickButton2) {
            //connect();

            //Custom button text
            Object[] options = {"Display All",
                "Add New",
                "Delete",
                "Update"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Employee Management",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[3]);
            if (n == 0) { //Display All Employees
                String Query = (" SELECT * FROM DAISYEMPLOYEE");
                String[] columns = new String[]{
                    "SSN", "FNAME", "MINIT", "LNAME", "ADDRESS", "DATEHIRED", "PAY", "PHONE#"
                };
                execution(Query, columns);

            }

            if (n == 1) { //New Employee
                String SSN, FNAME, MINIT, LNAME, ADDRESS, DATEHIRED, PAY, PHONE;

                String Statement = "INSERT INTO DAISYEMPLOYEE"
                        + "(SSN, FNAME, MINIT, LNAME, ADDRESS, DATEHIRED, PAY, PHONE) VALUES "
                        + "( ?, ?, ?, ?, ?, ?, ?, ?)";

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);
                    SSN = JOptionPane.showInputDialog("Please input SSN");
                    int result = Integer.parseInt(SSN);
                    FNAME = JOptionPane.showInputDialog("Please input First Name");
                    MINIT = JOptionPane.showInputDialog("Please input Middle Initial");
                    LNAME = JOptionPane.showInputDialog("Please input Last Name");
                    ADDRESS = JOptionPane.showInputDialog("Please input Address");
                    DATEHIRED = JOptionPane.showInputDialog("Please input Date Hired(year-month-day)");
                    PAY = JOptionPane.showInputDialog("Please input Pay(x.x)");
                    PHONE = JOptionPane.showInputDialog("Please input Phone Number(xxxxxxxxxx)");
                    PST.setInt(1, result);
                    PST.setString(2, FNAME);
                    PST.setString(3, MINIT);
                    PST.setString(4, LNAME);
                    PST.setString(5, ADDRESS);
                    PST.setString(6, DATEHIRED);
                    PST.setString(7, PAY);
                    PST.setString(8, PHONE);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }

            if (n == 2) {//Delete Employee

                String Statement = "delete from DAISYEMPLOYEE where FNAME = ? AND LNAME = ?";
                String FNAME = JOptionPane.showInputDialog("Please input First Name");

                String LNAME = JOptionPane.showInputDialog("Please input Last Name");
                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);

                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);

                    PST.setString(1, FNAME);
                    PST.setString(2, LNAME);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try

            }

            if (n == 3) { //Update Employee
                String Statement = "update DAISYEMPLOYEE set ADDRESS=?, PAY=?, PHONE=? where FNAME = ? AND LNAME = ?";

                String FNAME = JOptionPane.showInputDialog("Please input First Name");

                String LNAME = JOptionPane.showInputDialog("Please input Last Name");

                String ADDR = JOptionPane.showInputDialog("Please input latest Address");
                String PAY = JOptionPane.showInputDialog("Please input latest Pay");
                String PHONE = JOptionPane.showInputDialog("Please input latest Phone");

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);

                    PST.setString(1, ADDR);
                    PST.setString(2, PAY);
                    PST.setString(3, PHONE);
                    PST.setString(4, FNAME);
                    PST.setString(5, LNAME);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try

            }

        }
        if (e.getSource() == clickButton3) {
            //connect();

            //Custom button text
            Object[] options = {"Display All",
                "Add New",
                "Show Client's Cars",
                "Change Storage Status"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Client Management",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[3]);
            if (n == 0) {
                String Query = (" SELECT * FROM CAR");
                String[] columns = new String[]{
                    "VIN", "Stored", "Plate#", "Year", "Make", "Model", "Color", "OWNER"
                };
                execution(Query, columns);

            }
            if (n == 1) {
                String STORED, PLATE, MAKE, MODEL, COLOR;
                String VIN, WORDER, OWNER, YEAR;

                String Statement = "INSERT INTO CAR"
                        + "(VIN, STORAGESTATUS, PLATE, MAKE, MODEL, COLOR, OWNER, YEAR) VALUES "
                        + "( ?, ?, ?, ?, ?, ?, ?, ?)";

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);
                    VIN = JOptionPane.showInputDialog("Please input VIN");
                    int VINi = Integer.parseInt(VIN);
                    STORED = JOptionPane.showInputDialog("Please input Storage Status (Y/N)");
                    PLATE = JOptionPane.showInputDialog("Please input plate number");
                    MAKE = JOptionPane.showInputDialog("Please input make");
                    MODEL = JOptionPane.showInputDialog("Please input model");
                    COLOR = JOptionPane.showInputDialog("Please input color");
                    //WORDER = JOptionPane.showInputDialog("Please input work order number");
                    //int WORDERi = Integer.parseInt(WORDER);
                    OWNER = JOptionPane.showInputDialog("Please input owners Driver license number");
                    int OWNERi = Integer.parseInt(OWNER);
                    YEAR = JOptionPane.showInputDialog("Please input year of make");
                    int YEARi = Integer.parseInt(YEAR);
                    PST.setInt(1, VINi);
                    PST.setString(2, STORED);
                    PST.setString(3, PLATE);
                    PST.setString(4, MAKE);
                    PST.setString(5, MODEL);
                    PST.setString(6, COLOR);
                    //PST.setInt(7, WORDERi);
                    PST.setInt(7, OWNERi);
                    PST.setInt(8, YEARi);
                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }
            if (n == 2) {
                String FNAME = JOptionPane.showInputDialog("Please input First Name");
                String LNAME = JOptionPane.showInputDialog("Please input Last Name");
                String Query = (" select * from CAR "
                        + "where OWNER in "
                        + " (select LICENCE from CLIENT"
                        + " where FNAME = '"
                        + FNAME
                        + "' AND LNAME = '"
                        + LNAME
                        + "')");
                //JOptionPane.showMessageDialog(frame, Query, "Daisy Imports", 3);
                String[] columns = new String[]{
                    "VIN", "Stored", "Plate#", "Year", "Make", "Model", "Color", "OWNER"
                };
                execution(Query, columns);
            }
            if (n == 3) {
                String Statement = "update CAR set STORAGESTATUS=? where VIN = ?";

                String SS = JOptionPane.showInputDialog("Please input (Y/N) for a cars storage status");
                String VIN = JOptionPane.showInputDialog("Please input the Cars VIN");
                int VINi = Integer.parseInt(VIN);
                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);

                    PST.setString(1, SS);
                    PST.setInt(2, VINi);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try

            }

        }
        if (e.getSource() == clickButton4) {
            Object[] options = {"Display All Active",
                "Display by Date",
                "New Work Order"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Employee Management",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
            if (n == 0) {
                String Query = ("select * from WORKORDER "
                        + "JOIN JOB ON WORKORDER.ORDERNUM = JOB.ORDERNUM "
                        + "where STATUS like 'Y' "
                        + "order by JOB.ORDERNUM");
                String[] columns = new String[]{
                    "ORDER#", "Drop off", "Completion", "Car VIN", "Job #", "Description", "Being worked on?", "PRICE", "ORDER#"
                };
                execution(Query, columns);
            }
            if (n == 1) {
                String date1 = JOptionPane.showInputDialog("Please input start date(YYYY-MM-DD)");
                String date2 = JOptionPane.showInputDialog("Please input end date (YYYY-MM-DD)");

                String Query = ("select * from WORKORDER "
                        + "JOIN JOB ON WORKORDER.ORDERNUM = JOB.ORDERNUM "
                        + "where WORKORDER.DROPOFFDATE between '"
                        + date1
                        + "' and '"
                        + date2
                        + "' order by JOB.ORDERNUM");
                String[] columns = new String[]{
                    "ORDER#", "Drop off", "Completion", "Car VIN", "Job #", "Description", "Being worked on?", "PRICE", "ORDER#"
                };
                execution(Query, columns);
            }
            if (n == 2) {
                String ORDER, DROP, CAR;

                String Statement = "INSERT INTO WORKORDER"
                        + "(ORDERNUM, DROPOFFDATE, COMPLETIONDATE, CARVIN) VALUES "
                        + "( ?, ?, ?, ?)";

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);
                    ORDER = JOptionPane.showInputDialog("Please assign a order number for this order");
                    int ORDERi = Integer.parseInt(ORDER);
                    DROP = JOptionPane.showInputDialog("Please Enter the date the car was dropped off (YYYY-MM-DD)");
                    CAR = JOptionPane.showInputDialog("Enter the VIN for the car this work order is for");
                    PST.setInt(1, ORDERi);
                    PST.setString(2, DROP);
                    PST.setObject(3, null);
                    PST.setString(4, CAR);
                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try

            }
        }
        if (e.getSource() == clickButton5) {
            Object[] options = {"Display All ",
                "Add Client",
                "Update Information"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Client Management",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);
            if (n == 0) {
                String Query = (" SELECT * FROM CLIENT");
                String[] columns = new String[]{
                    "License Number", "First Name", "Last name", "Address", "Phone#"
                };
                execution(Query, columns);
            }
            if (n == 1) {
                String License, Fname, lname, address, phone;

                String Statement = "INSERT INTO CLIENT"
                        + "(LICENCE, FNAME, LNAME, ADDRESS, PHONE) VALUES "
                        + "( ?, ?, ?, ?, ?)";

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);
                    License = JOptionPane.showInputDialog("Please input their license number");
                    int Licensei = Integer.parseInt(License);
                    Fname = JOptionPane.showInputDialog("Please input their first name");
                    lname = JOptionPane.showInputDialog("please input their last name");
                    address = JOptionPane.showInputDialog("please input their address");
                    phone = JOptionPane.showInputDialog("please input their phone number (XXXXXXXXXX)");
                    int phonei = Integer.parseInt(phone);
                    PST.setInt(1, Licensei);
                    PST.setString(2, Fname);
                    PST.setString(3, lname);
                    PST.setString(4, address);
                    PST.setInt(5, phonei);
                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }
            if (n == 2) {
                String Statement = "update CLIENT set ADDRESS=?, PHONE=? where FNAME = ? AND LNAME = ?";

                String FNAME = JOptionPane.showInputDialog("Please input First Name");

                String LNAME = JOptionPane.showInputDialog("Please input Last Name");

                String ADDR = JOptionPane.showInputDialog("Please input latest Address");
                String PHONE = JOptionPane.showInputDialog("Please input latest Phone");

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);

                    PST.setString(1, ADDR);
                    PST.setString(2, PHONE);
                    PST.setString(3, FNAME);
                    PST.setString(4, LNAME);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }
        }
        if (e.getSource() == clickButton6) {
            String WorkOrderNum = JOptionPane.showInputDialog("Please input the number of the work order you wish to manage");
            Object[] options = {"Total Part Price ",
                "List Parts",
                "List Jobs",
                "Assign a Part",
                "Add Job"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Specific Work Order Management",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);

            if (n == 0) {
                String Query = (" select WORKORDER.ORDERNUM, SUM(PRICE)"
                        + " from WORKORDER, PARTS"
                        + " where PARTS.ORDERNUM = "
                        + WorkOrderNum
                        + " AND WORKORDER.ORDERNUM = PARTS.ORDERNUM");
                String[] columns = new String[]{
                    "ORDER NUMBER", "Total Part Price"
                };
                execution(Query, columns);
            }
            if (n == 1) {
                String Query = (" select DESCRIPTION, PRICE, DATE"
                        + " from PARTS"
                        + " where ORDERNUM = "
                        + WorkOrderNum);
                String[] columns = new String[]{
                    "Part Name", "Price", "Date"
                };
                execution(Query, columns);
            }
            if (n == 2) {
                String Query = (" select DESCRIPTION, STATUS, PRICE"
                        + " from JOB"
                        + " where ORDERNUM = "
                        + WorkOrderNum);
                String[] columns = new String[]{
                    "Job Description", "Status", "Price"
                };
                execution(Query, columns);
            }
            if (n == 3) {
                String Statement = "update PARTS set ORDERNUM=? where DESCRIPTION = ? ";

                String DESCRIPT = JOptionPane.showInputDialog("Please input PartName");
                int ORDERNUMINT = Integer.parseInt(WorkOrderNum);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);

                    PST.setInt(1, ORDERNUMINT);
                    PST.setString(2, DESCRIPT);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }
            if (n == 4) {
                String Description, Price, Status;

                String Statement = "INSERT INTO JOB"
                        + "(DESCRIPTION, STATUS, PRICE, ORDERNUM) VALUES "
                        + "( ?, ?, ?, ?)";

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);
                    Description = JOptionPane.showInputDialog("Please input the Job name/description");
                    Price = JOptionPane.showInputDialog("Please input the cost of the job");
                    int Pricei = Integer.parseInt(Price);
                    Status = JOptionPane.showInputDialog("please input the status of completion(Y/N)");
                    int ORDERNUMINT = Integer.parseInt(WorkOrderNum);
                    PST.setString(1, Description);
                    PST.setInt(3, Pricei);
                    PST.setString(2, Status);
                    PST.setInt(4, ORDERNUMINT);
                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }

        }
        if (e.getSource() == clickButton7) {
            Object[] options = {"Part Inventory ",
                "Availible Parts",
                "Add Part",
                "Delete Part",};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Part Management",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[3]);
            if (n == 0) {
                String Query = (" SELECT * FROM PARTS");
                String[] columns = new String[]{
                    "PARTNO", "Description/Name", "Price", "Date acquired", "Work Order Used for"
                };
                execution(Query, columns);
            }
            if (n == 1) {
                String Query = (" SELECT * FROM PARTS WHERE ORDERNUM IS NULL");
                String[] columns = new String[]{
                    "PARTNO", "Description/Name", "Price", "Date acquired", "Work Order Used for"
                };
                execution(Query, columns);
            }
            if (n == 2) {
                String Description, Price, Date;

                String Statement = "INSERT INTO PARTS"
                        + "(DESCRIPTION, PRICE, DATE, ORDERNUM) VALUES "
                        + "( ?, ?, ?, ?)";

                //JOptionPane.showMessageDialog(frame, Statement, "Daisy Imports", 3);
                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);
                    Description = JOptionPane.showInputDialog("Please input the part name/description");
                    Price = JOptionPane.showInputDialog("Please input thepart price");
                    int Pricei = Integer.parseInt(Price);
                    Date = JOptionPane.showInputDialog("please input the date of collection");

                    PST.setString(1, Description);
                    PST.setInt(2, Pricei);
                    PST.setString(3, Date);
                    PST.setObject(4, null);
                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }
            if (n == 3) {
                String Statement = "delete from PARTS where DESCRIPTION = ?";
                String PARTNAME = JOptionPane.showInputDialog("Please input Part name to be deleted");

                try {
                    PreparedStatement PST = connection.prepareStatement(Statement);

                    PST.setString(1, PARTNAME);

                    PST.execute();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try
            }
        }
        if (e.getSource() == clickButton8) {
            Object[] options = {
                "Cars Currently Being Worked on",
                "Total Part Inventory Cost",
                "Cars in the garage"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Please make a selection",
                    "Meta Data",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (n == 0) {
                String Query = ("select *"
                        + " from CAR"
                        + " where VIN in (select CARVIN"
                        + " from WORKORDER"
                        + " where ORDERNUM in (select ORDERNUM"
                        + " from JOB"
                        + " where STATUS like 'Y'))");
                String[] columns = new String[]{
                    "VIN", "Stored", "Plate#", "Year", "Make", "Model", "Color", "OWNER"
                };
                execution(Query, columns);
            }
            if (n == 1) {
                String Query = ("select WORKORDER.ORDERNUM, SUM(PRICE)"
                        + " from WORKORDER, PARTS"
                        + " where WORKORDER.ORDERNUM = PARTS.ORDERNUM"
                        + " AND WORKORDER.ORDERNUM in (select ORDERNUM"
                        + " from JOB"
                        + " where STATUS like 'Y')"
                        + " group by WORKORDER.ORDERNUM WITH ROLLUP");
                String[] headers = new String[]{
                    "Work Order", "Price"};
                try (PreparedStatement ps = connection.prepareStatement(Query)) {

                    try (ResultSet rs = ps.executeQuery()) {
                        ArrayList<String[]> result = new ArrayList<String[]>();
                        int columnCount = rs.getMetaData().getColumnCount();
                        while (rs.next()) {
                            String[] row = new String[columnCount];
                            for (int i = 0; i < columnCount; i++) {
                                row[i] = rs.getString(i + 1);
                            }
                            result.add(row);
                        }
                        String[][] data = result.toArray(new String[][]{});
                        //headers for the table
                        String[] columns = headers;
                        data[data.length - 1][0] = "total";

                        //actual data for the table in a 2d array
                        //create table with data
                        JFrame frame2 = new JFrame("Results Table");
                        JPanel panel = new JPanel();
                        panel.setLayout(new BorderLayout());

                        JTable table = new JTable(data, columns);
                        JScrollPane tableContainer = new JScrollPane(table);
                        panel.add(tableContainer, BorderLayout.CENTER);
                        //add the table to the frame
                        frame2.getContentPane().add(panel);

                        frame2.pack();
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        frame2.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
                        frame2.setSize(800, 400);
                        frame2.setVisible(true);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                        Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                    } // try // try
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Error in SQL", "Daisy Imports", 3);
                    Logger.getLogger(DaisyGui.class.getName()).log(Level.SEVERE, null, ex);
                } // try // try

            }
            if (n == 2) {
                String Query = (" SELECT * FROM CAR WHERE STORAGESTATUS = 'Y' ");
                String[] columns = new String[]{
                    "VIN", "Stored", "Plate#", "Year", "Make", "Model", "Color", "OWNER"
                };
                execution(Query, columns);
            }
        }

    }
}
