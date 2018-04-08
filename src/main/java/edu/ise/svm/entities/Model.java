package edu.ise.svm.entities;

import com.sun.javafx.sg.prism.NGShape;
import edu.ise.svm.Constants.Constant;
import edu.ise.svm.matrix.Matrix;
import edu.ise.svm.matrix.MatrixOperator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class Model {

    static {
        System.setProperty(Constant.LOG_TYPE, Constant.LOG_FORMAT);
    }

    public final static Logger LOG = Logger.getLogger(Model.class.getName());

    private Matrix x;
    private Matrix y;
    private double b;
    private Matrix alphas;
    private Matrix w;
    private String kernel;
    private String info;

    public Model(){

    }

    public Model(Matrix x, Matrix y, double b, Matrix alphas, Matrix w, String kernel) {
        this.x = x;
        this.y = y;
        this.b = b;
        this.alphas = alphas;
        this.w = w;
        this.kernel = kernel;
    }

    public Model(Matrix x, Matrix y, double b, Matrix alphas, Matrix w, String kernel, String info) {
        this.x = x;
        this.y = y;
        this.b = b;
        this.alphas = alphas;
        this.w = w;
        this.kernel = kernel;
        this.info = info;
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

    public String getInfo() { return info;}

    public void setInfo(String info) { this.info = info; }

    public void saveModel(String filepath) throws IOException {

        Path path = Paths.get(filepath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            if (this.getKernel().equals(Constant.LINEAR)) {
                writer.write("<model>");
                writer.newLine();
                writer.write(this.getKernel());
                writer.newLine();
                writer.write("</model>");
                writer.newLine();
                writer.write("<W>");
                writer.newLine();
                Matrix w = this.getW();
                //new MatrixOperator().sout(w);
                if (w != null && w.getRows() > 0) {
                    double[][] wArr = w.getMatDouble();
                    int rowWArr = wArr.length;
                    int colWarr = wArr[0].length;
                    for (int i = 0; i < rowWArr; i++) {
                        for (int j = 0; j < colWarr; j++) {
                            if (colWarr > 1) {
                                if (j < colWarr - 1) {
                                    writer.write(String.valueOf(wArr[i][j]) + ",");
                                }
                                writer.write(String.valueOf(wArr[i][j]));
                            }
                            writer.write(String.valueOf(wArr[i][j]) + "");
                        }
                        writer.newLine();
                    }
                }
                writer.write("</W>");
                writer.newLine();
                writer.write("<b>");
                writer.newLine();
                double b = this.getB();
                writer.write(String.valueOf(b));
                writer.newLine();
                writer.write("</b>");
                writer.newLine();
                writer.write("<info>");
                writer.newLine();
                writer.write(info);
                writer.newLine();
                writer.write("</info>");
                writer.newLine();
                //LOG.info("Model saved : " + filepath);
            }

            if (this.getKernel().equals(Constant.GAUSSIAN)) {
                writer.write("<model>");
                writer.newLine();
                writer.write(this.getKernel());
                writer.newLine();
                writer.write("</model>");
                writer.newLine();
                writer.write("<W>");
                writer.newLine();
                Matrix w = this.getW();
                //new MatrixOperator().sout(w);
                if (w != null && w.getRows() > 0) {
                    double[][] wArr = w.getMatDouble();
                    int rowWArr = wArr.length;
                    int colWarr = wArr[0].length;
                    for (int i = 0; i < rowWArr; i++) {
                        for (int j = 0; j < colWarr; j++) {
                            if (colWarr > 1) {
                                if (j < colWarr - 1) {
                                    writer.write(String.valueOf(wArr[i][j]) + ",");
                                }
                                writer.write(String.valueOf(wArr[i][j]));
                            }
                            writer.write(String.valueOf(wArr[i][j]) + "");
                        }
                        writer.newLine();
                    }
                }
                writer.write("</W>");
                writer.newLine();
                writer.write("<b>");
                writer.newLine();
                double b = this.getB();
                writer.write(String.valueOf(b));
                writer.newLine();
                writer.write("</b>");
                writer.newLine();
                writer.write("<info>");
                writer.newLine();
                writer.write(info);
                writer.newLine();
                writer.write("</info>");
                writer.newLine();
               // LOG.info("Model saved : " + filepath);
            }

            if (this.getKernel().equals(Constant.POLYNOMIAL)) {
                writer.write("<model>");
                writer.newLine();
                writer.write(this.getKernel());
                writer.newLine();
                writer.write("</model>");
                writer.newLine();
                writer.write("<W>");
                writer.newLine();
                Matrix w = this.getW();
                //new MatrixOperator().sout(w);
                if (w != null && w.getRows() > 0) {
                    double[][] wArr = w.getMatDouble();
                    int rowWArr = wArr.length;
                    int colWarr = wArr[0].length;
                    for (int i = 0; i < rowWArr; i++) {
                        for (int j = 0; j < colWarr; j++) {
                            if (colWarr > 1) {
                                if (j < colWarr - 1) {
                                    writer.write(String.valueOf(wArr[i][j]) + ",");
                                }
                                writer.write(String.valueOf(wArr[i][j]));
                            }
                            writer.write(String.valueOf(wArr[i][j]) + "");
                        }
                        writer.newLine();
                    }
                }
                writer.write("</W>");
                writer.newLine();
                writer.write("<b>");
                writer.newLine();
                double b = this.getB();
                writer.write(String.valueOf(b));
                writer.newLine();
                writer.write("</b>");
                writer.newLine();
                writer.write("<info>");
                writer.newLine();
                writer.write(info);
                writer.newLine();
                writer.write("</info>");
                writer.newLine();
               // LOG.info("Model saved : " + filepath);
            }
        }
    }

    public static Model loadModel(String modelpath) throws IOException{
        Model model = new Model();
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(modelpath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

        }
        //LOG.info("Processing Model ");
        int listSize = lines.size();
        if(listSize>0){
           // LOG.info("List Size : " + listSize);
        int wStart = -1;
        int wEnd = -1;
        int infoStart = -1;
        int infoEnd = -1;
            for (int i = 0; i < listSize; i++) {
                if(lines.get(i).equals("<model>")){
                    model.setKernel(lines.get(i+1));
                }
                if(lines.get(i).equals("<W>")){
                    wStart = i+1;
                }
                if(lines.get(i).equals("</W>")){
                    wEnd = i-1;
                }
                if(lines.get(i).equals("<b>")){
                    double b = Double.parseDouble(lines.get(i+1));
                    model.setB(b) ;
                }
                if(lines.get(i).equals("<info>")){
                    infoStart = i+1;
                }
                if(lines.get(i).equals("</info>")){
                    infoEnd = i-1;
                }
            }
            if(wStart != -1 && wEnd != -1){
                List<String> wList =  lines.subList(wStart, wEnd+1);
                int wlistSize = wList.size();
                double [][] wArr = new double[1][wlistSize];
                // LOG.info("wArr size : " + wlistSize);
                for (int j = 0; j < wlistSize; j++) {
                    wArr[0][j] = Double.parseDouble(wList.get(j));
                    // LOG.info(""+wArr[0][j]);
                }
                Matrix w = new Matrix(1, wArr[0].length, "DOUBLE");
                w.setMatDouble(wArr);
                MatrixOperator op = new MatrixOperator();
                w = op.transpose(w);
                model.setW(w);

            }

            if(infoStart != -1 && infoEnd != -1){
                List<String> infoList =  lines.subList(infoStart, infoEnd+1);
                String info = "";
                int infoListSize = infoList.size();
                int j=0;
                for (String s : infoList) {
                    if(j==infoListSize-1){
                        info+=s;
                    }else{
                        info += s+"\n";
                    }
                    j++;
                }
                model.setInfo(info);
            }
        }
        //model.saveModel("model/dummy");
        return model;
    }

}
