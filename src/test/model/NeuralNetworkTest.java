package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class NeuralNetworkTest {

    private Random rand;
    private NeuralNetwork nn;
    private NeuralNetwork nnTwo;
    private NeuralNetwork nnThree;
    private Matrix inputs;
    private Matrix targets;
    private Matrix guess;
    private Matrix input;
    private Matrix hiddenWeight;
    private Matrix outputWeight;
    private Matrix hiddenBias;
    private Matrix outputBias;
    private Matrix temp;
    private double loss;
    private double lossTwo;

    @BeforeEach
    public void setup() {
        rand = new Random();
        nn = new NeuralNetwork(2, 4, 1);
        nnTwo = new NeuralNetwork(2, 10, 3);
        nnThree = new NeuralNetwork(1,1,1);
        guess = new Matrix(1,1);

        //Input Training Data
        inputs = new Matrix(2, 4);
        inputs.setMatrix(0, 0, 0);
        inputs.setMatrix(0, 1, 0);
        inputs.setMatrix(0, 2, 1);
        inputs.setMatrix(0, 3, 1);
        inputs.setMatrix(1, 0, 0);
        inputs.setMatrix(1, 1, 1);
        inputs.setMatrix(1, 2, 0);
        inputs.setMatrix(1, 3, 1);


        //Input Training Data Labels
        targets = new Matrix(1, 4);
        targets.setMatrix(0, 0, 0);
        targets.setMatrix(0, 1, 1);
        targets.setMatrix(0, 2, 1);
        targets.setMatrix(0, 3, 0);

        input = new Matrix(1,1);
        temp = new Matrix(1,1);
        hiddenWeight = new Matrix(1,1);
        outputWeight = new Matrix(1,1);
        hiddenBias = new Matrix(1,1);
        outputBias = new Matrix(1,1);
        hiddenWeight.setMatrix(0,0, 1.0);
        outputWeight.setMatrix(0,0, 2.6);
        hiddenBias.setMatrix(0,0, 1.3);
        outputBias.setMatrix(0,0, -1.2);
        input.setMatrix(0,0, 2.3);

    }

    @Test
    public void NeuralNetworkTest() {
        assertEquals(nn.getInputNodesNum(), 2);
        assertEquals(nn.getHiddenNodesNum(), 4);
        assertEquals(nn.getOutputNodesNum(), 1);
        assertEquals(nnTwo.getHiddenNodesNum(), 10);
        assertEquals(nnTwo.getOutputNodesNum(), 3);
        assertEquals(nn.getHiddenWeights().getRows(), 4);
        assertEquals(nn.getHiddenWeights().getCols(), 2);
        assertEquals(nn.getOutputWeights().getRows(), 1);
        assertEquals(nn.getOutputWeights().getCols(), 4);
    }

    @Test
    public void forwardPropTest() {
        assertEquals(nn.forwardProp(inputs.getCol(0)).getCols(), 1);
        assertEquals(nn.forwardProp(inputs.getCol(0)).getRows(), 1);
        guess = nn.forwardProp(inputs.getCol(3));
        assertTrue(guess.getMatrix()[0][0]<=1 && guess.getMatrix()[0][0]>=0);
        assertEquals(nnTwo.forwardProp(inputs.getCol(1)).getRows(), 3);
        assertEquals(nnTwo.forwardProp(inputs.getCol(1)).getCols(), 1);
        nnThree.setHiddenWeights(hiddenWeight);
        nnThree.setOutputWeights(outputWeight);
        nnThree.setHiddenBias(hiddenBias);
        nnThree.setOutputBias(outputBias);

        hiddenWeight.setMatrix(0,0, 1.0);
        outputWeight.setMatrix(0,0, 2.6);
        hiddenBias.setMatrix(0,0, 1.3);
        outputBias.setMatrix(0,0, -1.2);
        input.setMatrix(0,0, 2.3);
        temp = hiddenWeight.dot(input).add(hiddenBias);
        temp = temp.sigmoid();
        temp = outputWeight.dot(temp).add(outputBias);
        temp = temp.sigmoid();
        assertEquals(nnThree.forwardProp(input).getMatrix()[0][0], temp.getMatrix()[0][0]);
    }

    @Test
    public void trainTest() {
        for (int i = 0; i < 10; i++) {
            int j = rand.nextInt(5);
            loss = nn.train(inputs.getCol(j), targets.getCol(j), 0.1);
        }

        for (int i = 0; i < 50000; i++) {
            int j = rand.nextInt(5);
            lossTwo = nn.train(inputs.getCol(j), targets.getCol(j), 0.1);
        }
        assertTrue(lossTwo<loss);
        assertTrue(lossTwo < 0.1);
    }

}
