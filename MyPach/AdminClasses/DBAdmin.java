package MyPach.AdminClasses;

import MyPach.DB.DBFileCreator;
import MyPach.DB.DBHonoric;
import MyPach.DB.Honoric;
import MyPach.EndData;
import MyPach.FileWork.FileReport;
import MyPach.JSON.JsonReport;
import MyPach.JSON.ProjectFlow;
import MyPach.Osnovnoe;
import MyPach.Sravnitel;

import java.util.ArrayList;
import java.util.HashSet;

public class DBAdmin {
    private JsonAdmin jsonAdmin;
    private ArrayList<JsonReport> jsonReports;
    private ArrayList<ProjectFlow> projectFlows;
    private ArrayList<FileReport> fileReports;
    public DBAdmin(){
        /*
        - Конструктор заполняет необходимые поля
        - а в функции createDBHonorics, происходит очень важный процесс поиска результата(review) своего project_id
        - а потом можно вызвать logic, который создать json файлик, путем вызова DBFileCreator
         */
        jsonAdmin = new JsonAdmin();
        // вообще можно брать на прямую, просто перейди в функции getData и поймещь
        jsonReports = jsonAdmin.getData();
        projectFlows = jsonAdmin.getProjectFlows();
        fileReports = new FilesAdmin().getData();

        logic();
    }
    private void lol(){
        // переменные для отладки
        /////////////////////////////////////
        var alredyExistingId = new HashSet<>();
        ArrayList<EndData> endDataFall = new ArrayList<>();
        ArrayList<EndData> endDataSpring = new ArrayList<>();
        boolean isFall;
        boolean isSpring;

        int countOfYearReports = 0;
        /////////////////////////////////////

        for (FileReport fileReport : fileReports){
            if (fileReport.getFio() == null)
                continue;

            isFall = false;
            isSpring = false;

            for (JsonReport jsonReport : jsonReports){
                if (Osnovnoe.compareJsonAndReport(jsonReport, fileReport)){

                    int projectId = jsonReport.getProject_id();

                    if (alredyExistingId.contains(projectId))
                        continue;

                    fileReport.setProject_id(projectId);
                    alredyExistingId.add(projectId);

                    if (jsonReport.getPrev_id() != 0) {
                        alredyExistingId.add(jsonReport.getPrev_id());
                    }

                    // END DATA
                    if (jsonReport.getData_start().contains(Osnovnoe.date_start)) {
                        // ОСЕНЬ
                        EndData ed = new EndData(fileReport, projectId, jsonReport.getPrev_id(), fileReport.getReview());
                        endDataFall.add(ed);

                        isFall = true;
                    }
                    else if (jsonReport.getData_start().contains(Osnovnoe.date_end)){
                        // ВЕСНА
                        EndData ed = new EndData(fileReport, projectId, jsonReport.getPrev_id(), fileReport.getReview());
                        endDataSpring.add(ed);

                        isSpring = true;
                    }
                }
            }
            if (isFall && isSpring){
                countOfYearReports++;
            }
        }

        int countOfLonlyFiles = 0;
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0)
                countOfLonlyFiles++;

        }
        System.out.println("Count of lonly files = " + countOfLonlyFiles);

        // Почему-то не сходится, поэтому положусь на данные из БД т.е. на EndData
        System.out.println("Count of year reports = " + countOfYearReports);
        int countOfYearEndData = 0;
        for (EndData fall: endDataFall){
            for (EndData spring: endDataSpring){
                if (fall.getProjectId() == spring.getPrevProjectId()){
                    countOfYearEndData++;
                }
            }
        }
        System.out.println("count of year EndData = " + countOfYearEndData + " // can respond for more then 1 fall EndData");

        System.out.println("alredyExistingId = " + alredyExistingId.size());
        System.out.println("endDataFall    = " + endDataFall.size());
        System.out.println("endDataSpring  = " + endDataSpring.size());

        // файлы, которым не нашлось пары
        int a = 0;
    }
    public ArrayList<DBHonoric> createDBHonorics(){
        ArrayList<DBHonoric> dbHonorics = new ArrayList<>();

        for (ProjectFlow projectFlow : projectFlows){
            for (FileReport fileReport : fileReports){
                if (fileReport.getFio() == null)
                    continue;
                dbHonorics.addAll(projectFlow.getDBHonorics(fileReport));
            }
        }
        return dbHonorics;
    }
    public void logic(){
        new DBFileCreator("OUT_REZULT\\pairs", createDBHonorics());
    }
}
