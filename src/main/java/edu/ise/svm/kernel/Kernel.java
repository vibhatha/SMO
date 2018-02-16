package edu.ise.svm.kernel;

import edu.ise.svm.matrix.Matrix;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Kernel {

    private Matrix x1;
    private Matrix x2;

    public Kernel(Matrix x1, Matrix x2) {
        this.x1 = x1;
        this.x2 = x2;
    }
}
