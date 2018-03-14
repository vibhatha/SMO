package edu.ise.svm.experiments;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.Main;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.matrix.FeatureMatrix;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;
import edu.ise.svm.smo.Predict;
import edu.ise.svm.smo.SMO;
import edu.ise.svm.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by vibhatha on 3/11/18.
 */

/**
 * In this model we use Multiple Data Multiple Model Training
 *
 * Here we select random data from the bigger data set based on
 * co-relation between data samples. We pick up the datasamples with
 * least co-relation to a singular batch. So these batches are then being
 * transformed into smaller batches. For instance the original data set
 * has M data points and after correlation testing we get M1 amount of data points.
 * Then we use a random algorithm to select random batches. These random batches
 * contain M1/m number of samples per each batch. We use these batched to train
 * m models and these m models provide m different trained models.
 *
 * For training stage we propose the Multiple Data Multiple Model Training approach.
 * That's the only way to get different models. The other approach we will be discussing
 * is multiple model multiple kernel approach.
 * **/


public class BulkMDMMTraining {

    static{
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    public final static Logger LOG = Logger.getLogger(Main.class.getName());

    private enum DATATYPE {
        DOUBLE, INT
    }

    public static void main(String[] args) throws OutOfMemoryError, IOException {

        LOG.info("Support Vector Machines Library v1.0");

        String info="";

        long read_start = System.currentTimeMillis();
        String [] argv = Util.optArgs(args, Constant.TRAINING);

        /**
         * Training (X) File : data/covtype/covtype_libsvm_ise_train_x.1
         Training (Y) File : data/covtype/covtype_libsvm_ise_train_y.1.bin
         Testing (X) File : data/covtype/covtype_libsvm_ise_test_x.1
         Testing (Y) File : data/covtype/covtype_libsvm_ise_test_y.1.bin
         * */

        String baseX = argv[0];
        String baseY = baseX;

        String trainX = argv[1];
        String trainY = argv[2];
        String testX = argv[3];
        String testY = argv[4];

        LOG.info(trainX);
        LOG.info(trainY);
        LOG.info(testX);
        LOG.info(testY);

        for (int k = 1; k < 51; k++) {
            trainX = trainX.split("\\.")[0]+ "." + String.valueOf(k);
            testX = testX.split("\\.")[0] + "." + String.valueOf(k);
            trainY = trainY.split(".bin")[0].split("\\.")[0] + "."  + String.valueOf(k)+".bin";
            testY = testY.split(".bin")[0].split("\\.")[0] + "." + String.valueOf(k)+".bin";

            String trainFilePathX = baseX + trainX;
            String trainFileType = "csv";
            CsvFile trainCsvFileX = new CsvFile(trainFilePathX, trainFileType);
            ReadCSV trainReadCSVX = new ReadCSV(trainCsvFileX);
            trainReadCSVX.readX();


            String trainFilePathY = baseY + trainY;
            String trainFileTypeY = "csv";
            CsvFile trainCsvFileY = new CsvFile(trainFilePathY, trainFileTypeY);
            ReadCSV trainReadCSVY = new ReadCSV(trainCsvFileY);
            trainReadCSVY.readY();

            String testFilePathX = baseX + testX;
            String testFileType = "csv";
            CsvFile testCsvFileX = new CsvFile(testFilePathX, testFileType);
            ReadCSV testReadCSVX = new ReadCSV(testCsvFileX);
            testReadCSVX.readX();


            String testFilePathY = baseY + testY;
            String testFileTypeY = "csv";
            CsvFile testCsvFileY = new CsvFile(testFilePathY, testFileTypeY);
            ReadCSV testReadCSVY = new ReadCSV(testCsvFileY);
            testReadCSVY.readY();

            info += "Training (X) File : "+trainFilePathX+" \n";
            info += "Training (Y) File : "+trainFilePathY+" \n";
            info += "Testing (X) File : "+testFilePathX+" \n";
            info += "Testing (Y) File : "+testFilePathY+" \n";

            long read_end = System.currentTimeMillis();
            long read_time = read_end - read_start;
            double read_time_d = (read_time/1000.0);

            info += "I/O Time : " + read_time + "\n";

            ArrayList<double[]> trainXValues = trainReadCSVX.getxVals();
            ArrayList<Double> trainYValues = trainReadCSVY.getyVals();

            ArrayList<double []> testXValues = testReadCSVX.getxVals();
            ArrayList<Double> testYValues = testReadCSVY.getyVals();

            FeatureMatrix featureMatrix = new FeatureMatrix(trainXValues, trainYValues);

            featureMatrix.generate();

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

            SMO SMO = new SMO(alpha,b,w,X,Y,lpd,info);
            //SMO.lagrangeCalculation(alpha,matX,matY,b,w);
            String kernel = Constant.LINEAR;
            long train_start = System.currentTimeMillis();
            Model model = SMO.svmTrain(X,Y,Constant.LINEAR);
            model.saveModel("model/model_"+trainX);
            long train_end = System.currentTimeMillis();
            double train_time = (train_end-train_start)/1000.0;

            String logdata = "";
            logdata += "Training (X) File : "+trainFilePathX+" \n";
            logdata += "Training (Y) File : "+trainFilePathY+" \n";
            logdata += "Testing (X) File : "+testFilePathX+" \n";
            logdata += "Testing (Y) File : "+testFilePathY+" \n";
            logdata += "I/O Time : " + read_time_d + " s\n";
            logdata += "Training Time : " + train_time + " s\n";

            Util.createLog("logs/log_"+trainX+"", logdata, Constant.TRAINING);

        }


    }
}
