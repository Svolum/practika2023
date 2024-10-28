package MyPach.JSON;

import MyPach.DB.DBHonoric;
import MyPach.FileWork.FileReport;
import MyPach.Osnovnoe;

import java.util.ArrayList;
import java.util.HashSet;

public class ProjectFlow {
    private ArrayList<JsonReport> jsonReports;
    private HashSet<String> titles;
    private ArrayList<Integer> ids;
    public ProjectFlow(){
        // по сути проекты - это что-то многосеместровое, а в базе оно разбито на семестры, ProjectFlow собирает все обратно
        jsonReports = new ArrayList<>();
        titles = new HashSet<>();
        ids = new ArrayList<>();
    }
    public ProjectFlow(JsonReport jsonReport){
        jsonReports = new ArrayList<>();
        titles = new HashSet<>();
        ids = new ArrayList<>();
        add(jsonReport);
    }
    public void add(JsonReport jsonReport){
        jsonReports.add(jsonReport);
        titles.add(jsonReport.getTitle());
        ids.add(jsonReport.getProject_id());
    }
    public boolean contains(int id){
        return ids.contains(id);
    }
    public boolean contains(String reportTitle){
        for (String title : titles){
            if (Osnovnoe.myContainsRelative(reportTitle, title)){
                return true;
            }
        }
        return false;
    }
    public ArrayList<DBHonoric> getDBHonorics(FileReport fileReport){
        ArrayList<DBHonoric> dbHonorics = new ArrayList<>();
        for (JsonReport jsonReport : jsonReports){
            if (Osnovnoe.compareJsonAndReport(jsonReport, fileReport))
                dbHonorics.add(new DBHonoric(jsonReport.getProject_id(), jsonReport.getProject_id(),
                        fileReport.getReview(), jsonReport, fileReport));
        }
        return dbHonorics;
    }
    @Override
    public String toString() {
        return ids + " | " + titles;
    }

    // GETTERS & SETTERS
    public ArrayList<JsonReport> getJsonReports() {
        return jsonReports;
    }
    public void setJsonReports(ArrayList<JsonReport> jsonReports) {
        this.jsonReports = jsonReports;
    }
    public HashSet<String> getTitles() {
        return titles;
    }
    public void setTitles(HashSet<String> titles) {
        this.titles = titles;
    }
    public ArrayList<Integer> getIds() {
        return ids;
    }
    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }
}
