package MyPach.FileWork;



import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.SupervisorFio;
import MyPach.Osnovnoe;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFDataExtractor{
    private String fileName;
    private PDDocument pdfDoc;
    private PDFTextStripper textStripper;
    private String text;
    private ArrayList<SupervisorFio> supervisorFios;
    public PDFDataExtractor(String fileName, String workDirectory){
        /*
        - вызывается getFileReport
        - он создает объект FileReport вызывая другие функции, которые вытаскивают нужные данные
         */
        this.fileName = fileName;
        try {
            pdfDoc = PDDocument.load(new File(workDirectory + fileName));
            textStripper = new PDFTextStripper();
            text = textStripper.getText(pdfDoc);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public FileReport getFileReport(){
        return new FileReport(fileName, getProjectTitle(), getSupervisorFIO(), getSupervisorEmail(), getReview());
    }
    public String getProjectTitle() {
        String projectTitle = "";

        String[] errText = text.split("\n");
        boolean t = false; // что-то вроде переключателя или же передатчик сигнала тригера
        for (String i : errText) {
            if (t) {
                if (Osnovnoe.lewenstain(i, "Краткое описание проекта") <= 3)
                    break;
                projectTitle += i.trim() + "\n";
            } else if (Osnovnoe.lewenstain(i, "Название проекта ") <= 3) // это триггер
                t = true;
        }
        if (projectTitle.trim().length() == 0){
            System.out.println("------------------------------------------------------------");
            System.out.println("getProjectTitle - че-то не может найти название\n" + fileName);
            System.out.println("------------------------------------------------------------");
        }
        return projectTitle.trim();
    }
    public String getSupervisorFIO(){
        if (supervisorFios == null)
            supervisorFios = JSONDataExtractor.getSupervisorFios();

        String[] errText = text.split("\n");
        for (String i : errText){

            String checkRes = checkFIO(i);
            if (checkRes != null) {
                return checkRes;
            }
        }
        return null;
    }
    public String getSupervisorEmail(){
        String supervisorEmail = null;

        String[] errText = text.split("\n");
        for (String i : errText){
            if (DocxDataExtractor.chechEmail(i) != null){
                supervisorEmail = DocxDataExtractor.chechEmail(i);
                break;
            }
        }
        return supervisorEmail;
    }
    public String getReview(){
        String endResult = "";

        String[] errText = text.split("\n");
        boolean t = false; // что-то вроде переключателя или же передатчик сигнала тригера
        for (String i : errText) {
            if (t) {
                if (i.indexOf("Использование материалов") != -1)
                    break;
                endResult += i.trim() + "\n";
                endResult += i.trim() + " ";
            } else if ((i.indexOf("Фактически полученный продуктовый результат")) != -1) // это триггер
                t = true;
        }
        return endResult.trim();
    }

    // Checkers
    private String checkFIO(String fio){
        /* Вот такие формати ФИО ищет
            Лена Ано Лео
            Агрошиц Г.Т.
            Именин Т. В.    - здесь пробел есть
         */
        String fioPattern = "([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)|([А-Я][а-я]+\\s[А-Я]\\.[А-Я]\\.)|([А-Я][а-я]" +
                "+\\s[А-Я]\\.\\s[А-Я]\\.)";
        Pattern pattern = Pattern.compile(fioPattern);

        Matcher matcher = pattern.matcher(fio);
        if (matcher.find()) {
            for (SupervisorFio supervisorFio : supervisorFios)
                if (supervisorFio.equals(matcher.group()))
                    return matcher.group();
        }
        return null;
    }
}