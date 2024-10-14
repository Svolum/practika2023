import MyPach.AdminClasses.DBAdmin;
import MyPach.AdminClasses.FilesAdmin;
import MyPach.AdminClasses.JsonAdmin;
import MyPach.Basic.BasicDataExtractor;
import MyPach.DB.DBFileCreator;
import MyPach.DB.DBHonoric;
import MyPach.FileWork.DocxDataExtractor;
import MyPach.FileWork.FileReport;
import MyPach.AdminClasses.FileTypeScanner;
import MyPach.FileWork.PDFDataExtractor;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.JsonReport;
import MyPach.Lol;
import MyPach.Osnovnoe;
import MyPach.Sravnitel;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {




//        new JsonAdmin();
//        new FilesAdmin();
//        new Sravnitel();
//        new DBAdmin();
        //////////////
        mainWOrk();
//        simpleLol();
        //////////////////////////////////////////////

        //new FileFinder();
        //new JSONDataExtractor();
        //workWithCertainFile();
        //new Sravnitel();


        //new FileTypeScanner().showOthcetsInfo();
        //workWithCertainFile();
//        new Sravnitel();
//        System.out.println(Sravnitel.getNumberOfUniqueOtchets());
        // разобрать json данные
        //checkJSON();

        //myContainsChecker();
        //myLewenstain();
//        compareOtchets();
//        System.out.println(Sravnitel.compareFIO("Яценко Светлана Анатольевна", "Яценко С.А."));
//        getPDFFileData();
//        someWorkWithPdfFiles();
    }
    public static void mainWOrk(){
        // уч год 22-23
        Osnovnoe.workingPath = "\\отчетыМного\\отчеты наставников 2023-2024\\отчеты наставников\\";
        Osnovnoe.date_start = "2022-09";
        Osnovnoe.date_end = "2023-02";
        // уч год 22-23
        Osnovnoe.workingPath = "\\отчеты 2022-2023\\отчеты";
        Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\без экспертов";
        Osnovnoe.date_start = "2022-09";
        Osnovnoe.date_end = "2023-02";
        Osnovnoe.jsonCreatingFileName = "22-23";
        // Старый или же обычный способ
//        new DBFileCreator(new Sravnitel().getDbHonorics());
        // сравнение через MyNode
//        new DBFileCreator(new Sravnitel().getDbHonoricsThroughMyNode());
        // для откладки
        ArrayList<DBHonoric> dbHonorics = new Sravnitel().getDbHonoricsThroughMyNode();
        dbHonorics.size();
        new DBFileCreator(dbHonorics);
    }
    public static void simpleLol(){
        new Lol();
    }
    public static void compareOtchets(){
        String otchetName = "Культура безопасности как элемент снижения уровня профессиональных рисков» (Издательство ИРНИТУ, Институт  вечерне-заочного обучения ИРНИТУ)».docx";
        FileReport fileReport = new FileTypeScanner().getOtchet(otchetName);

        String jsonTitle = "Профессиональные риски на объектах экономики Иркутской областиКультура безопасности как элемент снижения уровня профессиональных рисков";
        JsonReport jsonOtchet = new JSONDataExtractor().getJsonReport(jsonTitle);

        System.out.println(fileReport.getTitle());
        System.out.println(Sravnitel.remainOnlyWords(fileReport.getTitle()));
        System.out.println(jsonOtchet.getTitle());
        System.out.println(Sravnitel.remainOnlyWords(jsonOtchet.getTitle()));

        System.out.println(Sravnitel.lewenstain(fileReport.getTitle(), jsonOtchet.getTitle()) + "\t\t| Sravnitel.lewenstain");
        System.out.println(Sravnitel.compareJsonAndFile(jsonOtchet, fileReport) + "\t| Sravnitel.compareJsonAndFile");
        System.out.println((fileReport.getTitle().contains(jsonOtchet.getTitle()) || jsonOtchet.getTitle().contains(fileReport.getTitle())) + "\t| contains");

        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println(fileReport);
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println(jsonOtchet);
    }
    
    public static void myContainsChecker(){
        //System.out.println(Sravnitel.myContains("hello world", "hell"));
        System.out.println(Sravnitel.myContains("Создание веб-форм для Центра Карьеры", "Разработка веб форм для Центра Карьеры "));
    }
    public static void myLewenstain(){
        String s = "Разработка веб форм для Центра Карьеры ";
        String q = "Создание веб-форм для Центра Карьеры";
        System.out.println(Sravnitel.lewenstain(s, q));
    }
    public static void workWithCertainFile(){
        DocxDataExtractor rdf = new DocxDataExtractor("Конструкция и система управления навесными модулями настольного станка с ЧПУ.docx", System.getProperty("user.dir") + "/отчеты/");
        System.out.println(rdf.getSupervisorFIO());
    }
    public static void checkJSON(){
        System.out.println((new JSONDataExtractor().getJsonReports()).size());
        int a = 0;
        for (var lol : (new JSONDataExtractor().getJsonReports())){
            if (lol.getData_start().contains("2023-02")) {
                System.out.println(lol.getPrev_id());
                a++;
            }
        }
        System.out.println(a);
    }
    public static void getPDFFileData(){
        new PDFDataExtractor("myExample.pdf", FileTypeScanner.getCurrentDirrectory() + "/отчеты/");
    }
    public static void someWorkWithPdfFiles(){
        FileTypeScanner fileTypeScanner = new FileTypeScanner();
        ArrayList<String> pdFileNames = fileTypeScanner.getPDFileNames();
        for (String i : pdFileNames){
            System.out.println(i);
            new PDFDataExtractor(i, fileTypeScanner.getWorkDirectory());
        }
    }
}