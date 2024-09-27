import MyPach.FileWork.DocxDataExtractor;
import MyPach.FileWork.FileTypeScanner;
import MyPach.FileWork.PDFDataExtractor;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.OtchetNeedReview;
import MyPach.Sravnitel;
import MyPach.FileWork.Othcet;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
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
        someWorkWithPdfFiles();
    }
    public static void compareOtchets(){
        String otchetName = "Культура безопасности как элемент снижения уровня профессиональных рисков» (Издательство ИРНИТУ, Институт  вечерне-заочного обучения ИРНИТУ)».docx";
        Othcet othcet = new FileTypeScanner().getOtchet(otchetName);

        String jsonTitle = "Профессиональные риски на объектах экономики Иркутской областиКультура безопасности как элемент снижения уровня профессиональных рисков";
        OtchetNeedReview jsonOtchet = new JSONDataExtractor().getOtchetNeedReview(jsonTitle);

        System.out.println(othcet.getTitle());
        System.out.println(Sravnitel.remainOnlyWords(othcet.getTitle()));
        System.out.println(jsonOtchet.getTitle());
        System.out.println(Sravnitel.remainOnlyWords(jsonOtchet.getTitle()));

        System.out.println(Sravnitel.lewenstain(othcet.getTitle(), jsonOtchet.getTitle()) + "\t\t| Sravnitel.lewenstain");
        System.out.println(Sravnitel.compareJsonAndFile(jsonOtchet, othcet) + "\t| Sravnitel.compareJsonAndFile");
        System.out.println((othcet.getTitle().contains(jsonOtchet.getTitle()) || jsonOtchet.getTitle().contains(othcet.getTitle())) + "\t| contains");

        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println(othcet);
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