package persistence;

import model.Matrix;
import model.NeuralNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*CITATION: Used code from JSONSerializationDemo from CPSC210 course material*/
public class JsonWriterTest {

    private double epsilon = 0.000001d;
    private NeuralNetwork nn;
    private NeuralNetwork nnTwo;
    private Matrix hiddenWeight;
    private Matrix outputWeight;
    private Matrix twoByTwo;
    private Matrix twoByTwoB;

    @BeforeEach
    private void setup() {
        hiddenWeight = new Matrix(1,1);
        outputWeight = new Matrix(1,1);
        hiddenWeight.setMatrix(0,0, 1.0);
        outputWeight.setMatrix(0,0, 2.6);
        twoByTwo = new Matrix(2,2);
        twoByTwoB = new Matrix(2,2);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            nn = new NeuralNetwork(1,2,3);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyNN() {
        try {
            nn = new NeuralNetwork(1,1,1);
            nn.setHiddenWeights(hiddenWeight);
            nn.setOutputWeights(outputWeight);
            JsonWriter writer = new JsonWriter("./data/testnn.json");
            writer.open();
            writer.write(nn, "NeuralNetwork");
            writer.close();

            JsonReader reader = new JsonReader("./data/testnn.json");
            nnTwo = reader.read();
            compareNN(nn, nnTwo);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralNN() {
        try {
            nn = new NeuralNetwork(1,1,1);
            JsonWriter writer = new JsonWriter("./data/testnn.json");
            nn.setHiddenWeights(hiddenWeight);
            nn.setOutputWeights(outputWeight);
            writer.open();
            writer.write(nn, "NeuralNetwork");
            writer.close();

            JsonReader reader = new JsonReader("./data/testnn.json");
            nnTwo = reader.read();
            compareNN(nn, nnTwo);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralMatrix() {
        try {
            twoByTwo.setMatrix(0,0,1.1);
            twoByTwo.setMatrix(0,1,1.1);
            twoByTwo.setMatrix(1,0,2.1);
            twoByTwo.setMatrix(1,1,2.1);
            JsonWriter writer = new JsonWriter("./data/testmatrix.json");
            writer.open();
            writer.write(twoByTwo, "Matrix");
            writer.close();

            JsonReader reader = new JsonReader("./data/testmatrix.json");
            twoByTwoB = new Matrix(reader.read("Matrix"));
            assertEquals(twoByTwo.getMatrix()[0][0], twoByTwoB.getMatrix()[0][0], epsilon);
            assertEquals(twoByTwo.getMatrix()[0][1], twoByTwoB.getMatrix()[0][1], epsilon);
            assertEquals(twoByTwo.getMatrix()[1][0], twoByTwoB.getMatrix()[1][0], epsilon);
            assertEquals(twoByTwo.getMatrix()[1][1], twoByTwoB.getMatrix()[1][1], epsilon);

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
