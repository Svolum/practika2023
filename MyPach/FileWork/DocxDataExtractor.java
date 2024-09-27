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
    private XWPFDocument docxs;
    public DocxDataExtractor(String fileName, String workDirectory){
        this.fileName = fileName;

        try{
            FileInputStream fis = new FileInputStream(workDirectory + fileName);
            docxs = new XWPFDocument(fis);
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
        return new Othcet(fileName, getProjectTitle(), getSupervisorFIO(), getSupervisorEmail(), getReview());
    }
    public String getProjectTitle(){
        String projectTitle = "";

        List<XWPFTableRow> tableRows = docxs.getTableArray(0).getRows();
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
            XWPFTableCell cell = docxs.getTableArray(0).getRow(row).getCell(0);

            // Если ячейка почемуто пустая, может быть там форма
            if (cell.getText().isEmpty()) {
                try {
                    //XWPFTableCell lol = (XWPFTableCell)docxs.getTableArray(0).getRow(row).getTableICells().get(0);
                    // стоит заметить что это просто cell, не SDTCell,  возможно возникнут проблемы, а они возникнут, поэтому надо обработать оба варианта или привести к одному
                    // Первый здесь не сработал, поэтому склоняюсь к этому

                    // Здесь другой способ достать данные из формы
                    supervisorFIO = ((XWPFTableCell) docxs.getTableArray(0).getRow(row).getTableICells().get(0)).getTextRecursively().trim();
                    isFIOFinded = true;
                } catch (Exception e){
                    // а здесь ничего, потому что ячейка может быть просто пустой
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
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
            XWPFTableCell cell = docxs.getTableArray(0).getRow(row).getCell(0);
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
    public String getReview(){
        List<XWPFTableRow> tableRows = docxs.getTableArray(0).getRows();
        // The row where program starts to searching the header of title
        int row = 7;
        boolean isTitleReviewFinded = false;
        while (!isTitleReviewFinded) {
            String text = "";
            try{
                text = tableRows.get(row).getCell(0).getParagraphArray(0).getText();
            }catch (Exception e){
                text = ((XWPFSDTCell)tableRows.get(row).getTableICells().get(0)).getContent().getText();
            }

            // if Find desired "Название проекта"
            if (text.contains("Фактически полученный продуктовый результат")) {
                isTitleReviewFinded = true;
                break;
            }
            row++;
            // 4 is random number i pucked up
            if (row > tableRows.size() - 4) { // this 4
                System.out.println("------------------------------------------------------------");
                System.out.println("getReview - че-то не может найти название фактического результата\n" + fileName);
                System.out.println("------------------------------------------------------------");
                break;
            }
        }


        ArrayList<XWPFParagraph> paragraphs = (ArrayList<XWPFParagraph>) docxs.getTableArray(0).getRow(row + 1).getCell(0).getParagraphs();
        String endResult = "";
        for (XWPFParagraph paragraph : paragraphs){
            endResult += paragraph.getText().trim();

            // Если точки(или какого-то спец символа) между абзацами нет, то ставим ее
            if (Character.isLetter(endResult.charAt(endResult.length()-1)))
                endResult += '.';


            // просто добавляет перенос строки, ПОХОЖЕ НАДО УДАЛИТЬ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            if (paragraphs.lastIndexOf(paragraph) != (paragraphs.size() - 1))
                endResult += "\n";
        }
        return endResult;
    }


    // CHECKERS
    public static String checkFIO(String fio){
        /* Вот такие форматы ФИО ищет
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