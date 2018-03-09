package edu.ise.svm.util;

import java.util.ArrayList;
import java.util.logging.Logger;


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


}
