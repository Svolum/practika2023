package MyPach.AdminClasses;

import MyPach.FileWork.DocxDataExtractor;
import MyPach.FileWork.FileReport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;


public class FileTypeScanner {
    private String workDirectory;
    private ArrayList<FileReport> fileReports;

    public FileTypeScanner(){
        workDirectory = getCurrentDirrectory() + "/отчеты/";
        fileReports = new ArrayList<>();
    }

    // STATIC methods
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1? null : fileName.substring(index);
    }
    public static String getCurrentDirrectory(){
        return System.getProperty("user.dir");
    }
    ///////////////////////
    public void fillOtchets(){
        for (String fileName : getDocxFileNames()){
            DocxDataExtractor dde = new DocxDataExtractor(fileName, workDirectory);
            //System.out.println(dde.getProjectTitle() + "\n" + fileName + "\n\n");
            //System.out.println(dde.getProjectTitle() + "\n");

            fileReports.add(dde.getFileReport());
        }
    }
    public void showOthcetsInfo(){
        fillOtchets();

        for (FileReport fileReport : fileReports){
            if (fileReport.getFio() == null) {
                System.out.println("nully fio");
            }
        }
    }

    // Some getters with some logic
    public ArrayList<String> getDocxFileNames(){
        ArrayList<String> fileNames = new ArrayList<>();

        File[] masOfFiles = new File(workDirectory).listFiles();
        if (masOfFiles != null)
            for (File file : masOfFiles){
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (getFileExtension(fileName).equals(".docx"))
                        fileNames.add(fileName);
                }
            }
        return fileNames;
    }
    public ArrayList<String> getPDFileNames(){
        ArrayList<String> fileNames = new ArrayList<>();

        File[] masOfFiles = new File(workDirectory).listFiles();
        if (masOfFiles != null)
            for (File file : masOfFiles){
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (getFileExtension(fileName).equals(".pdf"))
                        fileNames.add(fileName);
                }
            }
        return fileNames;
    }
    public FileReport getOtchet(String otchetName){
        for (String fileName : getDocxFileNames()){
            if (fileName.equals(otchetName)) {
                DocxDataExtractor dde = new DocxDataExtractor(fileName, workDirectory);
                return dde.getFileReport();
            }
        }
        return null;
    }
    public HashSet<String> getAllFileTypes(){
        HashSet<String> fileTypes = new HashSet<>();
        File[] masOfFiles = new File(workDirectory).listFiles();
        if (masOfFiles != null)
            for (File file : masOfFiles){
                if (file.isFile()) {
                    String fileName = file.getName();
                    fileTypes.add(getFileExtension(fileName));
                }
            }
        return fileTypes;
    }


    // Field GETTERS
    public ArrayList<FileReport> getOthcets() {
        if (fileReports.size() == 0)
            fillOtchets();
        return fileReports;
    }
    public String getWorkDirectory() {
        return workDirectory;
    }
}
