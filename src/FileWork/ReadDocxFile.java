package FileWork;

import org.apache.commons.compress.archivers.ar.ArArchiveEntry;
import org.apache.poi.xwpf.usermodel.*;

import javax.swing.plaf.ViewportUI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadDocxFile {
    private String fileName;
    private FileInputStream fis;
    private XWPFDocument docs;
    private Visor visor;
    private Othcet othcet;
    public ReadDocxFile(String fileName, String workDirectory){
        this.fileName = fileName;
        this.othcet = new Othcet();

        try{
            fis = new FileInputStream(workDirectory + fileName);
            docs = new XWPFDocument(fis);
        }
        catch (FileNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }
        catch (Exception e){
            System.out.println(fileName);
            System.out.println(e.getMessage());
        }
    }
    public void fillOthcet(){
        othcet.setTitle(getProjectTitle());
        othcet.setFio(getSupervisorFIOs().get(0));
        othcet.setEmail(getEmails().get(0));
        othcet.setReview(getReview());



        // Осталось только найти даты и сделать сопоставление
        // Надо создать отдельный класс - "отчет", в котором просто буду запонять поля
    }
    public ArrayList<String> getSupervisorFIOs(){
        ArrayList<String> fios = new ArrayList<>();
        // предполагается, что там должно быть название поля, но иногда помещается имя препода
        // Самое сложное, когда имена лежат и там, и тут
        // В таких случаях приоритет буду отдачать тому что сверху
        XWPFTableCell titileOfFieldcell = docs.getTableArray(0).getRow(1).getCell(0);
        for (XWPFParagraph paragraph : titileOfFieldcell.getParagraphs()) {
            String text = paragraph.getText();


            if (Visor.checkFIO(text) != null)
                fios.add(Visor.checkFIO(text));

            // чтобы только первого хранить, по которому сравнивать буду
            if (visor == null || visor.getFio() == null)
                visor = new Visor(text);
        }

        // проверка того поля, где фио должно лежать
        XWPFTableCell cell = docs.getTableArray(0).getRow(2).getCell(0);
        for (XWPFParagraph paragraph : cell.getParagraphs()){
            String text = paragraph.getText();

            //System.out.println(text + " | " + Visor.checkFIO(text));
            if (Visor.checkFIO(text) != null)
                fios.add(Visor.checkFIO(text));

            // чтобы только первого хранить, по которому сравнивать буду
            if (visor == null || visor.getFio() == null)
                visor = new Visor(text);
        }
        return fios;
    }
    public String getReview(){
        XWPFTableCell cell = docs.getTableArray(0).getRow(11).getCell(0);
        String endResult = cell.getText();
        return endResult;
    }
    public ArrayList<String> getEmails(){
        ArrayList<String> emails = new ArrayList<>();
        XWPFTableCell titleCell = docs.getTableArray(0).getRow(3).getCell(0);
        for (XWPFParagraph paragraph : titleCell.getParagraphs()){
            String text = paragraph.getText();
            if (Visor.chechEmail(text) != null){
                emails.add(Visor.chechEmail(text));
            }
            if (visor == null || visor.getEmail() == null){
                visor = new Visor("");
                visor.setEmail(Visor.chechEmail(text));
            }
        }

        XWPFTableCell cell = docs.getTableArray(0).getRow(4).getCell(0);
        for (XWPFParagraph paragraph : cell.getParagraphs()){
            String text = paragraph.getText();
            if (Visor.chechEmail(text) != null){
                emails.add(Visor.chechEmail(text));
            }
            if (visor == null || visor.getEmail() == null){
                visor = new Visor("");
                visor.setEmail(Visor.chechEmail(text));
            }
        }
        return emails;
    }

    // is not getter
    public String getProjectTitle(){
        XWPFTableCell cell = docs.getTableArray(0).getRow(7).getCell(0);
        return cell.getText();
    }


    // GETTERS AND SETTERS
    public String getFileName() {
        return fileName;
    }
    public Visor getVisor() {
        return visor;
    }
    public void setVisor(Visor visor) {
        this.visor = visor;
    }
}