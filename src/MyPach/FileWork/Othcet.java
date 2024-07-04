package MyPach.FileWork;

public class Othcet {
    private String title;
    private String fio;
    private String email;
    private String review;
    public Othcet(){

    }
    public Othcet(String title, String fio, String email) {
        this.title = title;
        this.fio = fio;
        this.email = email;
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
    public String getReview() {
        return review;
    }
    public void setReview(String review) {
        this.review = review;
    }
}
