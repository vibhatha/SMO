package edu.ise.svm.test;

import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;

/**
 * Created by vibhatha on 3/29/18.
 */
public class MatrixTest {
    public static void main(String[] args) {
        Matrix a = new Matrix(3,3,"DOUBLE");
        Matrix b = new Matrix(1,3,"DOUBLE");
        double matA [][] = new double [3][3];
        double matB [][] = new double[1][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matA[i][j] = i+j+1;
            }
        }

        a.setMatDouble(matA);

        for (int i = 0; i < 3; i++) {
            matB[0][i] = i+1;
        }

        b.setMatDouble(matB);

        MatrixOperator matrixOperator = new MatrixOperator();
        System.out.println("Mat A");
        matrixOperator.sout(a);
        System.out.println("Mat B");
        matrixOperator.sout(b);

        System.out.println("Mat C");
        Matrix c = matrixOperator.bsxfun(a,b,"plus");
        matrixOperator.sout(c);
    }
}
