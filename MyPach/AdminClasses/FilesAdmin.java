package MyPach.AdminClasses;

import MyPach.FileWork.*;
import MyPach.Osnovnoe;
import MyPach.Sravnitel;

import java.awt.desktop.AboutEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesAdmin {
    private FolderScanner folderScanner;
    public FilesAdmin(){
        /* этот класс существует для поддержания структуры программы
         */
        folderScanner = new FolderScanner();
    }
    public ArrayList<FileReport> getData(){
        return folderScanner.getFileReports();
    }
    public void simple(){
        DocxDataExtractor dataExtractor = new DocxDataExtractor
                ("Гусакова Г.С. Отчет наставника (v2023) ТПБ-19.docx", Osnovnoe.getWorkingDirectory());
        String review = dataExtractor.getReview();
        System.out.println(review);
    }
    public void multiple(){
        for (FileReport fileReport : folderScanner.getFileReports()){
            System.out.println(fileReport.getFileName());
            System.out.println(fileReport.getReview());
            System.out.println("/////////////////////////////////////////////////////////////////////////////////////");
        }
    }
}
