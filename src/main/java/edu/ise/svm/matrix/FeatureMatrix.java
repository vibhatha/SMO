package edu.ise.svm.matrix;

import java.util.ArrayList;

/**
 * Created by vlabeyko on 9/28/2016.
 */

/*
* In the usage of this class after making an instance of the class
* the calling of the generator method is essential to generate
* the X and y matrices
* */

public class FeatureMatrix implements IFeatureMatrix {

    private int rows;
    private int columns;
    private ArrayList<double[]> xValues;
    private ArrayList<Double> yValues;

    private double[][] xVals;
    private double[] yVals;

    private double [][] xDimensions= new double[1][2];
    private double [][] yDimensions= new double[1][2];


    public enum PRODUCT {
        DOT,CROSS
    }


    public double[][] getxDimensions() {
        return xDimensions;
    }

    public void setxDimensions(double[][] xDimensions) {
        this.xDimensions = xDimensions;
    }

    public double[][] getyDimensions() {
        return yDimensions;
    }

    public void setyDimensions(double[][] yDimensions) {
        this.yDimensions = yDimensions;
    }

    public FeatureMatrix(){

    }

    public FeatureMatrix(ArrayList<double[]> xValues, ArrayList<Double> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
        if (xValues != null && xValues.size() > 0) {
            this.rows = xValues.size();
            this.columns = xValues.get(0).length;
            xVals = new double[rows][columns];
            yVals = new double[rows];
        }

    }

    public FeatureMatrix(int rows, int columns, ArrayList<double[]> xValues, ArrayList<Double> yValues) {
        this.rows = rows;
        this.columns = columns;
        this.xValues = xValues;
        this.yValues = yValues;
    }

    public ArrayList<double[]> getxValues() {
        return xValues;
    }

    public void setxValues(ArrayList<double[]> xValues) {
        this.xValues = xValues;
    }

    public ArrayList<Double> getyValues() {
        return yValues;
    }

    public void setyValues(ArrayList<Double> yValues) {
        this.yValues = yValues;
    }

    public double[][] getxVals() {
        return xVals;
    }

    public void setxVals(double[][] xVals) {
        this.xVals = xVals;
    }

    public double[] getyVals() {
        return yVals;
    }

    public void setyVals(double[] yVals) {
        this.yVals = yVals;
    }

    public FeatureMatrix generate() {

        //generate FeatureX
        xDimensions[0][0] = rows;
        xDimensions[0][1] = columns;
        yDimensions[0][0]=rows;
        yDimensions[0][0]=1;
        for (int i = 0; i < rows; i++) {
            xVals[i] = xValues.get(i);
        }


        //generate FeatureY
        for (int i = 0; i < rows; i++) {
            yVals[i] = yValues.get(i);
        }

        FeatureMatrix m = new FeatureMatrix();
        m.setxVals(xVals);
        m.setyVals(yVals);

        return m;
    }

    public void print() {

        int rows = xVals.length;
        int columns = xVals[0].length;
        System.out.println("X Features");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(xVals[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Y Features");
        for (int i = 0; i < rows; i++) {
            System.out.println(yVals[i]);
        }

    }

    public FeatureMatrix add() {
        return null;
    }

    public FeatureMatrix subtract() {
        return null;
    }

    public FeatureMatrix product(String productType) {

        FeatureMatrix m = new FeatureMatrix();

        if(productType.equals(PRODUCT.DOT)){




        }else if(productType.equals(PRODUCT.CROSS)){

        }


        return m;
    }

    public FeatureMatrix transpose(FeatureMatrix featureMatrix) {
        return null;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }


}
