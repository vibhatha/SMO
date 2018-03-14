package edu.ise.svm.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vibhatha on 3/10/18.
 */
public class Test {
    public static void main(String[] args) {
       /* ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(""+i);
            System.out.println(i);
        }
        System.out.println("----");
        List<String> lst = list.subList(5,7);
        for (String s : lst) {
            System.out.println(s);
        }*/

       String s = "covtype_libsvm_ise_train_x_bin.1";
       String ss [] = s.split("\\.");
        System.out.println(ss[0]);

       String s1 = "covtype_libsvm_ise_test_y.1.bin";
       String ss1 [] = s1.split(".bin")[0].split("\\.");
        System.out.println(ss1[0]);
    }
}
