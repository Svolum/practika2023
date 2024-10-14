package MyPach.AdminClasses;

import MyPach.FileWork.*;
import MyPach.Osnovnoe;

import java.awt.desktop.AboutEvent;
import java.util.ArrayList;
import java.util.Scanner;

public class FilesAdmin {
    private FolderScanner folderScanner;
    private ArrayList<FileReport> reports;
    private FileReport curReport;
    public FilesAdmin(){
        folderScanner = new FolderScanner();

//        simpleRead();
//        multipleRead();
//        advancedRead();
    }
    public void simpleRead(){ // read one certain file
        /*PDFDataExtractor dataExtractor = new PDFDataExtractor("myExample.pdf", Osnovnoe.getWorkingDirectory());
        System.out.println(dataExtractor.getProjectTitle());*/
        /*DocDataExtractor docDataExtractor = new DocDataExtractor("example.doc", Osnovnoe.getWorkingDirectory());
        DocDataExtractor docDataExtractor = new DocDataExtractor("example.doc", Osnovnoe.getWorkingDirectory());*/

    }
    public void multipleRead(){
        System.out.println("Выбери как именно обработать файлы:\n" +
                "0 - обработать файлы всех типов\n" +
                "1 - обработать файлы типа docx\n" +
                "2 - обработать файлы типа doc\n" +
                "3 - обработать файлы типа pdf\n" +
                "Что-то помимо цифр вызовет ошибку!!!");
        switch (Integer.parseInt(new Scanner(System.in).nextLine())){
            case 0:{
                System.out.println("пока ничего нету");
                break;
            }
            case 1:{
                ArrayList<FileReport> fileDocxReports = folderScanner.getFileDocxReports(); // здесь специально для наглядности так
                for (FileReport fileDocxReport : fileDocxReports){ // здесь специально для наглядности так
                    System.out.println(fileDocxReport.getTitle());
                }
                break;
            }
            case 2:{
                System.out.println("пока ничего нету");
                break;
            }
            case 3:{
                System.out.println("пока ничего нету");
                break;
            }
        }
    }
    public void advancedRead(){ // сейчас выступает в качестве "lol" или точнее функция режима разработчика
        /*for (var i : folderScanner.getFilePdfReports()){
            System.out.println(i);
            System.out.println("" +
                    "////////////////////////////////////////////////////////////////////////////////////////////////");
        }*/

        /*PDFDataExtractor dataExtractor =
                new PDFDataExtractor("myExample.pdf", folderScanner.getWorkDirectory());
        System.out.println(dataExtractor.getFileReport());*/
        folderScanner.getFileDocReports();
    }
    public ArrayList<FileReport> getData(){
        return folderScanner.getFileReports();
    }
}
