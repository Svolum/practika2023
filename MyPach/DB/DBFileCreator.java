package MyPach.DB;

import MyPach.JSON.JsonReport;
import MyPach.JSON.MyNode;
import MyPach.Osnovnoe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.impl.CTOleObjectsImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DBFileCreator {
//    private ArrayList<Honoric> honorics;
    private ArrayList<DBHonoric> dbHonorics;
    private String text;
    public DBFileCreator(ArrayList<DBHonoric> dbHonorics){
        /* примерно как будет генерироваться файл
        - получает откудато массив с id и результатом проекта,
        мб FileReport и здесь преобразовывать в удобный формат
        - 1 функция создает строку или массив строк для записи в файл
        - создание и запись в файл
        а потом я передумал
         */
        this.dbHonorics = dbHonorics;
        text = "";
//        fillText();
        jsonCreator();
    }
    public void fillText(){
        for (DBHonoric dbHonoric : dbHonorics){
            String str = String.format("UPDATE projects\n" +
                    "SET rezult = '%s'\n" +
                    "WHERE id = %d;\n", dbHonoric.getReview(), dbHonoric.getId());
            String pip = "UPDATE projects SET rezult = ... WHERE id = ...;";
            text += str;
            str = String.format("UPDATE projects\n" +
                    "SET rezult = '%s'\n" +
                    "WHERE id = %d;\n", dbHonoric.getReview(), dbHonoric.getPrev_id());
            text += str;
        }
        System.out.println(text.length());
    }
    public void jsonCreator(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String str = "";
        File file = new File(Osnovnoe.jsonCreatingFileName + ".json");
        ArrayList<MyPair> myPairs = new ArrayList<>();
        for (DBHonoric dbHonoric : dbHonorics){
            myPairs.add(new MyPair(dbHonoric.getId(), dbHonoric.getReview()));
        }
        try{
//            objectMapper.writeValue(new File("Data\\lol.json"), new TypeReference<ArrayList<DBHonoric>>(){});
            /*for (var i : dbHonorics) {
                str += objectMapper.writeValueAsString(i);
                break;
            }*/
            ArrayList<MyPair> oldMyPairs = new ArrayList<>();
            if (file.exists() && file.length() != 0) {
                // Читаем данные из файла в список users
                oldMyPairs = objectMapper.readValue(file, new TypeReference<ArrayList<MyPair>>() {});
            }
            for (MyPair myPair: myPairs){
                str += objectMapper.writeValueAsString(myPair);
            }
            oldMyPairs.addAll(myPairs);
            objectMapper.writeValue(file, oldMyPairs);
        }catch (Exception e){
            System.out.println("похоже не записалось");
            System.out.println(e);
        }
        System.out.println(str.length());
//        System.out.println(str);
    }
}
