package MyPach.DB;

import MyPach.Osnovnoe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.ArrayList;

public class DBFileCreator {
    private String fileName;
    private ArrayList<DBHonoric> dbHonorics;
    public DBFileCreator(ArrayList<DBHonoric> dbHonorics){
        /* примерно как будет генерироваться файл
        - получает откудато массив с id и результатом проекта,
        - создание и запись в файл // в какой файл можно указать в классе Osnovnoe
         */
        this.dbHonorics = dbHonorics;
        this.fileName = Osnovnoe.jsonCreatingFileName.replace(".json", "") + ".json";
        jsonCreator();
    }
    public DBFileCreator(String fileName, ArrayList<DBHonoric> dbHonorics) {
        this.fileName = fileName.replace(".json", "") + ".json";
        this.dbHonorics = dbHonorics;
        jsonCreator();
    }

    public void jsonCreator(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // str пока просто по фану существует, но удобно выводить её в консоль в случае чего
        String str = "";

        File file = new File(fileName);


        ArrayList<MyPair> myPairs = new ArrayList<>();
        for (DBHonoric dbHonoric : dbHonorics){
            myPairs.add(new MyPair(dbHonoric.getId(), dbHonoric.getReview()));
        }

        try{
            //этот способ перезаписывает уже существующие файлы
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
        System.out.println(str.length() + " столько символов записалось");
    }
}
