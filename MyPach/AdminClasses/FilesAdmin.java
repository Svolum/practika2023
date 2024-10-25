package MyPach.AdminClasses;

import MyPach.FileWork.*;
import MyPach.Osnovnoe;

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
}
