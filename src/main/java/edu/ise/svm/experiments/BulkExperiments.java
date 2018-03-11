package edu.ise.svm.experiments;

import edu.ise.svm.entities.Model;
import edu.ise.svm.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by vibhatha on 3/10/18.
 */
public class BulkExperiments {

    private static final Logger LOG = Logger.getLogger(BulkExperiments.class.getName());

    private ArrayList<String> modelList;
    private static final String MODEL_PATH = "model";

    public static void main(String[] args) throws IOException {
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
        LOG.info("Model Size :"+models.size());
    }
}
