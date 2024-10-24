package MyPach.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtchetWithReview extends OtchetNeedReview {

    @JsonProperty("project.id")
    private int project_id;

    @JsonProperty("prev_project_id")
    private int prev_id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("date_start")
    private String data_start;

    @JsonProperty("date_end")
    private String data_end;

    @JsonProperty("project_supervisor.id")
    private int project_supervisor_id;

    @JsonProperty("project_id")
    private int project_duble_id;
    @JsonProperty("supervisor_id")
    private int supervisor_id;

    @JsonProperty("project_supervisor_role_id")
    private int project_supervisor_role_id;

    @JsonProperty("project_review")
    private String project_review;

    @JsonProperty("fio")
    private String fio;

    @JsonProperty("email")
    private String email;

    @Override
    public String toString() {
        String message = "";
        message += "\n project_id: " + project_id + " | ";
        message += "\n prev_id: " + prev_id + " | ";
        message += "\n title: " + title + " | ";
        message += "\n data_start: " + data_start + " | ";
        message += "\n data_end: " + data_end + " | ";
        message += "\n project_supervisor_id: " + project_supervisor_id + " | ";
        message += "\n supervisor_id: " + supervisor_id + " | ";
        message += "\n project_supervisor_role_id: " + project_supervisor_role_id + " | ";
        message += "\n project_review: " + project_review + " | ";
        message += "\n fio: " + fio + " | ";
        message += "\n email: " + email + "\n";
        //message += project_id + " | ";
        //message += prev_id + " | ";
        //message += title + " | ";
        //message += data_start + " | ";
        //message += data_end + " | ";
        //message += project_supervisor_id + " | ";
        //message += supervisor_id + " | ";
        //message += project_supervisor_role_id + " | ";
        //message += project_review + " | ";
        //message += fio + " | ";
        //message += email;
        return message;
    }

    // Конструктор по умолчанию
    public OtchetWithReview() {
    }

    // Геттеры и сеттеры

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getPrev_id() {
        return prev_id;
    }

    public void setPrev_id(int prev_id) {
        this.prev_id = prev_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData_start() {
        return data_start;
    }

    public void setData_start(String data_start) {
        this.data_start = data_start;
    }

    public String getData_end() {
        return data_end;
    }

    public void setData_end(String data_end) {
        this.data_end = data_end;
    }

    public int getProject_supervisor_id() {
        return project_supervisor_id;
    }

    public void setProject_supervisor_id(int project_supervisor_id) {
        this.project_supervisor_id = project_supervisor_id;
    }

    public int getSupervisor_id() {
        return supervisor_id;
    }

    public void setSupervisor_id(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    public int getProject_supervisor_role_id() {
        return project_supervisor_role_id;
    }

    public void setProject_supervisor_role_id(int project_supervisor_role_id) {
        this.project_supervisor_role_id = project_supervisor_role_id;
    }

    public String getProject_review() {
        return project_review;
    }

    public void setProject_review(String project_review) {
        this.project_review = project_review;
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
}