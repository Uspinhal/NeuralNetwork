package neuralnet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Neuron
 */
public class Neuron {

    private ArrayList<Double> listOfWeightIn;
    private ArrayList<Double> listOfWeightOf;


// Getters & Setters

    public ArrayList<Double> getListOfWeightIn() {
        return this.listOfWeightIn;
    }

    public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
        this.listOfWeightIn = listOfWeightIn;
    }

    public ArrayList<Double> getListOfWeightOut() {
        return this.listOfWeightOf;
    }

    public void setListOfWeightOut(ArrayList<Double> listOfWeightOf) {
        this.listOfWeightOf = listOfWeightOf;
    }

// Métodos
    public double initNeuron() {
        return new Random().nextDouble();
    }

}