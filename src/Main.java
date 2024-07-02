import MyPach.FileWork.FileFinder;
import MyPach.FileWork.ReadDocxFile;
import MyPach.Sravnitel;

public class Main {
    public static void main(String[] args) {
        //new FileFinder();
        //new DataTransmitter();
        //workWithCertainFile();
        new Sravnitel();
    }
    public static void workWithCertainFile(){
        ReadDocxFile rdf = new ReadDocxFile("Example.docx", System.getProperty("user.dir") + "/отчеты/");
    }
}