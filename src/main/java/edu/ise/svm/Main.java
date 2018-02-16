package edu.ise.svm;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.kernel.GaussianKernel;
import edu.ise.svm.kernel.LinearKernel;
import edu.ise.svm.matrix.FeatureMatrix;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;
import edu.ise.svm.smo.ConstraintFunctions;
import edu.ise.svm.smo.Predict;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Main {

    private enum DATATYPE {
        DOUBLE, INT
    }

    public static void main(String[] args) {

        System.out.println("Support Vector Machines Library v1.0");

        String filePathX = "C:\\Users\\vlabeyko\\Desktop\\Sandbox\\Training Data\\SVM\\X.csv";
        String fileTypeX = "csv";
        CsvFile csvFileX = new CsvFile(filePathX, fileTypeX);
        ReadCSV readCSVX = new ReadCSV(csvFileX);
        readCSVX.readX();


        String filePathY = "C:\\Users\\vlabeyko\\Desktop\\Sandbox\\Training Data\\SVM\\Y.csv";
        String fileTypeY = "csv";
        CsvFile csvFileY = new CsvFile(filePathY, fileTypeY);
        ReadCSV readCSVY = new ReadCSV(csvFileY);
        readCSVY.readY();

        ArrayList<double[]> xValues = readCSVX.getxVals();
        ArrayList<Double> yValues = readCSVY.getyVals();



        FeatureMatrix featureMatrix = new FeatureMatrix(xValues, yValues);

        featureMatrix.generate();
        //featureMatrix.print();







     /*   for(int i=0; i < matA.getRows();i++){
            for(int j=0; j < matA.getColumns(); j++){

                System.out.print(matA.getMatInt()[i][j]+ " ");

            }
            System.out.println();
        }

        for(int i=0; i < matA.getRows();i++){
            for(int j=0; j < matA.getColumns(); j++){

                System.out.print( matB.getMatInt()[i][j]+ " ");

            }
            System.out.println();
        }*/


        //get dot product
      /*  System.out.println("Matrix Dot Product");

        MatrixOperator matrixOperator = new MatrixOperator();
        Matrix matDot = matrixOperator.product(matA,matB,"DOT");

        LinearKernel linearKernel = new LinearKernel(matA,matB);
        int value = linearKernel.getLinearKernelOutput().getMatInt()[0][0];
        int value1 = matDot.getMatInt()[0][0];
        System.out.println("Dot Product : "+value1);
        System.out.println("Linear Kernel : "+value);


        Matrix sub = matrixOperator.subtract(matB,matA);
        matrixOperator.disp(sub);

        double matANorm = matrixOperator.norm(matA).getMatDouble()[0][0];
        double matBNorm = matrixOperator.norm(matB).getMatDouble()[0][0];
        System.out.println("Matrix A norm "+matANorm);
        System.out.println("Matrix B norm "+matBNorm);

        double sigma = 0.25;
        GaussianKernel gaussianKernel = new GaussianKernel(matA,matB,sigma);
        double gaussianKernelValue = gaussianKernel.getGaussianKernelOutput().getMatDouble()[0][0];
        System.out.println("Gaussian Kernel Output :"+gaussianKernelValue);

        Matrix alpha; Matrix b; Matrix w; Matrix x; Matrix y; Matrix lpd;

        Matrix matX = new Matrix(3,3, DATATYPE.DOUBLE.toString());
        Matrix matY = new Matrix(3,1, DATATYPE.DOUBLE.toString());

        for(int i=0; i < matX.getRows();i++){
            for(int j=0; j < matX.getColumns(); j++){

                matX.getMatDouble()[i][j]=1+j;

            }
        }

        for(int i=0; i < matY.getRows();i++){
            for(int j=0; j < matY.getColumns(); j++){

                matY.getMatDouble()[i][j]=i%2;

            }
        }
*/



        //set CSV data in to Matrix format
        //X features
        int xRows = featureMatrix.getxVals().length;
        int xCols =  featureMatrix.getxVals()[0].length;

        Matrix X = new Matrix(xRows,xCols,"DOUBLE");



        for (int i = 0; i < xRows ; i++) {
            for (int j = 0; j < xCols; j++) {

                X.getMatDouble()[i][j]=featureMatrix.getxVals()[i][j];

            }
        }



        //Y features
        int yRows = featureMatrix.getxVals().length;
        int yCols =  featureMatrix.getxVals()[0].length;

        Matrix Y = new Matrix(yRows,1,"DOUBLE");



        for (int i = 0; i < yRows ; i++) {

                Y.getMatDouble()[i][0]=featureMatrix.getyVals()[i];

        }


        Matrix b = new Matrix(1, X.getColumns(), "DOUBLE");
        Matrix w = new Matrix(1, X.getColumns(), "DOUBLE");
        Matrix alpha = new Matrix(1, X.getColumns(), "DOUBLE");
        Matrix lpd = new Matrix(1, X.getColumns(), "DOUBLE");

        for(int i=0; i < w.getRows();i++){
            for(int j=0; j < w.getColumns(); j++){

                w.getMatDouble()[i][j]= 2.00;
                b.getMatDouble()[i][j]= 3.00;
                alpha.getMatDouble()[i][j]=0;

            }
        }


        ConstraintFunctions constraintFunctions = new ConstraintFunctions(alpha,b,w,X,Y,lpd);
        //constraintFunctions.lagrangeCalculation(alpha,matX,matY,b,w);
        String kernel = Constant.LINEAR;
        Model model = constraintFunctions.svmTrain(X,Y,Constant.LINEAR);

        //check the model output

        MatrixOperator matrixOperator = new MatrixOperator();
        System.out.println("Alphas from Model");
        matrixOperator.disp(model.getAlphas());

        System.out.println("X from Model");
        matrixOperator.disp(model.getX());

        System.out.println("Y from Model");
        matrixOperator.disp(model.getY());

        System.out.println("W from Model");
        matrixOperator.disp(model.getW());

        //x_test = [[1.34, 0.23], [2.2, 0.35], [1.1, 0.1], [0.08, 2.1]]

        //x_test_1 = [[3.49, 4.02], [1.59, 2.03], [1.53, 2.63], [1.89, 2.93]];

        double [] [] test_data_1 = new double[][]{

            new double[]{1.34,0.23}, new double[]{2.2,0.35},new double[]{1.1,0.1},
                new double[]{0.08,2.1}

        };

        double [] [] test_data_2 = new double[][]{

                new double[]{3.49,4.02}, new double[]{1.59, 2.03},new double[]{1.53, 2.63},
                new double[]{1.89, 2.93}

        };

        Matrix test1 = new Matrix(test_data_1.length, test_data_1[0].length,"DOUBLE");
        test1.setMatDouble(test_data_1);

        Matrix test2 = new Matrix(test_data_2.length, test_data_2[0].length,"DOUBLE");
        test2.setMatDouble(test_data_2);

        Predict predict = new Predict(model,test1);

        Predict predict2 = new Predict(model,test2);

        Matrix prediction1 = predict.predict();

        Matrix prediction2 = predict2.predict();



        System.out.println("Prediction of test 1");
        matrixOperator.disp(prediction1);

        System.out.println("Prediction of test 2");
        matrixOperator.disp(prediction2);


        System.out.println("-----------------------");



    }
}
