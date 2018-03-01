package edu.ise.svm.matrix;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Matrix {

    private enum DATATYPE {
        DOUBLE, INT
    }

    private int rows;
    private int columns;
    private String dataType;
    private double[][] matDouble;
    private int[][] matInt;
    private String type=null;

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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public double[][] getMatDouble() {
        return matDouble;
    }

    public void setMatDouble(double[][] matDouble) {
        this.matDouble = matDouble;
    }

    public int[][] getMatInt() {
        return matInt;
    }

    public void setMatInt(int[][] matInt) {
        this.matInt = matInt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Matrix(int rows, int columns, String dataType) throws OutOfMemoryError {
        this.rows = rows;
        this.columns = columns;
        this.dataType = dataType;

        if (dataType.equals(DATATYPE.DOUBLE.toString())) {
            matDouble = new double[rows][columns];
            setType(DATATYPE.DOUBLE.toString());
        } else if (dataType.equals(DATATYPE.INT.toString())) {
            matInt = new int[rows][columns];
            setType(DATATYPE.INT.toString());
        }

    }







}
