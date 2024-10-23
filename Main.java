import MyPach.DB.DBFileCreator;
import MyPach.DB.DBHonoric;
import MyPach.FileWork.DocxDataExtractor;
import MyPach.FileWork.FileReport;
import MyPach.AdminClasses.FileTypeScanner;
import MyPach.FileWork.PDFDataExtractor;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.JsonReport;
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
        // уч год 21-22
        /*Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2021-2022\\обработано\\";
        Osnovnoe.date_start = "2021-09";
        Osnovnoe.date_end = "2022-02";*/
        // уч год 22-23
/*//                Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\без экспертов\\";
//        Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\обработано\\";
        Osnovnoe.date_start = "2022-09";
        Osnovnoe.date_end = "2023-02";*/
        // уч год 23-24
        /*Osnovnoe.workingPath = "\\отчетыМного\\отчеты наставников 2023-2024\\отчеты наставников\\";
        Osnovnoe.date_start = "2023-09";
        Osnovnoe.date_end = "2024-02";*/


        Osnovnoe.jsonCreatingFileName = "отчетыОБработано";







        ///////////////////////////////////////////////////////////////////////////////////////////
        // Старый или же обычный способ
//        new DBFileCreator(new Sravnitel().getDbHonorics());
        // сравнение через MyNode
//        new DBFileCreator(new Sravnitel().getDbHonoricsThroughMyNode());
        // для откладки
        ArrayList<DBHonoric> dbHonorics = new Sravnitel().getDbHonoricsThroughMyNode();
        dbHonorics.size();
        new DBFileCreator(dbHonorics);
    }
}