package edu.ise.svm.entities;

import edu.ise.svm.matrix.Matrix;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Model {

    private Matrix x;
    private Matrix y;
    private double b;
    private Matrix alphas;
    private Matrix w;
    private String kernel;

    public Model(Matrix x, Matrix y, double b, Matrix alphas, Matrix w, String kernel) {
        this.x = x;
        this.y = y;
        this.b = b;
        this.alphas = alphas;
        this.w = w;
        this.kernel = kernel;
    }

    public Matrix getX() {
        return x;
    }

    public void setX(Matrix x) {
        this.x = x;
    }

    public Matrix getY() {
        return y;
    }

    public void setY(Matrix y) {
        this.y = y;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Matrix getAlphas() {
        return alphas;
    }

    public void setAlphas(Matrix alphas) {
        this.alphas = alphas;
    }

    public Matrix getW() {
        return w;
    }

    public void setW(Matrix w) {
        this.w = w;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }
}
