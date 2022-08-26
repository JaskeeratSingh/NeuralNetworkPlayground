package ui;

import model.Matrix;
import model.NeuralNetwork;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

/**NetworkGUI handles the GUI to get the Neural Network hyperparameters*/

public class NetworkGUI {

    private JFrame frame;
    private JPanel panel;
    private JButton load;
    private JButton session;
    private JLabel welcome;
    private JTextField hidden;
    private JLabel hiddenLabel;
    private JTextField learningRate;
    private JLabel learningRateLabel;
    private JTextField numIter;
    private JLabel numIterLabel;
    private Matrix classOneMatrix;
    private Matrix classTwoMatrix;
    File absender = new File("./data/assets/absender1.ttf");
    File raleway = new File("./data/assets/Raleway-Regular.ttf");
    NeuralNetwork nn;

    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == session) {
                if (hidden.getText().equals("Integer")
                        || numIter.getText().equals("Integer")
                        || learningRate.getText().equals("Double")) {
                    JOptionPane.showMessageDialog(null, "You forgot to enter some values");
                } else {
                    int h = Integer.parseInt(hidden.getText());
                    Double lr = Double.parseDouble(learningRate.getText());
                    int n = Integer.parseInt(numIter.getText());
                    nn = new NeuralNetwork(2, h, 1);
                    TrainingGUI tg = new TrainingGUI(classOneMatrix, classTwoMatrix, lr, n, nn);
                    frame.dispose();
                }
            }
        }
    };

    //EFFECTS: sets up and displays elements to allow user to enter Network parameters
    public NetworkGUI(Matrix classOneMatrix, Matrix classTwoMatrix) {
        this.classOneMatrix = classOneMatrix;
        this.classTwoMatrix = classTwoMatrix;
        frame = new JFrame();
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 30));
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.decode("#e4eaf1"));

        createElements();
        setFonts();
        placeElements();
        handleExceptions();
        showFrame();
    }

    //MODIFIES: this
    //EFFECTS: Creates elements that belong in the frame
    public void createElements() {
        session = new JButton("Train your neural network in real time!");
        session.setBorder(new LineBorder(Color.decode("#0070F3")));
        session.setPreferredSize(new Dimension(300,37));
        session.setForeground(Color.decode("#e4eaf1"));
        session.setBackground(Color.decode("#0070F3"));
        session.setFocusPainted(false);
        hidden = new JTextField("Integer", 10);
        hiddenLabel = new JLabel("Number of hidden neurons:");
        learningRate = new JTextField("Double", 10);
        learningRateLabel = new JLabel("Learning Rate:");
        numIter = new JTextField("Integer", 20);
        numIterLabel = new JLabel("Number of Iterations:");
        session.addActionListener(actionListener);
        welcome = new JLabel("Setup your Neural Network hyperparameters!");
    }

    //EFFECTS: ensures only the right data types can be entered into the fields
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void handleExceptions() {
        learningRate.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                if (learningRate.getText().equals("Double")) {
                    learningRate.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (learningRate.getText().length() >= 1
                        && !learningRate.getText().equals("Double")
                        && !learningRate.getText().equals("")) {
                    try {
                        Double.parseDouble(learningRate.getText());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "You entered invalid data");
                        learningRate.grabFocus();//make the textField in focus again
                    }
                } else {
                    learningRate.setText("Double");
                }
            }
        });

        hidden.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                if (hidden.getText().equals("Integer")) {
                    hidden.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (hidden.getText().length() >= 1
                        && !hidden.getText().equals("Integer")
                        && !hidden.getText().equals("")) {
                    try {
                        Integer.parseInt(hidden.getText());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "You entered invalid data");
                        hidden.grabFocus();
                    }
                } else {
                    hidden.setText("Integer");
                }
            }
        });

        numIter.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                if (numIter.getText().equals("Integer")) {
                    numIter.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent fe) {
                if (numIter.getText().length() >= 1
                        && !numIter.getText().equals("Integer")
                        && !numIter.getText().equals("")) {
                    try {
                        Integer.parseInt(numIter.getText());
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "You entered invalid data");
                        numIter.grabFocus();
                    }
                } else {
                    numIter.setText("Integer");
                }
            }
        });

    }

    //EFFECTS: sets fonts for all elements
    public void setFonts() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, absender);
            Font ralewayy = Font.createFont(Font.TRUETYPE_FONT, raleway);
            font = font.deriveFont(27f);
            ralewayy = ralewayy.deriveFont(15f);
            welcome.setFont(font);
            numIterLabel.setFont(ralewayy);
            hiddenLabel.setFont(ralewayy);
            learningRateLabel.setFont(ralewayy);
            numIter.setFont(ralewayy);
            hidden.setFont(ralewayy);
            learningRate.setFont(ralewayy);
            //load.setFont(ralewayy);
            session.setFont(ralewayy);
        } catch (FontFormatException e) {
            JOptionPane.showMessageDialog(null, "Font not found");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Font not found");
        }
    }

    //EFFECTS: places elements in their right place
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void placeElements() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10,10,10,10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 7;
        panel.add(session, c);
        c.gridx = 1;
        c.gridy = 3;
        panel.add(numIterLabel, c);
        c.gridx = 2;
        c.gridy = 3;
        panel.add(numIter, c);
        c.gridx = 2;
        c.gridy = 2;
        panel.add(hidden, c);
        c.gridx = 1;
        c.gridy = 2;
        panel.add(hiddenLabel, c);
        c.gridx = 2;
        c.gridy = 4;
        panel.add(learningRate, c);
        c.gridx = 1;
        c.gridy = 4;
        panel.add(learningRateLabel, c);
        c.insets = new Insets(10,10,50,10);
        c.gridwidth = 7;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(welcome, c);
    }

    //EFFECTS: displays the frame along with all elements
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
}
