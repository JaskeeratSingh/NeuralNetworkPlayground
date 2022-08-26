package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**NewSessionGUI handles the GUI display, frames and elements to
 * allow the user to create a dataset
 */

public class NewSessionGUI {

    private JFrame frame;
    private JButton clearBtn;
    private JButton blueBtn;
    private JButton redBtn;
    private JButton nextBtn;
    private DatasetWindow datasetArea;
    private JPanel dataset;
    private JPanel controls;
    private JLabel label;
    private Color black = Color.decode("#000");
    private Color blue = Color.decode("#0070F3");
    private Color green = Color.decode("#05b171");
    File absender = new File("./data/assets/absender1.ttf");
    File raleway = new File("./data/assets/Raleway-Regular.ttf");
    private int frameWidth = 0;

    ActionListener actionListener = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == blueBtn) {
                datasetArea.blue();
            } else if (e.getSource() == redBtn) {
                datasetArea.black();
            } else if (e.getSource() == nextBtn) {
                datasetArea.next();
            }
        }
    };

    //MODIFIES: this
    //EFFECTS: Creates, sets up and displays frame to handle creation of dataset
    public void show() {
        frame = new JFrame("Neural Network Playground");
        dataset = new JPanel();
        dataset.setLayout(new BorderLayout());
        datasetArea = new DatasetWindow(frame);
        datasetArea.setPreferredSize(new Dimension(400, 350));
        this.frameWidth = frame.getBounds().width;
        controls = new JPanel();
        controls.setLayout(new GridBagLayout());
        dataset.setBackground(Color.decode("#e4eaf1"));
        controls.setBackground(Color.decode("#e4eaf1"));

        createElements();
        setFonts();
        placeElements();
        showFrame();
    }

    //MODIFIES: this
    //EFFECTS: Creates elements that belong in the frame
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void createElements() {
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(actionListener);
        clearBtn.setBorder(new LineBorder(green, 1));
        clearBtn.setPreferredSize(new Dimension(100,37));
        clearBtn.setForeground(green);
        clearBtn.setBackground(Color.decode("#e4eaf1"));
        blueBtn = new JButton("Click to add Class One points");
        blueBtn.addActionListener(actionListener);
        blueBtn.setBorder(new LineBorder(Color.decode("#0070F3"), 1));
        blueBtn.setForeground(Color.decode("#0070F3"));
        blueBtn.setBackground(Color.decode("#e4eaf1"));
        blueBtn.setPreferredSize(new Dimension(250,37));
        redBtn = new JButton("Click to add Class Two points");
        redBtn.addActionListener(actionListener);
        redBtn.setBorder(new LineBorder(black, 1));
        redBtn.setPreferredSize(new Dimension(250,37));
        redBtn.setForeground(black);
        redBtn.setBackground(Color.decode("#e4eaf1"));
        nextBtn = new JButton("Setup Neural Network");
        nextBtn.addActionListener(actionListener);
        nextBtn.setBorder(new LineBorder(blue, 1));
        nextBtn.setPreferredSize(new Dimension(150,37));
        nextBtn.setForeground(Color.decode("#e4eaf1"));
        nextBtn.setBackground(blue);
        blueBtn.setFocusPainted(false);
        redBtn.setFocusPainted(false);
        nextBtn.setFocusPainted(false);
        clearBtn.setFocusPainted(false);
        label = new JLabel("First let's create a binary classification dataset!");
    }

    //EFFECTS: Sets the fonts for the elements in frame
    public void setFonts() {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, absender);
            Font ralewayy = Font.createFont(Font.TRUETYPE_FONT, raleway);
            font = font.deriveFont(22f);
            ralewayy = ralewayy.deriveFont(15f);
            //welcome.setFont(font);
            redBtn.setFont(ralewayy);
            label.setFont(font);
            blueBtn.setFont(ralewayy);
            clearBtn.setFont(ralewayy);
            nextBtn.setFont(ralewayy);
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
        controls.add(blueBtn, c);
        c.gridx = 1;
        c.gridy = 3;
        controls.add(redBtn, c);
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(10,35,20,10);
        controls.add(nextBtn, c);
        label.setBorder(BorderFactory.createEmptyBorder(35, 60, 0, 0));
        dataset.add(label, BorderLayout.NORTH);
        dataset.add(datasetArea, BorderLayout.CENTER);
        c.insets = new Insets(10,35,5,10);
        c.gridx = 1;
        c.gridy = 5;
    }

    //EFFECTS: displaces the current frame
    public void showFrame() {
        ImageIcon frameIcon = new ImageIcon("./data/assets/logo.png");
        frame.setIconImage(frameIcon.getImage());
        dataset.add(controls, BorderLayout.SOUTH);
        frame.add(dataset, BorderLayout.CENTER);
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
