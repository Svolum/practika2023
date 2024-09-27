package MyPach;

import MyPach.FileWork.FileTypeScanner;
import MyPach.FileWork.Othcet;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.OtchetNeedReview;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;

public class Sravnitel {
    ArrayList<Othcet> otchets;
    ArrayList<OtchetNeedReview> jsonOtchets;
    // Обработанные данные
    HashSet<Integer> alredyExistingId;
    ArrayList<EndData> endDataFall;
    ArrayList<EndData> endDataSpring;
    public Sravnitel(){
        Scanner in = new Scanner(System.in);

        generalLogic();
    }
    public static int getNumberOfUniqueOtchets(){
        System.out.println("_________________________________________________________________________________________________");
        System.out.println("-------------------------------------------------------------------------------------------------");
        System.out.println("Дубли:\n");
        ArrayList<Othcet> array = new FileTypeScanner().getOthcets();
        int numberOfUniqueOtchets = 0;

        ArrayList<Othcet> checkedOtchets = new ArrayList<>();

        for (Othcet i : array){
            for (Othcet j : array){
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
    public ArrayList<String> getDublesOtchetFileNames(){
        ArrayList<Othcet> array = new FileTypeScanner().getOthcets();

        ArrayList<Othcet> checkedOtchets = new ArrayList<>();
        ArrayList<String> dublesOtchetFileNames = new ArrayList<>();

        for (Othcet i : array){
            for (Othcet j : array){
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
        otchets = new FileTypeScanner().getOthcets();
        jsonOtchets = new JSONDataExtractor().getOthcetsNeedReviews();


        alredyExistingId = new HashSet<>();
        endDataFall = new ArrayList<>();
        endDataSpring = new ArrayList<>();


        int countOfYearReports = 0;

        ArrayList<String> dublesOtchetFileNames = getDublesOtchetFileNames();

        for (Othcet othcet : otchets){
            // Если есть такие отчеты, которые не читаются, имена их фалов надо закинуть в спец массив
            // Надо написать чеккер на не null важных полей и вызывать его, а не делать эти ифы
            if (othcet.getFio() == null)
                continue;
            /*if (dublesOtchetFileNames.contains(othcet.getFileName()))
                // я даже не знаю, кажется это должно добавлять количество файлов в отстойнике
                continue;*/



            boolean isFall = false;
            boolean isSpring = false;

            for (OtchetNeedReview jsonOtchet : jsonOtchets){
                if (compareJsonAndFile(jsonOtchet, othcet)){
                    String searchingTitle = "Культура безопасности как элемент снижения уровня профессиональных рисков";
                    if (jsonOtchet.getTitle().equals(searchingTitle)) {
                        System.out.println("X#");
                        System.out.println(othcet.getTitle());
                        System.out.println(othcet.getFileName());
                        System.out.println("X-->");
                    }

                    int projectId = jsonOtchet.getProject_id();
                    // Исключает дубли, есть 2 версии 1 файла, АКТУАЛЬНОСТЬ оставшегося файла проверить, пока что, НЕВОЗМОЖНО
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // Возможно исключает те отчеты, которые длятся только осенью
                    //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    // т.е. еще надо проверить как это работает
                    if (alredyExistingId.contains(projectId)) // итак вопрос, почему 1 проект, может откликаться больше чем на 1 отчет
                        continue; // Если убрать то countOfYearEndData, возможно будет больше количества весенних EndData
                    if (jsonOtchet.getTitle().equals(searchingTitle)){
                        System.out.println("Here");
                    }

                    othcet.setProject_id(projectId);
                    alredyExistingId.add(projectId);


                    if (jsonOtchet.getPrev_id() != 0) {
                        alredyExistingId.add(jsonOtchet.getPrev_id());
                    }

                    // END DATA
                    if (jsonOtchet.getData_start().contains("2022-09")) {
                        // ОСЕНЬ
                        EndData ed = new EndData(othcet, projectId, jsonOtchet.getPrev_id(), othcet.getReview());
                        endDataFall.add(ed);

                        isFall = true;
                    }
                    else if (jsonOtchet.getData_start().contains("2023-02")){
                        // ВЕСНА
                        EndData ed = new EndData(othcet, projectId, jsonOtchet.getPrev_id(), othcet.getReview());
                        endDataSpring.add(ed);

                        isSpring = true;
                    }
                }
            }
            if (isFall && isSpring){
                countOfYearReports++;
            }
        }

        int countOfLonlyFiles = 0;
        for (Othcet othcet : otchets){
            if (othcet.getProject_id() == 0)
                countOfLonlyFiles++;

        }
        System.out.println("Count of lonly files = " + countOfLonlyFiles);

        // Почему-то не сходится, поэтому положусь на данные из БД т.е. на EndData
        System.out.println("Count of year reports = " + countOfYearReports + " // not exactly");
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
        System.out.println("count of year EndData = " + countOfYearEndData + " // can respond for more then 1 fall EndData");

        System.out.println("ids = " + alredyExistingId.size());
        System.out.println("FALL    = " + endDataFall.size());
        System.out.println("SPRING  = " + endDataSpring.size());


        int a = 0;
        ArrayList<String> fileNamesWitoutPair = getFileNamesWithoutPair();
        for (Othcet othcet : otchets){
            if (othcet.getProject_id() == 0){
                if (dublesOtchetFileNames.contains(othcet.getFileName()))
                    continue;
                if (fileNamesWitoutPair.contains(othcet.getFileName()))
                    continue;

                System.out.println(othcet.toString(0));
                System.out.println("-------------------------------------------------------------------------------------------------");
                a++;
            }
        }
        System.out.println("\n\n\n");
        System.out.println("a = " + a + " | кол-во файлов, которым прога не нашла пары, и которые я пока считаю не дефектными");
        System.out.println("\n\n\n");
    }
    public ArrayList<String> getFileNamesWithoutPair(){
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
    public static boolean compareJsonAndFile(OtchetNeedReview json, Othcet othcet){
        return
                (json.getData_start().contains("2022-09") || (json.getData_start().contains("2023-02"))) &&
                (json.getProject_supervisor_role_id() == 2) && // i have to check role id
                compareFIO(json.getFio(), othcet.getFio()) &&
                //((othcet.getTitle().contains(jsonOtchet.getTitle())) || (jsonOtchet.getTitle().contains(othcet.getTitle()))) // проерка на название
                (
                        compareTwoTitles(json.getTitle(), othcet.getTitle()) ||
                        ((lewenstain(json.getTitle(), othcet.getTitle()) < 12) || (lewenstain(othcet.getTitle(), json.getTitle()) < 12)) // помоему достаточно одного
                )
        ;
    }
    public static boolean compareTwoTitles(String json, String otchet){
        json = remainOnlyWords(json);
        otchet = remainOnlyWords(otchet);
        return json.contains(otchet) || otchet.contains(json);
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
