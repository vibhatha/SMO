package edu.ise.svm.smo;

import com.sun.org.apache.xpath.internal.operations.Mod;
import edu.ise.svm.Constants.Constant;
import edu.ise.svm.entities.Model;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by vibhatha on 10/1/16.
 */

public class SMO {
    static{
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }
    public final static Logger LOG = Logger.getLogger(SMO.class.getName());
    private boolean debug = false;
    private Matrix alpha;
    private Matrix b;
    private Matrix w;
    private Matrix x;
    private Matrix y;
    private Matrix lpd;
    private String info;
    MatrixOperator operator = new MatrixOperator();

    public SMO(Matrix alpha, Matrix b, Matrix w, Matrix x, Matrix y, Matrix lpd) {
        this.alpha = alpha;
        this.b = b;
        this.w = w;
        this.x = x;
        this.y = y;
        this.lpd = lpd;
    }

    public SMO(Matrix alpha, Matrix b, Matrix w, Matrix x, Matrix y, Matrix lpd, String info) {
        this.alpha = alpha;
        this.b = b;
        this.w = w;
        this.x = x;
        this.y = y;
        this.lpd = lpd;
        this.info = info;
    }


    public Matrix wSquare() {

        Matrix w_square = null;

        try {

            w_square = this.operator.norm(this.w);
            double val = w_square.getMatDouble()[0][0];
            val = Math.pow(val, 2);
            w_square.getMatDouble()[0][0] = val;


        } catch (Exception ex) {
            LOG.info("Error in calculation " + ex.getMessage());
        }

        return w_square;
    }

    public Matrix lagrangeCalculation(Matrix alpha, Matrix x, Matrix y, Matrix b, Matrix w) {

        Matrix lagrange = null;
        int n = 0;
        n = y.getRows();

        double lpd = 0.0;


        MatrixOperator matrixOperator = new MatrixOperator();

        Matrix w_square = matrixOperator.norm(w);
        double w_norm = w_square.getMatDouble()[0][0];
        w_norm = w_norm * w_norm;


        x = matrixOperator.transpose(x);
        //y = matrixOperator.transpose(y);

        LOG.info("X " + x.getRows() + "," + x.getColumns());
        LOG.info("W " + w.getRows() + "," + w.getColumns());
        LOG.info("X :");
        matrixOperator.disp(x);
        LOG.info("W :");
        matrixOperator.disp(w);

        //W_t * X operation
        LOG.info("W_t * X operation");
        Matrix wx = matrixOperator.product(w, x, "CROSS");
        matrixOperator.disp(wx);

        //+b operation
        LOG.info("Wx + b operation");
        Matrix wx_b = matrixOperator.add(wx, b);
        matrixOperator.disp(wx_b);
        //y * wx_b

        LOG.info("Y ");
        matrixOperator.disp(y);

        Matrix y_wx_b = matrixOperator.product(wx_b, y, "CROSS");
        LOG.info("y(Wt*X+b)");
        matrixOperator.disp(y_wx_b);

        LOG.info("y(wx+b)");
        LOG.info(y_wx_b.getRows() + "," + y_wx_b.getColumns());


        double val = y_wx_b.getMatDouble()[0][0];
        val = val - 1; // y(wx+b)-1 operation

        //Matrix alpha_y_wx_b_1 = matrixOperator.product(alpha,)
        return lagrange;

    }

    public Model svmTrain(Matrix x, Matrix y, String kernel) {


        ArrayList<Integer> testVal = new ArrayList<>();

        int m = x.getRows();
        int n = x.getColumns();
        Matrix alphas = new Matrix(m, 1, "DOUBLE");

        for (int i = 0; i < alphas.getRows(); i++) {
            for (int j = 0; j < alphas.getColumns(); j++) {

                alphas.getMatDouble()[i][j] = 0.0;

            }
        }
        double tol = 0.001; //tolerance
        double b = 0;
        double b1 = 0;
        double b2 = 0;
        Matrix e = new Matrix(m, 1, "DOUBLE");
        int passes = 0;
        double eta = 0.0;
        double l = 0;
        double h = 0;
        double C = 1;
        int max_passes = 12;

        //moving on with the linear kernel
        Matrix k = null;
        try{
            LOG.info("Matrix Dimension : "+m+","+m);
            k = new Matrix(m, m, "DOUBLE");
        }catch (OutOfMemoryError ex){
            LOG.info("Exception " + ex.getMessage());
        }

        MatrixOperator matrixOperator = new MatrixOperator();
        Matrix x_dash = matrixOperator.transpose(x);
        long time_kernel_start = System.currentTimeMillis();
        k = matrixOperator.product(x, x_dash, "CROSS");
        long time_kernel_end = System.currentTimeMillis();
        LOG.info("====================================================");
        LOG.info("Kernel Matrix Calculation : " + (time_kernel_end - time_kernel_start)/1000.0);
        LOG.info("====================================================");
        this.info += "Kernel Matrix Calculation : " + (time_kernel_end - time_kernel_start)/1000.0 + " s";
        //LOG.info("X");
        //matrixOperator.disp(x);
        //LOG.info("Y");
        //matrixOperator.disp(y);
        //debug = true;
        if(debug){
            LOG.info("Kernel Matrix");
            matrixOperator.disp(k);
        }

        while (passes < max_passes) { //start while

            int num_changed_alphas = 0;
            debug = true;
            if(debug){
                LOG.info("Passes : " + passes + "/" + max_passes);
            }

            for (int i = 0; i < m; i++) {
                Matrix k_i = matrixOperator.getColumnData(k, i);
                Matrix op1 = matrixOperator.dotMultiply(alphas, y);
                Matrix op2 = matrixOperator.dotMultiply(op1, k_i);
                Matrix sum = matrixOperator.sum(op2);
                double itr_value = b + sum.getMatDouble()[0][0] - y.getMatDouble()[i][0];
                e.getMatDouble()[i][0] = itr_value;

                debug = false;
                if(debug){
                    LOG.info("Debug Mode");
                    LOG.info("Itr value : " + itr_value);

                }

                //input.hasNext();
                //Y(i) * E(i) multiplication

                double y_e = y.getMatDouble()[i][0] * e.getMatDouble()[i][0];
                double alphas_i = alphas.getMatDouble()[i][0];

                Random random = new Random();

                if ((y_e < -tol && alphas_i < C) || (y_e > tol && alphas_i > 0)) {

                    double rand = Math.random() * 0.98; //0.98 used to make sure the j won't cause an array index out of bounds condition
                    int j = (int) Math.ceil(m * rand);

                    while (j == i) {
                        rand = Math.random() * 0.98;
                        j = (int) Math.ceil(m * rand);

                    }
                    if(k.getColumns()==j){
                        j=j-1;
                    }

                    Matrix k_j = matrixOperator.getColumnData(k, j);
                    Matrix op1j = matrixOperator.dotMultiply(alphas, y);
                    Matrix op2j = matrixOperator.dotMultiply(op1j, k_j);
                    Matrix sumj = matrixOperator.sum(op2j);
                    double itr_valuej = b + sumj.getMatDouble()[0][0] - y.getMatDouble()[j][0];
                    e.getMatDouble()[j][0] = itr_valuej;

                    //old alpha values
                    double alpha_i_old = alphas.getMatDouble()[i][0];
                    double alpha_j_old = alphas.getMatDouble()[j][0];
                    //LOG.info(alpha_i_old+" / "+alpha_j_old);

                    //compute L and H
                    if (y.getMatDouble()[i][0] == y.getMatDouble()[j][0]) {

                        l = Math.max(0, alphas.getMatDouble()[j][0] + alphas.getMatDouble()[i][0] - C);
                        h = Math.min(C, alphas.getMatDouble()[j][0] + alphas.getMatDouble()[i][0]);

                    } else {

                        l = Math.max(0, alphas.getMatDouble()[j][0] - alphas.getMatDouble()[i][0]);
                        h = Math.min(C, C + alphas.getMatDouble()[j][0] - alphas.getMatDouble()[i][0]);

                    }

                    if (l == h) {
                        continue;
                    }

                    //calculate ETA

                    eta = 2.00 * k.getMatDouble()[i][j] - k.getMatDouble()[i][i] - k.getMatDouble()[j][j];

                    if (eta >= 0) {
                        debug = true;
                        if(debug){
                            LOG.info("Eta : " + eta);
                        }
                        continue;
                    }

                    //compute values for alpha_j
                    alphas.getMatDouble()[j][0] = alphas.getMatDouble()[j][0] - (y.getMatDouble()[j][0] * (e.getMatDouble()[i][0] - e.getMatDouble()[j][0])) / eta;

                    alphas.getMatDouble()[j][0] = Math.min(h, alphas.getMatDouble()[j][0]);
                    alphas.getMatDouble()[j][0] = Math.max(l, alphas.getMatDouble()[j][0]);

                    double diff = Math.abs(alphas.getMatDouble()[j][0] - alpha_j_old);
                    if (diff < tol) {
                        if(debug){
                            LOG.info("Diff : " + diff + ", tol : " +tol);
                        }
                        alphas.getMatDouble()[j][0] = alpha_j_old;
                        continue;
                    }
                    //values of alpha using i'
                    alphas.getMatDouble()[i][0] = alphas.getMatDouble()[i][0] + y.getMatDouble()[i][0] * y.getMatDouble()[j][0] * (alpha_j_old - alphas.getMatDouble()[j][0]);
                    //b1 and b2 value computing
                    b1 = b - e.getMatDouble()[i][0] - y.getMatDouble()[i][0] * (alphas.getMatDouble()[i][0] - alpha_i_old) * k.getMatDouble()[i][j] - y.getMatDouble()[j][0] * (alphas.getMatDouble()[j][0] - alpha_j_old) * k.getMatDouble()[i][j];
                    b2 = b - e.getMatDouble()[j][0] - y.getMatDouble()[i][0] * (alphas.getMatDouble()[i][0] - alpha_i_old) * k.getMatDouble()[i][j] - y.getMatDouble()[j][0] * (alphas.getMatDouble()[j][0] - alpha_j_old) * k.getMatDouble()[i][j];

                    if (0 < alphas.getMatDouble()[i][0] && alphas.getMatDouble()[i][0] < C) {

                        b = b1;
                    } else if (0 < alphas.getMatDouble()[j][0] && alphas.getMatDouble()[j][0] < C) {
                        b = b2;
                    } else {
                        b = (b1 + b2) / 2.00;
                    }
                    num_changed_alphas = num_changed_alphas + 1;
                }


            }
            if (num_changed_alphas == 0) {
                passes = passes + 1;
            } else {
                passes = 0;
            }

        }//end big while

        //LOG.info("Training Completed...");
        //matrixOperator.disp(alphas);
        Matrix alphaPositive = matrixOperator.setValueByBoundry(alphas, ">", 0);
        Matrix cleanAlphas = matrixOperator.getValueMatchingBoundary(alphas, alphaPositive);
        //LOG.info("Clean ALPHA POSITIVE");
        //matrixOperator.disp(alphaPositive);
        //LOG.info("CLEAN ALPHA POSITIVE");
        //LOG.info("b1,b2 : " + b1 + "," + b2);
        //LOG.info("b :" + b);

        //((alphas.*Y)'*X)'
        Matrix alphas_y = matrixOperator.dotMultiply(alphas, y);
        Matrix alphas_y_trans = matrixOperator.transpose(alphas_y);
        Matrix wt = matrixOperator.product(alphas_y_trans, x, "CROSS");
        Matrix w = matrixOperator.transpose(wt);
        //matrixOperator.disp(w);

        //Matrix idx = matrixOperator.setValueByBoundry(alphas,">",0.0);
        //Scanner input = new Scanner(System.in);
        //input.hasNext();

        //setting up the model
        Model model = null;
        if (x != null && y != null && alphas != null && w != null) {
            model = new Model(x, y, b, alphas, w,kernel, info);
        }

        return model;

    }

    public Model svmTrain(Matrix x, Matrix y, String kernel, String modelType) {


        ArrayList<Integer> testVal = new ArrayList<>();

        int m = x.getRows();
        int n = x.getColumns();
        Matrix alphas = new Matrix(m, 1, "DOUBLE");

        for (int i = 0; i < alphas.getRows(); i++) {
            for (int j = 0; j < alphas.getColumns(); j++) {

                alphas.getMatDouble()[i][j] = 0.0;

            }
        }
        double tol = 0.001; //tolerance
        double b = 0;
        double b1 = 0;
        double b2 = 0;
        Matrix e = new Matrix(m, 1, "DOUBLE");
        int passes = 0;
        double eta = 0.0;
        double l = 0;
        double h = 0;
        double C = 1;
        int max_passes = 12;

        //moving on with the linear kernel
        Matrix k = null;
        try{
            LOG.info("Matrix Dimension : "+m+","+m);
            k = new Matrix(m, m, "DOUBLE");
        }catch (OutOfMemoryError ex){
            LOG.info("Exception " + ex.getMessage());
        }

        MatrixOperator matrixOperator = new MatrixOperator();
        Matrix x_dash = matrixOperator.transpose(x);
        long time_kernel_start = System.currentTimeMillis();
        k = matrixOperator.product(x, x_dash, "CROSS");
        long time_kernel_end = System.currentTimeMillis();
        LOG.info("====================================================");
        LOG.info("Kernel Matrix Calculation : " + (time_kernel_end - time_kernel_start)/1000.0);
        LOG.info("====================================================");
        this.info += "Kernel Matrix Calculation : " + (time_kernel_end - time_kernel_start)/1000.0 + " s";
        //LOG.info("X");
        //matrixOperator.disp(x);
        //LOG.info("Y");
        //matrixOperator.disp(y);
        //debug = true;
        if(debug){
            LOG.info("Kernel Matrix");
            matrixOperator.disp(k);
        }

        while (passes < max_passes) { //start while

            int num_changed_alphas = 0;
            debug = true;
            if(debug){
                LOG.info("Passes : " + passes + "/" + max_passes);
            }

            for (int i = 0; i < m; i++) {
                Matrix k_i = matrixOperator.getColumnData(k, i);
                Matrix op1 = matrixOperator.dotMultiply(alphas, y);
                Matrix op2 = matrixOperator.dotMultiply(op1, k_i);
                Matrix sum = matrixOperator.sum(op2);
                double itr_value = b + sum.getMatDouble()[0][0] - y.getMatDouble()[i][0];
                e.getMatDouble()[i][0] = itr_value;

                debug = false;
                if(debug){
                    LOG.info("Debug Mode");
                    LOG.info("Itr value : " + itr_value);

                }

                //input.hasNext();
                //Y(i) * E(i) multiplication

                double y_e = y.getMatDouble()[i][0] * e.getMatDouble()[i][0];
                double alphas_i = alphas.getMatDouble()[i][0];

                Random random = new Random();

                if ((y_e < -tol && alphas_i < C) || (y_e > tol && alphas_i > 0)) {

                    double rand = Math.random() * 0.98; //0.98 used to make sure the j won't cause an array index out of bounds condition
                    int j = (int) Math.ceil(m * rand);

                    while (j == i) {
                        rand = Math.random() * 0.98;
                        j = (int) Math.ceil(m * rand);

                    }
                    if(k.getColumns()==j){
                        j=j-1;
                    }

                    Matrix k_j = matrixOperator.getColumnData(k, j);
                    Matrix op1j = matrixOperator.dotMultiply(alphas, y);
                    Matrix op2j = matrixOperator.dotMultiply(op1j, k_j);
                    Matrix sumj = matrixOperator.sum(op2j);
                    double itr_valuej = b + sumj.getMatDouble()[0][0] - y.getMatDouble()[j][0];
                    e.getMatDouble()[j][0] = itr_valuej;

                    //old alpha values
                    double alpha_i_old = alphas.getMatDouble()[i][0];
                    double alpha_j_old = alphas.getMatDouble()[j][0];
                    //LOG.info(alpha_i_old+" / "+alpha_j_old);

                    //compute L and H
                    if (y.getMatDouble()[i][0] == y.getMatDouble()[j][0]) {

                        l = Math.max(0, alphas.getMatDouble()[j][0] + alphas.getMatDouble()[i][0] - C);
                        h = Math.min(C, alphas.getMatDouble()[j][0] + alphas.getMatDouble()[i][0]);

                    } else {

                        l = Math.max(0, alphas.getMatDouble()[j][0] - alphas.getMatDouble()[i][0]);
                        h = Math.min(C, C + alphas.getMatDouble()[j][0] - alphas.getMatDouble()[i][0]);

                    }

                    if (l == h) {
                        continue;
                    }

                    //calculate ETA

                    eta = 2.00 * k.getMatDouble()[i][j] - k.getMatDouble()[i][i] - k.getMatDouble()[j][j];

                    if (eta >= 0) {
                        debug = true;
                        if(debug){
                            LOG.info("Eta : " + eta);
                        }
                        continue;
                    }

                    //compute values for alpha_j
                    alphas.getMatDouble()[j][0] = alphas.getMatDouble()[j][0] - (y.getMatDouble()[j][0] * (e.getMatDouble()[i][0] - e.getMatDouble()[j][0])) / eta;

                    alphas.getMatDouble()[j][0] = Math.min(h, alphas.getMatDouble()[j][0]);
                    alphas.getMatDouble()[j][0] = Math.max(l, alphas.getMatDouble()[j][0]);

                    double diff = Math.abs(alphas.getMatDouble()[j][0] - alpha_j_old);
                    if (diff < tol) {
                        if(debug){
                            LOG.info("Diff : " + diff + ", tol : " +tol);
                        }
                        alphas.getMatDouble()[j][0] = alpha_j_old;
                        continue;
                    }
                    //values of alpha using i'
                    alphas.getMatDouble()[i][0] = alphas.getMatDouble()[i][0] + y.getMatDouble()[i][0] * y.getMatDouble()[j][0] * (alpha_j_old - alphas.getMatDouble()[j][0]);
                    //b1 and b2 value computing
                    b1 = b - e.getMatDouble()[i][0] - y.getMatDouble()[i][0] * (alphas.getMatDouble()[i][0] - alpha_i_old) * k.getMatDouble()[i][j] - y.getMatDouble()[j][0] * (alphas.getMatDouble()[j][0] - alpha_j_old) * k.getMatDouble()[i][j];
                    b2 = b - e.getMatDouble()[j][0] - y.getMatDouble()[i][0] * (alphas.getMatDouble()[i][0] - alpha_i_old) * k.getMatDouble()[i][j] - y.getMatDouble()[j][0] * (alphas.getMatDouble()[j][0] - alpha_j_old) * k.getMatDouble()[i][j];

                    if (0 < alphas.getMatDouble()[i][0] && alphas.getMatDouble()[i][0] < C) {

                        b = b1;
                    } else if (0 < alphas.getMatDouble()[j][0] && alphas.getMatDouble()[j][0] < C) {
                        b = b2;
                    } else {
                        b = (b1 + b2) / 2.00;
                    }
                    num_changed_alphas = num_changed_alphas + 1;
                }


            }
            if (num_changed_alphas == 0) {
                passes = passes + 1;
            } else {
                passes = 0;
            }

        }//end big while

        //LOG.info("Training Completed...");
        //matrixOperator.disp(alphas);
        Matrix alphaPositive = matrixOperator.setValueByBoundry(alphas, ">", 0);
        Matrix cleanAlphas = matrixOperator.getValueMatchingBoundary(alphas, alphaPositive);
        //LOG.info("Clean ALPHA POSITIVE");
        //matrixOperator.disp(alphaPositive);
        //LOG.info("CLEAN ALPHA POSITIVE");
        //LOG.info("b1,b2 : " + b1 + "," + b2);
        //LOG.info("b :" + b);

        //((alphas.*Y)'*X)'
        Matrix alphas_y = matrixOperator.dotMultiply(alphas, y);
        Matrix alphas_y_trans = matrixOperator.transpose(alphas_y);
        Matrix wt = matrixOperator.product(alphas_y_trans, x, "CROSS");
        Matrix w = matrixOperator.transpose(wt);
        //matrixOperator.disp(w);
        boolean checkSum = matrixOperator.checkSumWeights(w);
        if(checkSum){
            if(modelType.equals(Constant.MODEL_TYPE_POSITIVE)){
                w = matrixOperator.transpose(this.w) ;
            }
            if(modelType.equals(Constant.MODEL_TYPE_NEGATIVE)){
                w = matrixOperator.transpose(this.w) ;
            }
        }
        matrixOperator.sout(w);
        //Matrix idx = matrixOperator.setValueByBoundry(alphas,">",0.0);
        //Scanner input = new Scanner(System.in);
        //input.hasNext();

        //setting up the model
        Model model = null;
        if (x != null && y != null && alphas != null && w != null) {
            model = new Model(x, y, b, alphas, w,kernel, info);
        }

        return model;

    }
}
