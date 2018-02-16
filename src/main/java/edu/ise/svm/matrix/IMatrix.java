package edu.ise.svm.matrix;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public interface IMatrix {

    public Matrix add(Matrix a, Matrix b);
    public Matrix subtract(Matrix a, Matrix b);
    public Matrix product(Matrix a, Matrix b, String productType);
    public Matrix transpose(Matrix a);
    public Matrix norm(Matrix a);
    public void disp(Matrix m);
    public Matrix dotMultiply(Matrix a, Matrix b);
    public Matrix sum(Matrix a);
    public Matrix getRowData(Matrix a,int row);
    public Matrix getColumnData(Matrix a,int column);
    public Matrix setValueByBoundry(Matrix a, String boundaryCondition, double boundaryValue);
    public Matrix getValueMatchingBoundary(Matrix a, Matrix matcher);
}
