package MyPach.AdminClasses;

import MyPach.JSON.*;

import java.util.*;

public class JsonAdmin {
    private JSONDataExtractor dataExtractor;
    private ArrayList<JsonReport> reports;
//    private JsonReport curReport;
    public JsonAdmin(){
        dataExtractor = new JSONDataExtractor();
    }
    // simpleRead()
    /*public void simpleRead() { // read one certain file
        Scanner in = new Scanner(System.in);

        System.out.println("Выбери по какому параметру искать:\n" +
                "Что-то помимо цифр вызовет ошибку!!!\n" +
                "1 - (Title) по названиею,\n" +
                "2 - (id) по id");
        switch (Integer.parseInt(in.nextLine())) {
            case 1: {
                System.out.println("Введи название(title) проекта(или введи его в коде):");
                String title = in.nextLine();
                *//*curReport = dataExtractor.getJsonReport("Проекты в рамках конкурса грантов:1 - Разработка модели " +
                        "коммерциализации проекта по оптимизации и проектированию системы антиобледенения дорожного полотна2" +
                        " - Мобильное приложение для бронирования мест в общежитиях для студентов");*//*
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
    }*/
    // по сути проекты - это что-то многосеместровое, а в базе оно разбито на семестры, myNode собирает все обратно
    public ArrayList<MyNode> getProjectTitles(){
        reports = dataExtractor.getJsonReports();

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
        return myNodes;
    }
    public ArrayList<JsonReport> getData(){
        return dataExtractor.getJsonReports();
    }
}
