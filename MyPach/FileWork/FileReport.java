package MyPach.FileWork;

public class FileReport {
    private String fileName;
    // Теперь отчет часть конечного объекта, поэтому хранить id необязательно
    private int project_id;
    private String title;
    private String fio;
    private String email;
    private String review;
    public FileReport(String fileName, String title, String fio, String email, String review) {
        this.fileName = fileName;
        this.title = title;
        this.fio = fio;
        this.email = email;
        this.review = review;
    }

    @Override
    public String toString() {
        return fileName + "\n" +
                project_id + "\n" +
                title + "\n" +
                fio + "\n" +
                email + "\n" +
                review;
    }
    public String toString(int a) {
        return fileName + "\n" +
                project_id + "\n" +
                title + "\n" +
                fio + "\n" +
                email;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public int getProject_id() {
        return project_id;
    }
    public void setProject_id(int project_id) {
        this.project_id = project_id;
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
