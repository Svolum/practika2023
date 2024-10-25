package MyPach.AdminClasses;

import MyPach.JSON.*;

import java.util.*;

public class JsonAdmin {
    private JSONDataExtractor dataExtractor;
    private ArrayList<JsonReport> reports;
    public JsonAdmin(){
        // по сути проекты - это что-то многосеместровое, а в базе оно разбито на семестры, ProjectFlow собирает все обратно
        dataExtractor = new JSONDataExtractor();
    }
    public ArrayList<ProjectFlow> getProjectFlows(){
        reports = dataExtractor.getJsonReports();

        ArrayList<ProjectFlow> projectFlows = new ArrayList<>();
        for (JsonReport report : reports){
            if (reports.indexOf(report) == 0) {
                projectFlows.add(new ProjectFlow(report));
                continue;
            }
            boolean isReportAdded = false;
            for (ProjectFlow projectFlow : projectFlows){
                if (projectFlow.contains(report.getPrev_id())){
                    projectFlow.add(report);
                    isReportAdded = true;
                }
            }
            if (isReportAdded == false)
                projectFlows.add(new ProjectFlow(report));
        }
        return projectFlows;
    }
    public ArrayList<JsonReport> getData(){
        return dataExtractor.getJsonReports();
    }
}
