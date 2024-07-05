package MyPach.FileWork;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
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
    public ArrayList<String> getSupervisorFIOs(){
        ArrayList<String> fios = new ArrayList<>();

        XWPFTableCell titileOfFieldcell = docs.getTableArray(0).getRow(1).getCell(0);
        for (XWPFParagraph paragraph : titileOfFieldcell.getParagraphs()) {
            String text = paragraph.getText();


            if (checkFIO(text) != null) {
                fios.add(checkFIO(text));
                break;
            }
        }


        XWPFTableCell cell = docs.getTableArray(0).getRow(2).getCell(0);
        for (XWPFParagraph paragraph : cell.getParagraphs()){
            String text = paragraph.getText();

            //System.out.println(text + " | " + checkFIO(text));
            if (checkFIO(text) != null)
                fios.add(checkFIO(text));
        }
        return fios;
    }
    public void showAllParagraphs(){
        for (XWPFParagraph paragraph : docs.getParagraphs()){
            System.out.println(paragraph.getText());
        }
        for (var lol: docs.getHeaderList()){
            System.out.println(lol.getText());
        }
        //XWPFHeaderFooterPolicy
    }
    public String getProjectTitle(){
        XWPFTableCell cell = docs.getTableArray(0).getRow(7).getCell(0);
        if (cell != null)
            return cell.getText();
        return null;
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
        ArrayList<XWPFParagraph> paragraphs = (ArrayList<XWPFParagraph>) docs.getTableArray(0).getRow(11).getCell(0).getParagraphs();
        int size = paragraphs.size();
        String endResult = "";
        for (XWPFParagraph paragraph : paragraphs){
            endResult += paragraph.getText().replaceFirst(" ", "");
            char ch = endResult.charAt(endResult.length()-1);
            // Если точки(или какого-то спец символа) между абзацами нет, то ставим ее
            if (Character.isLetter(ch) || ch == ' ')
                endResult += '.';
            // просто добавляет перенос строки, ПОХОЖЕ НАДО УДАЛИТЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (paragraphs.lastIndexOf(paragraph) != (size - 1))
                endResult += "\n";
        }
        return endResult;
    }


    // CHECKERS
    public static String checkFIO(String fio){
        String fioPattern = "([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)|([А-Я][а-я]+\\s[А-Я]\\.[А-Я]\\.)|([А-Я][а-я]+\\s[А-Я]\\.\\s[А-Я]\\.)";
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
}