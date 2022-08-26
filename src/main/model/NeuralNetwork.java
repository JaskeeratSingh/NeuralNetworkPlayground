package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

/**
 * Neural Network is a machine learning structure
 * This class represents the Neural Network and its operations*/

public class NeuralNetwork implements Writeable {

    private int inputNodesNum;
    private int hiddenNodesNum;
    private int outputNodesNum;

    private Matrix hiddenWeights;
    private Matrix hiddenNeurons;
    private Matrix hiddenActivation;
    private Matrix hiddenBias;
    private Matrix outputWeights;
    private Matrix outputNeurons;
    private Matrix outputActivation;
    private Matrix outputBias;

    //CONSTRUCTOR-------------------------------------------------------------//

    //EFFECTS: -Creates new NeuralNetwork, with inputNum input neurons
    //          hiddenNum hidden neurons and outputNum output Neurons
    //         -Randomizes all weights and biases
    public NeuralNetwork(int inputNum, int hiddenNum, int outputNum) {
        this.inputNodesNum = inputNum;
        this.hiddenNodesNum = hiddenNum;
        this.outputNodesNum = outputNum;

        this.hiddenNeurons = new Matrix(hiddenNum, 1);
        this.hiddenWeights = new Matrix(hiddenNum, inputNum);

        this.outputNeurons = new Matrix(outputNum, 1);
        this.outputWeights = new Matrix(outputNum, hiddenNum);

        this.hiddenBias = new Matrix(hiddenNum, 1);
        this.outputBias = new Matrix(outputNum, 1);

        hiddenWeights.randomize();
        hiddenBias.randomize();
        outputWeights.randomize();
        outputBias.randomize();
        EventLog.getInstance().logEvent(new Event("Neural network created, "
                + "training to draw decision boundary!"));
    }


    //REQUIRES: input.rows = inputNodesNum
    //EFFECTS: returns the outputMatrix of the Neural Network for current weights and biases
    public Matrix forwardProp(Matrix input) {

        //Input times weight -> add bias -> activate

        hiddenNeurons = hiddenWeights.dot(input).add(hiddenBias);
        hiddenActivation = hiddenNeurons.sigmoid();
        outputNeurons = outputWeights.dot(hiddenActivation).add(outputBias);
        outputActivation = outputNeurons.sigmoid();
        return this.outputActivation;
    }

    //REQUIRES: input.rows = inputNodesNum, target.rows = outputNodesNum
    //MODIFIES: this
    //EFFECTS: updates hiddenWeights, outputWeights, hiddenBias, outputBias values to reduce error
    //         returns current error between target and forwardProp output
    public double train(Matrix input, Matrix target, double learningRate) {
        Matrix outputs = this.forwardProp(input);
        double averageFactor = 1;

        //Backward Propagation
        //Calculate Gradients
        Matrix outputError = outputs.subtract(target);
        Matrix outputWeightsError = outputError.dot(hiddenActivation.transpose());
        outputWeightsError.scale(averageFactor);
        Matrix outputBiasError = outputError;
        outputBiasError.scale(averageFactor);

        Matrix temp = hiddenActivation.dsigmoid();
        Matrix hiddenError = outputWeights.transpose().dot(outputError).multiply(temp);
        Matrix hiddenWeightsError = hiddenError.dot(input.transpose());
        hiddenWeightsError.scale(averageFactor);
        Matrix hiddenBiasError = hiddenError;
        hiddenBiasError.scale(averageFactor);

        hiddenWeightsError.scale(learningRate);
        outputWeightsError.scale(learningRate);
        hiddenBiasError.scale(learningRate);
        outputBiasError.scale(learningRate);

        // Update weights and biases based on calculated gradients
        hiddenWeights = hiddenWeights.subtract(hiddenWeightsError);
        outputWeights = outputWeights.subtract(outputWeightsError);
        hiddenBias = hiddenBias.subtract(hiddenBiasError);
        outputBias = outputBias.subtract(outputBiasError);

        return outputError.getMatrix()[0][0];

    }

    //GETTERS AND SETTERS-------------------------------------------------------------//

    //EFFECTS: returns hiddenWeights of NeuralNetwork as a Matrix
    public Matrix getHiddenWeights() {
        return this.hiddenWeights;
    }

    //MODIFIES: this
    //EFFECTS: sets hiddenWeights of NeuralNetwork to given Matrix
    public void setHiddenWeights(Matrix hw) {
        this.hiddenWeights = hw;
    }

    //MODIFIES: this
    //EFFECTS: sets hiddenBias of NeuralNetwork to given Matrix
    public void setHiddenBias(Matrix hb) {
        this.hiddenBias = hb;
    }

    //EFFECTS: returns outputWeights of NeuralNetwork as a Matrix
    public Matrix getOutputWeights() {
        return this.outputWeights;
    }

    //MODIFIES: this
    //EFFECTS: sets outputWeights of NeuralNetwork to given Matrix
    public void setOutputWeights(Matrix ow) {
        this.outputWeights = ow;
    }

    //MODIFIES: this
    //EFFECTS: sets outputBias of NeuralNetwork to given Matrix
    public void setOutputBias(Matrix ob) {
        this.outputBias = ob;
    }

    //EFFECTS: returns number of inputs in NeuralNetwork
    public int getInputNodesNum() {
        return  this.inputNodesNum;
    }

    //EFFECTS: returns number of hidden neurons in NeuralNetwork
    public int getHiddenNodesNum() {
        return  this.hiddenNodesNum;
    }

    //EFFECTS: returns number of output neurons in NeuralNetwork
    public int getOutputNodesNum() {
        return  this.outputNodesNum;
    }

    //PERSISTENCE--------------------------------------------------------------------//

    //EFFECTS: converts the current NeuralNetwork to a json object
    @Override
    public JSONObject toJson(String name) {
        JSONObject json = new JSONObject();
        json.put("inputNodesNum", inputNodesNum);
        json.put("hiddenNodesNum", hiddenNodesNum);
        json.put("outputNodesNum", outputNodesNum);
        JSONArray arr = new JSONArray();
        arr.putAll(this.hiddenWeights.getMatrix());
        json.put("hiddenWeights", arr);
        arr = new JSONArray();
        arr.putAll(this.hiddenBias.getMatrix());
        json.put("hiddenBias", arr);
        arr = new JSONArray();
        arr.putAll(this.outputWeights.getMatrix());
        json.put("outputWeights", arr);
        arr = new JSONArray();
        arr.putAll(this.outputBias.getMatrix());
        json.put("outputBias", arr);
        return json;
    }
}
