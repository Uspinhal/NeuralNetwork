package neuralnet.util;

public class Matrix {
    private double[][] content;
    private int numberOfRows;
    private int numberOfColumns;

    private Double determinant;

    private static final String ERRORMSG1_STRING = "Number of rows of both matrices must match";
    private static final String ERRORMSG2_STRING = "Number of columns of both matrices must match";
    private static final String ERRORMSG3_STRING = "Row index must be lower than the number of rows";

    public Matrix(int numberOfRows, int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
        this.numberOfRows = numberOfRows;
    }
    public Matrix(double[][] matrix) {
        this.numberOfRows = matrix.length;
        this.numberOfColumns = matrix[0].length;
        this.content = matrix;
    }
    public Matrix(double[] vector) {
        this.numberOfRows = 1;
        this.numberOfColumns = vector.length;
        this.content = new double[this.numberOfRows][this.numberOfColumns];
        this.content[0] = vector;
    }
    public Matrix(Matrix a) {
        this.numberOfRows = a.getNumberOfRows();
        this.numberOfColumns = a.getNumberOfColumns();
        this.content = new double[this.numberOfRows][this.numberOfColumns];
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfColumns; j++) {
                setValue(i, j, a.getValue(i,j));
            }
        }
    }
    public Matrix add(Matrix a){
        int nRows = a.getNumberOfRows();
        int nColumns = a.getNumberOfColumns();

        if (this.numberOfRows != a.getNumberOfRows()){
            throw new IllegalArgumentException(ERRORMSG1_STRING);
        }
        if (this.numberOfColumns!=a.getNumberOfColumns()) {
            throw new IllegalArgumentException(ERRORMSG2_STRING);
        }
        Matrix result = new Matrix(nRows, nColumns);

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                result.setValue(i, j, getValue(i,j)+a.getValue(i,j));
            }
        }
        return result;  
    }
    public static Matrix add(Matrix a, Matrix b) {
        int nRows = a.getNumberOfRows();
        int nColumns = a.getNumberOfColumns();

        if(a.numberOfRows!=b.getNumberOfRows()){
            throw new IllegalArgumentException(ERRORMSG1_STRING);
        }
        if(a.numberOfColumns!=b.getNumberOfColumns()){
            throw new IllegalArgumentException(ERRORMSG2_STRING);
        }

        Matrix result = new Matrix(nRows, nColumns);

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nColumns; j++) {
                result.setValue(i, j, a.getValue(i,j)+b.getValue(i,j));
            }
        }
        return result;
    }
    public Matrix substract(Matrix a) {
        int nRows = a.getNumberOfRows();
        int nColumns = a.getNumberOfColumns();

    if(numberOfRows!=a.getNumberOfRows()){
        throw new IllegalArgumentException(ERRORMSG1_STRING);
    }
    if(numberOfColumns!=a.getNumberOfColumns()){
        throw new IllegalArgumentException(ERRORMSG2_STRING);
    }

    Matrix result = new Matrix(nRows, nColumns);

    for (int i = 0; i < nRows; i++) {
        for (int j = 0; j < nColumns; j++) {
            result.setValue(i, j, getValue(i, j)-a.getValue(i, j));
        }
    }
    return result;
    }
    public static Matrix subtract(Matrix a,Matrix b){
        int nRows = a.getNumberOfRows();
        int nColumns = a.getNumberOfColumns();
        
        if(a.numberOfRows!=b.getNumberOfRows()){
            throw new IllegalArgumentException(ERRORMSG1_STRING);
        }
        if(a.numberOfColumns!=b.getNumberOfColumns()){
            throw new IllegalArgumentException(ERRORMSG2_STRING);
        }
        Matrix result = new Matrix(nRows,nColumns);
        
        for(int i=0;i<nRows;i++){
            for(int j=0;j<nColumns;j++){
                result.setValue(i, j, a.getValue(i,j)-b.getValue(i, j));
            }
        }
        return result;
    }
    public Matrix transpose() {
        Matrix result = new Matrix(this.numberOfColumns, this.numberOfColumns);
        for (int i = 0; i < this.numberOfRows; i++) {
            for (int j = 0; j < this.numberOfColumns; j++) {
                result.setValue(j, i, getValue(i,j));
            }
        }
        return result;
    }
    public static Matrix transpose(Matrix a) {
        Matrix result = new Matrix(a.getNumberOfColumns(), a.getNumberOfRows());
        for (int i = 0; i < a.getNumberOfRows(); i++) {
            for (int j = 0; j < a.getNumberOfColumns(); j++) {
                result.setValue(j, i, a.getValue(i,j));
            }
        }
        return result;
    }
    public Matrix multiply(Matrix a){
        Matrix result = new Matrix(this.getNumberOfRows(),a.getNumberOfColumns());
        if(this.getNumberOfColumns()!=a.getNumberOfRows()){
            throw new IllegalArgumentException("Number of Columns of first Matrix must match the number of Rows of second Matrix");
        }
        for(int i=0;i<this.getNumberOfRows();i++){
            for(int j=0;j<a.getNumberOfColumns();j++){
                double value = 0;
                for(int k=0;k<a.getNumberOfRows();k++){
                    value+=getValue(i,k)*a.getValue(k,j);
                }
                result.setValue(i, j, value);
            }
        }
        return result;
    }
    public Matrix multiply(double a){
        Matrix result = new Matrix(getNumberOfRows(),getNumberOfColumns());
        
        for(int i=0;i<getNumberOfRows();i++){
            for(int j=0;j<getNumberOfColumns();j++){
                result.setValue(i, j, getValue(i,j)*a);
            }
        }
        
        return result;
    }
    public static Matrix multiply(Matrix a,Matrix b){
        Matrix result = new Matrix(a.getNumberOfRows(),b.getNumberOfColumns());
        if(a.getNumberOfColumns()!=b.getNumberOfRows())
            throw new IllegalArgumentException("Number of Columns of first Matrix must match the number of Rows of second Matrix");

        for(int i=0;i<a.getNumberOfRows();i++){
            for(int j=0;j<b.getNumberOfColumns();j++){
                double value = 0;
                for(int k=0;k<b.getNumberOfRows();k++){
                    value+=a.getValue(i,k)*b.getValue(k,j);
                }
                result.setValue(i, j, value);
            }
        }
        return result;
    }
    public static Matrix multiply(Matrix a, double b){
        Matrix result = new Matrix(a.getNumberOfRows(),a.getNumberOfColumns());
        
        for(int i=0;i<a.getNumberOfRows();i++){
            for(int j=0;j<a.getNumberOfColumns();j++){
                result.setValue(i, j, a.getValue(i,j)*b);
            }
        }
        
        return result;
    }
    public Matrix[] LUdecomposition() {
        Matrix[] result = new Matrix[2];
        Matrix LU = new Matrix(this);
        Matrix L = new Matrix(LU.getNumberOfRows(), LU.getNumberOfColumns());
        L.setZeros();
        L.setValue(0,0,1.0);
        for (int i = 0; i < LU.getNumberOfRows(); i++) {
            L.setValue(i, i, 1.0);
            for (int j = 0; j < i; j++) {
                double multiplier = -LU.getValue(i, j)/LU.getValue(j, j);
                LU.sumRowByRow(i, j, multiplier);
                L.setValue(i, j, -multiplier);
            }
        }
        Matrix U = new Matrix(LU);
        result[0]=L;
        result[1]=U;
        return result;
    }
    public void multiplyRow(int row, double multiplier) {
        if (row>this.getNumberOfRows()) {
            throw new IllegalArgumentException(ERRORMSG3_STRING);
        }
        sumRowByRow(row, row, multiplier);
    }
    public void sumRowByRow(int row, int rowSum, double multiplier) {
        if(row>getNumberOfRows())
            throw new IllegalArgumentException(ERRORMSG3_STRING);
        if(rowSum>getNumberOfRows())
            throw new IllegalArgumentException(ERRORMSG3_STRING);
        for(int j=0;j<getNumberOfColumns();j++){
            setValue(row,j,getValue(row,j)+getValue(rowSum,j)*multiplier);
        }        
    }
    public double determinant(){
        if(determinant!=null)
            return determinant;
        
//        double result = 0;
        if(getNumberOfRows()!=getNumberOfColumns())
            throw new IllegalArgumentException("Only square matrices can have determinant");

        if(getNumberOfColumns()==1){
            return content[0][0];
        }
        else if(getNumberOfColumns()==2){
            return (content[0][0]*content[1][1])-(content[1][0]*content[0][1]);
        }
        else{
            Matrix[] LU = LUdecomposition();
            return LU[1].multiplyDiagonal();
        }
//        else{
//            for(int k=0;k<getNumberOfColumns();k++){
//                Matrix minorMatrix = subMatrix(0,k);
//                result+= ((k%2==0)? getValue(0,k): -getValue(0,k)) * minorMatrix.determinant();
//            }
//            setDeterminant(result);
//            return result;
//        }
    }
    public static double determinant(Matrix a){
        if(a.determinant!=null)
            return a.getDeterminant();
        
        if(a.getNumberOfRows()!=a.getNumberOfColumns())
            throw new IllegalArgumentException("Only square matrices can have determinant");

        if(a.getNumberOfColumns()==1){
            return a.getValue(0, 0);
        }
        else if(a.getNumberOfColumns()==2){
            return (a.getValue(0, 0)*a.getValue(1, 1))-(a.getValue(1, 0)*a.getValue(0, 1));
        }
        else{
            Matrix[] LU = a.LUdecomposition();
            return LU[1].multiplyDiagonal();
        }        
//        for(int k=0;k<a.getNumberOfColumns();k++){
//            Matrix minorMatrix = a.subMatrix(0, k);
//            result+= ((k%2==0)? a.getValue(0,k): -a.getValue(0,k)) * minorMatrix.determinant();
//        }
//        a.setDeterminant(result);
        
    }
    public double multiplyDiagonal() {
        double result=1;
        for (int i = 0; i < this.getNumberOfColumns(); i++) {
            result*=getValue(i,i);
        }
        return result;
    }
    public Matrix subMatrix(int row,int column){
        if(row>getNumberOfRows())
            throw new IllegalArgumentException("Row index out of matrix`s limits");
        if(column>getNumberOfColumns())
            throw new IllegalArgumentException("Column index out of matrix`s limits");
        
        Matrix result = new Matrix(getNumberOfRows()-1,getNumberOfColumns()-1);
        for(int i=0;i<getNumberOfRows();i++){
            if(i==row) continue;
            for(int j=0;j<getNumberOfRows();j++){
                if(j==column) continue;
                result.setValue((i<row?i:i-1), (j<column?j:j-1), getValue(i,j));
            }
        }
        return result;
    }
    public static Matrix subMatrix(Matrix a,int row,int column){
        if(row>a.getNumberOfRows())
            throw new IllegalArgumentException("Row index out of matrix`s limits");
        if(column>a.getNumberOfColumns())
            throw new IllegalArgumentException("Column index out of matrix`s limits");
        
        Matrix result = new Matrix(a.getNumberOfRows()-1,a.getNumberOfColumns()-1);
        for(int i=0;i<a.getNumberOfRows();i++){
            if(i==row) continue;
            for(int j=0;j<a.getNumberOfRows();j++){
                if(j==column) continue;
                result.setValue((i<row?i:i-1), (j<column?j:j-1), a.getValue(i,j));
            }
        }
        return result;
    }
    public Matrix coFactors(){
        Matrix result = new Matrix(getNumberOfRows(),getNumberOfColumns());
        for(int i=0;i<getNumberOfRows();i++){
            for(int j=0;j<getNumberOfColumns();j++){
                result.setValue(i, j, subMatrix(i,j).determinant());
            }
        }
        return result;
    }
    public static Matrix coFactors(Matrix a){
        Matrix result = new Matrix(a.getNumberOfRows(),a.getNumberOfColumns());
        for(int i=0;i<a.getNumberOfRows();i++){
            for(int j=0;j<a.getNumberOfColumns();j++){
                result.setValue(i, j, a.subMatrix(i,j).determinant());
            }
        }
        return result;
    }
    public Matrix inverse(){
        Matrix result = coFactors().transpose().multiply((1/determinant()));
        return result;
    }
    public static Matrix inverse(Matrix a){
        if(a.getDeterminant()==0)
            throw new IllegalArgumentException("This matrix is not inversible");
        Matrix result = a.coFactors().transpose().multiply((1/a.determinant()));
        return result;
    }
    public double getValue(int i, int j) {
        if(i>=numberOfRows)
            throw new IllegalArgumentException("Number of Row outside the matrix`s limits");
        if(j>=numberOfColumns)
            throw new IllegalArgumentException("Number of Column outside the matrix`s limits");
    
        return content[i][j];
    }
    public void setValue(int i, int j, double value) {
        if(i>=numberOfRows)
            throw new IllegalArgumentException("Number of Row outside the matrix`s limits");
        if(j>=numberOfColumns)
            throw new IllegalArgumentException("Number of Column outside the matrix`s limits");

        this.content[i][j]=value;
        this.determinant = null;
    }
    public void setZeros(){
        for(int i=0;i<getNumberOfRows();i++){
            for(int j=0;j<getNumberOfColumns();j++){
                setValue(i,j,0.0);
            }
        }
    }  
    public void setOnes(){
        for(int i=0;i<getNumberOfRows();i++){
            for(int j=0;j<getNumberOfColumns();j++){
                setValue(i,j,1.0);
            }
        }
    }

// Getters & Setters
    public double[][] getContent() {
        return this.content;
    }
    public void setContent(double[][] content) {
        this.content = content;
    }
    public int getNumberOfRows() {
        return this.numberOfRows;
    }
    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }
    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }
    public Double getDeterminant() {
        if (this.determinant!=null) {
            return this.determinant;
        } else {
            return determinant();
        }
    }
    public void setDeterminant(Double determinant) {
        this.determinant = determinant;
    }
}
