package neuralnet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Neuron
 */
public class Neuron {

    private ArrayList<Double> listOfWeightIn;
    private ArrayList<Double> listOfWeightOut;
    private double outputValue;
    private double error;
    private double sensibility;


// Getters & Setters

    public ArrayList<Double> getListOfWeightIn() {
        return listOfWeightIn;
    }

    public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
        this.listOfWeightIn = listOfWeightIn;
    }

    public ArrayList<Double> getListOfWeightOut() {
        return listOfWeightOut;
    }

    public void setListOfWeightOut(ArrayList<Double> listOfWeightOut) {
        this.listOfWeightOut = listOfWeightOut;
    }
    public double getOutputValue() {
		return outputValue;
	}

	public void setOutputValue(double outputValue) {
		this.outputValue = outputValue;
	}

	public double getError() {
		return this.error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public double getSensibility() {
		return this.sensibility;
	}

	public void setSensibility(double sensibility) {
		this.sensibility = sensibility;
	}

// Métodos
    public double initNeuron() {
        return new Random().nextDouble();
    }

}