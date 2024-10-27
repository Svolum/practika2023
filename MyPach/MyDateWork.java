package MyPach;

import MyPach.AdminClasses.JsonAdmin;
import MyPach.JSON.JsonReport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MyDateWork {
    private int year_start;
    private int year_end;
    private ArrayList<JsonReport> jsonReports;
    public MyDateWork(){
        year_start = 22;
        year_end = 23;
        if (year_start < 2000)
            year_start += 2000;
        if (year_end < 2000)
            year_end += 2000;

        jsonReports =  new JsonAdmin().getData();
        lol();
    }
    private void lol(){
        for (JsonReport jsonReport : jsonReports){
            System.out.println(jsonReport.getData_start() + "=" + isDateInRange(LocalDate.parse(jsonReport.getData_start()))
                    + " | " + jsonReport.getData_end() + "=" + isDateInRange(LocalDate.parse(jsonReport.getData_end())));
        }
    }
    public boolean isDateInRange(LocalDate date){
        if (date.getYear() == year_start){
            return date.getMonthValue() >= 9;
        }else if (date.getYear() == year_end){
            return date.getMonthValue() <= 6;
        }
        return false;
    }
}
