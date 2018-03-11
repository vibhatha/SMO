package edu.ise.svm.experiments;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;
import edu.ise.svm.smo.Predict;
import edu.ise.svm.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by vibhatha on 3/11/18.
 */
public class BulkModelTesting {

    private static final Logger LOG = Logger.getLogger(BulkModelTesting.class.getName());
    private static String info = "" ;
    private static String logdata = "";

    private ArrayList<String> modelList;
    private static final String MODEL_PATH = "model";

    public static void main(String[] args) throws IOException {

        long read_start = System.currentTimeMillis();

        ArrayList<ReadCSV> data = getData(args);
        ReadCSV testReadCSVX = data.get(0);
        ReadCSV testReadCSVY = data.get(1);

        ArrayList<Model> models = loadModels();

        LOG.info("Model Size :"+models.size());

        long read_end = System.currentTimeMillis();
        long read_time = read_end - read_start;
        double read_time_d = (read_time/1000.0);

        info += "I/O Time : " + read_time + "\n";

        Matrix testData = generateTestMatrix(testReadCSVX, testReadCSVY);
        Double testArrRes [] = getTestArray(testReadCSVX, testReadCSVY);

        Model model = models.get(0);

        long start_testing = System.currentTimeMillis();

        Predict predict = new Predict(model, testData);

        long end_testing = System.currentTimeMillis();

        long testing_time = end_testing - start_testing;

        Matrix prediction1 = predict.predict();

        double [] predictionArr = new MatrixOperator().transpose(prediction1).getMatDouble()[0];

        double accuracy = predict.getAccuracy(testArrRes , predictionArr);
        LOG.info("Prediction of test 1");
        LOG.info("-----------------------------------------");
        LOG.info("Accuracy : " + accuracy);
        LOG.info("----------------------------------------");
        double b_cal = model.getB();
        LOG.info("b : " + b_cal);

        logdata += "I/O Time : " + read_time_d + " s\n";
        logdata += "Testing Time : " + testing_time + " s\n";
        logdata += "Accuracy : " + accuracy;

        Util.createLog("logs/log_bulk_model_testing_"+Util.optArgs(args, Constant.TESTING)[1]+"", logdata, Constant.TESTING);

    }

    public double [] getModelWeights(ArrayList<Model> models, Matrix testData ){

        double [] predictionArr = null;

        return predictionArr;
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

    public static ArrayList<Model> loadModels() throws IOException{

        ArrayList<String> modelPaths = null;
        modelPaths = Util.loadPaths(MODEL_PATH);
        ArrayList<Model> models = new ArrayList<>();
        if(modelPaths.size()>0){
            for (String modelpath : modelPaths) {
                Model model = new Model();
                model = model.loadModel(modelpath);
                models.add(model);
            }
        }


        return models;
    }
}
