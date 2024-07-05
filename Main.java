import MyPach.FileWork.DocxDataExtractor;
import MyPach.FileWork.FileTypeScanner;
import MyPach.Sravnitel;

public class Main {
    public static void main(String[] args) {
        //new FileFinder();
        //new JSONDataExtractor();
        //workWithCertainFile();
        //new Sravnitel();


        //new FileTypeScanner().showOthcetsInfo();
        //workWithCertainFile();
        new Sravnitel();

    }
    public static void workWithCertainFile(){
        DocxDataExtractor rdf = new DocxDataExtractor("Использование низкопотенциальной теплоты системы оборотного водоснабжения ТЭЦ .docx", System.getProperty("user.dir") + "/отчеты/");
        System.out.println(rdf.getProjectTitle());
    }
}