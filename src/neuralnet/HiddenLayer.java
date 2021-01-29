package neuralnet;

import java.util.ArrayList;
import java.util.Arrays;

public class HiddenLayer extends Layer {

    public ArrayList<HiddenLayer> initLayer(HiddenLayer hiddenLayer, ArrayList<HiddenLayer> listOfHiddenLayer, 
                                            InputLayer inputLayer, OutputLayer outputLayer) {


		ArrayList<Double> listOfWeightIn = new ArrayList<>();
		ArrayList<Double> listOfWeightOut = new ArrayList<>();
		ArrayList<Neuron> listOfNeurons = new ArrayList<>();

		int numberOfHiddenLayers = listOfHiddenLayer.size();

		for (int hdn_i = 0; hdn_i < numberOfHiddenLayers; hdn_i++) {
			for (int neuron_i = 0; neuron_i < hiddenLayer.getNumberOfNeuronsInLayer(); neuron_i++) {
				Neuron neuron = new Neuron();

				int limitIn = 0;
				int limitOut = 0;

				if (hdn_i == 0) { // first
					limitIn = inputLayer.getNumberOfNeuronsInLayer();
					if (numberOfHiddenLayers > 1) {
						limitOut = listOfHiddenLayer.get(hdn_i + 1).getNumberOfNeuronsInLayer();
					} else if(numberOfHiddenLayers == 1) {
						limitOut = outputLayer.getNumberOfNeuronsInLayer();
					}
				} else if (hdn_i == numberOfHiddenLayers - 1) { // last
					limitIn = listOfHiddenLayer.get(hdn_i - 1).getNumberOfNeuronsInLayer();
					limitOut = outputLayer.getNumberOfNeuronsInLayer();
				} else { // middle
					limitIn = listOfHiddenLayer.get(hdn_i - 1).getNumberOfNeuronsInLayer();
					limitOut = listOfHiddenLayer.get(hdn_i + 1).getNumberOfNeuronsInLayer();
				}

				limitIn  = limitIn  - 1; // bias is not connected
				limitOut = limitOut - 1; // bias is not connected

				if (neuron_i >= 1) {
					for (int k = 0; k <= limitIn; k++) {
						listOfWeightIn.add(neuron.initNeuron());

					}
				}
				for (int k = 0; k <= limitOut; k++) {
					listOfWeightOut.add(neuron.initNeuron());

				}

				neuron.setListOfWeightIn(listOfWeightIn);
				neuron.setListOfWeightOut(listOfWeightOut);
				listOfNeurons.add(neuron);

				listOfWeightIn = new ArrayList<>();
				listOfWeightOut = new ArrayList<>();

			}

			listOfHiddenLayer.get(hdn_i).setListOfNeurons(listOfNeurons);

			listOfNeurons = new ArrayList<>();

		}

		return listOfHiddenLayer;

    }
    
	public void printLayer(ArrayList<HiddenLayer> listOfHiddenLayer) {
		if (!listOfHiddenLayer.isEmpty()) {
			System.out.println("### HIDDEN LAYER ###");
			int h = 1;
			for (HiddenLayer hiddenLayer : listOfHiddenLayer) {
				System.out.println("Hidden Layer #" + h);
				int n = 1;
				for (Neuron neuron : hiddenLayer.getListOfNeurons()) {
					System.out.println("Neuron #" + n);
					System.out.println("Input Weights:");
					System.out.println(Arrays.deepToString(neuron
							.getListOfWeightIn().toArray()));
					System.out.println("Output Weights:");
					System.out.println(Arrays.deepToString(neuron
							.getListOfWeightOut().toArray()));
					n++;
				}
				h++;
			}
		}
	}

	@Override
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer + 1; //B
	}
}
