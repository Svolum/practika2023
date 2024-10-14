package MyPach.DB;

public class MyPair {
    private int id;
    private String review;

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
