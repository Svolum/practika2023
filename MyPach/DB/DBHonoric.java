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
    public DBHonoric(int id, int prev_id, String review, EndData endDataFall, EndData endDataSpring) {
        this.id = id;
        this.prev_id = prev_id;
        this.review = review;
        this.endDataFall = endDataFall;
        this.endDataSpring = endDataSpring;
    }
    public DBHonoric(Honoric honoric, EndData endDataFall, EndData endDataSpring){
        this.id = honoric.getId();
        this.prev_id = honoric.getPrev_id();
        this.review = honoric.getReview();
        this.endDataFall = endDataFall;
        this.endDataSpring = endDataSpring;
    }

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
}
