package ui;

import model.Event;
import model.EventLog;
import model.Matrix;
import model.NeuralNetwork;
import persistence.JsonWriter;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

/**TrainingGUI handles the GUI to allow the user to view the
 * decision boundary after training. */

public class TrainingGUI {

    private Matrix classOneMatrix;
    private Matrix classTwoMatrix;
    private Double learningRate;
    private int numIterations;
    private NeuralNetwork nn;
    private Matrix labelOne = new Matrix(1, 1);
    private Matrix labelTwo = new Matrix(1, 1);
    JFrame frame;
    JPanel train;
    JLabel label;
    JPanel elements;
    JLabel description;
    JButton saveExit;
    JButton back;
    TrainingWindow trainArea;
    private Random rand = new Random();
    File absender = new File("./data/assets/absender1.ttf");
    File raleway = new File("./data/assets/Raleway-Regular.ttf");
    WindowListener windowListener = new WindowListener() {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            for (Iterator<Event> ev = EventLog.getInstance().iterator(); ev.hasNext();) {
                Event log = ev.next();
                System.out.println(log.toString() + "\n");
            }
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    };
    ActionListener actionListener = new ActionListener() {

        @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == saveExit) {
                save();
                for (Iterator<Event> ev = EventLog.getInstance().iterator(); ev.hasNext();) {
                    Event log = ev.next();
                    System.out.println(log.toString() + "\n");
                }
                frame.dispose();
            } else if (e.getSource() == back) {
                NetworkGUI ng = new NetworkGUI(classOneMatrix, classTwoMatrix);
                frame.dispose();
            }
        }
    };

    private static final String NN_STORE = "./data/nn.json";
    private static final String CLASS_ONE_STORE = "./data/classOne.json";
    private static final String CLASS_TWO_STORE = "./data/classTwo.json";
    private JsonWriter nnWriter;
    private JsonWriter datasetWriter;

    //EFFECTS: gets the created database and neural network parameters from past frames
    //         when the Neural Network finishes training, creates Training window
    public TrainingGUI(Matrix classOneMatrix, Matrix classTwoMatrix, Double learningRate,
                       int numIterations, NeuralNetwork nn) {
        this.classOneMatrix = classOneMatrix;
        this.classTwoMatrix = classTwoMatrix;
        this.learningRate = learningRate;
        this.numIterations = numIterations;
        this.labelOne.setMatrix(0,0,0);
        this.labelTwo.setMatrix(0,0,1);
        this.nn = nn;

        frame = new JFrame("Neural Network Playground");
        train = new JPanel();
        elements = new JPanel();
        elements.setLayout(new GridBagLayout());
        train.setLayout(new BorderLayout());

        if (trained()) {
            trainArea = new TrainingWindow(frame, nn, classOneMatrix, classTwoMatrix,
                    numIterations, learningRate);
        }


        train.setBackground(Color.decode("#e4eaf1"));
        elements.setBackground(Color.decode("#e4eaf1"));
        createElements();
        setFonts();
        placeElements();
        showFrame();
        frame.addWindowListener(windowListener);

    }

    //MODIFIES: this
    //EFFECTS: creates the elements that are supposed to be inside the frame
    private void createElements() {
        label = new JLabel("Training Complete!");
        description = new JLabel("This is the learned decision boundary!");
        back = new JButton("Go back to edit Network");
        back.setBorder(new LineBorder(Color.decode("#0070F3")));
        back.setPreferredSize(new Dimension(170,37));
        back.setForeground(Color.decode("#e4eaf1"));
        back.setBackground(Color.decode("#0070F3"));
        back.setFocusPainted(false);
        saveExit = new JButton("Save and exit");
        saveExit.setBorder(new LineBorder(Color.decode("#0070F3"), 1));
        saveExit.setForeground(Color.decode("#0070F3"));
        saveExit.setBackground(Color.decode("#e4eaf1"));
        saveExit.setPreferredSize(new Dimension(170,37));
        saveExit.setFocusPainted(false);
        saveExit.addActionListener(actionListener);
        back.addActionListener(actionListener);

    }

    //EFFECTS: sets the fonts of elements
    public void setFonts() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, absender);
            Font ralewayy = Font.createFont(Font.TRUETYPE_FONT, raleway);
            font = font.deriveFont(27f);
            ralewayy = ralewayy.deriveFont(15f);
            label.setFont(font);
            description.setFont(ralewayy);
            saveExit.setFont(ralewayy);
            back.setFont(ralewayy);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //EFFECTS: places elements in their right place
    private void placeElements() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(15,35,0,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 2;
        elements.add(description, c);
        c.gridx = 1;
        c.gridy = 3;
        elements.add(back, c);
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(10,35,20,10);
        elements.add(saveExit, c);
        label.setBorder(BorderFactory.createEmptyBorder(35, 230, 0, 0));
        train.add(label, BorderLayout.NORTH);
        train.add(trainArea, BorderLayout.CENTER);
        frame.add(train, BorderLayout.CENTER);
        frame.add(elements, BorderLayout.SOUTH);
    }

    //MODIFIES: this.nn
    //EFFECTS: trains Neural Network on created dataset
    //         returns true when finished training
    public Boolean trained() {
        int count = 0;
        for (int i = 0; i < numIterations; i++) {
            count++;
            if (count % 2 == 0) {
                int s = rand.nextInt(classOneMatrix.getCols());
                double error = nn.train(classOneMatrix.getCol(s), labelOne, learningRate);
            } else {
                int s = rand.nextInt(classTwoMatrix.getCols());
                double error = nn.train(classTwoMatrix.getCol(s), labelTwo, learningRate);
            }
        }
        return count == numIterations;
    }

    //EFFECTS: Displays all elements added to frame
    public void showFrame() {
        ImageIcon frameIcon = new ImageIcon("./data/assets/logo.png");
        frame.setIconImage(frameIcon.getImage());
        elements.setVisible(true);
        train.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Neural Network Playground");
        frame.pack();
        frame.setSize(700,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    //EFFECTS: saves the state of the created dataset and Neural Network
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void save() {
        nnWriter = new JsonWriter(NN_STORE);
        try {
            nnWriter.open();
            nnWriter.write(nn, "NN");
            nnWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write Neural Network data");
        }
        datasetWriter = new JsonWriter(CLASS_ONE_STORE);
        try {
            datasetWriter.open();
            datasetWriter.write(classOneMatrix, "classOneData");
            datasetWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write Class one data");
        }
        datasetWriter = new JsonWriter(CLASS_TWO_STORE);
        try {
            datasetWriter.open();
            datasetWriter.write(classTwoMatrix, "classTwoData");
            datasetWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write Class two data");
        }

    }
}
