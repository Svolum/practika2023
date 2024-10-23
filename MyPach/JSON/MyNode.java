package MyPach.JSON;

import java.util.ArrayList;
import java.util.HashSet;

public class MyNode {
    private ArrayList<JsonReport> jsonReports;
    private HashSet<String> titles;
    private ArrayList<Integer> ids;
    public MyNode(){
        jsonReports = new ArrayList<>();
        titles = new HashSet<>();
        ids = new ArrayList<>();
    }
    public MyNode(JsonReport jsonReport){
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
    @Override
    public String toString() {
        return ids + " | " + titles;
    }

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
