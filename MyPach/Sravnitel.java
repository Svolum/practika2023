package MyPach;

import MyPach.AdminClasses.FilesAdmin;
import MyPach.AdminClasses.JsonAdmin;
import MyPach.DB.DBHonoric;
import MyPach.DB.Honoric;
import MyPach.FileWork.FileReport;
import MyPach.AdminClasses.FileTypeScanner;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.JsonReport;
import MyPach.JSON.MyNode;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Sravnitel {
    ArrayList<FileReport> fileReports;
    ArrayList<JsonReport> jsonReports;
    // Обработанные данные
    HashSet<Integer> alredyExistingId;
    ArrayList<EndData> endDataFall;
    ArrayList<EndData> endDataSpring;
    ArrayList<Honoric> honorics;
    ArrayList<DBHonoric> dbHonorics;
    public Sravnitel(){
        Scanner in = new Scanner(System.in);


        /*generalLogic();
        System.out.println("endDataFall=" + endDataFall.size());
        System.out.println("endDataSpring=" + endDataSpring.size());
        System.out.println("honorics=" + honorics.size());

//        cleanHonoric();
        checkFallandSpring();*/
    }
    public static int getNumberOfUniqueOtchets(){
        System.out.println("_________________________________________________________________________________________________");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("Дубли:\n");
        ArrayList<FileReport> array = new FileTypeScanner().getOthcets();
        int numberOfUniqueOtchets = 0;

        ArrayList<FileReport> checkedOtchets = new ArrayList<>();

        for (FileReport i : array){
            for (FileReport j : array){
                /*if (array.indexOf(i) > array.indexOf(j))
                    continue;*/
                if (checkedOtchets.contains(j))
                    continue;
                if (i == j)
                    continue;

                if (i.getTitle().equals(j.getTitle())){
                    System.out.println(i.getTitle());
                    System.out.println(i.getFileName());
                    System.out.println(j.getFileName());
                    System.out.println("---|---\n\n");
                    /*numberOfUniqueOtchets--;*/
                }
            }
            checkedOtchets.add(i);
            numberOfUniqueOtchets++;
        }
//        return numberOfUniqueOtchets;
        return -1;
    }
    public static ArrayList<String> getDublesOtchetFileNames(){
        ArrayList<FileReport> array = new FileTypeScanner().getOthcets();

        ArrayList<FileReport> checkedOtchets = new ArrayList<>();
        ArrayList<String> dublesOtchetFileNames = new ArrayList<>();

        for (FileReport i : array){
            for (FileReport j : array){
                /*if (array.indexOf(i) > array.indexOf(j))
                    continue;*/
                if (checkedOtchets.contains(j))
                    continue;
                if (i == j)
                    continue;

                if (i.getTitle().equals(j.getTitle())){
                    // Это значит, что те файлы, что стоят выше будут считаться истинными
                    dublesOtchetFileNames.add(i.getFileName());
                    dublesOtchetFileNames.add(j.getFileName());
                }
            }
            checkedOtchets.add(i);
        }
        return dublesOtchetFileNames;
    }
    private void generalLogic(){
        // старое
        /*fileReports = new FileTypeScanner().getOthcets();
        jsonReports = new JSONDataExtractor().getJsonReports();*/
        // Новое
        jsonReports = new JsonAdmin().getData();
        fileReports = new FilesAdmin().getData();


        alredyExistingId = new HashSet<>();
        endDataFall = new ArrayList<>();
        endDataSpring = new ArrayList<>();
        honorics = new ArrayList<>();


        int countOfYearReports = 0;

        ArrayList<String> dublesOtchetFileNames = getDublesOtchetFileNames();

        for (FileReport fileReport : fileReports){
            // Если есть такие отчеты, которые не читаются, имена их фалов надо закинуть в спец массив
            // Надо написать чеккер на не null важных полей и вызывать его, а не делать эти ифы
            if (fileReport.getFio() == null)
                continue;
            /*if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                // я даже не знаю, кажется это должно добавлять количество файлов в отстойнике
                continue;*/



            boolean isFall = false;
            boolean isSpring = false;

            for (JsonReport jsonReport : jsonReports){
                if (compareJsonAndFile(jsonReport, fileReport)){
//                    String searchingTitle = "Культура безопасности как элемент снижения уровня профессиональных рисков";
//                    if (jsonReport.getTitle().equals(searchingTitle)) {
//                        System.out.println("X#");
//                        System.out.println(fileReport.getTitle());
//                        System.out.println(fileReport.getFileName());
//                        System.out.println("X-->");
//                    }

                    int projectId = jsonReport.getProject_id();
                    // Исключает дубли, есть 2 версии 1 файла, АКТУАЛЬНОСТЬ оставшегося файла проверить, пока что, НЕВОЗМОЖНО
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Возможно исключает те отчеты, которые длятся только осенью
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // т.е. еще надо проверить как это работает
                    if (alredyExistingId.contains(projectId)) // итак вопрос, почему 1 проект, может откликаться больше чем на 1 отчет
                        continue; // Если убрать то countOfYearEndData, возможно будет больше количества весенних EndData
//                    if (jsonReport.getTitle().equals(searchingTitle)){
//                        System.out.println("Here");
//                    }

                    fileReport.setProject_id(projectId);
                    alredyExistingId.add(projectId);


                    if (jsonReport.getPrev_id() != 0) {
                        alredyExistingId.add(jsonReport.getPrev_id());
                    }

                    // END DATA
                    if (jsonReport.getData_start().contains("2022-09")) {
                        // ОСЕНЬ
                        EndData ed = new EndData(fileReport, projectId, jsonReport.getPrev_id(), fileReport.getReview());
                        endDataFall.add(ed);

                        isFall = true;
                    }
                    else if (jsonReport.getData_start().contains("2023-02")){
                        // ВЕСНА
                        EndData ed = new EndData(fileReport, projectId, jsonReport.getPrev_id(), fileReport.getReview());
                        endDataSpring.add(ed);

                        isSpring = true;
                    }
                    // honoric
                    if (jsonReport.getData_start().contains("2022-09") || jsonReport.getData_start().contains("2023-02")){
                        honorics.add(new Honoric(jsonReport.getProject_id(), jsonReport.getPrev_id(), fileReport.getReview()));
                    }
                }
            }
            if (isFall && isSpring){
                countOfYearReports++;
            }
        }

        int countOfLonlyFiles = 0;
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0)
                countOfLonlyFiles++;

        }
//        System.out.println("Count of lonly files = " + countOfLonlyFiles);

        // Почему-то не сходится, поэтому положусь на данные из БД т.е. на EndData
//        System.out.println("Count of year reports = " + countOfYearReports + " // not exactly");
        int countOfYearEndData = 0;
        for (var fall: endDataFall){
            boolean notIsYearProject = true;
            for (var spring: endDataSpring){
                if (fall.getProjectId() == spring.getPrevProjectId()){
                    notIsYearProject = false;
                    countOfYearEndData++;
                }
            }
            if (notIsYearProject) {
                // count of semestr reports
            }
        }
//        System.out.println("count of year EndData = " + countOfYearEndData + " // can respond for more then 1 fall EndData");

        /*System.out.println("ids = " + alredyExistingId.size());
        System.out.println("FALL    = " + endDataFall.size());
        System.out.println("SPRING  = " + endDataSpring.size());*/


        int a = 0;
        ArrayList<String> fileNamesWitoutPair = getFileNamesWithoutPair();
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0){
                if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                    continue;
                if (fileNamesWitoutPair.contains(fileReport.getFileName()))
                    continue;

                /*System.out.println(fileReport.toString(0));
                System.out.println("-------------------------------------------------------------------------------------------------");*/
                a++;
            }
        }
        /*System.out.println("\n\n\n");
        System.out.println("a = " + a + " | кол-во файлов, которым прога не нашла пары, и которые я пока считаю не дефектными");
        System.out.println("\n\n\n");*/
    }
    private void generalLogicThroughMyNode(){
        ArrayList<MyNode> myNodes = new JsonAdmin().getProjectTitles();
        fileReports = new FilesAdmin().getData();


        alredyExistingId = new HashSet<>();
        endDataFall = new ArrayList<>();
        endDataSpring = new ArrayList<>();
        honorics = new ArrayList<>();


        int countOfYearReports = 0;

        ArrayList<String> dublesOtchetFileNames = getDublesOtchetFileNames();

        for (FileReport fileReport : fileReports){
            // Если есть такие отчеты, которые не читаются, имена их фалов надо закинуть в спец массив
            // Надо написать чеккер на не null важных полей и вызывать его, а не делать эти ифы
            if (fileReport.getFio() == null)
                continue;


            boolean isFall = false;
            boolean isSpring = false;

            for (MyNode myNode : myNodes){
                if (compareMyNodeAndFile(myNode, fileReport) == false)
                    continue;
                JsonReport jsonFallReport = null;
                JsonReport jsonSpringReport = null;
                for (JsonReport jsonReport : myNode.getJsonReports()){
                    if (Osnovnoe.compareDates(jsonReport.getData_start(), Osnovnoe.date_start)) // Осень
                        jsonFallReport = jsonReport;
                    else if (Osnovnoe.compareDates(jsonReport.getData_start(), Osnovnoe.date_end)) // Весна
                        jsonSpringReport = jsonReport;
                }

                if ((jsonFallReport != null) // ОСЕНЬ
                        && (alredyExistingId.contains(jsonFallReport.getProject_id()) == false)) {
                    // endData
                    EndData ed = new EndData(fileReport, jsonFallReport.getProject_id(),
                            jsonFallReport.getPrev_id(), fileReport.getReview());
                    endDataFall.add(ed);
                    isFall = true;


                    // honoric
                    honorics.add(new Honoric(jsonFallReport.getProject_id(), jsonFallReport.getPrev_id(),
                            fileReport.getReview()));
                }
                if ((jsonSpringReport != null) // ВЕСНА
                        && (alredyExistingId.contains(jsonSpringReport.getProject_id()))){
                    // endData
                    EndData ed = new EndData(fileReport, jsonSpringReport.getProject_id(),
                            jsonSpringReport.getPrev_id(), fileReport.getReview());
                    endDataSpring.add(ed);
                    isSpring = true;


                    // honoric
                    honorics.add(new Honoric(jsonSpringReport.getProject_id(), jsonSpringReport.getPrev_id(),
                            fileReport.getReview()));
                }
            }
            if (isFall && isSpring){
                countOfYearReports++;
            }
        }

        // те отчеты, которым пару не нашел
        /*int countOfLonlyFiles = 0;
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0)
                countOfLonlyFiles++;

        }*/

        // Почему-то не сходится, поэтому положусь на данные из БД т.е. на EndData
        int countOfYearEndData = 0;
        for (var fall: endDataFall){
            boolean notIsYearProject = true;
            for (var spring: endDataSpring){
                if (fall.getProjectId() == spring.getPrevProjectId()){
                    notIsYearProject = false;
                    countOfYearEndData++;
                }
            }
            if (notIsYearProject) {
                // count of semestr reports
            }
        }

        int a = 0; // кол-во файлов, которым прога не нашла пары, и которые я пока считаю не дефектными
        ArrayList<String> fileNamesWitoutPair = getFileNamesWithoutPair();
        for (FileReport fileReport : fileReports){
            if (fileReport.getProject_id() == 0){
                if (dublesOtchetFileNames.contains(fileReport.getFileName()))
                    continue;
                if (fileNamesWitoutPair.contains(fileReport.getFileName()))
                    continue;

                a++;
            }
        }
    }
    public void cleanHonoric(){
        // key-id & value-prev_id
        HashMap<Integer, Integer> couples = new HashMap();

        /*
        Сейчас я не могу точно понять сколько у меня дублей или чего-то похожего
        - Сначала собираю те пары, что имеют prev_id

         */

        int onlyId = 0;
        int withPrev_id = 0;
        for (int i = 0; i < honorics.size(); i++){
            int id = honorics.get(i).getId();
            int prev_id = honorics.get(i).getPrev_id();
//            System.out.println(id + " | " + prev_id);
            if ((id != 0) && (prev_id == 0))
                onlyId++;
            if ((id != 0) && (prev_id != 0))
                withPrev_id++;

            couples.put(id, prev_id);
        }
        System.out.println("onlyId=" + onlyId);
        System.out.println("withPrev_id=" + withPrev_id);
        System.out.println("couples=" + couples.size());
    }
    public void checkFallandSpring(){
        dbHonorics = new ArrayList<>();
        for (EndData spring : endDataSpring){
            for (EndData fall : endDataFall){
                if (spring.getPrevProjectId() == fall.getProjectId()) {
                    dbHonorics.add(new DBHonoric(fall.getProjectId(), spring.getProjectId(), fall.getReview(), fall, spring));
                    break;
                }
            }
        }
        System.out.println("dbHonorics=" + dbHonorics.size());
    }

    public ArrayList<DBHonoric> getDbHonorics() {
        dbHonorics = new ArrayList<>();

        generalLogic();

        for (EndData spring : endDataSpring){
            for (EndData fall : endDataFall){
                if (spring.getPrevProjectId() == fall.getProjectId()) {
                    dbHonorics.add(new DBHonoric(fall.getProjectId(), spring.getProjectId(), fall.getReview(), fall, spring));
                    break;
                }
            }
        }
        return dbHonorics;
    }
    public ArrayList<DBHonoric> getDbHonoricsThroughMyNode() {
        dbHonorics = new ArrayList<>();

        generalLogicThroughMyNode();

        /*System.out.println(jsonReports.size());
        System.out.println(fileReports.size());
        System.out.println(honorics.size());
        System.out.println(endDataFall.size());
        System.out.println(endDataSpring.size());*/


        // обычное
        /*for (Honoric honoric : honorics){
            // пока так и не понял зачем мне endData здесь
            // ну и вроде беспокоится о том, что отчеты не подхояд по временному промежутку не надо
            // обычное
            dbHonorics.add(new DBHonoric(honoric, null, null));
        }*/
        // для откладки, т.к. ссылка на endDdate имеет ссылку на json и на file, для более подробной проверки
        ArrayList<EndData> endDatassss = endDataFall;
        endDatassss.addAll(endDataSpring);
        for (EndData endData : endDatassss){
            dbHonorics.add(new DBHonoric(endData.getProjectId(), endData.getProjectId(), endData.getReview(), endData, null));
        }
        // lonly files names  или же файлы, которым не нашлось пары
        ////////////////////////////////////////////////////////////////////////////////////////
        // просто здесь их удобно находить
        ArrayList<String> lonlyFileNames = new ArrayList<>();
        for (FileReport fileReport : fileReports){
            boolean isFileNameFinded = false;
            for (EndData endData : endDatassss){
                if (endData.getFileReport().getFileName().equals(fileReport.getFileName())) {
                    isFileNameFinded = true;
                    break;
                }
            }
            if (isFileNameFinded == false){
                lonlyFileNames.add(fileReport.getFileName());
            }
        }
        System.out.println(lonlyFileNames.size() + " =size of lonly"); // слегка гонит, либо дублеры
        System.out.println(fileReports.size() + " =count of files");
        System.out.println(dbHonorics.size() + " =count of processed");
        ////////////////////////////////////////////////////////////////////////////////////////
        return dbHonorics;
    }

    public static ArrayList<String> getFileNamesWithoutPair(){
        ArrayList<String > fileNamesWitoutPair = new ArrayList<>();
        fileNamesWitoutPair.add("Архитектурно-планировочная концепция развития территории «ЭКОПАРК ТАНХОЙ».docx");
        fileNamesWitoutPair.add("Отчет наставника (v2023) Арсентьев ОВ.docx");
        fileNamesWitoutPair.add("Причины отрицательной миграции населения в Иркутской области и меры по ее преодолению..docx");
        fileNamesWitoutPair.add("Проведение оценки профессиональных рисков в структурных подразделениях ИРНИТУ Издательство «УЛиУМП».docx");
        fileNamesWitoutPair.add("Проектные работы на строительство группы жилых домов в г. Тайшете.docx");
        fileNamesWitoutPair.add("Работы по сохранению объекта культурного наследия «Особняка Бутиных», 1886г., г. Иркутск, Хасановский пер.,1 лит. А.docx");
        fileNamesWitoutPair.add("Ярмарка выходного дня «Клубничная феерия».docx");
        fileNamesWitoutPair.add("Отчет наставника каф.РМПИ Иванов.docx");
//        fileNamesWitoutPair.add("");
        return fileNamesWitoutPair;
    }
    public static boolean compareFIO(String json, String otchet){
        if (json.equals(otchet))
            return true;
        String[] arr = json.split("( )|(\\.)");
        String[] mas = otchet.split("( )|(\\.)");
        if (arr.length == mas.length){
            if (    (arr[0].equals(mas[0])) &&
                    (arr[0].charAt(0) == mas[0].charAt(0)) &&
                    (arr[1].charAt(0) == mas[1].charAt(0)))
                return true;
        }

        /*for (var lol : arr){
            System.out.println(lol);
        }
        for (var lol : mas){
            System.out.println(lol);
        }*/
        return false;
    }
    public static boolean compareJsonAndFile(JsonReport json, FileReport fileReport){
        return
                (json.getData_start().contains("2022-09") || (json.getData_start().contains("2023-02"))) &&
                (json.getProject_supervisor_role_id() == 2) && // i have to check role id
                compareFIO(json.getFio(), fileReport.getFio()) &&
                //((fileReport.getTitle().contains(jsonReport.getTitle())) || (jsonReport.getTitle().contains(fileReport.getTitle()))) // проерка на название
                (
                        compareTwoTitles(json.getTitle(), fileReport.getTitle()) ||
                        ((lewenstain(json.getTitle(), fileReport.getTitle()) < 12) || (lewenstain(fileReport.getTitle(), json.getTitle()) < 12)) // помоему достаточно одного
                )
        ;
    }
    public static boolean compareMyNodeAndFile(MyNode myNode, FileReport fileReport){
        boolean titleFitting = false;
        for (String title : myNode.getTitles()){
            if (compareTwoTitles(title, fileReport.getTitle())){
                titleFitting = true;
                break;
            }
        }
        if (titleFitting == false) // Если ни один title из ветки названий проекта не подошел
            return false;
        for (JsonReport jsonReport : myNode.getJsonReports()){
            // поиск ПОДХОДЯЩЯГО report, хотя их может быть и 2. Точнее это наиболее вероятно, что их 2
            // однако для проверки достаточно и одного
            if (Osnovnoe.isDateInTimeRange(jsonReport.getData_start()) || Osnovnoe.isDateInTimeRange(jsonReport.getData_end())){
//                (json.getProject_supervisor_role_id() == 2) && // поля где (supervisor_role != 2) отсеяны на этапе чтения
                if (
                        (compareFIO(jsonReport.getFio(), fileReport.getFio())) &&
                        (compareTitlesLewenshtain(jsonReport.getTitle(), fileReport.getTitle()))
                )
                    return true;
            }
        }

        return false;
    }
    public static boolean compareTwoTitles(String json, String otchet){
        json = remainOnlyWords(json);
        otchet = remainOnlyWords(otchet);
        return json.contains(otchet) || otchet.contains(json);
    }
    public static boolean compareTitlesLewenshtain(String title1, String title2){
        return Osnovnoe.lewenstainExtended(title1, title2) < Osnovnoe.lewenshtainAllowableCountForTitles;
    }
    public static String remainOnlyWords(String s){
        return s.replaceAll("[^A-Za-zА-Яа-я0-9]", "").toLowerCase();
    }
    public static boolean anotherCompareOfTitles(String json, String otchet){
        // здесь превращение одних символов в другие и т.д.
        // или переписать contains

        return false;
    }
    public static boolean myContains(String s, String q){
        /////////////////////////////////////////////////////
        //s = remainOnlyWords(s);
        //q = remainOnlyWords(q);
        //System.out.println(s);
        //System.out.println(q);
        /////////////////////////////////////////////////////
        if (s.length() < q.length())
            return false;
        // max 6
        // Так же, когда катушка будет встречать несовпадение, она должна попробовать следующий символ из s, и из q //типо подстановка, хотя если писать MyContains под каждую строку, то подстановку, наверное моэно запилить только из одного массива символов
        // как получиться, короче
        int step = 0;
        // Для начала, надо написать простой contains, а потом уже с возможностью допустить ошибку
        //System.out.println(s + " | " + q);

        char[] smas = s.toCharArray();
        char[] qmas = q.toCharArray();
        for (int si = 0; si < smas.length - qmas.length + 1; si++){ // если s становится короче чем q, то продолжать нет смысла
            int ind = si;
            int qe = 0;
            int se = 0;
            step = 0;
            for (int qi = 0; qi < qmas.length; qi++){
                ind = si + qi - qe + se;  // сюда та самая формула, до этого я менял si, но потом возникали заморочки с установкой того значения, которое должно быть по факту(циклу фору --for)
                if (smas[ind] == qmas[qi]){
                    if (qi == qmas.length - 1) // если добрался до конца, значит q является подстрокой s
                        return true;
                    continue;
                }
                else {
                    if ((se != 2) && (step == 0)){ // т.е допускаемое кол-во ошибок = 2
                        se++;
                        continue;
                    }else {
                        step = 1;
                        se = 0;
                    }
                    qe++; // подготовка qe к следующему шагу в q
                    //System.out.println("qe = " + qe);
                    // код ниже - ликвидный код
                    if (qe != 3) // максимальное допустимое количество ошибок == 2
                    {
                        if (qi == qmas.length - 1) // пусть и с ошибками, но q является подстрокой s
                            return true;
                        continue;
                    }
                    qe--; // так как следующий шаг, будет уже в s, а не в q. И мне надо чтобы прога норм возвращала si в свое нормальное значение
                    //System.out.println(si + " | " + (ind - qi + qe - se)); // это так, если в самом конце они будут сходится, то удалю ind
                    break;
                }
            }
        }
        //System.out.println("end false");
        return false;
    }
    public static int lewenstain(String s, String q){
        /*int up = 1; // prev j in column (i, j-1)
        int left = 1; // prev i in row (i - 1, j)
        int prev = 0; // just prev (i - 1, j - 1)
        //int cur; // (i, j)*/

        int prev = 0;
        int[] up = new int[q.length()];
        int[] left = new int[s.length()];
        int cur = 0;
        for (int i = 0; i < s.length(); i++){
            left[i] = i + 1;
            for (int j = 0; j < q.length(); j++){
                if (i == 0)
                    up[j] = j + 1;

                int a = up[j] + 1;
                int b = left[i] + 1;
                int c = prev;
                if (s.charAt(i) != q.charAt(j))
                    c++;
                cur = Math.min(Math.min(a, b), c);

                prev = up[j];
                left[i] = cur;
                up[j] = cur;
            }
        }
        return cur;
    }
}
