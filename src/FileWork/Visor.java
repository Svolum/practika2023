package FileWork;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Visor {
    private String fio;
    private String email;
    public Visor(String fio) {
        this.fio = checkFIO(fio);
    }

    @Override
    public String toString() {
        return fio;
    }
    public static String checkFIO(String fio){
        String fioPattern = "([А-Яа-я]+\\s[А-Яа-я]+\\s[А-Яа-я]+)|([А-Яа-я]+\\s[А-Яа-я]\\.[А-Яа-я]\\.)|([А-Яа-я]+\\s[А-Яа-я]\\.[А-Яа-я]\\.)";
        Pattern pattern = Pattern.compile(fioPattern);
        Matcher matcher = pattern.matcher(fio);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    public static String chechEmail(String email){
        String emailPattern = "^.+@.+\\.\\w+";
        Pattern pattern = Pattern.compile(emailPattern);

        Matcher matcher = pattern.matcher(email);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
    public String getFio() {
        return fio;
    }
    public void setFio(String fio) {
        this.fio = fio;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
