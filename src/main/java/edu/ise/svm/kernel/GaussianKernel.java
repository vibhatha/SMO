package edu.ise.svm.kernel;

import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class GaussianKernel extends Kernel implements IKernel {

    private Matrix x1;
    private Matrix x2;
    private double sigma;

    public GaussianKernel(Matrix x1, Matrix x2, double sigma) {
        super(x1, x2);
        this.x1 = x1;
        this.x2 = x2;
        this.sigma = sigma;
    }

    public Matrix getGaussianKernelOutput() {

        Matrix m = null;

        if (x1 != null && x2 != null) {
            MatrixOperator matrixOperator = new MatrixOperator();
            m = matrixOperator.norm(matrixOperator.subtract(x1, x2));//this matrix is a double matrix -> norm returns a double matrix
            m.getMatDouble()[0][0] = Math.exp(-1 * (m.getMatDouble()[0][0] / (2 * sigma * sigma)));
        }

        return m;

    }

}
