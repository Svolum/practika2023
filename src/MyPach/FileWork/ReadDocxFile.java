package MyPach.FileWork;

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ReadDocxFile {
    private String fileName;
    private XWPFDocument docs;
    private Othcet othcet;
    public ReadDocxFile(String fileName, String workDirectory){
        this.fileName = fileName;
        this.othcet = new Othcet();

        try{
            FileInputStream fis = new FileInputStream(workDirectory + fileName);
            docs = new XWPFDocument(fis);
        }
        catch (FileNotFoundException e){
            System.out.println(e.getLocalizedMessage());
        }
        catch (Exception e){
            System.out.println(fileName);
            System.out.println(e.getMessage());
        }
        fillOthcet();
    }
    public void fillOthcet(){
        othcet.setTitle(getProjectTitle());
        ArrayList<String> fios = getSupervisorFIOs();
        if (fios.size() != 0)
            othcet.setFio(fios.get(0));
        ArrayList<String> emails = getEmails();
        if (emails.size() != 0)
            othcet.setEmail(emails.get(0));
        othcet.setReview(getReview());
    }


    // File data getters
    public String getProjectTitle(){
        XWPFTableCell cell = docs.getTableArray(0).getRow(7).getCell(0);
        if (cell != null)
            return cell.getText();
        return null;
    }
    public ArrayList<String> getSupervisorFIOs(){
        ArrayList<String> fios = new ArrayList<>();
        // предполагается, что там должно быть название поля, но иногда помещается имя препода
        // Самое сложное, когда имена лежат и там, и тут
        // В таких случаях приоритет буду отдачать тому что сверху
        XWPFTableCell titileOfFieldcell = docs.getTableArray(0).getRow(1).getCell(0);
        for (XWPFParagraph paragraph : titileOfFieldcell.getParagraphs()) {
            String text = paragraph.getText();


            if (checkFIO(text) != null)
                fios.add(checkFIO(text));
        }

        // проверка того поля, где фио должно лежать
        XWPFTableCell cell = docs.getTableArray(0).getRow(2).getCell(0);
        for (XWPFParagraph paragraph : cell.getParagraphs()){
            String text = paragraph.getText();

            //System.out.println(text + " | " + checkFIO(text));
            if (checkFIO(text) != null)
                fios.add(checkFIO(text));
        }
        return fios;
    }
    public ArrayList<String> getEmails(){
        ArrayList<String> emails = new ArrayList<>();
        XWPFTableCell titleCell = docs.getTableArray(0).getRow(3).getCell(0);
        for (XWPFParagraph paragraph : titleCell.getParagraphs()){
            String text = paragraph.getText();
            if (chechEmail(text) != null){
                emails.add(chechEmail(text));
            }
        }

        XWPFTableCell cell = docs.getTableArray(0).getRow(4).getCell(0);
        for (XWPFParagraph paragraph : cell.getParagraphs()){
            String text = paragraph.getText();
            if (chechEmail(text) != null){
                emails.add(chechEmail(text));
            }
        }
        return emails;
    }
    public String getReview(){
        XWPFTableCell cell = docs.getTableArray(0).getRow(11).getCell(0);
        String endResult = cell.getText();
        return endResult;
    }


    // CHECKERS
    public static String checkFIO(String fio){
        String fioPattern = "([А-Яа-я]+\\s[А-Яа-я]+\\s[А-Яа-я]+)|([А-Яа-я]+\\s[А-Яа-я]\\.[А-Яа-я]\\.)|([А-Яа-я]+\\s[А-Яа-я]\\.[А-Яа-я]\\.)";
        Pattern pattern = Pattern.compile(fioPattern);
        Matcher matcher = pattern.matcher(fio);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    public static String chechEmail(String email){
        String emailPattern = "^.+@.+\\.\\w+";
        Pattern pattern = Pattern.compile(emailPattern);

        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    // GETTERS AND SETTERS
    public String getFileName() {
        return fileName;
    }
    public Othcet getOthcet() {
        return othcet;
    }
    public void setOthcet(Othcet othcet) {
        this.othcet = othcet;
    }
}