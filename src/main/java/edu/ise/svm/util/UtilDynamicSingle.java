package edu.ise.svm.util;

import edu.ise.svm.Constants.Constant;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by vibhatha on 3/30/18.
 */
public class UtilDynamicSingle {
    private final static Logger LOG = Logger.getLogger(UtilSingle.class.getName());

    static{
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    public static double [][] converToArray (ArrayList<double []> drlst){

        if(drlst!=null){
            double [][] arr = null;
            int listsize = drlst.size();
            if(listsize >0  ){
                arr = new double[drlst.size()][];
                for (int i = 0; i < listsize; i++) {
                    double [] ar1 = drlst.get(i);
                    arr[i]=ar1;
                }
            }
            return arr;
        }
        else{
            return null;
        }
    }

    public static String [] optArgs(String [] args, String type){

        if(args.length==0){
            LOG.info("Invalid Arguments");

            LOG.info("Training : ");
            LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                    " <train-X> <train-Y> <test-X> <test-Y>");
            LOG.info("Testing : ");
            LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                    " <test-X> <test-Y>");

            System.exit(0);

        }else{
            if(type.equals(Constant.SDSM_SVM)){
                if(args!=null && args.length==11){
                    LOG.info("Source Folder : "+args[0]);
                    LOG.info("Train X: "+args[1]);
                    LOG.info("Train Y : "+args[2]);
                    LOG.info("Test X : "+args[3]);
                    LOG.info("Test Y : "+args[4]);
                    //"model/single/heart/1"
                    LOG.info("Model path : "+args[5]);
                    LOG.info("Data Paritions: " + args[6]);
                    LOG.info("C: " + args[7]);
                    LOG.info("Gamma: " + args[8]);
                    LOG.info("Kernel : " + args[9]);
                    LOG.info("Base Accuracy : " + args[10]);

                }else{
                    LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                            " <train-X> <train-Y> <test-X> <test-Y> <model-path> <data-partitions> <C> <gamma> <kernel>");
                    System.exit(0);
                }
            }
            if(type.equals(Constant.DYNAMIC_TRAINING)){
                if(args!=null && args.length==10){
                    LOG.info("Source Folder : "+args[0]);
                    LOG.info("Train X: "+args[1]);
                    LOG.info("Train Y : "+args[2]);
                    LOG.info("Test X : "+args[3]);
                    LOG.info("Test Y : "+args[4]);
                    //"model/single/heart/1"
                    LOG.info("Model path : "+args[5]);
                    LOG.info("Data Paritions: " + args[6]);
                    LOG.info("C: " + args[7]);
                    LOG.info("Gamma: " + args[8]);

                }else{
                    LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                            " <train-X> <train-Y> <test-X> <test-Y> <model-path> <data-partitions> <C> <gamma> <kernel>");
                    System.exit(0);
                }
            }
            if(type.equals(Constant.DYNAMIC_TESTING)){
                if(args!=null && args.length==3){
                    LOG.info("Source Folder : "+args[0]);
                    LOG.info("Test X : "+args[1]);
                    LOG.info("Test Y : "+args[2]);
                }else{
                    LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                            " <test-X> <test-Y>");
                }
            }
            if(type.equals(Constant.DYNAMIC_PREDICTING)){
                if(args!=null && args.length==8){
                    LOG.info("Source Folder : "+args[0]);
                    LOG.info("Test X : "+args[1]);
                    LOG.info("Test Y : "+args[2]);
                    LOG.info("Model Path : " + args[3]);
                    LOG.info("Data Partitions : " + args[4]);
                    LOG.info("Model Name : " + args[5]);
                    LOG.info("C: " + args[6]);
                    LOG.info("Gamma: " + args[7]);
                }else{
                    LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                            " <test-X> <test-Y> <model-path> <data-partitions> <model-name> <C> <gamma>");
                    System.exit(0);
                }
            }
        }


        return args;
    }

    public static void createLog (String filepath, String data, String type)throws IOException {
        String getDateTime = getDataTime();
        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("=====================================================================================\n");
            writer.write("Log "+type+" :" +getDateTime+ " \n");
            writer.write("=====================================================================================\n");
            writer.write(data);
            writer.newLine();
        }

    }

    public static String getDataTime(){
        String datatime="";
        // Reference : https://www.mkyong.com/java/java-how-to-get-current-date-time-date-and-calender/
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        datatime = (dateFormat.format(date)); //2016/11/16 12:08:43
        return datatime;
    }

    public static Stream<Path> loadStreams(String path) throws IOException {
        Stream<Path> stream =  Files.list(Paths.get(path));
        //LOG.info("Stream Count : " + stream.count());
        return stream;
    }

    public static List<Path> loadFromStream(Stream<Path> stream){

        List<Path> listPath = null;

        if(stream.count()>0){
            listPath = stream.collect(Collectors.toList());
        }
        System.out.println(listPath.get(0));

        return  listPath;

    }

    public static ArrayList<String> loadPaths(String path) throws IOException {

        ArrayList<String> pathList  = null;
        File directory = new File(path);
        File[] fList = directory.listFiles();
        //LOG.info("List Size : " + fList.length);
        if(fList.length>0){
            pathList = new ArrayList<>();
            for (File file: fList) {
                pathList.add(file.getPath());
            }
        }
        //LOG.info("Loading Paths : ");
        pathList.iterator().forEachRemaining(s -> LOG.info(s));
        return pathList;
    }

    public static void modelAccuracySaveCSV(double [] accruacies, String filepath) throws IOException{
        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (int i = 0; i < accruacies.length; i++) {
                writer.write(String.valueOf(accruacies[i]));
                writer.newLine();
            }
        }
    }

    public static double [] loadModelWeights(String modelWeightPath) throws IOException{
        double [] weights = null;
        ArrayList<Double> weightList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(modelWeightPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                weightList.add(Double.parseDouble(line));
            }
        }
        weights =  weightList.stream().mapToDouble(d -> d).toArray();

        return weights;
    }

    public static boolean mkdir(String folderpath){
        boolean status = true;

        File file = new File(folderpath);

        if(file.exists()){
            status = false;
        }else{
            file.mkdir();
        }

        return status;
    }

    public static long datacount(String filepath) throws IOException {
        long count = -1;
        Path path = Paths.get(filepath);
        try (Stream<String> stream = Files.lines(path)) {
            long lineCount = stream.count();
            count = lineCount;
        }

        return count;
    }

    public static void writeFinalLog(String logfile, String log) throws IOException {
        Writer output;
        output = new BufferedWriter(new FileWriter(logfile));  //clears file every time
        output.append(log);
        output.close();
    }

    public static void appendLogs(String filename, String log){
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            String data = log;

            File file = new File(filename);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);

            LOG.info("Log Written : "+filename);

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }
}
