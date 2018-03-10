package edu.ise.svm.test;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by vibhatha on 3/10/18.
 */

public class ModelTest {

    static{
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    private static final Logger LOG = Logger.getLogger(ModelTest.class.getName());

    public static void main(String[] args) throws IOException {

        String modelpath = "model/model_covtype_libsvm_ise_train_x.1";
        Model.loadModel(modelpath);
    }

}
