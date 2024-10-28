package MyPach;

import MyPach.AdminClasses.FilesAdmin;
import MyPach.AdminClasses.JsonAdmin;
import MyPach.DB.DBHonoric;
import MyPach.DB.Honoric;
import MyPach.FileWork.FileReport;
import MyPach.FileWork.FolderScanner;
import MyPach.JSON.JsonReport;
import MyPach.JSON.ProjectFlow;
import MyPach.JSON.SupervisorFio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Sravnitel {
    ArrayList<FileReport> fileReports;
    ArrayList<JsonReport> jsonReports;
    // Обработанные данные
    HashSet<Integer> alredyExistingId;
    ArrayList<EndData> endDataFall;
    ArrayList<EndData> endDataSpring;
    ArrayList<Honoric> honorics;
    ArrayList<DBHonoric> dbHonorics;
    public Sravnitel(){
        Scanner in = new Scanner(System.in);
    }
    public static ArrayList<String> getDublesOtchetFileNames(){
        ArrayList<FileReport> array = new FolderScanner().getFileReports();

        ArrayList<FileReport> checkedOtchets = new ArrayList<>();
        ArrayList<String> dublesOtchetFileNames = new ArrayList<>();

        for (FileReport i : array){
            for (FileReport j : array){
                if (checkedOtchets.contains(j))
                    continue;
                if (i == j)
                    continue;

                if (i.getTitle().equals(j.getTitle())){
                    // Это значит, что те файлы, что стоят выше будут считаться истинными
                    dublesOtchetFileNames.add(i.getFileName());
                    dublesOtchetFileNames.add(j.getFileName());
                }
            }
            checkedOtchets.add(i);
        }
        return dublesOtchetFileNames;
    }
    private void generalLogic(){
        jsonReports = new JsonAdmin().getData();
        fileReports = new FilesAdmin().getData();


        alredyExistingId = new HashSet<>();
        endDataFall = new ArrayList<>();
        endDataSpring = new ArrayList<>();
        honorics = new ArrayList<>();


        int countOfYearReports = 0;

        ArrayList<String> dublesOtchetFileNames = getDublesOtchetFileNames();

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
                if (Osnovnoe.compareJsonAndReport(jsonReport, fileReport)){
//                    String searchingTitle = "Культура безопасности как элемент снижения уровня профессиональных рисков";
//                    if (jsonReport.getTitle().equals(searchingTitle)) {
//                        System.out.println("X#");
//                        System.out.println(fileReport.getTitle());
//                        System.out.println(fileReport.getFileName());
//                        System.out.println("X-->");
//                    }

                    int projectId = jsonReport.getProject_id();
                    // Исключает дубли, есть 2 версии 1 файла, АКТУАЛЬНОСТЬ оставшегося файла проверить, пока что, НЕВОЗМОЖНО
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Возможно исключает те отчеты, которые длятся только осенью
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // т.е. еще надо проверить как это работает
                    if (alredyExistingId.contains(projectId)) // итак вопрос, почему 1 проект, может откликаться больше чем на 1 отчет
                        continue; // Если убрать то countOfYearEndData, возможно будет больше количества весенних EndData
//                    if (jsonReport.getTitle().equals(searchingTitle)){
//                        System.out.println("Here");
//                    }

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
                    // honoric
                    if (jsonReport.getData_start().contains("2022-09") || jsonReport.getData_start().contains("2023-02")){
                        honorics.add(new Honoric(jsonReport.getProject_id(), jsonReport.getPrev_id(), fileReport.getReview()));
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
//        System.out.println("Count of lonly files = " + countOfLonlyFiles);

        // Почему-то не сходится, поэтому положусь на данные из БД т.е. на EndData
//        System.out.println("Count of year reports = " + countOfYearReports + " // not exactly");
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
//        System.out.println("count of year EndData = " + countOfYearEndData + " // can respond for more then 1 fall EndData");

        /*System.out.println("ids = " + alredyExistingId.size());
        System.out.println("FALL    = " + endDataFall.size());
        System.out.println("SPRING  = " + endDataSpring.size());*/


        int a = 0;
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0){
                if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                    continue;

                /*System.out.println(fileReport.toString(0));
                System.out.println("-------------------------------------------------------------------------------------------------");*/
                a++;
            }
        }
        /*System.out.println("\n\n\n");
        System.out.println("a = " + a + " | кол-во файлов, которым прога не нашла пары, и которые я пока считаю не дефектными");
        System.out.println("\n\n\n");*/
    }
    private void generalLogicThroughMyNode(){
        ArrayList<ProjectFlow> projectFlows = new JsonAdmin().getProjectFlows();
        fileReports = new FilesAdmin().getData();


        alredyExistingId = new HashSet<>();
        endDataFall = new ArrayList<>();
        endDataSpring = new ArrayList<>();
        honorics = new ArrayList<>();


        int countOfYearReports = 0;

        ArrayList<String> dublesOtchetFileNames = getDublesOtchetFileNames();

        for (FileReport fileReport : fileReports){
            // Если есть такие отчеты, которые не читаются, имена их фалов надо закинуть в спец массив
            // Надо написать чеккер на не null важных полей и вызывать его, а не делать эти ифы
            if (fileReport.getFio() == null)
                continue;


            boolean isFall = false;
            boolean isSpring = false;

            for (ProjectFlow projectFlow : projectFlows){
                if (compareMyNodeAndFile(projectFlow, fileReport) == false)
                    continue;
                JsonReport jsonFallReport = null;
                JsonReport jsonSpringReport = null;
                for (JsonReport jsonReport : projectFlow.getJsonReports()){
                    if (Osnovnoe.isDateInTimeRangeFall(jsonReport.getData_start())) // Осень
                        jsonFallReport = jsonReport;
                    else if (Osnovnoe.isDateInTimeRangeSpring(jsonReport.getData_start())) // Весна
                        jsonSpringReport = jsonReport;
                }

                if ((jsonFallReport != null) // ОСЕНЬ
                        && (alredyExistingId.contains(jsonFallReport.getProject_id()) == false)) {
                    // endData
                    EndData ed = new EndData(fileReport, jsonFallReport.getProject_id(),
                            jsonFallReport.getPrev_id(), fileReport.getReview());
                    endDataFall.add(ed);
                    isFall = true;


                    // honoric
                    honorics.add(new Honoric(jsonFallReport.getProject_id(), jsonFallReport.getPrev_id(),
                            fileReport.getReview()));
                }
                if ((jsonSpringReport != null) // ВЕСНА
                        && (alredyExistingId.contains(jsonSpringReport.getProject_id()))){
                    // endData
                    EndData ed = new EndData(fileReport, jsonSpringReport.getProject_id(),
                            jsonSpringReport.getPrev_id(), fileReport.getReview());
                    endDataSpring.add(ed);
                    isSpring = true;


                    // honoric
                    honorics.add(new Honoric(jsonSpringReport.getProject_id(), jsonSpringReport.getPrev_id(),
                            fileReport.getReview()));
                }
            }
            if (isFall && isSpring){
                countOfYearReports++;
            }
        }

        // те отчеты, которым пару не нашел
        /*int countOfLonlyFiles = 0;
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0)
                countOfLonlyFiles++;

        }*/

        // Почему-то не сходится, поэтому положусь на данные из БД т.е. на EndData
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

        int a = 0; // кол-во файлов, которым прога не нашла пары, и которые я пока считаю не дефектными
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0){
                if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                    continue;
                a++;
            }
        }
    }
    public ArrayList<DBHonoric> getDbHonorics() {
        dbHonorics = new ArrayList<>();

        generalLogic();

        for (EndData spring : endDataSpring){
            for (EndData fall : endDataFall){
                if (spring.getPrevProjectId() == fall.getProjectId()) {
                    dbHonorics.add(new DBHonoric(fall.getProjectId(), spring.getProjectId(), fall.getReview(), fall, spring));
                    break;
                }
            }
        }
        return dbHonorics;
    }
    public ArrayList<DBHonoric> getDbHonoricsThroughMyNode() {
        dbHonorics = new ArrayList<>();

        generalLogicThroughMyNode();

        /*System.out.println(jsonReports.size());
        System.out.println(fileReports.size());
        System.out.println(honorics.size());
        System.out.println(endDataFall.size());
        System.out.println(endDataSpring.size());*/


        // обычное
        /*for (Honoric honoric : honorics){
            // пока так и не понял зачем мне endData здесь
            // ну и вроде беспокоится о том, что отчеты не подхояд по временному промежутку не надо
            // обычное
            dbHonorics.add(new DBHonoric(honoric, null, null));
        }*/
        // для откладки, т.к. ссылка на endDdate имеет ссылку на json и на file, для более подробной проверки
        ArrayList<EndData> endDatassss = endDataFall;
        endDatassss.addAll(endDataSpring);
        for (EndData endData : endDatassss){
            dbHonorics.add(new DBHonoric(endData.getProjectId(), endData.getProjectId(), endData.getReview(), endData, null));
        }
        // lonly files names  или же файлы, которым не нашлось пары
        ////////////////////////////////////////////////////////////////////////////////////////
        // просто здесь их удобно находить
        ArrayList<String> lonlyFileNames = new ArrayList<>();
        for (FileReport fileReport : fileReports){
            boolean isFileNameFinded = false;
            for (EndData endData : endDatassss){
                if (endData.getFileReport().getFileName().equals(fileReport.getFileName())) {
                    isFileNameFinded = true;
                    break;
                }
            }
            if (isFileNameFinded == false){
                lonlyFileNames.add(fileReport.getFileName());
            }
        }
        System.out.println(lonlyFileNames.size() + " =size of lonly"); // слегка гонит, либо дублеры
        System.out.println(fileReports.size() + " =count of files");
        System.out.println(dbHonorics.size() + " =count of processed");
        ////////////////////////////////////////////////////////////////////////////////////////
        return dbHonorics;
    }

    public static boolean compareJsonAndFile(JsonReport json, FileReport fileReport){
        return
                (json.getData_start().contains(Osnovnoe.date_start) || (json.getData_start().contains(Osnovnoe.date_end))) &&
                SupervisorFio.areEqual(json.getFio(), fileReport.getFio()) &&
                ((Osnovnoe.lewenstain(json.getTitle(), fileReport.getTitle()) < 12) || (Osnovnoe.lewenstain(fileReport.getTitle(), json.getTitle()) < 12))
        ;
    }
    public static boolean compareMyNodeAndFile(ProjectFlow projectFlow, FileReport fileReport){
        boolean titleFitting = false;
        for (String title : projectFlow.getTitles()){
            if (Osnovnoe.myContainsRelative(title, fileReport.getTitle())){
                titleFitting = true;
                break;
            }
        }
        if (titleFitting == false) // Если ни один title из ветки названий проекта не подошел
            return false;
        for (JsonReport jsonReport : projectFlow.getJsonReports()){
            // поиск ПОДХОДЯЩЯГО report, хотя их может быть и 2. Точнее это наиболее вероятно, что их 2
            // однако для проверки достаточно и одного
            if (Osnovnoe.isDateInTimeRange(jsonReport.getData_start()) || Osnovnoe.isDateInTimeRange(jsonReport.getData_end())){
//                (json.getProject_supervisor_role_id() == 2) && // поля где (supervisor_role != 2) отсеяны на этапе чтения
                if (
                        (SupervisorFio.areEqual(jsonReport.getFio(), fileReport.getFio())) &&
                        Osnovnoe.myContainsRelative(jsonReport.getTitle(), fileReport.getTitle())
                )
                    return true;
            }
        }

        return false;
    }
}
