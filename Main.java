import MyPach.AdminClasses.DBAdmin;
import MyPach.AdminClasses.FilesAdmin;
import MyPach.Osnovnoe;

public class Main {
    public static void main(String[] args) {
        /* название папки, в которой лежат отчеты, учебный год, к которому они принадлежат
        а так же имя создаваемого .json файла надо прописать самостоятельно
         */
//        mainWOrk();
//        sideWork();
//        certinFileWork();
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
        Osnovnoe.setYears();

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