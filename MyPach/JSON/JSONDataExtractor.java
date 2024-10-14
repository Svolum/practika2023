package MyPach.JSON;



import MyPach.Basic.DataExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class JSONDataExtractor {

    private ObjectMapper objectMapper;
    private ArrayList<String> supervisorsFIO;
    public JSONDataExtractor(){
        objectMapper = new ObjectMapper();
        supervisorsFIO = new ArrayList<>();
    }
    public ArrayList<JsonReportAdvanced> getJsonReportAdvanced(){
        ArrayList<JsonReportAdvanced> jsonReportAdvanceds = new ArrayList<>();
        try {
            jsonReportAdvanceds = jsonReaderAdvancedReport();
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return jsonReportAdvanceds;
    }
    public ArrayList<JsonReport> getJsonReports(){
        ArrayList<JsonReport> jsonReports = new ArrayList<>();
        try {
            jsonReports = jsonReaderReports();
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return jsonReports;
    }
    // учитывая, что role_id мне нужен тольео (=) 2
    public ArrayList<JsonReportAdvanced> getJsonReportAdvancedOnlyRole(){
        ArrayList<JsonReportAdvanced> jsonReportAdvanceds = new ArrayList<>();
        try {
//            jsonReportAdvanceds = jsonReaderAdvancedReport();
            for (JsonReportAdvanced jsonReport : jsonReaderAdvancedReport()){
                if (jsonReport.getProject_supervisor_role_id() == 2)
                    jsonReportAdvanceds.add(jsonReport);
            }
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return jsonReportAdvanceds;
    }
    public ArrayList<JsonReport> getJsonReportsOnlyRole(){
        ArrayList<JsonReport> jsonReports = new ArrayList<>();
        try {
//            jsonReports = jsonReaderReports();
            for (JsonReport jsonReport : jsonReaderReports()){
                if (jsonReport.getProject_supervisor_role_id() == 2)
                    jsonReports.add(jsonReport);
            }
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return jsonReports;
    }
    /////////////////////////////////////////
    public JsonReport getJsonReport(String jsonName){
        for (JsonReport jsonOtchet : getJsonReports()){
            if (jsonOtchet.getTitle().equals(jsonName))
                return jsonOtchet;
        }
        return null;
    }
    public JsonReport getJsonReport(int project_id){
        for (JsonReport jsonOtchet : getJsonReports()){
            if (jsonOtchet.getProject_id() == project_id)
                return jsonOtchet;
        }
        return null;
    }

    private ArrayList<JsonReportAdvanced> jsonReaderAdvancedReport() throws JsonProcessingException {
        ArrayList<JsonReportAdvanced> jsonReportAdvanceds = new ArrayList<>();
        try{
            jsonReportAdvanceds = objectMapper.readValue(new File("Data\\withreview.json"), new TypeReference<ArrayList<JsonReportAdvanced>>(){});
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return jsonReportAdvanceds;
    }
    private ArrayList<JsonReport> jsonReaderReports() throws JsonProcessingException {
        ArrayList<JsonReport> jsonReports = new ArrayList<>();
        try{
            jsonReports = objectMapper.readValue(new File("Data\\needreview.json"), new TypeReference<ArrayList<JsonReport>>(){});
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return jsonReports;
    }
    public static ArrayList<SupervisorFio> getSupervisorFios(){
        JSONDataExtractor dataExtractor = new JSONDataExtractor();
        HashSet<String> fios = new HashSet<>();
        for (JsonReport report : dataExtractor.getJsonReports()){
            fios.add(report.getFio());
        }
        ArrayList<SupervisorFio> supervisorFios = new ArrayList<>();
        for(String fio : fios){
            supervisorFios.add(new SupervisorFio(fio));
        }
        return supervisorFios;
    }
}
