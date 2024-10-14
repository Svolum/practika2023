package MyPach.AdminClasses;

import MyPach.JSON.*;

import java.util.*;

public class JsonAdmin {
    private JSONDataExtractor dataExtractor;
    private ArrayList<JsonReport> reports;
    private JsonReport curReport;
    private ArrayList<SupervisorFio> supervisorFios;
    public JsonAdmin(){
        dataExtractor = new JSONDataExtractor();

//        simpleRead();
//        multipleRead();
//        advancedRead();
//        supervisorFios();
//        getProjectTitles();
//        System.out.println(new SupervisorFio("Гаврилов Михаил Аркадьевич").equals("Гаврилов М.А."));
    }
    public void simpleRead() { // read one certain file
        Scanner in = new Scanner(System.in);

        System.out.println("Выбери по какому параметру искать:\n" +
                "Что-то помимо цифр вызовет ошибку!!!\n" +
                "1 - (Title) по названиею,\n" +
                "2 - (id) по id");
        switch (Integer.parseInt(in.nextLine())) {
            case 1: {
                System.out.println("Введи название(title) проекта(или введи его в коде):");
                String title = in.nextLine();
                /*curReport = dataExtractor.getJsonReport("Проекты в рамках конкурса грантов:1 - Разработка модели " +
                        "коммерциализации проекта по оптимизации и проектированию системы антиобледенения дорожного полотна2" +
                        " - Мобильное приложение для бронирования мест в общежитиях для студентов");*/
                curReport = dataExtractor.getJsonReport(title);
                break;
            }
            case 2: {
                System.out.println("Введи id проекта:");
                int project_id = Integer.parseInt(in.nextLine());

                curReport = dataExtractor.getJsonReport(project_id);
                break;
            }
            default:
                return;
        }
        System.out.println(curReport);
    }
    public void multipleRead(){
        reports = dataExtractor.getJsonReports();

        for (JsonReport report : reports){
            System.out.println(report);
        }
    }
    public void advancedRead(){
        ArrayList<JsonReportAdvanced> jsonReportAdvanceds = dataExtractor.getJsonReportAdvanced();

        for (JsonReportAdvanced report : jsonReportAdvanceds){
            System.out.println(report);
        }
    }
    public void supervisorFios(){
        reports = dataExtractor.getJsonReports();
        HashSet<String> fios = new HashSet<>();
        for (JsonReport report : reports){
            fios.add(report.getFio());
        }
        System.out.println("Количество уникальных фио | Количество отчетов\n" + fios.size() + " | " + reports.size());
        supervisorFios = new ArrayList<>();
        for(String fio : fios){
            System.out.println(fio);
            supervisorFios.add(new SupervisorFio(fio));
        }

    }
    // по факту этот метод может и, наверное, должен быть в jsonDataExtractor
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
    public ArrayList<MyNode> getProjectTitles(){
        /*reports = dataExtractor.getJsonReports();
        reports.addAll(dataExtractor.getJsonReportAdvanced());*/
        reports = dataExtractor.getJsonReportsOnlyRole();
        reports.addAll(dataExtractor.getJsonReportAdvancedOnlyRole());

//        System.out.println("reports.size()=" + reports.size());


        HashSet<Integer> uniqueIds = new HashSet<>();
        for (JsonReport report : reports){
            /*if (uniqueIds.contains(report.getProject_id()))
                System.out.println("id=" + report.getProject_id() + " \t| class=" + report.getClass());*/
            uniqueIds.add(report.getProject_id());
        }
//        System.out.println("uniqueIds.size()=" + uniqueIds.size());


        ArrayList<MyNode> myNodes = new ArrayList<>();
        for (JsonReport report : reports){
            if (reports.indexOf(report) == 0) {
                myNodes.add(new MyNode(report));
                continue;
            }
            boolean isReportAdded = false;
            for (MyNode myNode : myNodes){
                if (myNode.contains(report.getPrev_id())){
                    myNode.add(report);
                    isReportAdded = true;
                }
            }
            if (isReportAdded == false)
                myNodes.add(new MyNode(report));
        }
//        System.out.println("myNodes.size()=" + myNodes.size());
        return myNodes;
    }

    public ArrayList<JsonReport> getData(){
        return dataExtractor.getJsonReports();
    }
}
