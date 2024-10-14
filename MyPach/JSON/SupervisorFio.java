package MyPach.JSON;

import java.util.Arrays;

public class SupervisorFio{
    private String fio;
    private String[] mas;
    private boolean[] isFioWrittenShort;
    private boolean[] hasFioDot;
    private int fioLenght;
    public SupervisorFio(String fio){
        this.fio = fio;

        // делит строку на 3 по наличию пробела или точки пробела // причем пробела может быть сколько угодно
        mas = fio.split("\\.+ {1,}| {1,}|\\.");

        fioLenght = mas.length;
        isFioWrittenShort = new boolean[fioLenght]; // filled with false
        hasFioDot = new boolean[fioLenght]; // filled with false too

        for (int i = 0; i < fioLenght; i++){
            if (mas[i].contains(".")){
                isFioWrittenShort[i] = true;
                hasFioDot[i] = true;
            } else if (mas[i].length() == 1) {
                isFioWrittenShort[i] = true;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            if (((String)obj).equals(fio))
                return true;
            SupervisorFio foreignFio = new SupervisorFio((String) obj);
            // теперь надо придумать как сравнивать, притом что есть китайцы(на него я забил)
            // надо напомнить, что это просто проверка, ведь главное сравнение будет лежать на title
            // сначала буду сравнивать первые буквы
            // если предыдущий этап пройдем сравнивается целиком
            if (foreignFio.fioLenght !=  fioLenght)
                return false;
            // повторяется чтобы фамилия полностью проверялась
            String cur = mas[0]; //current
            String fgn = foreignFio.mas[0]; //fogeing
            if (cur.equals(fgn) == false)
                return false;
            for (int i = 1; i < fioLenght; i++){
                cur = mas[0]; //current
                fgn = foreignFio.mas[0]; //fogeing
                if (cur.equals(fgn) || (cur.charAt(0) == fgn.charAt(0)))
                    continue;
                else
                    return false;
            }
        }
        return true;
    }
    public static boolean areEqual(String fio1, String fio2) {
        if (fio1.equals(fio2))
            return true;
        SupervisorFio sfio1 = new SupervisorFio(fio1);
        SupervisorFio sfio2 = new SupervisorFio(fio2);
        // теперь надо придумать как сравнивать, притом что есть китайцы
        // надо напомнить, что это просто проверка, ведь главное сравнение будет лежать на title
        // сначала буду сравнивать первые буквы
        // если предыдущий этап пройдем сравнивается целиком
        if (sfio1.fioLenght !=  sfio2.fioLenght)
            return false;
        // повторяется чтобы фамилия полностью проверялась
        String cur1 = sfio1.mas[0];
        String cur2 = sfio2.mas[0];
        if (cur1.equals(cur2) == false)
            return false;
        for (int i = 1; i < sfio1.fioLenght; i++){
            cur1 = sfio1.mas[i];
            cur2 = sfio2.mas[i];
            if (cur1.equals(cur2) || (cur1.charAt(0) == cur2.charAt(0)))
                continue;
            else
                return false;
        }
        return true;
    }

    public String getFio() {
        return fio;
    }

    public boolean[] getIsFioWrittenShort() {
        return isFioWrittenShort;
    }

    public boolean[] getHasFioDot() {
        return hasFioDot;
    }

    public int getFioLenght() {
        return fioLenght;
    }
}