package edu.ise.svm.kernel;

import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;

import java.io.IOException;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class GaussianKernel extends Kernel implements IKernel {

    private Matrix x1;

    public Matrix getX1() {
        return x1;
    }

    public void setX1(Matrix x1) {
        this.x1 = x1;
    }

    public Matrix getX2() {
        return x2;
    }

    public void setX2(Matrix x2) {
        this.x2 = x2;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    private Matrix x2;
    private double sigma;

    public GaussianKernel(Matrix x1, Matrix x2, double sigma) {
        super(x1, x2);
        this.x1 = x1;
        this.x2 = x2;
        this.sigma = sigma;
    }

    public Matrix getKernel() throws IOException {
        Matrix m = null;
        MatrixOperator matrixOperator = new MatrixOperator();
        if (getX1() != null && getX2() != null) {
            Matrix x1 = matrixOperator.product(getX1(), getX2(),"DOT");
            Matrix x2 = matrixOperator.sum(x1,2);
            matrixOperator.saveMatrix(x2,"/home/vibhatha/Sandbox/msvm/x2.gaussian");
            Matrix op1 = matrixOperator.product(getX1(),matrixOperator.transpose(getX1()),"CROSS"); // x*x'
            matrixOperator.saveMatrix(op1,"/home/vibhatha/Sandbox/msvm/op1.gaussian");
            Matrix op2 = matrixOperator.multiplyByConstant(op1,-2.0);
            matrixOperator.saveMatrix(op2,"/home/vibhatha/Sandbox/msvm/op2.gaussian");
            Matrix op3 = matrixOperator.bsxfun(op2,matrixOperator.transpose(x2),"plus");
            matrixOperator.saveMatrix(op3,"/home/vibhatha/Sandbox/msvm/op3.gaussian");
            Matrix k1 = matrixOperator.bsxfun(op3, x2, "plus");
            matrixOperator.saveMatrix(k1,"/home/vibhatha/Sandbox/msvm/k1.gaussian");
            m = k1;
            //m = matrixOperator.product(k1, k1,"DOT");
            //matrixOperator.saveMatrix(m,"/home/vibhatha/Sandbox/msvm/m.gaussian");
        }

        return m;
    }

    public Matrix getGaussianKernelOutput() throws IOException {

        Matrix m = null;

        if (getX1() != null && getX2() != null) {
            //System.out.println("X1");
            //System.out.println(getX1().getRows()+","+ getX1().getColumns());
            //System.out.println("X2");
            //System.out.println(getX2().getRows()+","+ getX2().getColumns());

            MatrixOperator matrixOperator = new MatrixOperator();
            //m = new Matrix(getX1().getRows(), getX1().getColumns(), "DOUBLE");
            //m = matrixOperator.norm(matrixOperator.subtract(getX1(), getX2()));//this matrix is a double matrix -> norm returns a double matrix
            m = getX1();
            matrixOperator.saveMatrix(m,"/home/vibhatha/Sandbox/msvm/m1.gaussian2");
            System.out.println("GK");
            System.out.println(m.getRows()+","+m.getColumns());
            for (int i = 0; i < getX1().getRows(); i++) {
                for (int j = 0; j < getX1().getColumns(); j++) {
                    double expc = -1.0 / (2 * getSigma() * getSigma());
                    double d = Math.pow(Math.exp(expc), m.getMatDouble()[i][j] * m.getMatDouble()[i][j]);
                    m.getMatDouble()[i][j] = d;
                    //System.out.print(d+" ");
                }
                //System.out.println();
            }
            matrixOperator.saveMatrix(m,"/home/vibhatha/Sandbox/msvm/m.gaussian2");
            System.out.println("Matrix m dim : "+m.getRows()+","+m.getColumns());
            Matrix m_dash = matrixOperator.transpose(m);
            m = matrixOperator.product(m, m_dash, "CROSS");
        }

        return m;

    }


}
