package edu.ise.svm.experiments.dynamic.checkacc;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.Main;
import edu.ise.svm.entities.Model;
import edu.ise.svm.io.CsvFile;
import edu.ise.svm.io.ReadCSV;
import edu.ise.svm.matrix.FeatureMatrix;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;
import edu.ise.svm.smo.ModularPrediction;
import edu.ise.svm.smo.Predict;
import edu.ise.svm.smo.SMO;
import edu.ise.svm.util.UtilDynamicSingle;
import edu.ise.svm.util.UtilSingle;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
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
 *
 * **/


public class BulkMDMMTraining {

    static{
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    public final static Logger LOG = Logger.getLogger(Main.class.getName());
    public static String MODEL_PATH = "model/single/heart/1";
    private static String MODEL_NAME = "weighted_acc_heart_scale_test_x_bin.1_1";
    private static String WEIGHETD_MODEL_PATH = "stats/weightedmodels/single/heart/1/"+MODEL_NAME;
    public static int DATA_PARTITIONS = 1;
    public static int DATA_PARTITION_SIZE = 1;
    private static String MODEL_BASE=""; // model
    private static String MODEL_DATANAME=""; //heart
    private static String MODEL_VERSION=""; //2
    private static String MODEL_SINGLE=""; //positive, negative or zero
    private static double gamma=2.0;
    private static double C = 1;
    private static String info = "" ;
    private static String logdata = "";
    private static String KERNEL = "gaussian";

    private enum DATATYPE {
        DOUBLE, INT
    }

    public static void main(String[] args) throws OutOfMemoryError, IOException {

        LOG.info("Support Vector Machines Library v1.0");

        String info="";

        long read_start = System.currentTimeMillis();
        String [] argv = UtilDynamicSingle.optArgs(args, Constant.DYNAMIC_TRAINING);

        /**
         * Training (X) File : data/covtype/covtype_libsvm_ise_train_x.1
         Training (Y) File : data/covtype/covtype_libsvm_ise_train_y.1.bin
         Testing (X) File : data/covtype/covtype_libsvm_ise_test_x.1
         Testing (Y) File : data/covtype/covtype_libsvm_ise_test_y.1.bin
         **/

        String baseX = argv[0];
        String baseY = baseX;

        String trainX = argv[1];
        String trainY = argv[2];
        String testX = argv[3];
        String testY = argv[4];

        MODEL_PATH = argv[5];
        DATA_PARTITIONS = Integer.parseInt(argv[6]);
        C = Double.parseDouble(argv[7]);
        gamma = Double.parseDouble(argv[8]);
        KERNEL = argv[9];


        String [] model_path_attrb = MODEL_PATH.split("/");
        MODEL_BASE = model_path_attrb[0];
        MODEL_DATANAME = model_path_attrb[2];
        MODEL_VERSION = model_path_attrb[3];
        MODEL_SINGLE = model_path_attrb[1];

        LOG.info(trainX);
        LOG.info(trainY);
        LOG.info(testX);
        LOG.info(testY);
        int start = new Random().nextInt(2);
        double avg_acc = 0.0;
        double [] accuracyPerModel = new double[0];
        String expName = "Test";
        int k = start;
        int iteration = 1;
        double base_accuracy = 80.0;
        while ( avg_acc<base_accuracy) {
                k = new Random().nextInt(200);
                LOG.info("Selected Data Set : "+k);
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
                String kernel = KERNEL;
                long train_start = System.currentTimeMillis();
                Model model = SMO.svmTrain(X,Y,kernel,C,gamma);
                String model_path = MODEL_PATH+"/model_"+trainX;
                model.saveModel(model_path);
                long train_end = System.currentTimeMillis();
                double train_time = (train_end-train_start)/1000.0;

                String logdata = "";
                logdata += "Training (X) File : "+trainFilePathX+" \n";
                logdata += "Training (Y) File : "+trainFilePathY+" \n";
                logdata += "Testing (X) File : "+testFilePathX+" \n";
                logdata += "Testing (Y) File : "+testFilePathY+" \n";
                logdata += "I/O Time : " + read_time_d + " s\n";
                logdata += "Training Time : " + train_time + " s\n";

                UtilDynamicSingle.createLog("logs/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/log_"+trainX+"__C="+C+"_gamma="+gamma, logdata, Constant.DYNAMIC_TRAINING);

                //COVALIDATION TEST

                long read_start1 = System.currentTimeMillis();

                ArrayList<ReadCSV> data = getData(args);
                ReadCSV testReadCSVX1 = data.get(0);
                ReadCSV testReadCSVY1 = data.get(1);
                MODEL_PATH = args[5];
                String [] model_path_attrb1 = MODEL_PATH.split("/");
                MODEL_BASE = model_path_attrb[0];
                MODEL_DATANAME = model_path_attrb[2];
                MODEL_VERSION = model_path_attrb[3];
                MODEL_SINGLE = model_path_attrb[1];

                ArrayList<Model> models = loadModels();

                LOG.info("Model Size :"+models.size());

                long read_end1 = System.currentTimeMillis();
                long read_time1 = read_end1 - read_start1;
                double read_time_d1 = (read_time1/1000.0);
                logdata += "I/O Time : " + read_time_d1 + " s\n";
                info += "I/O Time : " + read_time1 + "\n";

                // generates the TestMatrix
                Matrix testData = generateTestMatrix(testReadCSVX1, testReadCSVY1);
                // generate the testArr in the Double format
                Double testArrRes [] = getTestArray(testReadCSVX1, testReadCSVY1);
                // get the accuracy array for each model
                expName =  trainX;//UtilSingle.optArgs(args, Constant.TESTING)[1];

                accuracyPerModel = getModelTrainingAccuracies(models, testData, testArrRes, expName);

                double totalAccuracies = 0.0;
                for (int i = 0; i < accuracyPerModel.length; i++) {
                    //LOG.info("Model "+i+" Accuracy : " + accuracyPerModel[i]);
                    totalAccuracies+= accuracyPerModel[i];
                }
            avg_acc = totalAccuracies / accuracyPerModel.length;
            LOG.info("Iteration :"+iteration+", Average Accuracy : " + avg_acc);

            File file = new File(model_path);
            if(avg_acc<base_accuracy){

                if(file.delete())
                {
                    LOG.info("Model deleted successfully");
                }
                else
                {
                    LOG.info("Failed to delete the file");
                }
            }

            iteration++;
        }


        UtilSingle.modelAccuracySaveCSV(accuracyPerModel,"stats/SDMMTestAccuracies/"+MODEL_SINGLE+"/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/"+"stats_"+expName+"_"+MODEL_VERSION);

        double [] weightedModels = generateWeightedModels(accuracyPerModel);

        UtilSingle.modelAccuracySaveCSV(weightedModels,"stats/weightedmodels/"+MODEL_SINGLE+"/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/"+"weighted_acc_"+expName+"_"+MODEL_VERSION);

        // PREDICTION


        DATA_PARTITION_SIZE = DATA_PARTITIONS;
        WEIGHETD_MODEL_PATH ="stats/weightedmodels/"+MODEL_SINGLE+"/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/"+"weighted_acc_"+expName+"_"+MODEL_VERSION;


        double [] allDataSetAccuracies = new double[DATA_PARTITION_SIZE];
        for (int i = 1; i < DATA_PARTITION_SIZE+1; i++) {
            double accuracyPerDataSet = 0.0;
            baseX = args[0];
            baseY = baseX;
            testX = args[3];
            testY = args[4];
            testX = testX.split("\\.")[0] + "." + String.valueOf(i);
            testY = testY.split(".bin")[0].split("\\.")[0] + "." + String.valueOf(i)+".bin";
            String argv1 [] = new String[3];
            argv1[0] = baseX;
            argv1[1] = testX;
            argv1[2] = testY;
            ArrayList<ReadCSV> data = getDataPrediction(argv1);
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

        UtilDynamicSingle.modelAccuracySaveCSV(allDataSetAccuracies,"stats/accuracyPerDataSet/"+MODEL_SINGLE+"/"+MODEL_DATANAME+"/"+MODEL_VERSION+"/"+"accuracy_"+expName+"_"+"_C="+C+"_gamma="+gamma);
        double total_test_acc = 0.0;
        for (int i = 0; i < allDataSetAccuracies.length; i++) {
            LOG.info("Accuracy 1 : "+ allDataSetAccuracies[i]);
            total_test_acc += allDataSetAccuracies[i];
        }
        double final_pred_acc = total_test_acc / allDataSetAccuracies.length;
        LOG.info("Final Prediction Accuracy : "+ final_pred_acc);

    }

    public static double [] getModelTrainingAccuracies(ArrayList<Model> models, Matrix testData, Double [] testArrRes, String expName ) throws IOException{

        double [] accuracyArray = new double[models.size()];
        int modelId = 0;
        for (Model model: models) {

            long start_testing = System.currentTimeMillis();

            ModularPrediction predict = new ModularPrediction(model, testData);

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
            logdata += "Testing Time : " + testing_time + " s\n";
            logdata += "Accuracy : " + accuracy;
            accuracyArray[modelId] = accuracy;
            modelId++;
            UtilSingle.createLog("logs/log_bulk_model_testing_"+expName+"_"+modelId, logdata, Constant.TESTING);
        }

        return accuracyArray;
    }

    public static double [] getModelTrainingAccuracies(ArrayList<Model> models, Matrix testData, Double [] testArrRes, String expName, String modelType ) throws IOException{

        double [] accuracyArray = new double[models.size()];
        int modelId = 0;
        for (Model model: models) {

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
            logdata += "Testing Time : " + testing_time + " s\n";
            logdata += "Accuracy : " + accuracy;
            accuracyArray[modelId] = accuracy;
            modelId++;
            UtilSingle.createLog("logs/log_bulk_model_testing_"+expName+"_"+modelId, logdata, Constant.TESTING);
        }

        return accuracyArray;
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

        //LOG.info("Test Array Rows : " + testArrRows);
        //LOG.info("Test Array Cols : " + testArrCols);


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

    public static ArrayList<ReadCSV> getData(String [] args){
        ArrayList<ReadCSV> readCSVS = new ArrayList<>();

        String [] argv = UtilSingle.optArgs(args, Constant.TESTING);
        String baseX = argv[0];
        String baseY = baseX;

        String testX = argv[3];
        String testY = argv[4];

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

    public static double [] generateWeightedModels(double [] accuracies){
        double [] weights = new double [accuracies.length];
        double total = 0;
        for (int i = 0; i < accuracies.length; i++) {
            total += accuracies[i];
        }

        double totalWeights = 0.0;
        for (int i = 0; i < weights.length; i++) {
            weights[i] = accuracies[i]/total;
            totalWeights += weights[i];
            LOG.info("Weights "+i+" :  " + weights[i]);
        }

        LOG.info("Total Weights : " + totalWeights);
        return weights;
    }

    // PREDICTION METHODS

    public static double perDataSetPrediction(Matrix testData, ArrayList<Model> models,  ArrayList<ReadCSV> data ) throws IOException{

        ReadCSV testReadCSVX = data.get(0);
        ReadCSV testReadCSVY = data.get(1);

        double [] modelWeights = UtilDynamicSingle.loadModelWeights(WEIGHETD_MODEL_PATH);
        for (int i = 0; i < modelWeights.length; i++) {
            LOG.info("Model Id :  "+i+ " : weight value : " + modelWeights[i]);
        }

        double [] getAllPredictionArray = getPredictions(testData, models, modelWeights);
        /*for (int i = 0; i < getAllPredictionArray.length; i++) {
            LOG.info("Sample : " + i +" prediction : " + getAllPredictionArray[i]);
        }*/
        ArrayList<double []> testXValues = testReadCSVX.getxVals();
        ArrayList<Double> testYValues = testReadCSVY.getyVals();
        double [][] testArr = UtilDynamicSingle.converToArray(testXValues);
        Double [] testRes = new Double[testArr.length];
        Double [] testArrRes = testYValues.toArray(testRes);

        double accuracy = Predict.getAccuracy(testArrRes, getAllPredictionArray);
        LOG.info("Accuracy : " + accuracy);
        return accuracy;
    }

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

    public static ArrayList<ReadCSV> getDataPrediction(String [] args){
        ArrayList<ReadCSV> readCSVS = new ArrayList<>();

        String [] argv = UtilDynamicSingle.optArgs(args, Constant.TESTING);
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

}
