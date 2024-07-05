package MyPach.FileWork;

import java.io.File;
import java.util.ArrayList;

public class FileFinder {
    private String workDirectory;
    private ArrayList<ReadDocxFile> docxFiles;
    public ArrayList<ReadDocxFile> getDocxFiles() {
        return docxFiles;
    }
    public FileFinder(){
        docxFiles = new ArrayList<>();

        workDirectory = getCurrentDirrectory() + "/отчеты/";


        for (String fileName : getFileNames()){
            ReadDocxFile rdf = new ReadDocxFile(fileName, workDirectory);
            docxFiles.add(rdf);
        }
        //new WorkWithDocxs(docxFiles);
    }
    class WorkWithDocxs {
        private ArrayList<ReadDocxFile> docxFiles;
        public WorkWithDocxs(ArrayList<ReadDocxFile> docxFiles){
            this.docxFiles = docxFiles;
            main();
        }
        public void main(){
            showAllInfo();
        }
        public void showAllInfo(){
            for (ReadDocxFile rdf : docxFiles){

                System.out.println(rdf.getFileName());

                Othcet othcet = rdf.getOthcet();
                System.out.println(othcet.getTitle());
                System.out.println(othcet.getFio());
                System.out.println(othcet.getEmail());
                System.out.println(othcet.getReview());
                System.out.println("\n\n------------------------------------------------------------------------------------------------------\n\n");
            }
        }
    }
    public static String getCurrentDirrectory(){
        return System.getProperty("user.dir");
    }
    // Список всех фалов с расширением .docx 
    public ArrayList<String> getFileNames(){
        ArrayList<String> fileNames = new ArrayList<>();

        File folder = new File(workDirectory);
        File[] masOfFiles = folder.listFiles();
        if (masOfFiles != null)
            for (File file : masOfFiles){
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (checkTypeOfFileForDocx(fileName))
                        fileNames.add(fileName);
                }
            }
        return fileNames;
    }
    public boolean checkTypeOfFileForDocx(String fileName){
        if (fileName.charAt(0) == '~' && fileName.charAt(1) == '$')
            return false;

        int len = fileName.length();
        if (fileName.charAt(len - 5) == '.' && fileName.charAt(len - 4) == 'd' && fileName.charAt(len - 3) == 'o'
                && fileName.charAt(len - 2) == 'c' && fileName.charAt(len - 1) == 'x')
            return true;
        return false;
    }
}
