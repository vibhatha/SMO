package edu.ise.svm.io;

/**
 * Created by vlabeyko on 9/28/2016.
 */
public class CsvFile {

    private String filepath;
    private String fileType;


    public CsvFile(String filepath, String fileType) {
        this.filepath = filepath;
        this.fileType = fileType;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
