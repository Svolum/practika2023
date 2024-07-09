package MyPach.FileWork;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;


public class FileTypeScanner {
    private String workDirectory;
    private ArrayList<Othcet> othcets;
    public FileTypeScanner(){
        workDirectory = getCurrentDirrectory() + "/отчеты/";
        othcets = new ArrayList<>();
    }
    public void fillOtchets(){
        for (String fileName : getDocxFileNames()){
            DocxDataExtractor dde = new DocxDataExtractor(fileName, workDirectory);
            //System.out.println(dde.getProjectTitle() + "\n" + fileName + "\n\n");
            //System.out.println(dde.getProjectTitle() + "\n");

            othcets.add(dde.getOtchet());
        }
    }
    public void showOthcetsInfo(){
        fillOtchets();

        for (Othcet othcet : othcets){
            if (othcet.getFio() == null) {
                System.out.println("nully fio");
            }
        }
    }

    // GETTERS
    public ArrayList<Othcet> getOthcets() {
        if (othcets.size() == 0)
            fillOtchets();
        return othcets;
    }
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
    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return index == -1? null : fileName.substring(index);
    }
    public static String getCurrentDirrectory(){
        return System.getProperty("user.dir");
    }
}
