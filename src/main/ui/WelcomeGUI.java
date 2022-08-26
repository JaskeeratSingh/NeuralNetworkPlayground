package ui;

import model.Matrix;
import model.NeuralNetwork;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**WelcomeGUI handles the GUI display, frames, elements
 * and is the opening application page. */

public class WelcomeGUI {

    private JFrame frame;
    private JPanel panel;
    private JButton load;
    private JButton session;
    private JLabel welcome;
    File absender = new File("./data/assets/absender1.ttf");
    File raleway = new File("./data/assets/Raleway-Regular.ttf");
    private static final String NN_STORE = "./data/nn.json";
    private static final String CLASS_ONE_STORE = "./data/classOne.json";
    private static final String CLASS_TWO_STORE = "./data/classTwo.json";
    private JsonReader nnReader;
    private JsonReader datasetReader;
    Matrix classOneMatrix;
    Matrix classTwoMatrix;
    NeuralNetwork nn;

    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == load) {
                load();
                new TrainingGUI(classOneMatrix, classTwoMatrix, 0.1, 100000, nn);
                frame.dispose();
            } else if (e.getSource() == session) {
                new NewSessionGUI().show();
                frame.dispose();
            }
        }
    };

    //EFFECTS: creates the opening frame of the application
    public WelcomeGUI() {
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 30));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.decode("#e4eaf1"));

        createElements();
        setFonts();
        placeElements();
        showFrame();
    }

    //MODIFIES: this
    //EFFECTS: creates the elements that are supposed to be inside the frame
    public void createElements() {
        load = new JButton("Load previous data");
        session = new JButton("Start a new session");
        Color color = panel.getBackground();
        session.setBorder(new LineBorder(Color.decode("#0070F3")));
        session.setPreferredSize(new Dimension(170,37));
        session.setForeground(Color.decode("#e4eaf1"));
        session.setBackground(Color.decode("#0070F3"));
        load.setBorder(new LineBorder(Color.decode("#0070F3"), 1));
        load.setForeground(Color.decode("#0070F3"));
        load.setBackground(Color.decode("#e4eaf1"));
        load.setPreferredSize(new Dimension(170,37));
        load.setFocusPainted(false);
        session.setFocusPainted(false);
        load.addActionListener(actionListener);
        session.addActionListener(actionListener);
        welcome = new JLabel("Welcome to Neural Network playground!");
    }

    //EFFECTS: sets the fonts of elements
    public void setFonts() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, absender);
            Font ralewayy = Font.createFont(Font.TRUETYPE_FONT, raleway);
            font = font.deriveFont(27f);
            ralewayy = ralewayy.deriveFont(15f);
            welcome.setFont(font);
            load.setFont(ralewayy);
            session.setFont(ralewayy);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //EFFECTS: places elements in their right place
    public void placeElements() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,180,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 5;
        c.gridy = 6;
        panel.add(load, c);
        c.gridx = 5;
        c.gridy = 5;
        panel.add(session, c);
        c.insets = new Insets(10,10,50,10);
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 6;
        c.gridheight = 3;
        panel.add(welcome, c);
    }

    //EFFECTS: displaces the current frame
    public void showFrame() {
        ImageIcon frameIcon = new ImageIcon("./data/assets/logo.png");
        frame.setIconImage(frameIcon.getImage());
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Neural Network Playground");
        frame.pack();
        frame.setSize(700,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //EFFECTS: loads saved Neural Network and Matrix
    public void load() {

        nnReader = new JsonReader(NN_STORE);
        try {
            nn = nnReader.read();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read Neural Network");
        }

        try {
            datasetReader = new JsonReader(CLASS_ONE_STORE);
            classOneMatrix = new Matrix(datasetReader.read("classOneData"));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load Class one data");
        }
        try {
            datasetReader = new JsonReader(CLASS_TWO_STORE);
            classTwoMatrix = new Matrix(datasetReader.read("classTwoData"));

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to load Class two data");
        }
    }

}
