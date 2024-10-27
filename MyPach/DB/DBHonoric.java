package MyPach.DB;

import MyPach.EndData;
import MyPach.FileWork.FileReport;
import MyPach.JSON.JsonReport;

public class DBHonoric {
    private int id;
    private int prev_id;
    private String review;
    private EndData endDataFall;
    private EndData endDataSpring;
    private JsonReport jsonReport;
    private FileReport fileReport;
    public DBHonoric(int id, int prev_id, String review, EndData endDataFall, EndData endDataSpring) {
        this.id = id;
        this.prev_id = prev_id;
        this.review = review;
        this.endDataFall = endDataFall;
        this.endDataSpring = endDataSpring;
        /* Это класс, каждый объкет которого является результатом подбора проекта(из ярмарки проектов) из отчета
        (файл формата doc, docx, pdf) преподователя(Supervisor) и проекта из базы данных ярмарки проектов
        - Кроме необходимых полей id и review имеет дополнительные поля, которые используются для отладки
         */
    }
    public DBHonoric(int id, int prev_id, String review, JsonReport jsonReport, FileReport fileReport) {
        this.id = id;
        this.prev_id = prev_id;
        this.review = review;
        this.jsonReport = jsonReport;
        this.fileReport = fileReport;
    }

    // Getters & Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPrev_id() {
        return prev_id;
    }
    public void setPrev_id(int prev_id) {
        this.prev_id = prev_id;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
    public EndData getEndDataFall() {
        return endDataFall;
    }
    public void setEndDataFall(EndData endDataFall) {
        this.endDataFall = endDataFall;
    }
    public EndData getEndDataSpring() {
        return endDataSpring;
    }
    public void setEndDataSpring(EndData endDataSpring) {
        this.endDataSpring = endDataSpring;
    }
    public JsonReport getJsonReport() {
        return jsonReport;
    }
    public void setJsonReport(JsonReport jsonReport) {
        this.jsonReport = jsonReport;
    }
    public FileReport getFileReport() {
        return fileReport;
    }
    public void setFileReport(FileReport fileReport) {
        this.fileReport = fileReport;
    }
}
