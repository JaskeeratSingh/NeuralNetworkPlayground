package ui;

import model.Matrix;
import model.NeuralNetwork;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**TrainingWindow is the canvas where the decision boundary is displayed. */

public class TrainingWindow extends JComponent {

    private Image image;
    private Graphics2D g2;
    private Color black = Color.decode("#000");
    private Color blue = Color.decode("#0070F3");
    private JFrame frame;
    private Random rand = new Random();
    private NeuralNetwork nn;
    private Matrix input = new Matrix(2,1);
    private Matrix classOneMatrix;
    private Matrix classTwoMatrix;
    private int numIterations;
    private double learningRate;
    private Matrix out;
    private double prediction;
    private Color grey = Color.decode("#666666");
    private Color lightBlue = Color.decode("#7DBDFF");

    //EFFECTS: displays training window frame with the learnt decision boundary
    public TrainingWindow(JFrame frame, NeuralNetwork nn, Matrix classOneMatrix,
                          Matrix classTwoMatrix, int numIterations, double learningRate) {
        this.frame = frame;
        this.nn = nn;
        this.classOneMatrix = classOneMatrix;
        this.classTwoMatrix = classTwoMatrix;
        this.numIterations = numIterations;
        this.learningRate = learningRate;
        setDoubleBuffered(false);

    }

    //MODIFIES: this
    //EFFECTS: draws the dots of the database and decision boundary
    protected void paintComponent(Graphics g) {
        if (image == null) {
            image = createImage(400, 320);
            g2 = (Graphics2D) image.getGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        drawDecisionBoundary();

        double[][] classOne = classOneMatrix.getMatrix();
        double[][] classTwo = classTwoMatrix.getMatrix();
        for (int i = 0; i < classOneMatrix.getCols(); i++) {
            g2.setColor(blue);
            g2.fillOval((int)(classOne[0][i] * 400), (int)(classOne[1][i] * 320), 8, 8);
        }

        for (int i = 0; i < classTwoMatrix.getCols(); i++) {
            g2.setColor(Color.black);
            g2.fillOval((int)(classTwo[0][i] * 400), (int)(classTwo[1][i] * 320), 8, 8);
        }

        g.drawImage(image, (frame.getBounds().width - 400) / 2, 20, null);
    }

    //MODIFIES: this
    //EFFECTS: draws the decision boundary
    public void drawDecisionBoundary() {
        for (int i = 0; i < 400; i++) {
            for (int j = 0; j < 320; j++) {
                input.setMatrix(0,0, (double) i / 400);
                input.setMatrix(1,0, (double) j / 320);
                out = nn.forwardProp(input);
                prediction = out.getMatrix()[0][0];
                g2.setColor((prediction < 0.5) ? lightBlue : grey);
                g2.fillRect(i, j, 1, 1);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: clears the training window
    public void clear() {
        g2.setPaint(Color.white);
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(blue);
        repaint();
    }


}
