package persistence;

import model.Matrix;
import model.NeuralNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/*CITATION: Used code from JSONSerializationDemo from CPSC210 course material*/
public class JsonReaderTest {

    private double epsilon = 0.000001d;
    private NeuralNetwork nn;
    private NeuralNetwork nnTwo;
    private Matrix hiddenWeight;
    private Matrix outputWeight;
    private Matrix twoByTwo;

    @BeforeEach
    private void setup() {
        hiddenWeight = new Matrix(1,1);
        outputWeight = new Matrix(1,1);
        hiddenWeight.setMatrix(0,0, 1.0);
        outputWeight.setMatrix(0,0, 2.6);
        twoByTwo = new Matrix(2,2);
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            NeuralNetwork nn = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }
    @Test
    void testReaderGeneralMatrix() {
        try {
            JsonReader reader = new JsonReader("./data/testmatrix.json");
            twoByTwo = new Matrix(reader.read("Matrix"));
            assertEquals(twoByTwo.getMatrix()[0][0], 1.1, epsilon);
            assertEquals(twoByTwo.getMatrix()[0][1], 1.1, epsilon);
            assertEquals(twoByTwo.getMatrix()[1][0], 2.1, epsilon);
            assertEquals(twoByTwo.getMatrix()[1][1], 2.1, epsilon);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testReaderGeneralNN() {
        try {
            JsonReader reader = new JsonReader("./data/testnn.json");
            nn = new NeuralNetwork(1,1,1);
            nn.setHiddenWeights(hiddenWeight);
            nn.setOutputWeights(outputWeight);
            nnTwo = reader.read();
            compareNN(nn, nnTwo);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void compareNN(NeuralNetwork nn, NeuralNetwork nnTwo) {
        assertEquals(nn.getInputNodesNum(), nnTwo.getInputNodesNum());
        assertEquals(nn.getHiddenNodesNum(), nnTwo.getHiddenNodesNum());
        assertEquals(nn.getOutputNodesNum(), nnTwo.getOutputNodesNum());
        assertArrayEquals(nn.getHiddenWeights().getMatrix(), nnTwo.getHiddenWeights().getMatrix());
        assertArrayEquals(nn.getOutputWeights().getMatrix(), nnTwo.getOutputWeights().getMatrix());
    }

}
