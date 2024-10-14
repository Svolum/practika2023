package MyPach;

import MyPach.FileWork.FileReport;

public class EndData {
    private int projectId;
    private int prevProjectId;
    private String review;

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private FileReport fileReport;
    public EndData(FileReport fileReport, int projectId, int prevProjectId, String review) {
        this.fileReport = fileReport;
        this.projectId = projectId;
        this.prevProjectId = prevProjectId;
        this.review = review;
    }
    public FileReport getFileReport(){
        return fileReport;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    public EndData(int projectId, int prevProjectId, String review) {
        this.projectId = projectId;
        this.prevProjectId = prevProjectId;
        this.review = review;
    }
    public int getProjectId() {
        return projectId;
    }
    public int getPrevProjectId() {
        return prevProjectId;
    }
    public String getReview() {
        return review;
    }
}