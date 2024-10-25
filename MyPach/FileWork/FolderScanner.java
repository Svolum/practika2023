package MyPach.FileWork;

import MyPach.Osnovnoe;

import java.io.File;
import java.util.ArrayList;

public class FolderScanner {
    private String workDirectory;
    public FolderScanner(){
        /*
        как рабоает класс:
        - Вызывается функция getFileReports
        - он вызывает getFileDocxReports, getFilePdfReports, getFileDocReports
        - они, каждый сканирует папку в поисках своего расширения, создают массив, и все 3 массива объеденяются в 1
         */
        workDirectory = Osnovnoe.getCurrentDirrectory() + Osnovnoe.workingPath;
    }
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1? null : fileName.substring(index + 1); // "+ 1" что бы точку убрать
    }
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
        return fileReports;
    }

    // GET Files certain extention names
    public ArrayList<FileReport> getFileDocxReports(){
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
        return fileDocReports;
    }

    // GETTERS & SETTERS
    public String getWorkDirectory(){
        return  workDirectory;
    }
}
