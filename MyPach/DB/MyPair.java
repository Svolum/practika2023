package MyPach.DB;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPair {
    @JsonProperty("id")
    private int id;
    @JsonProperty("review")
    private String review;

    public MyPair(){

    }
    public MyPair(int id, String review) {
        this.id = id;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
