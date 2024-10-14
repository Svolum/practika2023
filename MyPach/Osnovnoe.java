package MyPach;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Osnovnoe {
    public static String workingPath;
    // Типо это начала осеннего
    public static String date_start;
    // Типо это начала весеннего
    public static String date_end;
    public static String jsonCreatingFileName;
    public static int lewenshtainAllowableCountForTitles;
    public static int lewenshtainAllowableCountForFio;

    static {
        lewenshtainAllowableCountForTitles = 12;
        lewenshtainAllowableCountForFio = 5;
    }

    public static boolean compareDates(String data1, String data2){
        if (data1.contains(data2) || data2.contains(data1))
            return true;
        return false;
    }
    // Находится ли дата в том уч году, который нужен
    public static boolean isDateInTimeRange(String date){
        return compareDates(date_start, date) || compareDates(date_end, date);
    }
    public static String getCurrentDirrectory(){
        return System.getProperty("user.dir");
    }
//    public static String getWorkingDirectory(){
//        return getCurrentDirrectory() + "\\отчеты\\";
//    }
    public static String getWorkingDirectory(){
        return getCurrentDirrectory() + workingPath;
    }
    public static int lewenstain(String s, String q){ // basic method // нативный
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
    // регистро не зависимо(toLowerCase) и такие буквы как е и ё или и и й считаются одной и тоже буквой
    public static int lewenstainExtended(String s, String q){
        /*int up = 1; // prev j in column (i, j-1)
        int left = 1; // prev i in row (i - 1, j)
        int prev = 0; // just prev (i - 1, j - 1)
        //int cur; // (i, j)*/
        s = Osnovnoe.remainOnlyWords(s);
        q = Osnovnoe.remainOnlyWords(q);

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
                if ((s.charAt(i) != q.charAt(j))) {
                    /*if (
                            (
                                    (
                                            (s.charAt(i) == 'е' && q.charAt(i) == 'ё') ||
                                            (s.charAt(i) == 'ё' && q.charAt(i) == 'е')
                                    ) == false
                            ) &&
                            (
                                    (
                                            (s.charAt(i) == 'и' && q.charAt(i) == 'й') ||
                                            (s.charAt(i) == 'й' && q.charAt(i) == 'и')
                                    ) == false
                            )
                    )*/
                        c++;
                }
                cur = Math.min(Math.min(a, b), c);

                prev = up[j];
                left[i] = cur;
                up[j] = cur;
            }
        }
        return cur;
    }
    public static String remainOnlyWords(String s){
        return s.replaceAll("[^A-Za-zА-Яа-я0-9]", "").toLowerCase();
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
