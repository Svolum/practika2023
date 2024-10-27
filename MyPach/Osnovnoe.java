package MyPach;

import MyPach.FileWork.FileReport;
import MyPach.JSON.JsonReport;
import MyPach.JSON.SupervisorFio;
import org.apache.poi.util.StringUtil;

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
    public static String getWorkingDirectory(){
        return getCurrentDirrectory() + workingPath;
    }
    public static int lewenstain(String s, String q){ // basic method // нативный
        if (s.length() > q.length()){
            String tempStr = s;
            s = q;
            q = tempStr;
        }
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
        s = Osnovnoe.remainOnlyWords(s);
        q = Osnovnoe.remainOnlyWords(q);

        s = s.replaceAll("ё", "е");
        s = s.replaceAll("й", "и");
        q = q.replaceAll("ё", "е");
        q = q.replaceAll("й", "и");

        return lewenstain(s, q);
    }
    public static int lewenstainExtendedTitles(String s, String q){
        /*return (s.length() > q.length()) ?
                lewenstainExtended(s, q) - Math.abs(s.length() - q.length()) :
                lewenstainExtended(q, s) - Math.abs(s.length() - q.length());*/
        return lewenstainExtended(s, q) - Math.abs(remainOnlyWords(s).length() - remainOnlyWords(q).length());
    }
    public static int lewenstainTitles(String s, String q){
        return lewenstain(s, q) - Math.abs(s.length() - q.length());
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
    public static boolean compareJsonAndReport(JsonReport jsonReport, FileReport fileReport){
//        boolean date = isDateInTimeRange(jsonReport.getData_start());
//        boolean title = lewenstainExtendedTitles(jsonReport.getTitle(), fileReport.getTitle()) <
//                lewenshtainAllowableCountForTitles;
//        boolean fio = SupervisorFio.areEqual(jsonReport.getFio(), fileReport.getFio());
//        return date && title && fio;
        return isDateInTimeRange(jsonReport.getData_start())
                && (lewenstainExtendedTitles(jsonReport.getTitle(), fileReport.getTitle()) < lewenshtainAllowableCountForTitles)
                && SupervisorFio.areEqual(jsonReport.getFio(), fileReport.getFio());
    }
}
