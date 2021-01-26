package neuralnet;

import java.util.ArrayList;

public abstract class Layer {

    private ArrayList<Neuron> listOfNeurons;
    protected Integer numberOfNeuronsInLayer;

    public void printLayer(){

    }

    public ArrayList<Neuron> getListOfNeurons() {
        return listOfNeurons;
    }

    public void setListOfNeurons(ArrayList<Neuron> listOfNeurons){
        this.listOfNeurons = listOfNeurons;
    }

    public Integer getNumberOfNeuronsInLayer() {
        return numberOfNeuronsInLayer;
    }

    public void setNumberOfNeuronsInLayer(int numberOdNeuronsInLayer) {
        this.numberOfNeuronsInLayer = numberOdNeuronsInLayer;
    }

}
