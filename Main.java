import MyPach.FileWork.DocxDataExtractor;
import MyPach.FileWork.FileTypeScanner;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.OtchetNeedReview;
import MyPach.Sravnitel;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //new FileFinder();
        //new JSONDataExtractor();
        //workWithCertainFile();
        //new Sravnitel();


        //new FileTypeScanner().showOthcetsInfo();
        //workWithCertainFile();
        new Sravnitel();
        // разобрать json данные
        //checkJSON();

        //System.out.println(Sravnitel.myContains("hello world", "1here"));
    }
    public static void workWithCertainFile(){
        DocxDataExtractor rdf = new DocxDataExtractor("Конструкция и система управления навесными модулями настольного станка с ЧПУ.docx", System.getProperty("user.dir") + "/отчеты/");
        System.out.println(rdf.getSupervisorFIO());
    }
    public static void checkJSON(){
        System.out.println((new JSONDataExtractor().getOthcetsNeedReviews()).size());
        int a = 0;
        for (var lol : (new JSONDataExtractor().getOthcetsNeedReviews())){
            if (lol.getData_start().contains("2023-02")) {
                System.out.println(lol.getPrev_id());
                a++;
            }
        }
        System.out.println(a);
    }
}