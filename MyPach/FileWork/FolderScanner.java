package MyPach.FileWork;

import MyPach.AdminClasses.FileTypeScanner;
import MyPach.Osnovnoe;

import java.io.File;
import java.util.ArrayList;

public class FolderScanner {
    private String workDirectory;
    private ArrayList<FileReport> fileReports;
    public FolderScanner(){
        /*
        как рабоает класс:
        - "getPDFileNames" Сканирует папку отчеты и получает названия файлов типа pdf, docx, doc
        - "getFileExtension" используется внутри "getPDFileNames" для получения и последующей проверки расширения
            файла
        - ""
         */
//        workDirectory = getCurrentDirrectory() + "/отчеты/";
        workDirectory = getCurrentDirrectory() + Osnovnoe.workingPath;
//        workDirectory = getWorkDirectory();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        ///// РАЗКОММЕНТИТЬ, наверное? или удалить это поле?
//        fileReports = getFileReports();
    }
    // Static methods
    public static String getCurrentDirrectory(){
        return System.getProperty("user.dir");
    }
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1? null : fileName.substring(index + 1); // "+ 1" что бы точку убрать
    }
    // Getters with some logic
    public ArrayList<String> getFileNames(String extention){
        extention = extention.replace(".", "");
        ArrayList<String> fileNames = new ArrayList<>();

        File[] masOfFiles = new File(workDirectory).listFiles();
        if (masOfFiles != null)
            for (File file : masOfFiles){
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (getFileExtension(fileName).equals(extention))
                        fileNames.add(fileName);
                }
            }
        return fileNames;
    }
    public ArrayList<FileReport> getFileReports(){
        ArrayList<FileReport> fileReports = new ArrayList<>();

        fileReports.addAll(getFileDocxReports());
        fileReports.addAll(getFilePdfReports());
        fileReports.addAll(getFileDocReports());
        // здесь будет что-то вроде добавить массив к массиву
        return fileReports;
    }
    public ArrayList<FileReport> getFileDocxReports(){ // и даже сдезь я не могу полностью доверять этому коду
        ArrayList<FileReport> fileDocxReports = new ArrayList<>();
        DocxDataExtractor dataExtractor;

        for (String fileName : getFileNames(".docx")){
            dataExtractor = new DocxDataExtractor(fileName, workDirectory);

            fileDocxReports.add(dataExtractor.getFileReport());
        }
        return fileDocxReports;
    }
    public ArrayList<FileReport> getFilePdfReports(){
        ArrayList<FileReport> filePdfReports = new ArrayList<>();
        PDFDataExtractor dataExtractor;

        for (String fileName : getFileNames(".pdf")){
            dataExtractor = new PDFDataExtractor(fileName, workDirectory);

            filePdfReports.add(dataExtractor.getFileReport());
        }
        return filePdfReports;
    }
    public ArrayList<FileReport> getFileDocReports(){
        ArrayList<FileReport> fileDocReports = new ArrayList<>();
        DocDataExtractor dataExtractor;

        int a = 0;
        for (String fileName : getFileNames(".doc")){
            a++;
            dataExtractor = new DocDataExtractor(fileName, workDirectory);

            fileDocReports.add(dataExtractor.getFileReport());
        }
        // show some data to ensure
        /*System.out.println("FolderScanner");
        System.out.println("getFileNames(\".pdf\")="+getFileNames(".doc").size());
        System.out.println("a = " + a);
        System.out.println("fileDocReports=" + fileDocReports.size());
        System.out.println("FolderScanner");*/
        return fileDocReports;
    }
    // Simple field getters
    public String getWorkDirectory(){
        return  workDirectory;
    }
}
