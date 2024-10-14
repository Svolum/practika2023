package MyPach.AdminClasses;

import MyPach.DB.Honoric;
import MyPach.EndData;
import MyPach.FileWork.FileReport;
import MyPach.JSON.JsonReport;
import MyPach.Osnovnoe;
import MyPach.Sravnitel;

import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;

public class DBAdmin {
    private ArrayList<JsonReport> jsonReports;
    private ArrayList<FileReport> fileReports;
    private ArrayList<Honoric> honorics;
    public DBAdmin(){
        // вообще можно брать на прямую, просто перейди в функции getData и поймещь
        jsonReports = new JsonAdmin().getData();
        fileReports = new FilesAdmin().getData();

        lol();
//        generalLogic();
    }
    public void lol(){
        honorics = new ArrayList<>();

        System.out.println("jsonReports" + jsonReports.size());
        System.out.println("fileReports" + fileReports.size());
        for (FileReport fileReport : fileReports){
            if (fileReport.getFio() == null)
                continue;
            for (JsonReport jsonReport : jsonReports){
                try {
                    if (Osnovnoe.lewenstainExtended(fileReport.getTitle(), jsonReport.getTitle()) > 4) {
                        continue;
                    }
                    if (Osnovnoe.lewenstainExtended(fileReport.getFio(), jsonReport.getFio()) > 4) {
                        continue;
                    }
                }catch (Exception e){

                    continue;
                }
                honorics.add(new Honoric(jsonReport.getProject_id(), jsonReport.getPrev_id(), fileReport.getReview(), fileReport, jsonReport));
                break;
            }
            System.out.println("\nherehereherehereherehereherehereherehereherehereherehereherehere\n");
        }
        System.out.println("honorics=" + honorics.size());
    }
    private void generalLogic(){


        var alredyExistingId = new HashSet<>();
        ArrayList<EndData> endDataFall = new ArrayList<>();
        ArrayList<EndData> endDataSpring = new ArrayList<>();


        int countOfYearReports = 0;

        ArrayList<String> dublesOtchetFileNames = Sravnitel.getDublesOtchetFileNames();

        for (FileReport fileReport : fileReports){
            // Если есть такие отчеты, которые не читаются, имена их фалов надо закинуть в спец массив
            // Надо написать чеккер на не null важных полей и вызывать его, а не делать эти ифы
            if (fileReport.getFio() == null)
                continue;
            /*if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                // я даже не знаю, кажется это должно добавлять количество файлов в отстойнике
                continue;*/



            boolean isFall = false;
            boolean isSpring = false;

            for (JsonReport jsonReport : jsonReports){
                if (Sravnitel.compareJsonAndFile(jsonReport, fileReport)){
                    /*String searchingTitle = "Культура безопасности как элемент снижения уровня профессиональных рисков";
                    if (jsonReport.getTitle().equals(searchingTitle)) {
                        System.out.println("X#");
                        System.out.println(fileReport.getTitle());
                        System.out.println(fileReport.getFileName());
                        System.out.println("X-->");
                    }*/

                    int projectId = jsonReport.getProject_id();
                    // Исключает дубли, есть 2 версии 1 файла, АКТУАЛЬНОСТЬ оставшегося файла проверить, пока что, НЕВОЗМОЖНО
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Возможно исключает те отчеты, которые длятся только осенью
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // т.е. еще надо проверить как это работает
                    if (alredyExistingId.contains(projectId)) // итак вопрос, почему 1 проект, может откликаться больше чем на 1 отчет
                        continue; // Если убрать то countOfYearEndData, возможно будет больше количества весенних EndData
                    /*if (jsonReport.getTitle().equals(searchingTitle)){
                        System.out.println("Here");
                    }*/

                    fileReport.setProject_id(projectId);
                    alredyExistingId.add(projectId);


                    if (jsonReport.getPrev_id() != 0) {
                        alredyExistingId.add(jsonReport.getPrev_id());
                    }

                    // END DATA
                    if (jsonReport.getData_start().contains("2022-09")) {
                        // ОСЕНЬ
                        EndData ed = new EndData(fileReport, projectId, jsonReport.getPrev_id(), fileReport.getReview());
                        endDataFall.add(ed);

                        isFall = true;
                    }
                    else if (jsonReport.getData_start().contains("2023-02")){
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
        System.out.println("Count of year reports = " + countOfYearReports + " // not exactly");
        int countOfYearEndData = 0;
        for (var fall: endDataFall){
            boolean notIsYearProject = true;
            for (var spring: endDataSpring){
                if (fall.getProjectId() == spring.getPrevProjectId()){
                    notIsYearProject = false;
                    countOfYearEndData++;
                }
            }
            if (notIsYearProject) {
                // count of semestr reports
            }
        }
        System.out.println("count of year EndData = " + countOfYearEndData + " // can respond for more then 1 fall EndData");

        System.out.println("ids = " + alredyExistingId.size());
        System.out.println("FALL    = " + endDataFall.size());
        System.out.println("SPRING  = " + endDataSpring.size());


        int a = 0;
        ArrayList<String> fileNamesWitoutPair = Sravnitel.getFileNamesWithoutPair();
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0){
                if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                    continue;
                if (fileNamesWitoutPair.contains(fileReport.getFileName()))
                    continue;

                System.out.println(fileReport.toString(0));
                System.out.println("-------------------------------------------------------------------------------------------------");
                a++;
            }
        }
        System.out.println("\n\n\n");
        System.out.println("a = " + a + " | кол-во файлов, которым прога не нашла пары, и которые я пока считаю не дефектными");
        System.out.println("\n\n\n");
    }
}
