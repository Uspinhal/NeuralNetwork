package neuralnet.util;

public class IdentityMatrix extends Matrix{


    public IdentityMatrix(int order) {
        super(order, order);
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                setValue(i, j, (i==j)?1:0);
            }
        }
    }

     public void setValue(int row, int column) {
        // To prevet editions on this matrix
    }

}
