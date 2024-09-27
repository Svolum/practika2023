package MyPach.FileWork;



import org.apache.log4j.BasicConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.log4j.*;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFDataExtractor{
    private String fileName;
    private PDDocument pdfDoc;
    public PDFDataExtractor(String fileName, String workDirectory){
        this.fileName = fileName;
        /*org.apache.log4j.BasicConfigurator.configure();
        java.util.logging.Logger.getLogger("org.apache.pdfbox").setLevel(java.util.logging.Level.OFF);*/
        /*String workDirectory = FileTypeScanner.getCurrentDirrectory() + "/отчеты/";
        BasicConfigurator.configure();
        try {
            PDDocument doc = PDDocument.load(new File(workDirectory + "myExample.pdf"));
//            System.out.println(doc.getPage(0).getContents().read());
            System.out.println(doc.getPages().getCount());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }*/
        try {
            pdfDoc = PDDocument.load(new File(workDirectory + fileName));


            /*PDDocument doc = new PDDocument();
            doc.save("delete_it_because_it_test");
            doc.close();*/
            lol();
        }catch (Exception e){
            System.out.println(e.getMessage());
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // ДЕЛЕНИЕ НА НОЛЬ 0/0!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            System.out.println(0/0);
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
    }
    private void lol() throws IOException {
        PDFTextStripper textStripper = new PDFTextStripper();
        String text = textStripper.getText(pdfDoc);
        System.out.println(getSupervisorFIO(text));
    }
    public String getSupervisorFIO(String text){
        String[] errText = text.split("\n");
//        System.out.println(text.contains("\n") + " | " + "len=" + errText.length);
        for (String i : errText){
//            System.out.println(i.replace(" ", "_"));

            String checkRes = checkFIO(i);
            if (checkRes != null) {
//                System.out.println(checkRes);
                return checkRes;
            }
        }
        return null;
    }
    private String checkFIO(String fio){
        /* Вот такие формати ФИО ищет
            Лена Ано Лео
            Агрошиц Г.Т.
            Именин Т. В.    - здесь пробел есть
         */
        String fioPattern = "([А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+)|([А-Я][а-я]+\\s[А-Я]\\.[А-Я]\\.)|([А-Я][а-я]+\\s[А-Я]\\.\\s[А-Я]\\.)";
        Pattern pattern = Pattern.compile(fioPattern);

        Matcher matcher = pattern.matcher(fio);
        if (matcher.find()) {
            return matcher.group().equals("Иркутский Национальный Исследовательский")?null:matcher.group();
        }
        return null;
    }
}