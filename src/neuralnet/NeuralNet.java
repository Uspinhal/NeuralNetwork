package neuralnet;

import java.util.ArrayList;

import neuralnet.learn.Adaline;
import neuralnet.learn.Perceptron;
import neuralnet.learn.Training.ActivationFncENUM;
import neuralnet.learn.Training.TrainingTypesENUM;

public class NeuralNet {
    private InputLayer inputLayer;
	private HiddenLayer hiddenLayer;
	private ArrayList<HiddenLayer> listOfHiddenLayer;
	private OutputLayer outputLayer;
	private int numberOfHiddenLayers;

	// Chapter2
	private double[][] trainSet;
	private double[]   realOutputSet;
	private int        maxEpochs;
	private double     learningRate;
	private double     targetError;
	private double     trainingError;
	private ArrayList<Double> listOfMSE = new ArrayList<>();
	private ActivationFncENUM activationFnc;
	private TrainingTypesENUM trainType;

	public NeuralNet initNet(int numberOfInputNeurons,
							int numberOfHiddenLayers,
							int numberOfNeuronsInHiddenLayer,
							int numberOfOutputNeurons){
		
		inputLayer = new InputLayer();
		inputLayer.setNumberOfNeuronsInLayer(numberOfInputNeurons);
		
        listOfHiddenLayer = new ArrayList<>();
		
		 for (int i = 0; i < numberOfHiddenLayers; i++) {
			hiddenLayer = new HiddenLayer();
			hiddenLayer.setNumberOfNeuronsInLayer(numberOfNeuronsInHiddenLayer);
			listOfHiddenLayer.add(hiddenLayer);
		}
		
		outputLayer = new OutputLayer();
		outputLayer.setNumberOfNeuronsInLayer(numberOfOutputNeurons);
		
		inputLayer = inputLayer.initLayer(inputLayer);
		
		if (numberOfHiddenLayers>0) {
			listOfHiddenLayer = hiddenLayer.initLayer(hiddenLayer, listOfHiddenLayer, inputLayer, outputLayer);			
		}

		outputLayer = outputLayer.initLayer(outputLayer);

		NeuralNet newNet = new NeuralNet();
		newNet.setInputLayer(inputLayer);
		newNet.setHiddenLayer(hiddenLayer);
		newNet.setListOfHiddenLayer(listOfHiddenLayer);
		newNet.setNumberOfHiddenLayers(numberOfHiddenLayers);
		newNet.setOutputLayer(outputLayer);

		return newNet;
		
	}
	
	public void printNet(NeuralNet n){
		inputLayer.printLayer(n.getInputLayer());
		System.out.println();
		if (n.getHiddenLayer() != null){
			hiddenLayer.printLayer(n.getListOfHiddenLayer());
			System.out.println();	
		}
		outputLayer.printLayer(n.getOutputLayer());
	}
	
	// Chapter 2
	public NeuralNet trainNet(NeuralNet n) {
		
		NeuralNet trainedNet = new NeuralNet();

		switch (n.trainType) {
			case PERCEPTRON:
				Perceptron t = new Perceptron();
				trainedNet = t.train(n);
				return trainedNet;
			case ADALINE:
				Adaline a = new Adaline();
				trainedNet = a.train(n);
				return trainedNet;		
			default:
				throw new IllegalArgumentException(n.trainType + " does not exist in TrainingTypesENUM");
		}
	}

	public void printTrainedNetResult(NeuralNet n) {
		switch (n.trainType) {
		case PERCEPTRON:
			Perceptron t = new Perceptron();
			t.printTrainedNetResult( n );
			break;
		case ADALINE:
			Adaline a = new Adaline();
			a.printTrainedNetResult( n );
			break;
		default:
			throw new IllegalArgumentException(n.trainType+" does not exist in TrainingTypesENUM");
		}
	}

	public InputLayer getInputLayer() {
		return this.inputLayer;
	}

	public void setInputLayer(InputLayer inputLayer) {
		this.inputLayer = inputLayer;
	}

	public HiddenLayer getHiddenLayer() {
		return this.hiddenLayer;
	}

	public void setHiddenLayer(HiddenLayer hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}

	public ArrayList<HiddenLayer> getListOfHiddenLayer() {
		return this.listOfHiddenLayer;
	}

	public void setListOfHiddenLayer(ArrayList<HiddenLayer> listOfHiddenLayer) {
		this.listOfHiddenLayer = listOfHiddenLayer;
	}

	public OutputLayer getOutputLayer() {
		return this.outputLayer;
	}

	public void setOutputLayer(OutputLayer outputLayer) {
		this.outputLayer = outputLayer;
	}

	public int getNumberOfHiddenLayers() {
		return this.numberOfHiddenLayers;
	}

	public void setNumberOfHiddenLayers(int numberOfHiddenLayers) {
		this.numberOfHiddenLayers = numberOfHiddenLayers;
	}
// Chapter 2
	public double[][] getTrainSet() {
		return this.trainSet;
	}

	public void setTrainSet(double[][] trainSet) {
		this.trainSet = trainSet;
	}

	public double[] getRealOutputSet() {
		return this.realOutputSet;
	}

	public void setRealOutputSet(double[] realOutputSet) {
		this.realOutputSet = realOutputSet;
	}

	public int getMaxEpochs() {
		return this.maxEpochs;
	}

	public void setMaxEpochs(int maxEpochs) {
		this.maxEpochs = maxEpochs;
	}

	public double getLearningRate() {
		return this.learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getTargetError() {
		return this.targetError;
	}

	public void setTargetError(double targetError) {
		this.targetError = targetError;
	}

	public double getTrainingError() {
		return this.trainingError;
	}

	public void setTrainingError(double trainingError) {
		this.trainingError = trainingError;
	}

	public ArrayList<Double> getListOfMSE() {
		return this.listOfMSE;
	}

	public void setListOfMSE(ArrayList<Double> listOfMSE) {
		this.listOfMSE = listOfMSE;
	}

	public ActivationFncENUM getActivationFnc() {
		return this.activationFnc;
	}

	public void setActivationFnc(ActivationFncENUM activationFnc) {
		this.activationFnc = activationFnc;
	}

	public TrainingTypesENUM getTrainType() {
		return this.trainType;
	}

	public void setTrainType(TrainingTypesENUM trainType) {
		this.trainType = trainType;
	}
	

}
