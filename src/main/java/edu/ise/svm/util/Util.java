package edu.ise.svm.util;

import java.util.ArrayList;

/**
 * Created by vibhatha on 2/27/18.
 */
public class Util {

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


}
