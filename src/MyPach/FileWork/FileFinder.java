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


        int numberOfFilesFinded = 0;
        for (String fileName : getFileNames()){
            String message = "";


            // File name
            message += "File name: \n" + fileName + "\n";


            // work directly with file
            ReadDocxFile rdf = new ReadDocxFile(fileName, workDirectory);

            // Get all general visors
            message += "Visors: \n";
            ArrayList<String> textFIOS = rdf.getSupervisorFIOs();
            for (String textFIO : textFIOS){
                message += textFIO + "\n";
            }

            // GET all emails
            message += "Email: \n";
            ArrayList<String> emails = rdf.getEmails();
            for (String email : emails){
                message += email + "\n";

            }

            // Get review (or result)
            message += "End result: \n";
            message += rdf.getReview() + "\n";
            message += "\n\n------------------------------------------------------------------------------------------------------\n\n\n";

            //System.out.println(message);
            docxFiles.add(rdf);


            numberOfFilesFinded++;
        }
        System.out.println("number of files found = " + numberOfFilesFinded);
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
            int i = 0;
            for (ReadDocxFile rdf : docxFiles){
                i++;

                System.out.println(rdf.getFileName());

                Othcet othcet = rdf.getOthcet();
                System.out.println(othcet.getTitle());
                System.out.println(othcet.getFio());
                System.out.println(othcet.getEmail());
                System.out.println(othcet.getReview());
                System.out.println("\n\n------------------------------------------------------------------------------------------------------\n\n");
            }
            System.out.println("i = " + i);
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
