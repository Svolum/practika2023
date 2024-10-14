package MyPach.FileWork;



import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.SupervisorFio;
import MyPach.Sravnitel;
import org.apache.log4j.BasicConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.log4j.*;

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
        this.fileName = fileName;
        try {
            pdfDoc = PDDocument.load(new File(workDirectory + fileName));
            textStripper = new PDFTextStripper();
            text = textStripper.getText(pdfDoc);

//            lol();
        }catch (Exception e){
            System.out.println(e.getMessage());
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
            // ДЕЛЕНИЕ НА НОЛЬ 0/0!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            System.out.println(0/0);
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }
    public FileReport getFileReport(){
        return new FileReport(fileName, getProjectTitle(), getSupervisorFIO(), getSupervisorEmail(), getReview());
    }
    private void lol() throws IOException { // эта функция уже была и может работать почти в вакууме потому что она из начала создания этого класса
        textStripper = new PDFTextStripper();
        String text = textStripper.getText(pdfDoc);
        supervisorFios = JSONDataExtractor.getSupervisorFios();
        /*System.out.println(supervisorFios.size());
        for (SupervisorFio supervisorFio : supervisorFios){
            System.out.println(supervisorFio.getFio());
        }*/
//        System.out.println(getSupervisorFIO(text));
//        System.out.println(getSupervisorFIO());
//        System.out.println(getProjectTitle());
//        System.out.println(getSupervisorEmail());
        System.out.println(getReview());
    }
    public String getProjectTitle() {
        String projectTitle = ""; // Инициализируем пустую строку

        String[] errText = text.split("\n");
        boolean t = false; // что-то вроде переключателя или же передатчик сигнала тригера
        for (String i : errText) {
            if (t) {
                if (Sravnitel.lewenstain(i, "Краткое описание проекта") <= 3)
                    break;
                projectTitle += i.trim() + "\n";
//                projectTitle += i.trim() + " ";
            } else if (Sravnitel.lewenstain(i, "Название проекта ") <= 3) // это триггер
                t = true;
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
    /*private String getSupervisorFIO(String text){
        String[] errText = text.split("\n");
        for (String i : errText){

            String checkRes = checkFIO(i);
            if (checkRes != null) {
                return checkRes;
            }
        }
        return null;
    }*/
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
//            return matcher.group().equals("Иркутский Национальный Исследовательский")?null:matcher.group(); // это надо пофиксить
            for (SupervisorFio supervisorFio : supervisorFios)
//                return supervisorFio.equals(matcher.group())?matcher.group():null;
                if (supervisorFio.equals(matcher.group()))
                    return matcher.group();
        }
        return null;
    }
}