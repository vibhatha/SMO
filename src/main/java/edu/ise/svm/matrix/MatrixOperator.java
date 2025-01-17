package edu.ise.svm.matrix;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class MatrixOperator implements IMatrix {
    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    public final static Logger LOG = Logger.getLogger(MatrixOperator.class.getName());

    private enum PRODUCT {
        DOT, CROSS
    }

    @Override
    public Matrix sum(Matrix a) {

        Matrix m = null;

        int aRows = a.getRows();
        int aCols = a.getColumns();

        m = new Matrix(1, aCols, a.getType().toString());
        Matrix[] temps = new Matrix[aRows];

        if (a.getType().equals("DOUBLE")) {

            for (int i = 0; i < aRows; i++) {
                temps[i] = new Matrix(1, aCols, a.getType().toString());
                for (int j = 0; j < aCols; j++) {

                    temps[i].getMatDouble()[0][j] = a.getMatDouble()[i][j];
                }
                m = this.add(m, temps[i]);
            }


        } else if (a.getType().equals("INT")) {

            for (int i = 0; i < aRows; i++) {
                temps[i] = new Matrix(1, aCols, a.getType().toString());
                for (int j = 0; j < aCols; j++) {

                    temps[i].getMatInt()[0][j] = a.getMatInt()[i][j];
                }
                m = this.add(m, temps[i]);
            }

        }


        return m;


    }

    public Matrix sum(Matrix a, int type) {

        Matrix m = null;

        if (type == 2) {
            int aRows = a.getRows();
            int aCols = a.getColumns();
            m = new Matrix(aRows, 1, a.getType().toString());
            Matrix[] temps = new Matrix[aCols];
            if (a.getType().equals("DOUBLE")) {

                for (int i = 0; i < aRows; i++) {
                    double colValues[] = a.getMatDouble()[i];
                    double value = 0.0;
                    for (int j = 0; j < colValues.length; j++) {
                        value += colValues[j];
                    }
                    m.getMatDouble()[i][0] = value;
                }

            } else if (a.getType().equals("INT")) {

                for (int i = 0; i < aRows; i++) {
                    int colValues[] = a.getMatInt()[i];
                    int value = 0;
                    for (int j = 0; j < colValues.length; j++) {
                        value += colValues[j];
                    }
                    m.getMatInt()[i][0] = value;
                }

            }

        }
        if (type == 1) {
            int aRows = a.getRows();
            int aCols = a.getColumns();

            m = new Matrix(1, aCols, a.getType().toString());
            Matrix[] temps = new Matrix[aRows];

            if (a.getType().equals("DOUBLE")) {

                for (int i = 0; i < aRows; i++) {
                    temps[i] = new Matrix(1, aCols, a.getType().toString());
                    for (int j = 0; j < aCols; j++) {

                        temps[i].getMatDouble()[0][j] = a.getMatDouble()[i][j];
                    }
                    m = this.add(m, temps[i]);
                }


            } else if (a.getType().equals("INT")) {

                for (int i = 0; i < aRows; i++) {
                    temps[i] = new Matrix(1, aCols, a.getType().toString());
                    for (int j = 0; j < aCols; j++) {

                        temps[i].getMatInt()[0][j] = a.getMatInt()[i][j];
                    }
                    m = this.add(m, temps[i]);
                }

            }

        }


        return m;


    }


    @Override
    public Matrix getValueMatchingBoundary(Matrix a, Matrix matcher) {

        // a matrix represents the original matrix
        //matcher matrix represents the boundary values
        //here the corresponding values with 1 in matcher is going to be mapped with matrix a
        //so that values matching the boundary condition will only be selected
        //matcher always come as a double matrix

        Matrix m = null;
        int aRows = a.getRows();
        int aCols = a.getColumns();

        int matcherRows = matcher.getRows();
        int matcherColumns = matcher.getColumns();

        if (aRows == matcherRows && aCols == matcherColumns) {

            int count = 0;
            for (int i = 0; i < matcherRows; i++) {
                for (int j = 0; j < matcherColumns; j++) {

                    if (matcher.getMatDouble()[i][j] == 1) {
                        count++;
                    }
                }
            }
            m = new Matrix(count, 1, "DOUBLE");
            int c = 0;
            for (int i = 0; i < matcherRows; i++) {
                for (int j = 0; j < matcherColumns; j++) {

                    if (matcher.getMatDouble()[i][j] == 1) {
                        m.getMatDouble()[c][0] = a.getMatDouble()[i][j];
                        c++;
                    }


                }
            }
        }


        return m;
    }

    @Override
    public Matrix setValueByBoundry(Matrix a, String boundaryCondition, double boundaryValue) {

        Matrix m = null;

        if (a != null && boundaryCondition != null) {

            int aRows = a.getRows();
            int aCols = a.getColumns();
            m = new Matrix(aRows, aCols, a.getType().toString());

            for (int i = 0; i < aRows; i++) {
                for (int j = 0; j < aCols; j++) {

                    if (boundaryCondition.equals("==")) {

                        if (a.getMatDouble()[i][j] == boundaryValue) {

                            m.getMatDouble()[i][j] = 1;
                        } else {
                            m.getMatDouble()[i][j] = 0;
                        }

                    } else if (boundaryCondition.equals("!=")) {

                        if (a.getMatDouble()[i][j] != boundaryValue) {

                            m.getMatDouble()[i][j] = 1;
                        } else {
                            m.getMatDouble()[i][j] = 0;
                        }

                    } else if (boundaryCondition.equals(">")) {

                        if (a.getMatDouble()[i][j] > boundaryValue) {

                            m.getMatDouble()[i][j] = 1;
                        } else {
                            m.getMatDouble()[i][j] = 0;
                        }

                    } else if (boundaryCondition.equals("<")) {

                        if (a.getMatDouble()[i][j] < boundaryValue) {

                            m.getMatDouble()[i][j] = 1;
                        } else {
                            m.getMatDouble()[i][j] = 0;
                        }

                    } else if (boundaryCondition.equals(">=")) {

                        if (a.getMatDouble()[i][j] >= boundaryValue) {

                            m.getMatDouble()[i][j] = 1;
                        } else {
                            m.getMatDouble()[i][j] = 0;
                        }

                    } else if (boundaryCondition.equals("<=")) {

                        if (a.getMatDouble()[i][j] <= boundaryValue) {

                            m.getMatDouble()[i][j] = 1;
                        } else {
                            m.getMatDouble()[i][j] = 0;
                        }

                    }


                }
            }


        }


        return m;
    }

    @Override
    public Matrix getRowData(Matrix a, int row) {
        Matrix m = null;

        if (a != null) {

            int aRows = a.getRows();
            int aCols = a.getColumns();

            m = new Matrix(1, aCols, a.getType().toString());
            if (a.getType().toString().equals("DOUBLE")) {
                double[] ar = a.getMatDouble()[row];
                m.getMatDouble()[0] = ar;
            } else if (a.getType().toString().equals("INT")) {
                int[] ar = a.getMatInt()[row];
                m.getMatInt()[0] = ar;
            }

        }
        return m;
    }

    @Override
    public Matrix getColumnData(Matrix a, int column) {
        Matrix m = null;

        if (a != null) {

            int aRows = a.getRows();
            int aCols = a.getColumns();

            m = new Matrix(aRows, 1, a.getType().toString());
            if (a.getType().toString().equals("DOUBLE")) {

                for (int i = 0; i < aRows; i++) {

                    m.getMatDouble()[i][0] = a.getMatDouble()[i][column];

                }

            } else if (a.getType().toString().equals("INT")) {

                for (int i = 0; i < aRows; i++) {

                    m.getMatInt()[i][0] = a.getMatInt()[i][column];
                }
            }

        }


        return m;
    }

    @Override
    public Matrix dotMultiply(Matrix a, Matrix b) {

        Matrix m = null;
        //assuming matrix can be subjected to a.*b matrix operation
        //matrix a of m x 1 format
        //matrix b of any format
        int aRows = a.getRows();
        int aCols = a.getColumns();

        int bRows = b.getRows();
        int bCols = b.getColumns();
        m = new Matrix(b.getRows(), b.getColumns(), b.getType().toString());

        //b = this.transpose(b);


        if (aCols == 1) {


            for (int i = 0; i < bRows; i++) {

                for (int j = 0; j < bCols; j++) {

                    m.getMatDouble()[i][j] = b.getMatDouble()[i][j] * a.getMatDouble()[i][0];

                }
                //LOG.info();
            }


        } else {
            m = null;
        }


        return m;
    }

    @Override
    public Matrix add(Matrix a, Matrix b) {

        Matrix m = null;

        int aRow = a.getRows();
        int aCol = a.getColumns();

        int bRow = b.getRows();
        int bCol = b.getColumns();


        if (a.getType().toString().equals(b.getType().toString())) {

            if (aRow == bRow && aCol == bCol) {


                if (a.getType().toString().equals("DOUBLE")) {

                    m = new Matrix(aRow, aCol, a.getType().toString());

                    for (int i = 0; i < aRow; i++) {
                        for (int j = 0; j < aCol; j++) {
                            m.getMatDouble()[i][j] = a.getMatDouble()[i][j] + b.getMatDouble()[i][j];
                        }
                    }


                } else if (a.getType().toString().equals("INT")) {

                    m = new Matrix(aRow, aCol, a.getType().toString());
                    for (int i = 0; i < aRow; i++) {
                        for (int j = 0; j < aCol; j++) {
                            m.getMatInt()[i][j] = a.getMatInt()[i][j] + b.getMatInt()[i][j];
                        }
                    }
                }

            }

        }


        return m;
    }

    public Matrix bsxfun(Matrix a, Matrix b, String type){
        // Matrix a : must be the nxn dimensional one
        // Matrix b : must be nx1
        Matrix m = new Matrix(a.getRows(),a.getColumns(), a.getType().toString());
        MatrixOperator matrixOperator = new MatrixOperator();
        double arr [][] = new double[a.getRows()][a.getColumns()];
        if(type.equals("plus")){
          if(a.getColumns() == b.getColumns()){
              for (int i = 0; i < a.getRows(); i++) {
                  Matrix row1 = matrixOperator.getRowData(a,i);
                  Matrix add1 = matrixOperator.add(row1,b);
                  arr[i] = add1.getMatDouble()[0];
              }
          }
          m.setMatDouble(arr);
        }
        return m;
    }


    public Matrix multiplyByConstant(Matrix a, double constant) {

        Matrix m = null;

        int aRow = a.getRows();
        int aCol = a.getColumns();

        if (a.getType().toString().equals("DOUBLE")) {
            m = new Matrix(aRow, aCol, a.getType().toString());
            for (int i = 0; i < aRow; i++) {
                for (int j = 0; j < aCol; j++) {
                    m.getMatDouble()[i][j] = a.getMatDouble()[i][j] * constant;
                }
            }
        } else if (a.getType().toString().equals("INT")) {
            m = new Matrix(aRow, aCol, a.getType().toString());
            for (int i = 0; i < aRow; i++) {
                for (int j = 0; j < aCol; j++) {
                    m.getMatInt()[i][j] = (int) (a.getMatInt()[i][j] * constant);
                }
            }
        }

        return m;
    }

    public Matrix addConstant(Matrix a, double constant) {

        Matrix m = null;

        int aRow = a.getRows();
        int aCol = a.getColumns();

        if (a.getType().toString().equals("DOUBLE")) {
            m = new Matrix(aRow, aCol, a.getType().toString());
            for (int i = 0; i < aRow; i++) {
                for (int j = 0; j < aCol; j++) {
                    m.getMatDouble()[i][j] = a.getMatDouble()[i][j] + constant;
                }
            }
        } else if (a.getType().toString().equals("INT")) {
            m = new Matrix(aRow, aCol, a.getType().toString());
            for (int i = 0; i < aRow; i++) {
                for (int j = 0; j < aCol; j++) {
                    m.getMatInt()[i][j] = (int) (a.getMatInt()[i][j] + constant);
                }
            }
        }

        return m;
    }


    public Matrix powerBy(Matrix a, int power){
        Matrix m = null;

        return m;
    }


    @Override
    public Matrix subtract(Matrix a, Matrix b) {
        System.out.println("Mat Sub");
        Matrix result = null;

        int aRows = a.getRows();
        int aCols = a.getColumns();

        int bRows = b.getRows();
        int bCols = b.getColumns();
        System.out.println(a.getType() + "," + b.getType());
        if (a.getType().equals(b.getType())) {
            System.out.println("Equals Sub");
            if (aRows == bRows && aCols == bCols) {//condition for matrices a and b to be eligibel for matrix subtraction

                result = new Matrix(a.getRows(), a.getColumns(), a.getType().toString());

                if (a.getType().equals("DOUBLE")) {

                    for (int i = 0; i < a.getRows(); i++) {
                        for (int j = 0; j < a.getColumns(); j++) {

                            result.getMatDouble()[i][j] = a.getMatDouble()[i][j] - b.getMatDouble()[i][j];

                        }
                    }


                } else if (a.getType().equals("INT")) {

                    for (int i = 0; i < a.getRows(); i++) {
                        for (int j = 0; j < a.getColumns(); j++) {

                            result.getMatInt()[i][j] = a.getMatInt()[i][j] - b.getMatInt()[i][j];

                        }
                    }
                }


            }


        } else {

            result = null;
        }


        return result;
    }

    @Override
    public Matrix norm(Matrix a) {

        double value = 0.0;
        Matrix m = null;
        LOG.info("NORM CALC");
        if (a != null) {
            LOG.info("NORM NOT NULL CALC");
            int rows = a.getRows();
            int cols = a.getColumns();

            m = new Matrix(rows, cols, "DOUBLE");
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {

                    if (a.getMatDouble() != null) { //matrix with double values

                        value += a.getMatDouble()[i][j] * a.getMatDouble()[i][j];

                    } else if (a.getMatInt() != null) { //matrix with int values

                        value += a.getMatInt()[i][j] * a.getMatInt()[i][j];
                    }
                    m.getMatDouble()[i][j] = Math.sqrt(value);
                }

            }

        }

        return m;
    }

    @Override
    public Matrix product(Matrix a, Matrix b, String productType) {

        Matrix m = null;
        String matType = null;
        String aType = a.getType().toString();
        String bType = b.getType().toString();

        if (aType.equals(bType)) {
            //LOG.info("Type matches");
            m = new Matrix(1, 1, aType);

            int aRow = a.getRows();
            int aCol = a.getColumns();

            int bRow = b.getRows();
            int bCol = b.getColumns();


            if (productType.equals(PRODUCT.DOT.toString())) {

                m = new Matrix(aRow, aCol, a.getType().toString());
                if (aRow == bRow && aCol == bCol) { //condition to check whether the dot product is mathematical possible for entered matrices

                    for (int i = 0; i < aRow; i++) {
                        for (int j = 0; j < aCol; j++) {


                            if (m.getMatDouble() != null) {

                                m.getMatDouble()[i][j] += a.getMatDouble()[i][j] * b.getMatDouble()[i][j];


                            } else if (m.getMatInt() != null) {
                                m.getMatInt()[i][j] += a.getMatInt()[i][j] * b.getMatInt()[i][j];
                            }


                        }
                    }


                } else {
                    m = null;
                }


            } else if (productType.equals(PRODUCT.CROSS.toString())) {

                if (aCol == bRow) {

                    if (a.getType().toString().equals(b.getType().toString())) {

                        if (a.getType().equals("DOUBLE")) {
                            m = new Matrix(aRow, bCol, a.getType().toString());
                            for (int i = 0; i < m.getRows(); i++) {

                                for (int j = 0; j < m.getColumns(); j++) {
                                    double sum = 0.0;
                                    for (int k = 0; k < b.getRows(); k++) {

                                        sum += a.getMatDouble()[i][k] * b.getMatDouble()[k][j];
                                    }
                                    m.getMatDouble()[i][j] = sum;
                                }
                            }

                        } else if (a.getType().equals("INT")) {

                        }

                    } else {
                        System.err.println("Matrix type mismatch...");
                    }

                }


            }


            //LOG.info();
        } else {

            m = null;
        }


        return m;
    }

    @Override
    public Matrix transpose(Matrix a) {

        int rows = 0;
        int columns = 0;

        if (a != null) {

            rows = a.getRows();
            columns = a.getColumns();
            //LOG.info(rows + "," + columns);
        }

        //swap rows and columns
        rows = rows + columns;
        columns = rows - columns;
        rows = rows - columns;

        Matrix tMat = new Matrix(rows, columns, a.getType().toString());
        //LOG.info(rows + "," + columns);
        //swap again
        rows = rows + columns;
        columns = rows - columns;
        rows = rows - columns;

        if (a.getType().equals("DOUBLE")) {

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    tMat.getMatDouble()[j][i] = a.getMatDouble()[i][j];
                }
            }


        } else if (a.getType().equals("INT")) {

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    tMat.getMatInt()[j][i] = a.getMatInt()[i][j];
                }
            }


        }

        return tMat;
    }


    @Override
    public void disp(Matrix m) {

        if (m != null) {

            int rows = m.getRows();
            int cols = m.getColumns();

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (m.getMatDouble() != null) {

                        LOG.info(m.getMatDouble()[i][j] + " ");

                    } else if (m.getMatInt() != null) {
                        LOG.info(m.getMatInt()[i][j] + " ");
                    }

                }
                LOG.info("");
            }


        }


    }

    public void sout(Matrix m) {

        if (m != null) {

            int rows = m.getRows();
            int cols = m.getColumns();

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (m.getMatDouble() != null) {

                        System.out.print(m.getMatDouble()[i][j] + " ");

                    } else if (m.getMatInt() != null) {
                        System.out.print(m.getMatInt()[i][j] + " ");
                    }

                }
                System.out.println();
            }


        }


    }

    public boolean checkSumWeights(Matrix op1) {
        boolean status = true;
        double[][] d = op1.getMatDouble();
        double sum = 0.0;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                sum += d[i][j];
            }
        }
        if (sum > 0) {
            status = false;
        }
        return status;
    }

    public void saveMatrix(Matrix a, String filepath) throws IOException {
        int aRows = a.getRows();
        int aCols = a.getColumns();
        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = 0; i < aRows; i++) {
                for (int j = 0; j < aCols; j++) {
                    if(j<aCols-1){
                        writer.write(String.valueOf(a.getMatDouble()[i][j])+",");
                    }else{
                        writer.write(String.valueOf(a.getMatDouble()[i][j]));
                    }
                }
                writer.newLine();
            }
        }
    }

}
