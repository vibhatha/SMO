package edu.ise.svm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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
 * Created by vibhatha on 2/27/18.
 */
public class Util {

    private final static Logger LOG = Logger.getLogger(Util.class.getName());

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

    public static String [] optArgs(String [] args){
        if(args!=null && args.length==5){
            LOG.info("Source Folder : "+args[0]);
            LOG.info("Train X: "+args[1]);
            LOG.info("Train Y : "+args[2]);
            LOG.info("Test X : "+args[3]);
            LOG.info("Test Y : "+args[4]);
        }else{
            LOG.info("Usage java -Xms30072m -cp target/svm-ise.jar edu.ise.svm.Main <source-folder> " +
                    " <train-X> <train-Y> <test-X> <test-Y>");
        }
        return args;
    }

    public static void createLog (String filepath, String data)throws IOException {
        String getDateTime = getDataTime();
        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("=====================================================================================\n");
            writer.write("Training Log : " +getDateTime+ " \n");
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
        LOG.info("List Size : " + fList.length);
        if(fList.length>0){
            pathList = new ArrayList<>();
            for (File file: fList) {
                pathList.add(file.getPath());
            }
        }
        LOG.info("Loading Paths : ");
        pathList.iterator().forEachRemaining(s -> LOG.info(s));
        return pathList;
    }

}
