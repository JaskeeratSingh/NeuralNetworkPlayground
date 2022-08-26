package ui;

import model.Matrix;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;

/** DatasetWindow is the canvas where the dataset is created by mouse clicks*/

public class DatasetWindow extends JComponent {
    private Image image;
    private Graphics2D g2;
    private int currentX;
    private int currentY;
    private Color black = Color.decode("#000");
    private Color blue = Color.decode("#0070F3");
    private JFrame frame;
    private Matrix classOneMatrix;
    private Matrix classTwoMatrix;
    private ArrayList<double[]> classOneData = new ArrayList<>();
    private ArrayList<double[]> classTwoData = new ArrayList<>();


    //EFFECTS: displays dataset window frame where user can add data by mouse clicks
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public DatasetWindow(JFrame frame) {
        this.frame = frame;
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                if (g2 != null) {
                    //We want to normalize coordinates when we send them into the neural network and transpose them to
                    //be relative coordinates
                    if (currentX >= ((frame.getBounds().width - 400) / 2)
                            && currentY >= 20
                            && currentX <= ((frame.getBounds().width - 400) / 2) + 400
                            && currentY <= 20 + 320) {
                        if (g2.getPaint() == black) {
                            classTwoData.add(new double[]{(double) (currentX - (frame.getBounds().width - 400) / 2)
                                    / 400, (((double) currentY - 20) / 320)});
                        } else if (g2.getPaint() == blue) {
                            classOneData.add(new double[]{(double) (currentX - (frame.getBounds().width - 400) / 2)
                                    / 400, (((double) currentY - 20) / 320)});
                        }
                        g2.fillOval(currentX - 4 - (frame.getBounds().width - 400) / 2,
                                currentY - 4 - 20, 8, 8);
                    }
                    repaint();
                }
            }
        });

    }

    //MODIFIES: this
    //EFFECTS: renders dataset window everytime user adds points
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(400, 320);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, (frame.getBounds().width - 400) / 2, 20, null);
    }


    //MODIFIES: this
    //EFFECTS: clears the training window
    public void clear() {
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(blue);
        repaint();
    }

    //EFFECTS: sets colour for points to black
    public void black() {
        g2.setPaint(black);
    }

    //EFFECTS: sets colour for points to blue
    public void blue() {
        g2.setPaint(blue);
    }

    //EFFECTS: converts points clicked by users into respective class matrices
    public void next() {
        double[][] classOne = new double[2][classOneData.size()];
        double[][] classTwo = new double[2][classTwoData.size()];
        for (int i = 0; i < classOneData.size(); i++) {
            classOne[0][i] = classOneData.get(i)[0];
            classOne[1][i] = classOneData.get(i)[1];
        }
        for (int i = 0; i < classTwoData.size(); i++) {
            classTwo[0][i] = classTwoData.get(i)[0];
            classTwo[1][i] = classTwoData.get(i)[1];
        }
        classOneMatrix = new Matrix(classOne);
        classTwoMatrix = new Matrix(classTwo);
        NetworkGUI ng = new NetworkGUI(classOneMatrix, classTwoMatrix);
        frame.dispose();
    }
}
