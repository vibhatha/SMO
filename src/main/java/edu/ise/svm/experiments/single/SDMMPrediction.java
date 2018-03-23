package edu.ise.svm.experiments.single;

/**
 * Created by vibhatha on 3/14/18.
 */

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;
import edu.ise.svm.smo.Predict;
import edu.ise.svm.util.UtilSingle;

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
public class SDMMPrediction {

    static{
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    private static final Logger LOG = Logger.getLogger(SDMMPrediction.class.getName());
    private static String info = "" ;
    private static String logdata = "";

    private static String MODEL_NAME = "weighted_acc_heart_scale_test_x_bin.1_1";
    private static String WEIGHETD_MODEL_PATH = "stats/weightedmodels/single/heart/1/"+MODEL_NAME;

    private ArrayList<String> modelList;
    private static final String EXP_ID = "covtype/1";
    private static String MODEL_PATH = "model/single/heart/1";
    private static int  DATA_PARTITION_SIZE = 1;
    private static String MODEL_BASE=""; // model
    private static String MODEL_DATANAME=""; //heart
    private static String MODEL_VERSION=""; //2
    private static String MODEL_SINGLE=""; //positive, negative or zero

    public static void main(String[] args) throws IOException{
        long read_start = System.currentTimeMillis();

        String expName =  UtilSingle.optArgs(args, Constant.PREDICTING)[1];
        MODEL_PATH = args[3];
        String [] model_path_attrb = MODEL_PATH.split("/");
        MODEL_BASE = model_path_attrb[0];
        MODEL_DATANAME = model_path_attrb[2];
        MODEL_VERSION = model_path_attrb[3];
        MODEL_SINGLE = model_path_attrb[1];

        DATA_PARTITION_SIZE = Integer.parseInt(args[4]);
        MODEL_NAME = args[5];
        WEIGHETD_MODEL_PATH ="stats/weightedmodels/"+MODEL_SINGLE+"/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/"+MODEL_NAME;

        double [] allDataSetAccuracies = new double[DATA_PARTITION_SIZE];
        for (int i = 1; i < DATA_PARTITION_SIZE+1; i++) {
            double accuracyPerDataSet = 0.0;
            String baseX = args[0];
            String baseY = baseX;
            String testX = args[1];
            String testY = args[2];
            testX = testX.split("\\.")[0] + "." + String.valueOf(i);
            testY = testY.split(".bin")[0].split("\\.")[0] + "." + String.valueOf(i)+".bin";
            String argv [] = new String[3];
            argv[0] = baseX;
            argv[1] = testX;
            argv[2] = testY;
            ArrayList<ReadCSV> data = getData(argv);
            ReadCSV testReadCSVX = data.get(0);
            ReadCSV testReadCSVY = data.get(1);



            ArrayList<Model> models = loadModels();

            long read_end = System.currentTimeMillis();
            long read_time = read_end - read_start;
            double read_time_d = (read_time/1000.0);
            logdata += "I/O Time : " + read_time_d + " s\n";
            info += "I/O Time : " + read_time + "\n";

            // generates the TestMatrix
            Matrix testData = generateTestMatrix(testReadCSVX, testReadCSVY);
            accuracyPerDataSet = perDataSetPrediction(testData, models, data);
            allDataSetAccuracies[i-1] = accuracyPerDataSet;
        }

        UtilSingle.modelAccuracySaveCSV(allDataSetAccuracies,"stats/accuracyPerDataSet/"+MODEL_SINGLE+"/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/"+"accuracy_"+expName+"_"+"1");

    }

    public static double perDataSetPrediction(Matrix testData, ArrayList<Model> models,  ArrayList<ReadCSV> data ) throws IOException{

        ReadCSV testReadCSVX = data.get(0);
        ReadCSV testReadCSVY = data.get(1);

        double [] modelWeights = UtilSingle.loadModelWeights(WEIGHETD_MODEL_PATH);
        for (int i = 0; i < modelWeights.length; i++) {
            LOG.info("Model Id :  "+i+ " : weight value : " + modelWeights[i]);
        }

        double [] getAllPredictionArray = getPredictions(testData, models, modelWeights);
        /*for (int i = 0; i < getAllPredictionArray.length; i++) {
            LOG.info("Sample : " + i +" prediction : " + getAllPredictionArray[i]);
        }*/
        ArrayList<double []> testXValues = testReadCSVX.getxVals();
        ArrayList<Double> testYValues = testReadCSVY.getyVals();
        double [][] testArr = UtilSingle.converToArray(testXValues);
        Double [] testRes = new Double[testArr.length];
        Double [] testArrRes = testYValues.toArray(testRes);

        double accuracy = Predict.getAccuracy(testArrRes, getAllPredictionArray);
        LOG.info("Accuracy : " + accuracy);
        return accuracy;
    }


/*
* In this method the complete test data set and list of models are provided along with the
* weights calculated during the prediction. Then per each sample in the test data set, the
* prediction from each model is calculated and the relationship for final prediction is obtained
* by getting the convolution with the model weights and comparing it with the boundary value zero.
* If the convolution value is greater than  or equal to zero it means class 1 is prominent with higher weighted sums.
* And if the convolution is less than zero the class prominent is -1.*
* **/

    public static double [] getPredictions(Matrix testData, ArrayList<Model> models, double [] modelWeights){

        MatrixOperator matrixOperator = new MatrixOperator();
        int rows = testData.getRows();
        double [] allPredictionArray = new double[rows];
        int allPredictionArrayId=0;
        for (int i = 0; i < rows; i++) {
            Matrix sample = matrixOperator.getRowData(testData, i);
            double [] modelPredictionList = new double[models.size()];
            int modelId=0;
            double predictLabelOfSample=-1;
            for (Model model:models) {
                Predict predict = new Predict(model, sample);
                Matrix prediction = predict.predict();
                double[] curPrediction = matrixOperator.transpose(prediction).getMatDouble()[0];
                double predClass = curPrediction[0];
                modelPredictionList[modelId] = predClass;
                modelId++;
            }
            double predictScore = 0.0;
            for (int j = 0; j < modelWeights.length; j++) {
                predictScore += modelWeights[j]*modelPredictionList[j];
            }
            if(predictScore>=0){
                predictLabelOfSample = 1;
            }else{
                predictLabelOfSample = -1;
            }
            allPredictionArray[allPredictionArrayId] = predictLabelOfSample;
            allPredictionArrayId++;
        }

        return allPredictionArray;
    }

    public static double gerPredictionAccuracy(Double [] testArrRes, double [] predictionArray){
        double accuracy = 0.0;

        return  accuracy;
    }

    public static ArrayList<Model> loadModels() throws IOException {

        ArrayList<String> modelPaths = null;
        modelPaths = UtilSingle.loadPaths(MODEL_PATH);
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

        String [] argv = UtilSingle.optArgs(args, Constant.TESTING);
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
        LOG.info(info);
        readCSVS.add(testReadCSVX);
        readCSVS.add(testReadCSVY);

        return readCSVS;
    }

    public static Matrix generateTestMatrix(ReadCSV testReadCSVX, ReadCSV testReadCSVY){
        Matrix testData = null;

        ArrayList<double []> testXValues = testReadCSVX.getxVals();
        ArrayList<Double> testYValues = testReadCSVY.getyVals();

        double [][] testArr = UtilSingle.converToArray(testXValues);
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
        double [][] testArr = UtilSingle.converToArray(testXValues);
        ArrayList<Double> testYValues = testReadCSVY.getyVals();
        Double [] testRes = new Double[testArr.length];
        Double [] testArrRes = testYValues.toArray(testRes);

        return testArrRes;

    }

}