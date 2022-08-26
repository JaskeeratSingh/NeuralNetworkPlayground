package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Matrix;
import model.NeuralNetwork;
import org.json.*;

/** Represents a reader that reads NeuralNetwork and Matrices from JSON data stored in file
 * CITATION: Used code from JSONSerializationDemo from CPSC210 course material*/
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads NeuralNetwork from file and returns it;
    // throws IOException if an error occurs reading data from file
    public NeuralNetwork read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseNeuralNetwork(jsonObject);
    }

    // EFFECTS: reads Matrix from file and returns it;
    // throws IOException if an error occurs reading data from file
    public double[][] read(String name) throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return jsonToArray(jsonObject.getJSONArray(name));
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Neural Network from JSON object and returns it
    private NeuralNetwork parseNeuralNetwork(JSONObject jsonObject) {
        int inputNodesNum = jsonObject.getInt("inputNodesNum");
        int hiddenNodesNum = jsonObject.getInt("hiddenNodesNum");
        int outputNodesNum = jsonObject.getInt("outputNodesNum");
        NeuralNetwork nn = new NeuralNetwork(inputNodesNum, hiddenNodesNum, outputNodesNum);
        JSONArray hiddenWeights = jsonObject.getJSONArray("hiddenWeights");
        JSONArray outputWeights = jsonObject.getJSONArray("outputWeights");
        JSONArray hiddenBias = jsonObject.getJSONArray("hiddenBias");
        JSONArray outputBias = jsonObject.getJSONArray("outputBias");
        Matrix hw = new Matrix(jsonToArray(hiddenWeights));
        Matrix ow = new Matrix(jsonToArray(outputWeights));
        Matrix hb = new Matrix(jsonToArray(hiddenBias));
        Matrix ob = new Matrix(jsonToArray(outputBias));
        nn.setOutputBias(ob);
        nn.setOutputWeights(ow);
        nn.setHiddenBias(hb);
        nn.setHiddenWeights(hw);
        return nn;
    }


    // EFFECTS: converts parsed json object to double array
    private double[][] jsonToArray(JSONArray arr) {
        double[][] result = new double[arr.length()][];
        for (int i = 0; i < arr.length(); i++) {
            JSONArray innerArray = arr.getJSONArray(i);
            double[] innerResult = new double[innerArray.length()];

            for (int j = 0; j < innerArray.length(); j++) {
                innerResult[j] = innerArray.getDouble(j);
            }

            result[i] = innerResult;
        }
        return result;
    }

}


