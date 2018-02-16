package edu.ise.svm.attributes;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Attributes {

    private double c;
    private double sigma;
    private FeatureX x;
    private FeatureY y;
    private double x1;
    private double x2;

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public FeatureX getX() {
        return x;
    }

    public void setX(FeatureX x) {
        this.x = x;
    }

    public FeatureY getY() {
        return y;
    }

    public void setY(FeatureY y) {
        this.y = y;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }
}
