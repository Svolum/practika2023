package MyPach.FileWork;

import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.SupervisorFio;
import MyPach.Osnovnoe;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocDataExtractor {
    private String fileName;
    private HWPFDocument doc;
    private WordExtractor wordExtractor;
    private ArrayList<SupervisorFio> supervisorFios;
    public DocDataExtractor(String  fileName, String workDirectory){
        /*
        - вызывается getFileReport
        - он создает объект FileReport вызывая другие функции, которые вытаскивают нужные данные
         */
        this.fileName = fileName;
        try {
            FileInputStream fis = new FileInputStream(workDirectory + fileName);
            doc = new HWPFDocument(fis);
            wordExtractor = new WordExtractor(doc);
        }
        catch (FileNotFoundException e){
            System.out.println(fileName);
            System.out.println(e.getLocalizedMessage());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(fileName);
        }
    }
    public FileReport getFileReport(){
        return new FileReport(fileName, getProjectTitle(), getSupervisorFIO(), getSupervisorEmail(), getReview());
    }
    public String getProjectTitle() {
        String projectTitle = ""; // Инициализируем пустую строку

        String [] paragraphs = wordExtractor.getParagraphText();
        boolean t = false; // что-то вроде переключателя или же передатчик сигнала тригера
        for (String paragraph : paragraphs) {
            if (paragraph.trim().length() == 0)
                continue;
            if (t) {
                if (Osnovnoe.lewenstain(paragraph, "Краткое описание проекта") <= 3)
                    break;
                projectTitle += paragraph.trim() + "\n";
            } else if (Osnovnoe.lewenstain(paragraph, "Название проекта ") <= 3) // это триггер
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

        String [] paragraphs = wordExtractor.getParagraphText();
        for (String paragraph : paragraphs){
            if (paragraph.trim().length() == 0)
                continue;

            String checkRes = checkFIO(paragraph);
            if (checkRes != null) {
                return checkRes;
            }
        }
        return null;
    }
    public String getSupervisorEmail(){
        String supervisorEmail = null;

        String [] paragraphs = wordExtractor.getParagraphText();
        for (String paragraph : paragraphs){
            if (paragraph.trim().length() == 0)
                continue;

            if (Osnovnoe.chechEmail(paragraph) != null){
                supervisorEmail = Osnovnoe.chechEmail(paragraph);
                break;
            }
        }

        return supervisorEmail;
    }
    public String getReview(){
        String endResult = "";

        String [] paragraphs = wordExtractor.getParagraphText();
        boolean t = false; // что-то вроде переключателя или же передатчик сигнала тригера
        for (String paragraph : paragraphs) {
            if (paragraph.trim().length() == 0)
                continue;

            if (t) {
                if (paragraph.indexOf("Использование материалов") != -1)
                    break;
                endResult += paragraph.trim() + "\n";
                endResult += paragraph.trim() + " ";
            } else if ((paragraph.indexOf("Фактически полученный продуктовый результат")) != -1) // это триггер
                t = true;
        }
        return endResult.trim();
    }

    // Checkers
    private String checkFIO(String fio){
        if (supervisorFios == null)
            supervisorFios = JSONDataExtractor.getSupervisorFios();
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
