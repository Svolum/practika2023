package MyPach.DB;

import MyPach.FileWork.FileReport;
import MyPach.JSON.JsonReport;

import java.io.File;

// Будущая замена класса EndData
public class Honoric {
    private int id;
    private int prev_id;
    private String review;
    private FileReport fileReport;
    private JsonReport jsonReport;
    public Honoric(int id, String review){
        this.id = id;
        this.review = review;
    }
    public Honoric(int id, int prev_id, String review){
        this.id = id;
        this.prev_id = prev_id;
        this.review = review;
    }
    public Honoric(int id, int prev_id, String review, FileReport fileReport, JsonReport jsonReport){
        this.id = id;
        this.prev_id = prev_id;
        this.review = review;
        this.fileReport = fileReport;
        this.jsonReport = jsonReport;
    }

    public int getId() {
        return id;
    }
    public int getPrev_id() {
        return prev_id;
    }
    public String getReview() {
        return review;
    }
    public FileReport getFileReport() {
        return fileReport;
    }
    public void setFileReport(FileReport fileReport) {
        this.fileReport = fileReport;
    }
    public JsonReport getJsonReport() {
        return jsonReport;
    }
    public void setJsonReport(JsonReport jsonReport) {
        this.jsonReport = jsonReport;
    }
}
