import MyPach.FileWork.FileFinder;
import MyPach.FileWork.ReadDocxFile;

public class Main {
    public static void main(String[] args) {
        new FileFinder();
        //new DataTransmitter();
        //workWithCertainFile();
    }
    public static void workWithCertainFile(){
        ReadDocxFile rdf = new ReadDocxFile("Example.docx", System.getProperty("user.dir") + "/отчеты/");

        System.out.println(rdf.getProjectTitle());
    }
}