package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MatrixTest {

    private Matrix twoByTwo;
    private Matrix twoByTwoB;
    private Matrix twoByThree;
    private Matrix threeByTwo;
    private Matrix testMatrix;
    private Matrix testMatrixTwo;
    private double[][] dataTest;
    private ArrayList<Double> check;
    private ArrayList<Double> test;
    private double epsilon = 0.000001d;

    @BeforeEach
    public void setup(){
        twoByTwo = new Matrix(2,2);
        twoByTwoB = new Matrix(2,2);
        twoByThree = new Matrix(2,3);
        threeByTwo = new Matrix(3,2);
        check = new ArrayList<>();
        test = new ArrayList<>();
        test.add(1.0);
        test.add(2.6);
        test.add(3.4);
        testMatrix = new Matrix(test);
        dataTest = new double[][] {
                {1.0, 2.2, 3.1},
                {3.6, 4.9, 2, 1}
        };
        testMatrixTwo = new Matrix(dataTest);
    }

    @Test
    public void MatrixTest() {
        for (int i = 0; i < twoByTwo.getRows(); i++) {
            for (int j = 0; j < twoByTwo.getCols(); j++) {
                assertEquals(twoByTwo.getMatrix()[i][j], 0);
            }
        }
        for (int i = 0; i < twoByThree.getRows(); i++) {
            for (int j = 0; j < twoByTwo.getCols(); j++) {
                assertEquals(twoByThree.getMatrix()[i][j], 0);
            }
        }

        for (int i = 0; i < threeByTwo.getRows(); i++) {
            for (int j = 0; j < threeByTwo.getCols(); j++) {
                assertEquals(threeByTwo.getMatrix()[i][j], 0);
            }
        }

        assertEquals(twoByTwo.getRows(), 2);
        assertEquals(twoByTwo.getCols(), 2);
        assertEquals(twoByThree.getRows(), 2);
        assertEquals(twoByThree.getCols(), 3);
        assertEquals(threeByTwo.getRows(), 3);
        assertEquals(threeByTwo.getCols(), 2);
    }

    @Test
    public void toMatrixTest() {
        assertEquals(testMatrix.getRows(), 3);
        assertEquals(testMatrix.getCols(), 1);
        assertEquals(testMatrix.getMatrix()[0][0], 1.0);
        assertEquals(testMatrix.getMatrix()[1][0], 2.6);
        assertEquals(testMatrix.getMatrix()[2][0], 3.4);
    }

    @Test
    public void toMatrixDataTest() {

        assertEquals(testMatrixTwo.getRows(), 2);
        assertEquals(testMatrixTwo.getCols(), 3);
        assertEquals(testMatrixTwo.getMatrix()[0][0], 1.0);
        assertEquals(testMatrixTwo.getMatrix()[1][0], 3.6);
        assertEquals(testMatrixTwo.getMatrix()[1][1], 4.9);
    }

    @Test
    public void randomizeTest() {
        twoByThree.randomize();
        for (int i = 0; i < twoByThree.getRows(); i++) {
            for (int j = 0; j < twoByThree.getCols(); j++) {
                check.add(twoByThree.getMatrix()[i][j]);
            }
        }
        assertNotEquals(check.subList(0,3), check.subList(3,5));
        assertNotEquals(check.subList(0,2), check.subList(2,4));
    }

    @Test
    public void scaleTest() {
        twoByThree.randomize();
        for (int i = 0; i < twoByThree.getRows(); i++) {
            for (int j = 0; j < twoByThree.getCols(); j++) {
                check.add(twoByThree.getMatrix()[i][j]);
            }
        }
        twoByThree.scale(3);
        assertEquals(twoByThree.getMatrix()[0][0], (check.get(0)*3), epsilon);
        assertEquals(twoByThree.getMatrix()[0][2], (check.get(2)*3), epsilon);
        assertEquals(twoByThree.getMatrix()[1][2], (check.get(5)*3), epsilon);
    }

    @Test
    public void addTest() {
        threeByTwo.randomize();
        for (int i = 0; i < threeByTwo.getRows(); i++) {
            for (int j = 0; j < threeByTwo.getCols(); j++) {
                check.add(threeByTwo.getMatrix()[i][j]);
            }
        }
        threeByTwo.add(3.1);
        assertEquals(threeByTwo.getMatrix()[0][0], (check.get(0)+3.1), epsilon);
        assertEquals(threeByTwo.getMatrix()[1][0], (check.get(2)+3.1), epsilon);
        assertEquals(threeByTwo.getMatrix()[2][1],  (check.get(5)+3.1), epsilon);
    }

    @Test
    public void addMatrixTest() {
        twoByTwo.setMatrix(0,0,1.1);
        twoByTwo.setMatrix(0,1,1.1);
        twoByTwo.setMatrix(1,0,2.1);
        twoByTwo.setMatrix(1,1,2.1);
        twoByTwoB.setMatrix(0,0,1.3);
        twoByTwoB.setMatrix(0,1,1.4);
        twoByTwoB.setMatrix(1,0,5.1);
        twoByTwoB.setMatrix(1,1,4.2);
        twoByTwo = twoByTwo.add(twoByTwoB);
        assertEquals(twoByTwo.getMatrix()[0][0], (1.1 + 1.3), epsilon);
        assertEquals(twoByTwo.getMatrix()[0][1], (1.1 + 1.4), epsilon);
        assertEquals(twoByTwo.getMatrix()[1][0], (2.1 + 5.1), epsilon);
        assertEquals(twoByTwo.getMatrix()[1][1], (2.1 + 4.2), epsilon);
    }

    @Test
    public void subtractMatrixTest() {
        twoByTwo.setMatrix(0,0,1.1);
        twoByTwo.setMatrix(0,1,1.1);
        twoByTwo.setMatrix(1,0,2.1);
        twoByTwo.setMatrix(1,1,2.1);
        twoByTwoB.setMatrix(0,0,1.3);
        twoByTwoB.setMatrix(0,1,1.4);
        twoByTwoB.setMatrix(1,0,5.1);
        twoByTwoB.setMatrix(1,1,4.2);
        twoByTwo = twoByTwo.subtract(twoByTwoB);
        assertEquals(twoByTwo.getMatrix()[0][0], (1.1 - 1.3), epsilon);
        assertEquals(twoByTwo.getMatrix()[0][1], (1.1 - 1.4), epsilon);
        assertEquals(twoByTwo.getMatrix()[1][0], (2.1 - 5.1), epsilon);
        assertEquals(twoByTwo.getMatrix()[1][1], (2.1 - 4.2), epsilon);
    }

    @Test
    public void multiplyMatrixTest() {
        twoByTwo.setMatrix(0,0,1.1);
        twoByTwo.setMatrix(0,1,1.1);
        twoByTwo.setMatrix(1,0,2.1);
        twoByTwo.setMatrix(1,1,2.1);
        twoByTwoB.setMatrix(0,0,1.3);
        twoByTwoB.setMatrix(0,1,1.4);
        twoByTwoB.setMatrix(1,0,5.1);
        twoByTwoB.setMatrix(1,1,4.2);
        twoByTwo = twoByTwo.multiply(twoByTwoB);
        assertEquals(twoByTwo.getMatrix()[0][0], (1.1 * 1.3), epsilon);
        assertEquals(twoByTwo.getMatrix()[0][1], (1.1 * 1.4), epsilon);
        assertEquals(twoByTwo.getMatrix()[1][0], (2.1 * 5.1), epsilon);
        assertEquals(twoByTwo.getMatrix()[1][1], (2.1 * 4.2), epsilon);
    }

    @Test
    public void dotProductTest() {
        twoByTwo.setMatrix(0,0,1.1);
        twoByTwo.setMatrix(0,1,1.1);
        twoByTwo.setMatrix(1,0,2.1);
        twoByTwo.setMatrix(1,1,2.1);
        twoByTwoB.setMatrix(0,0,1.3);
        twoByTwoB.setMatrix(0,1,1.4);
        twoByTwoB.setMatrix(1,0,5.1);
        twoByTwoB.setMatrix(1,1,4.2);
        twoByTwo = twoByTwo.dot(twoByTwoB);
        assertEquals(twoByTwo.getMatrix()[0][0], 7.04, epsilon);
        assertEquals(twoByTwo.getMatrix()[0][1], 6.16, epsilon);
        assertEquals(twoByTwo.getMatrix()[1][0], 13.44, epsilon);
        assertEquals(twoByTwo.getMatrix()[1][1], 11.76, epsilon);

    }

    @Test
    public void transposeTest() {
        threeByTwo.randomize();
        for (int i = 0; i < threeByTwo.getRows(); i++) {
            for (int j = 0; j < threeByTwo.getCols(); j++) {
                check.add(threeByTwo.getMatrix()[i][j]);
            }
        }
        twoByThree = threeByTwo.transpose();
        assertEquals(check.get(0), twoByThree.getMatrix()[0][0], epsilon);
        assertEquals(check.get(2), twoByThree.getMatrix()[0][1], epsilon);
        assertEquals(check.get(5), twoByThree.getMatrix()[1][2], epsilon);

    }

    @Test
    public void sigmoidTest() {
        threeByTwo.randomize();
        for (int i = 0; i < threeByTwo.getRows(); i++) {
            for (int j = 0; j < threeByTwo.getCols(); j++) {
                check.add(1/(1+Math.exp(-1*threeByTwo.getMatrix()[i][j])));
            }
        }
        threeByTwo = threeByTwo.sigmoid();
        assertEquals(check.get(0), threeByTwo.getMatrix()[0][0], epsilon);
        assertEquals(check.get(2), threeByTwo.getMatrix()[1][0], epsilon);
        assertEquals(check.get(5), threeByTwo.getMatrix()[2][1], epsilon);

    }

    @Test
    public void dsigmoidTest() {
        threeByTwo.randomize();
        for (int i = 0; i < threeByTwo.getRows(); i++) {
            for (int j = 0; j < threeByTwo.getCols(); j++) {
                double temp = threeByTwo.getMatrix()[i][j];
                check.add(temp*(1-temp));
            }
        }
        threeByTwo = threeByTwo.dsigmoid();
        assertEquals(check.get(0), threeByTwo.getMatrix()[0][0], epsilon);
        assertEquals(check.get(2), threeByTwo.getMatrix()[1][0], epsilon);
        assertEquals(check.get(5), threeByTwo.getMatrix()[2][1], epsilon);

    }

    @Test
    public void toArrayList(){
        check = testMatrix.toArrayList();
        assertEquals(check, test);
        twoByTwo.randomize();
        check = twoByTwo.getCol(0).toArrayList();
        assertEquals(check.get(0), twoByTwo.getMatrix()[0][0]);
        assertEquals(check.get(1), twoByTwo.getMatrix()[1][0]);
    }

}
