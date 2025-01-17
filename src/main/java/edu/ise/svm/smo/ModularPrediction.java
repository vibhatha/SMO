package edu.ise.svm.smo;

        import edu.ise.svm.Constants.Constant;
        import edu.ise.svm.entities.Model;
        import edu.ise.svm.matrix.Matrix;
        import edu.ise.svm.matrix.MatrixOperator;

        import java.util.logging.Logger;

/**
 * Created by vlabeyko on 10/21/2016.
 */

/**
 * Predict class manages the prediction of a trained dataset along with test data
 * This class allows to use different kernels in order to get predictions. But the results depending
 * on the training data set and the kernel used in the training process. *
 */

public class ModularPrediction{

    static{
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
    }

    public final static Logger LOG = Logger.getLogger(Predict.class.getName());
    private Model model;
    private Matrix x;


    public ModularPrediction(Model model, Matrix x) {
        this.model = model;
        this.x = x;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Matrix getX() {
        return x;
    }

    public void setX(Matrix x) {
        this.x = x;
    }


    public Matrix predict() {

        Matrix predictions = null;
        int m = 0; //number of samples has to be predicted

        Model currentModel = getModel();
        Matrix test_data = getX();
        //validating null data
        if (currentModel == null || test_data == null) {
            return null;
        } else {

            //checking the appropriate Kernel function for the prediction
            //start of switch statement to get the kernel
            String kernel = null;
            switch (model.getKernel()) {
                case Constant.LINEAR://checking the linear condition
                    predictions = getLinearKernelPredictions(currentModel, test_data);
                    break;

                case Constant.RBF://checking the RBF condition
                    predictions = getRBFKernelPredictions(currentModel, test_data);
                    break;

                case Constant.GAUSSIAN://checking the gaussian condition
                    predictions = getGaussianKernelPredictions(currentModel, test_data);
                    break;

                case Constant.POLYNOMIAL://checking the gaussian condition
                    predictions = getPolynomialPredictions(currentModel, test_data);
                    break;

                default://the default kernel function for a dataset with no specific kernel setup is the gaussian kernel //this can be different based on the programmers perspective
                    predictions = getGaussianKernelPredictions(currentModel, test_data);

            }
            //end of switch statement


            return predictions;
        }

    }

    private Matrix getPolynomialPredictions(Model currentModel, Matrix test_data) {
        Matrix polynomialPredictions = null;
        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        polynomialPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        polynomialPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        polynomialPredictions = getClassesForTwoClass(op2);
        //matrixOperator.disp(linearPredictions);


        return polynomialPredictions;
    }

    //linear kernel based predictions
    public Matrix getLinearKernelPredictions(Model model, Matrix x) {
        Matrix linearPredictions = null;
        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        linearPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        linearPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        linearPredictions = getClassesForTwoClass(op2);
        //matrixOperator.disp(linearPredictions);


        return linearPredictions;

    }

    //rbf kernel based predictions
    public Matrix getRBFKernelPredictions(Model model, Matrix x) {
        Matrix rbfPredictions = null;

        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        rbfPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        rbfPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        rbfPredictions = getClassesForTwoClass(op2);
        //matrixOperator.disp(linearPredictions);


        return rbfPredictions;

    }

    //gaussian kernel based predictions
    public Matrix getGaussianKernelPredictions(Model model, Matrix x) {
        Matrix gaussianPredictions = null;

        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        gaussianPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        gaussianPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        gaussianPredictions = getClassesForTwoClass(op2);

        return gaussianPredictions;

    }


    public Matrix predict(String modelType) {

        Matrix predictions = null;
        int m = 0; //number of samples has to be predicted

        Model currentModel = getModel();
        Matrix test_data = getX();
        //validating null data
        if (currentModel == null || test_data == null) {
            return null;
        } else {

            //checking the appropriate Kernel function for the prediction
            //start of switch statement to get the kernel
            String kernel = null;
            switch (model.getKernel()) {

                case Constant.LINEAR://checking the linear condition
                    predictions = getLinearKernelPredictions(currentModel, test_data, modelType);
                    break;

                case Constant.RBF://checking the RBF condition
                    predictions = getRBFKernelPredictions(currentModel, test_data, modelType);
                    break;

                case Constant.GAUSSIAN://checking the gaussian condition
                    predictions = getGaussianKernelPredictions(currentModel, test_data, modelType);
                    break;

                default://the default kernel function for a dataset with no specific kernel setup is the gaussian kernel //this can be different based on the programmers perspective
                    predictions = getGaussianKernelPredictions(currentModel, test_data, modelType);

            }
            //end of switch statement


            return predictions;
        }

    }


    public Matrix getLinearKernelPredictions(Model model, Matrix x, String modelType) {
        Matrix linearPredictions = null;
        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        linearPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");

        //CEHCKSUM OF OP1
        boolean wightUpdateLess = checkSumWeights(op1);
        //LOG.info("Modular Prediction : op1 status "+ wightUpdateLess);
        //LOG.info("MODEL Type : " + modelType);
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        linearPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        if(wightUpdateLess==true){
            linearPredictions = getClassesForTwoClass(op2, modelType);
        }else{
            linearPredictions = getClassesForTwoClass(op2);
        }


        //matrixOperator.disp(linearPredictions);


        return linearPredictions;

    }



    public boolean checkSumWeights(Matrix op1){
        boolean status = true;
        double [] [] d = op1.getMatDouble();
        double sum = 0.0;
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d[0].length; j++) {
                sum+= d[i][j];
            }
        }
        if(sum>0){
            status = false;
        }
        return status;
    }


    //rbf kernel based predictions
    public Matrix getRBFKernelPredictions(Model model, Matrix x, String modelType) {
        Matrix rbfPredictions = null;

        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        rbfPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");

        //CEHCKSUM OF OP1
        boolean wightUpdateLess = checkSumWeights(op1);
        //LOG.info("Modular Prediction : op1 status "+ wightUpdateLess);
        //LOG.info("MODEL Type : " + modelType);
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        rbfPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        if(wightUpdateLess==true){
            rbfPredictions = getClassesForTwoClass(op2, modelType);
        }else{
            rbfPredictions = getClassesForTwoClass(op2);
        }


        //matrixOperator.disp(linearPredictions);





        return rbfPredictions;

    }

    //gaussian kernel based predictions
    public Matrix getGaussianKernelPredictions(Model model, Matrix x, String modelType) {
        Matrix gaussianPredictions = null;

        MatrixOperator matrixOperator = new MatrixOperator();
        int m = x.getRows();
        gaussianPredictions = new Matrix(m, 1, "DOUBLE");
        Matrix tmp = new Matrix(m, 1, "DOUBLE");

        //the y=mx+C relation ship for a given coordinates is considered here
        //the m*X multiplication is done and assigned to op1 matrix
        Matrix op1 = matrixOperator.product(x,model.getW(),"CROSS");

        //CEHCKSUM OF OP1
        boolean wightUpdateLess = checkSumWeights(op1);
        //LOG.info("Modular Prediction : op1 status "+ wightUpdateLess);
        //LOG.info("MODEL Type : " + modelType);
        //LOG.info("op1");
        //matrixOperator.disp(op1);
        //the matrix addition is done based on the definitions in the matrix package
        Matrix b = new Matrix(m,1,"DOUBLE");

        for (int i = 0; i < m; i++) {
            b.getMatDouble()[i][0]= model.getB();
        }

        //LOG.info("b");
        //matrixOperator.disp(b);
        //mx+C addition is done in this stage
        Matrix op2 = matrixOperator.add(op1,b);

        //LOG.info("op2");
        //matrixOperator.disp(op2);

        //the classes identification is done here
        gaussianPredictions = new Matrix(op2.getRows(),op2.getColumns(),op2.getType());

        if(wightUpdateLess==true){
            gaussianPredictions = getClassesForTwoClass(op2, modelType);
        }else{
            gaussianPredictions = getClassesForTwoClass(op2);
        }


        //matrixOperator.disp(linearPredictions);



        return gaussianPredictions;

    }

    //this class converts the predictions scores into classes
    //this class is only relevant for the two class classification
    public Matrix getClassesForTwoClass(Matrix predictions){

        Matrix classes = null;

        if(predictions!=null){
            //basically in the two class classification there are only two values
            //as a convention the 1 and -1 are considered as the classes
            //dynamically the classes can be assigned by uniquely identifying the values in the y matrix
            //in this stage of development it is not yet considered.
            //this development is based on the two class classification problem
            classes = new Matrix(predictions.getRows(),predictions.getColumns(),predictions.getType());//the type is most of the time double, rarely becomes int

            for (int i = 0; i < predictions.getRows(); i++) {
                for (int j = 0; j < predictions.getColumns(); j++) {
                    if(predictions.getMatDouble()[i][j]>=0){ //when the prediction value is greater than 0 the class is considered as 1 and it can be any number but basically 1 and -1 are used here
                        classes.getMatDouble()[i][j]=1;
                    }else if(predictions.getMatDouble()[i][j]<0){//prediction value lesser than 0 can be considered as the other class
                        classes.getMatDouble()[i][j]=-1;
                    }
                }
            }
        }else{
            classes=null;
        }



        return classes;
    }

    public Matrix getClassesForTwoClass(Matrix predictions, String modelType){
        Matrix classes = null;
        if(predictions!=null){
            //basically in the two class classification there are only two values
            //as a convention the 1 and -1 are considered as the classes
            //dynamically the classes can be assigned by uniquely identifying the values in the y matrix
            //in this stage of development it is not yet considered.
            //this development is based on the two class classification problem
            classes = new Matrix(predictions.getRows(),predictions.getColumns(),predictions.getType());//the type is most of the time double, rarely becomes int
            if(modelType==Constant.MODEL_TYPE_POSITIVE){
                for (int i = 0; i < predictions.getRows(); i++) {
                    for (int j = 0; j < predictions.getColumns(); j++) {
                        if(predictions.getMatDouble()[i][j]>=0){ //when the prediction value is greater than 0 the class is considered as 1 and it can be any number but basically 1 and -1 are used here
                            classes.getMatDouble()[i][j]=1;
                        }else if(predictions.getMatDouble()[i][j]<0){//prediction value lesser than 0 can be considered as the other class
                            classes.getMatDouble()[i][j]=-1;
                        }
                    }
                }
            }else if(modelType.equals(Constant.MODEL_TYPE_NEGATIVE)){//boundary checks for positive and negative models must be opposite
                for (int i = 0; i < predictions.getRows(); i++) {
                    for (int j = 0; j < predictions.getColumns(); j++) {
                        if(predictions.getMatDouble()[i][j]>=0){ //when the prediction value is greater than 0 the class is considered as 1 and it can be any number but basically 1 and -1 are used here
                            classes.getMatDouble()[i][j]=1;
                        }else if(predictions.getMatDouble()[i][j]<0){//prediction value lesser than 0 can be considered as the other class
                            classes.getMatDouble()[i][j]=-1;
                        }
                    }
                }

            }else if(modelType==Constant.MODEL_TYPE_ZERO){

            }

        }else{
            classes=null;
        }



        return classes;
    }



    public static double getAccuracy(Double [] testData, double [] predictData){
        int matchcount = 0;
        int totalCount = predictData.length;
        for (int i = 0; i < testData.length; i++) {
            if(testData[i].doubleValue()==predictData[i]){
                matchcount++;
            }
        }

        double accuracy = (double) matchcount/totalCount*100.0;

        return accuracy;
    }


    //this class converts the predictions scores into classes
    //this class is only relevant for the two class classification
    public Matrix getClassesForMultiClass(Matrix predictions){

        Matrix classes = null;

        return classes;
    }

}




