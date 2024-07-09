package MyPach;

import MyPach.FileWork.Othcet;

public class EndData {
    private int projectId;
    private int prevProjectId;
    private String review;

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    private Othcet othcet;
    public EndData(Othcet othcet, int projectId, int prevProjectId, String review) {
        this.othcet = othcet;
        this.projectId = projectId;
        this.prevProjectId = prevProjectId;
        this.review = review;
    }
    public Othcet getFileName(){
        return othcet;
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
