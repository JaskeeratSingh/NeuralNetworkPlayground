package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Matrix is a two-dimensional array
 * This class represents the Matrix object and its corresponding operations
 * Matrix math is essential for Neural Network calculations*/

public class Matrix implements Writeable {

    private int rows;
    private int cols;
    private double[][] matrix;
    private Random rand = new Random();
    private static int count = 0;


    //CONSTRUCTORS-------------------------------------------------------------//

    //EFFECTS: Creates a Matrix object of rows, cols with all elements 0s
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.matrix[i][j] = 0;
            }
        }
    }

    //EFFECTS: Creates a column Matrix from a one dimensional ArrayList
    public Matrix(ArrayList<Double> a) {
        this.rows = a.size();
        this.cols = 1;
        matrix = new double[rows][cols];
        for (int i = 0; i < this.rows; i++) {
            this.matrix[i][0] = a.get(i);
        }
    }

    //EFFECTS: Creates a Matrix object from a 2-dimensional double Array
    public Matrix(double[][] arr) {
        this.rows = arr.length;
        this.cols = arr[0].length;
        this.matrix = arr;
        if (count == 0) {
            count = 1;
            EventLog.getInstance().logEvent(new Event("Points added to create Class one dataset"));
        } else if (count == 1) {
            EventLog.getInstance().logEvent(new Event("Points added to create Class two dataset"));
        }

    }

    //OPERATIONS-------------------------------------------------------------//

    //REQUIRES: cols = 1
    //EFFECTS: returns an Arraylist of column Matrix elements
    public ArrayList<Double> toArrayList() {
        ArrayList<Double> a = new ArrayList<>();
        for (int i = 0; i < this.rows; i++) {
            a.add(this.matrix[i][0]);
        }
        return a;
    }

    //MODIFIES: this
    //EFFECTS: randomizes all elements of Matrix
    public void randomize() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.matrix[i][j] = ((rand.nextDouble() * 2.0) - 1);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: scales each element of Matrix by double constant 'n'
    public void scale(double n) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.matrix[i][j] *= n;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: adds double 'n' to each element of Matrix
    public void add(double n) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.matrix[i][j] += n;
            }
        }
    }

    //REQUIRES: this.rows = n.rows and this.cols = n.cols
    //EFFECTS: returns new Matrix which is the element-wise sum of this and Matrix 'n'
    public Matrix add(Matrix n) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setMatrix(i, j, (this.matrix[i][j] + n.getMatrix()[i][j]));
            }
        }
        return result;
    }

    //REQUIRES: this.rows = n.rows and this.cols = n.cols
    //EFFECTS: returns new Matrix which is the element-wise multiplication of this and Matrix 'n'
    public Matrix multiply(Matrix n) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setMatrix(i, j, (this.matrix[i][j] * n.getMatrix()[i][j]));
            }
        }
        return result;
    }


    //REQUIRES: this.rows = n.rows and this.cols = n.cols
    //EFFECTS: returns new Matrix which is the element-wise difference of this and Matrix 'n'
    public Matrix subtract(Matrix n) {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setMatrix(i, j, (this.matrix[i][j] - n.getMatrix()[i][j]));
            }
        }
        return result;
    }

    //EFFECTS: returns new Matrix applying Sigmoid function on each element of this Matrix
    public Matrix sigmoid() {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setMatrix(i, j, (1 / (1 + Math.exp(-1 * this.matrix[i][j]))));
            }
        }
        return result;
    }

    //EFFECTS: returns new Matrix applying derivative of sigmoid function on each element of this Matrix
    public Matrix dsigmoid() {
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                double temp = this.getMatrix()[i][j];
                result.setMatrix(i, j, temp * (1 - temp));
            }
        }
        return result;
    }


    //REQUIRES: this.cols = n.rows
    //EFFECTS: returns new Matrix which is the dot product of this Matrix and 'n'
    public Matrix dot(Matrix n) {
        Matrix result = new Matrix(this.rows, n.cols);
        for (int i = 0; i < result.rows; i++) {
            for (int j = 0; j < result.cols; j++) {
                double sum = 0;
                for (int k = 0; k < this.cols; k++) {
                    sum += (this.getMatrix()[i][k] * n.getMatrix()[k][j]);
                }
                result.setMatrix(i, j, sum);
            }
        }
        return result;
    }

    //EFFECTS: returns new Matrix which is this Matrix transposed
    public Matrix transpose() {
        Matrix result = new Matrix(this.cols, this.rows);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.setMatrix(j, i, this.matrix[i][j]);
            }
        }
        return result;
    }

    //GETTERS AND SETTERS-----------------------------------------------------------//

    //EFFECTS: returns Matrix values as a double 2d Array
    public double[][] getMatrix() {
        return this.matrix;
    }

    //MODIFIES: this
    //EFFECTS: inserts val at coordinate i,j in Matrix
    public void setMatrix(int i, int j, double val) {
        this.matrix[i][j] = val;
    }

    //EFFECTS: returns number of rows of Matrix
    public int getRows() {
        return this.rows;
    }

    //EFFECTS: returns number of columns of Matrix
    public int getCols() {
        return this.cols;
    }

    //EFFECTS: returns kth indexed column of Matrix
    public Matrix getCol(int k) {
        Matrix result = new Matrix(this.rows, 1);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (j == k) {
                    result.setMatrix(i, 0, this.matrix[i][k]);
                }
            }
        }
        return result;
    }

    //EFFECTS: converts the current matrix to a json object
    @Override
    public JSONObject toJson(String name) {
        JSONObject json = new JSONObject();
        JSONArray arr = new JSONArray();
        arr.putAll(this.matrix);
        json.put(name, arr);
        return json;
    }

}
