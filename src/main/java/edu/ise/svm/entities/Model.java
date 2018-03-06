package edu.ise.svm.entities;

import edu.ise.svm.Constants.Constant;
import edu.ise.svm.matrix.Matrix;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Model {

    static{
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    public final static Logger LOG = Logger.getLogger(Model.class.getName());

    private Matrix x;
    private Matrix y;
    private double b;
    private Matrix alphas;
    private Matrix w;
    private String kernel;

    public Model(Matrix x, Matrix y, double b, Matrix alphas, Matrix w, String kernel) {
        this.x = x;
        this.y = y;
        this.b = b;
        this.alphas = alphas;
        this.w = w;
        this.kernel = kernel;
    }

    public Matrix getX() {
        return x;
    }

    public void setX(Matrix x) {
        this.x = x;
    }

    public Matrix getY() {
        return y;
    }

    public void setY(Matrix y) {
        this.y = y;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public Matrix getAlphas() {
        return alphas;
    }

    public void setAlphas(Matrix alphas) {
        this.alphas = alphas;
    }

    public Matrix getW() {
        return w;
    }

    public void setW(Matrix w) {
        this.w = w;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public void saveModel(String filepath) throws IOException{

        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            if(this.getKernel().equals(Constant.LINEAR)){
                writer.write("<model>");
                writer.write(this.getKernel());
                writer.newLine();
                writer.write("</model>");
                writer.newLine();
                writer.write("<W>");
                writer.newLine();
                Matrix w = this.getW();
                if(w!=null && w.getRows()>0){
                    double [][] wArr = w.getMatDouble();
                    int rowWArr = wArr.length;
                    int colWarr = wArr[0].length;
                    for (int i = 0; i < rowWArr; i++) {
                        for (int j = 0; j < colWarr; j++) {
                            if(colWarr>1){
                                if(j<colWarr-1){
                                    writer.write(String.valueOf(wArr[i][j])+",");
                                }
                                writer.write(String.valueOf(wArr[i][j]));
                            }
                            writer.write(String.valueOf(wArr[i][j])+"");
                        }
                        writer.newLine();
                    }
                }
                else{
                    writer.write("</W>");
                    writer.newLine();
                }
                writer.write("<b>");
                writer.newLine();
                double b = this.getB();
                writer.write(String.valueOf(b));
                writer.newLine();
                writer.write("</b>");
                writer.newLine();
                LOG.info("Model saved : " + filepath);
            }
        }

    }

}
