package MyPach.JSON;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class JSONDataExtractor {
    private ArrayList<JsonReport> jsonReports;
    public JSONDataExtractor(){
        jsonReports = new ArrayList<>();

        // для того чтоб роль была того человека который проектом руководит
        try {
            for (JsonReport jsonReport : jsonReaderReports()){
                if (jsonReport.getProject_supervisor_role_id() == 2)
                    jsonReports.add(jsonReport);
            }
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
    }
    private ArrayList<JsonReport> jsonReaderReports() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<JsonReport> jsonReports = new ArrayList<>();
        try{
            jsonReports = objectMapper.readValue(new File("Data\\needreview.json"), new TypeReference<ArrayList<JsonReport>>(){});
            jsonReports.addAll(objectMapper.readValue(new File("Data\\withreview.json"), new TypeReference<ArrayList<JsonReport>>(){}));
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
    public JsonReport getJsonReport(String jsonName){
        for (JsonReport jsonOtchet : jsonReports){
            if (jsonOtchet.getTitle().equals(jsonName))
                return jsonOtchet;
        }
        return null;
    }
    public JsonReport getJsonReport(int project_id){
        for (JsonReport jsonOtchet : jsonReports){
            if (jsonOtchet.getProject_id() == project_id)
                return jsonOtchet;
        }
        return null;
    }
    public ArrayList<JsonReport> getJsonReports(){
        return jsonReports;
    }
}
