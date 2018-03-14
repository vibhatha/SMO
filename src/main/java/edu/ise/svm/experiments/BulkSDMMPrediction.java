package edu.ise.svm.experiments;

/**
 * Created by vibhatha on 3/14/18.
 */

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/***
 * In this class, the Single Data Multiple Model training programme is used
 * and the trained weighted models are chosen for the prediction task.
 * Here we load a single feature set which needs to be predicted and we use
 * weights from each model to determine the target class for each sample in the
 * training model.
 * */
public class BulkSDMMPrediction {

    private static final Logger LOG = Logger.getLogger(BulkSDMMPrediction.class.getName());
    private static String info = "" ;
    private static String logdata = "";

    private static final String MODEL_NAME = "stats_covtype_libsvm_ise_test_x_bin.1_1";
    private static final String WEIGHETD_MODEL_PATH = "stats/"+MODEL_NAME;

    private ArrayList<String> modelList;
    private static final String EXP_ID = "1";
    private static final String MODEL_PATH = "model/"+EXP_ID+"/";

    public static void main(String[] args) throws IOException{
        long read_start = System.currentTimeMillis();

        ArrayList<ReadCSV> data = getData(args);
        ReadCSV testReadCSVX = data.get(0);
        ReadCSV testReadCSVY = data.get(1);

        ArrayList<Model> models = loadModels();

        LOG.info("Model Size :"+models.size());

        long read_end = System.currentTimeMillis();
        long read_time = read_end - read_start;
        double read_time_d = (read_time/1000.0);
        logdata += "I/O Time : " + read_time_d + " s\n";
        info += "I/O Time : " + read_time + "\n";

        // generates the TestMatrix
        Matrix testData = generateTestMatrix(testReadCSVX, testReadCSVY);
        // generate the testArr in the Double format
        Double testArrRes [] = getTestArray(testReadCSVX, testReadCSVY);
        // get the accuracy array for each model
        String expName =  Util.optArgs(args, Constant.PREDICTING)[1];
        

    }

    public static ArrayList<Model> loadModels() throws IOException {

        ArrayList<String> modelPaths = null;
        modelPaths = Util.loadPaths(MODEL_PATH);
        ArrayList<Model> models = new ArrayList<>();
        if(modelPaths.size() > 0){
            for (String modelpath : modelPaths) {
                Model model = new Model();
                model = model.loadModel(modelpath);
                models.add(model);
            }
        }

        return models;
    }

    public static ArrayList<ReadCSV> getData(String [] args){
        ArrayList<ReadCSV> readCSVS = new ArrayList<>();

        String [] argv = Util.optArgs(args, Constant.TESTING);
        String baseX = argv[0];
        String baseY = baseX;

        String testX = argv[1];
        String testY = argv[2];

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

        info += "Testing (X) File : "+testFilePathX+" \n";
        info += "Testing (Y) File : "+testFilePathY+" \n";
        logdata += "Testing (X) File : "+testFilePathX+" \n";
        logdata += "Testing (Y) File : "+testFilePathY+" \n";

        readCSVS.add(testReadCSVX);
        readCSVS.add(testReadCSVY);

        return readCSVS;
    }

    public static Matrix generateTestMatrix(ReadCSV testReadCSVX, ReadCSV testReadCSVY){
        Matrix testData = null;

        ArrayList<double []> testXValues = testReadCSVX.getxVals();
        ArrayList<Double> testYValues = testReadCSVY.getyVals();

        double [][] testArr = Util.converToArray(testXValues);
        Double [] testRes = new Double[testArr.length];
        Double [] testArrRes = testYValues.toArray(testRes);

        int testArrRows = testArr.length;
        int testArrCols = testArr[0].length;

        LOG.info("Test Array Rows : " + testArrRows);
        LOG.info("Test Array Cols : " + testArrCols);


        testData = new Matrix(testArrRows, testArrCols,"DOUBLE");
        testData.setMatDouble(testArr);

        return testData;
    }

    public static Double [] getTestArray(ReadCSV testReadCSVX, ReadCSV testReadCSVY){

        ArrayList<double []> testXValues = testReadCSVX.getxVals();
        double [][] testArr = Util.converToArray(testXValues);
        ArrayList<Double> testYValues = testReadCSVY.getyVals();
        Double [] testRes = new Double[testArr.length];
        Double [] testArrRes = testYValues.toArray(testRes);

        return testArrRes;

    }

}
