import MyPach.AdminClasses.DBAdmin;
import MyPach.AdminClasses.FilesAdmin;
import MyPach.AdminClasses.JsonAdmin;
import MyPach.JSON.JsonReport;
import MyPach.Osnovnoe;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        /* название папки, в которой лежат отчеты, учебный год, к которому они принадлежат
        а так же имя создаваемого .json файла надо прописать самостоятельно
         */
//        mainWOrk();
//        sideWork();
//        certinFileWork();
//        [2022-01-09, 2023-12-30, 2024-02-05, 2023-02-01, 2022-12-30, 2023-09-01, 2024-05-30, 2023-05-30, 2022-09-01, 2024-02-01]
    }
    public static void mainWOrk(){
        // уч год 21-22
        /*Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2021-2022\\обработано\\";
        Osnovnoe.date_start = "2021-09";
        Osnovnoe.date_end = "2022-02";*/

        // уч год 22-23
        Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\без экспертов\\";
//        Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\обработано\\";
        Osnovnoe.date_start = "2022-09";
        Osnovnoe.date_end = "2023-02";
        Osnovnoe.year_start = 2022;
        Osnovnoe.year_end = 2023;

        // уч год 23-24
        /*Osnovnoe.workingPath = "\\отчетыМного\\отчеты наставников 2023-2024\\отчеты наставников\\";
        Osnovnoe.date_start = "2023-09";
        Osnovnoe.date_end = "2024-02";*/




        ////////////////////
        Osnovnoe.jsonCreatingFileName = "отчетыОБработано";







        ///////////////////////////////////////////////////////////////////////////////////////////
        new DBAdmin();
        ///////////////////////////////////////////////////////////////////////////////////////////
        /*ArrayList<DBHonoric> dbHonorics = new Sravnitel().getDbHonoricsThroughMyNode();
        new DBFileCreator(dbHonorics);*/
    }
    public static void certinFileWork(){
        Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\без экспертов\\";
        new FilesAdmin().simple();
    }
    public static void sideWork(){
        Osnovnoe.workingPath = "\\отчетыМного\\отчеты 2022-2023\\отчеты\\без экспертов\\";
        new FilesAdmin().multiple();
    }
}