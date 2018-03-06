package edu.ise.svm;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.matrix.FeatureMatrix;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;
import edu.ise.svm.smo.SMO;
import edu.ise.svm.smo.Predict;
import edu.ise.svm.util.Util;

import java.io.IOException;
import java.util.logging.Logger;

import java.util.ArrayList;

public class Main {

    static{
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    public final static Logger LOG = Logger.getLogger(Main.class.getName());

    private enum DATATYPE {
        DOUBLE, INT
    }

    public static void main(String[] args) throws OutOfMemoryError, IOException {

        LOG.info("Support Vector Machines Library v1.0");

        long read_start = System.currentTimeMillis();

        String baseX = "data/covtype/";
        String baseY = "data/covtype/multifiles/";

        String trainX = "covtype_libsvm_ise_train_x.2";
        String trainFilePathX = baseX + trainX;
        String trainFileType = "csv";
        CsvFile trainCsvFileX = new CsvFile(trainFilePathX, trainFileType);
        ReadCSV trainReadCSVX = new ReadCSV(trainCsvFileX);
        trainReadCSVX.readX();

        String trainY = "covtype_libsvm_ise_train_y.2.bin";
        String trainFilePathY = baseY + trainY;
        String trainFileTypeY = "csv";
        CsvFile trainCsvFileY = new CsvFile(trainFilePathY, trainFileTypeY);
        ReadCSV trainReadCSVY = new ReadCSV(trainCsvFileY);
        trainReadCSVY.readY();

        String testX = "covtype_libsvm_ise_test_x.2";
        String testFilePathX = baseX + testX;
        String testFileType = "csv";
        CsvFile testCsvFileX = new CsvFile(testFilePathX, testFileType);
        ReadCSV testReadCSVX = new ReadCSV(testCsvFileX);
        testReadCSVX.readX();

        String testY = "covtype_libsvm_ise_test_y.2.bin";
        String testFilePathY = baseY + testY;
        String testFileTypeY = "csv";
        CsvFile testCsvFileY = new CsvFile(testFilePathY, testFileTypeY);
        ReadCSV testReadCSVY = new ReadCSV(testCsvFileY);
        testReadCSVY.readY();




        long read_end = System.currentTimeMillis();
        long read_time = read_end - read_start;
        double read_time_d = (read_time/1000.0);

        ArrayList<double[]> trainXValues = trainReadCSVX.getxVals();
        ArrayList<Double> trainYValues = trainReadCSVY.getyVals();

        ArrayList<double []> testXValues = testReadCSVX.getxVals();
        ArrayList<Double> testYValues = testReadCSVY.getyVals();

        FeatureMatrix featureMatrix = new FeatureMatrix(trainXValues, trainYValues);

        featureMatrix.generate();
        //featureMatrix.print();

     /*   for(int i=0; i < matA.getRows();i++){
            for(int j=0; j < matA.getColumns(); j++){

                System.out.print(matA.getMatInt()[i][j]+ " ");

            }
            LOG.info();
        }

        for(int i=0; i < matA.getRows();i++){
            for(int j=0; j < matA.getColumns(); j++){

                System.out.print( matB.getMatInt()[i][j]+ " ");

            }
            LOG.info();
        }*/


        //get dot product
      /*  LOG.info("Matrix Dot Product");

        MatrixOperator matrixOperator = new MatrixOperator();
        Matrix matDot = matrixOperator.product(matA,matB,"DOT");

        LinearKernel linearKernel = new LinearKernel(matA,matB);
        int value = linearKernel.getLinearKernelOutput().getMatInt()[0][0];
        int value1 = matDot.getMatInt()[0][0];
        LOG.info("Dot Product : "+value1);
        LOG.info("Linear Kernel : "+value);


        Matrix sub = matrixOperator.subtract(matB,matA);
        matrixOperator.disp(sub);

        double matANorm = matrixOperator.norm(matA).getMatDouble()[0][0];
        double matBNorm = matrixOperator.norm(matB).getMatDouble()[0][0];
        LOG.info("Matrix A norm "+matANorm);
        LOG.info("Matrix B norm "+matBNorm);

        double sigma = 0.25;
        GaussianKernel gaussianKernel = new GaussianKernel(matA,matB,sigma);
        double gaussianKernelValue = gaussianKernel.getGaussianKernelOutput().getMatDouble()[0][0];
        LOG.info("Gaussian Kernel Output :"+gaussianKernelValue);

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

        SMO SMO = new SMO(alpha,b,w,X,Y,lpd);
        //SMO.lagrangeCalculation(alpha,matX,matY,b,w);
        String kernel = Constant.LINEAR;
        long train_start = System.currentTimeMillis();
        Model model = SMO.svmTrain(X,Y,Constant.LINEAR);
        model.saveModel("model/model_"+trainX);
        long train_end = System.currentTimeMillis();
        double train_time = (train_end-train_start)/1000.0;

        //check the model output

        MatrixOperator matrixOperator = new MatrixOperator();
        //LOG.info("Alphas from Model");
        //matrixOperator.disp(model.getAlphas());

        //LOG.info("X from Model");
        //matrixOperator.disp(model.getX());

        //LOG.info("Y from Model");
        //matrixOperator.disp(model.getY());

        //LOG.info("W from Model");
        //matrixOperator.disp(model.getW());

        //x_test = [[1.34, 0.23], [2.2, 0.35], [1.1, 0.1], [0.08, 2.1]]

        //x_test_1 = [[3.49, 4.02], [1.59, 2.03], [1.53, 2.63], [1.89, 2.93]];

        double [][] testArr = Util.converToArray(testXValues);
        Double [] testRes = new Double[testArr.length];
        Double [] testArrRes = testYValues.toArray(testRes);

        int testArrRows = testArr.length;
        int testArrCols = testArr[0].length;

        LOG.info("Test Array Rows : " + testArrRows);
        LOG.info("Test Array Cols : " + testArrCols);


        Matrix test1 = new Matrix(testArrRows, testArrCols,"DOUBLE");
        test1.setMatDouble(testArr);

        Predict predict = new Predict(model,test1);

        Matrix prediction1 = predict.predict();


        double [] predictionArr = new MatrixOperator().transpose(prediction1).getMatDouble()[0];
        double accuracy = predict.getAccuracy(testArrRes , predictionArr);

        LOG.info("Prediction of test 1");
        LOG.info("-----------------------------------------");
        LOG.info("I/O Time : " + read_time_d + " s");
        LOG.info("Training Time : " + train_time + " s");
        LOG.info("Accuracy : " + accuracy);
        LOG.info("----------------------------------------");

        double b_cal = model.getB();
        LOG.info("b : " + b_cal);


    }
}
