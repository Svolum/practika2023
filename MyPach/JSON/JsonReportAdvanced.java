package MyPach.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonReportAdvanced extends JsonReport {
    @JsonProperty("project_review")
    private String project_review;

    // Конструктор по умолчанию
    public JsonReportAdvanced() {
        super();
    }

    @Override
    public String toString() {
        return super.toString() + "\n project_review: " + project_review + " | ";
    }
    // Геттеры и сеттеры
    public String getProject_review() {
        return project_review;
    }

    public void setProject_review(String project_review) {
        this.project_review = project_review;
    }
}