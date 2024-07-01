import FileWork.FileFinder;
import FileWork.JSON.DataTransmitter;
import FileWork.ReadDocxFile;
import FileWork.Visor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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