package MyPach.FileWork;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DocxDataExtractor {
    private String fileName;
    private XWPFDocument docs;
    public DocxDataExtractor(String fileName, String workDirectory){
        this.fileName = fileName;

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
    }
    // some getters
    public Othcet getOtchet(){
        return new Othcet(fileName, getProjectTitle(), getSupervisorFIO(), getSupervisorEmail());
    }
    public String getProjectTitle(){
        String projectTitle = "";

        List<XWPFTableRow> tableRows = docs.getTableArray(0).getRows();
        // The row where program starts to searching the header of title
        int row = 4;
        boolean isTitleHeadLineFinded = false;
        while (!isTitleHeadLineFinded) {
            XWPFParagraph paragraph = tableRows.get(row).getCell(0).getParagraphArray(0);

            // if Find desired "Название проекта"
            if (paragraph.getText().contains("Название проекта")) {
                isTitleHeadLineFinded = true;
                break;
            }
            row++;

            // 6 is random number i pucked up
            if (row > tableRows.size() - 6) { // this 6
                System.out.println("------------------------------------------------------------");
                System.out.println("getProjectTitle - че-то не может найти название\n" + fileName);
                System.out.println("------------------------------------------------------------");
                break;
            }
        }
        XWPFTableCell cell = tableRows.get(row + 1).getCell(0);

        // иногда в ячейку запихнуто что-то вроде формы и в таком случае, ячейка больше не ячейка, а что-то другое
        if (cell == null)
            // И надо ее обрабатывать уже так, это что-то вроде формы для заполнения с какими-то доп параметрами
            return ((XWPFSDTCell) tableRows.get(row + 1).getTableICells().get(0)).getContent().getText().trim();

        // it read only first abzac(paragraph), not all text from cell
        projectTitle = cell.getParagraphArray(0).getText().trim();

        // Название проекта может находится в той же ячейке где находится "Название проекта"
        if (projectTitle.length() < 3) {
            XWPFTableCell prevCell = tableRows.get(row).getCell(0);
            return prevCell.getText().replaceAll("Название проекта", "").trim();
        }
        return projectTitle;
    }
    public String getSupervisorFIO() {
        String supervisorFIO = null;

        boolean isFIOFinded = false;
        int row = 1;
        // To check all field, where supervisor can put fio
        while (!isFIOFinded) {
            XWPFTableCell cell = docs.getTableArray(0).getRow(row).getCell(0);

            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                String paragraphText = paragraph.getText();

                // if Find FIO in th
                if (checkFIO(paragraphText) != null) {
                    supervisorFIO = checkFIO(paragraphText);
                    isFIOFinded = true;
                    break;
                }
            }
            row++;
            // if it can't find FIO, it means there isn't fio in this file
            // но скорее всего прога ошибается и потом надо будет отлаживать
            if (row > 3) {
                break;
            }
        }
        return supervisorFIO;
    }
    public String getSupervisorEmail() {
        String supervisorEmail = null;

        boolean isEmailFinded = false;
        int row = 3;
        // To check all field, where supervisor can put fio
        while (!isEmailFinded) {
            XWPFTableCell cell = docs.getTableArray(0).getRow(row).getCell(0);
            for (XWPFParagraph paragraph : cell.getParagraphs()) {
                String paragraphText = paragraph.getText();

                // if Find email
                if (chechEmail(paragraphText) != null) {
                    supervisorEmail = chechEmail(paragraphText);
                    isEmailFinded = true;
                    break;
                }
            }
            row++;
            // if it can't find email, it means there isn't email in this file
            if (row > 7) {
                break;
            }
        }
        return supervisorEmail;
    }


    // CHECKERS
    public static String checkFIO(String fio){
        /* Вот такие формати ФИО ищет
            Лена Ано Лео
            Агрошиц Г.Т.
            Именин Т. В.    - здесь пробел есть
         */
        String fioPattern = "([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)|([А-Я][а-я]+\\s[А-Я]\\.[А-Я]\\.)|([А-Я][а-я]+\\s[А-Я]\\.\\s[А-Я]\\.)";
        Pattern pattern = Pattern.compile(fioPattern);

        Matcher matcher = pattern.matcher(fio);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    public static String chechEmail(String email){
        // взял regexp отсюда
        // https://dmtrvk.ru/2019/10/14/regexp-dlya-email/
        String emailPattern = "([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z0-9_-]+)";
        Pattern pattern = Pattern.compile(emailPattern);

        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}