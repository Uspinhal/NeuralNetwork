package neuralnet.learn;

import java.util.ArrayList;

import neuralnet.NeuralNet;
import neuralnet.Neuron;
import neuralnet.util.Matrix;
import neuralnet.util.IdentityMatrix;

public class LevenbergMarquardt extends Backpropagation {
    private Matrix jacobian = null;
    private double damping = 0.1;
    private Matrix error = null;

    @Override
    public NeuralNet train(NeuralNet n) {
        
        int epoch = 0;
        setMse(1.0);

        while (getMse()>n.getTargetError()) {
            if (epoch >= n.getMaxEpochs()) break;

            int rows = n.getTrainSet().length;
            double sumErrors = 0.0;

            for (int rows_i= 0; rows_i< rows; rows_i++) {
                n = forward(n, rows_i);
                buildJacobianMatrix(n, rows_i);
                sumErrors = sumErrors +n.getErrorMean();
            }
            setMse(sumErrors/rows);
            n=updateWeights(n);
            System.out.println(getMse());
            epoch++;
        }
        System.out.println("Number of Epochs: " + epoch);
        return n;
    } 
       
    private void buildJacobianMatrix(NeuralNet n, int row) {
        ArrayList<Neuron> outputLayer = new ArrayList<>();
        ArrayList<Neuron> hiddenLayer = new ArrayList<>();
        outputLayer = n.getOutputLayer().getListOfNeurons();
        hiddenLayer = n.getListOfHiddenLayer().get(0).getListOfNeurons();

        NeuralNet nb = backpropagation(n, row);

        int numberOfInputs = n.getInputLayer().getNumberOfNeuronsInLayer();
        int numberOfHiddenNeuros = n.getHiddenLayer().getNumberOfNeuronsInLayer();
        int numberOfOutputs = n.getOutputLayer().getNumberOfNeuronsInLayer();

        if (jacobian == null) {
            jacobian = new Matrix(n.getTrainSet().length, 
                                (numberOfInputs)*(numberOfHiddenNeuros-1)+(numberOfHiddenNeuros)*(numberOfOutputs));
        }

        int i=0;
        //hidden layer
        for (Neuron neuron : hiddenLayer) {
            ArrayList<Double> hiddenLayerInputWeights = new ArrayList<>();
            hiddenLayerInputWeights = neuron.getListOfWeightIn();
            if (!hiddenLayerInputWeights.isEmpty()) {
                for (int j = 0; j < n.getInputLayer().getNumberOfNeuronsInLayer(); j++) {
                    jacobian.setValue(row, ((i-1)*(numberOfInputs))+(j), (neuron.getSensibility() * n.getTrainSet()[row][j])/n.getErrorMean());
                }
            } else {
                //jacobian.setValue(row, i*numberOfInputs, 1.0);
            }
            //bias wil have no effect
            i++;
        }
        if (this.error == null) {
            this.error = new Matrix(n.getTrainSet().length,1);
        }
        i=0;

        //output layer
        for (Neuron output : outputLayer) {
            int j = 0;
            for (Neuron neuron : hiddenLayer) {
                jacobian.setValue(row, numberOfInputs*(numberOfHiddenNeuros-1)+(i*(numberOfHiddenNeuros))+j, (output.getSensibility()*neuron.getOutputValue())/n.getErrorMean());
                j++;
            }
        i++;
        }
        error.setValue(row, 0, n.getErrorMean());
    }

    private NeuralNet updateWeights(NeuralNet n) {
        // delta = inv(J`J + damping I) * J` error
        Matrix term1 = jacobian.transpose().multiply(jacobian).add(new IdentityMatrix(jacobian.getNumberOfColumns()).multiply(damping));
        Matrix term2 = jacobian.transpose().multiply(this.error);
        Matrix delta = term1.inverse().multiply(term2);

        ArrayList<Neuron> outputLayer = new ArrayList<>();
        ArrayList<Neuron> hiddenLayer = new ArrayList<>();
        outputLayer = n.getOutputLayer().getListOfNeurons();
        hiddenLayer = n.getListOfHiddenLayer().get(0).getListOfNeurons();

        int numberOfInputs = n.getInputLayer().getNumberOfNeuronsInLayer();
        int numberOfHiddenNeurons = n.getHiddenLayer().getNumberOfNeuronsInLayer();
        int numberOfOutputs = n.getOutputLayer().getNumberOfNeuronsInLayer();

        int i = 0;
        for (Neuron hidden : hiddenLayer) {
            ArrayList<Double> hiddenLayerInputWeights = new ArrayList<>();
            hiddenLayerInputWeights = hidden.getListOfWeightIn();

            if (!hiddenLayerInputWeights.isEmpty()) {
                double newWeight = 0.0;
                for (int j = 0; j < n.getInputLayer().getNumberOfNeuronsInLayer(); j++) {
                    newWeight = hiddenLayerInputWeights.get(i) + delta.getValue(((i)*(numberOfInputs)+(j)) ,0);
                    hidden.getListOfWeightIn().set(i, newWeight);
                }
                i++;
            }
        }
        i = 0;
        for (Neuron output : outputLayer) {
            int j = 0;
            double newWeight = 0.0;
            for (Neuron neuron : hiddenLayer) {
                newWeight = neuron.getListOfWeightOut().get(i) + delta.getValue((numberOfInputs)*(numberOfHiddenNeurons-1)+(i*(numberOfHiddenNeurons))+j, 0);
                neuron.getListOfWeightOut().set(i, newWeight);
                j++;
            }
            i++;
        }
        return n;
    }
}
