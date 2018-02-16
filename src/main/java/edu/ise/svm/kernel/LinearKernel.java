package edu.ise.svm.kernel;

import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class LinearKernel extends Kernel implements IKernel {

    private Matrix x1;
    private Matrix x2;
    private final static String operation = "DOT";

    public LinearKernel(Matrix x1, Matrix x2) {
        super(x1,x2);
        this.x1 = x1;
        this.x2 = x2;
    }

    public Matrix getLinearKernelOutput() {

        double value = 0.0;


        MatrixOperator operator = new MatrixOperator();
        Matrix m = operator.product(x1,x2,operation);

        return m;

    }

}
