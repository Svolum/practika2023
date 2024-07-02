package MyPach.FileWork;

public class Othcet {
    private String title;
    private String fio;
    private String email;
    private String date_start;
    private String review;
    public Othcet(){

    }
    public Othcet(String title, String fio, String email, String date_start) {
        this.title = title;
        this.fio = fio;
        this.email = email;
        this.date_start = date_start;
    }

    @Override
    public String toString() {
        return title + "\n" +
                fio + "\n" +
                email + "\n" +
                //date_start + "\n" +
                review;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDate_start() {
        return date_start;
    }
    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
}
