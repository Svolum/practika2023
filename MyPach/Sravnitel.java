package MyPach;

import MyPach.FileWork.FileFinder;
import MyPach.FileWork.FileTypeScanner;
import MyPach.FileWork.Othcet;
import MyPach.FileWork.ReadDocxFile;
import MyPach.JSON.JSONDataExtractor;
import MyPach.JSON.OtchetNeedReview;
import org.apache.commons.compress.archivers.ar.ArArchiveEntry;

import java.util.ArrayList;
import java.util.Scanner;

public class Sravnitel {
    private int date_year;
    private int date_month;
    public Sravnitel(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter start date of project");
        System.out.println("Enter year");
        date_year = in.nextInt();

        System.out.println("Enter month");
        date_month = in.nextInt();


        //main();
        generalLogic();
    }
    private void generalLogic(){
        
        ArrayList<Othcet> otchets = new FileTypeScanner().getOthcets();
        ArrayList<OtchetNeedReview> jsonOtchets = new JSONDataExtractor().getOthcetsNeedReviews();

        for (Othcet othcet : otchets){
            // Если есть такие отчеты, которые не читаются, имена их фалов надо закинуть в спец массив
            // Надо написать чеккер на не null важных полей и вызывать его, а не делать эти ифы
            if (othcet.getFio() == null)
                continue;
            for (OtchetNeedReview jsonOtchet : jsonOtchets){
                if (    ((getOtchetYearStart(jsonOtchet) == date_year) && (getOtchetMonthStart(jsonOtchet) == date_month)) && // compare date monto and yeat
                        othcet.getFio().equals(jsonOtchet.getFio()) &&
                        ((othcet.getTitle().contains(jsonOtchet.getTitle())) || (jsonOtchet.getTitle().contains(othcet.getTitle())))){
                    //System.out.println(othcet);
                    System.out.println(jsonOtchet);
                    System.out.println("------------------------------------------------------------------------------------------\n\n");
                }
            }
        }
    }
    private void main(){
        ArrayList<EndData> endData = new ArrayList<>();
        ArrayList<String> problemFiles = new ArrayList<>();
        JSONDataExtractor dt = new JSONDataExtractor();

        // count of matches
        int sovpadenie = 0;

        FileFinder ff = new FileFinder();
        ArrayList<ReadDocxFile> docxFiles = ff.getDocxFiles();
        for (ReadDocxFile rdf : docxFiles){
            Othcet othcet = rdf.getOthcet();
            //System.out.println(rdf.getFileName());
            //System.out.println(othcet);
            //System.out.println("\n\n------------------------------------------------------------------------------------------------------\n\n");

            ArrayList<OtchetNeedReview> othcetsNeedReviews = dt.getOthcetsNeedReviews();
            int a = 0;
            for (OtchetNeedReview jsonOtchet : othcetsNeedReviews){
                if (jsonOtchet.getFio() == null){
                    System.out.println("json is null");
                }
                else if (othcet.getFio() == null){
                    problemFiles.add(rdf.getFileName());
                    //System.out.println("otchet is null");
                    break;
                }
                else if (othcet.getEmail() == null){
                    problemFiles.add(rdf.getFileName());
                    break;
                }
                if ((getOtchetYearStart(jsonOtchet) == date_year) && (getOtchetMonthStart(jsonOtchet) == date_month) &&
                        jsonOtchet.getFio().contains(othcet.getFio())
                        && (jsonOtchet.getProject_supervisor_role_id() == 2)
                        && (othcet.getTitle().contains(jsonOtchet.getTitle()))){

                    System.out.println(othcet);
                    System.out.println(jsonOtchet);
                    System.out.println("\n\n------------------------------------------------------------------------------------------------------\n\n");
                    EndData current = new EndData(othcet.getReview());
                    current.addId(jsonOtchet.getProject_id());
                    if (jsonOtchet.getPrev_id() != 0)
                        current.addId(jsonOtchet.getPrev_id());
                    endData.add(current);


                    a++;
                    sovpadenie++;
                }
            }
            if (a > 1){
                System.out.println("DUBLESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            }
            else
                System.out.println("NOT DUBLESSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
        }
        System.out.println("sovpadenie = " + sovpadenie);
        System.out.println(problemFiles.size());

        for (var lol : problemFiles){
            System.out.println(lol);
        }
    }
    public void workWithOnlyJsonData(){
        JSONDataExtractor dt = new JSONDataExtractor();
        ArrayList<OtchetNeedReview> othcetsNeedReviews = dt.getOthcetsNeedReviews();
        for (OtchetNeedReview otchet : othcetsNeedReviews){
            String fio = otchet.getFio();
            if (fio.contains("Шакирова Эльвира Венеровна")) {
                System.out.println(otchet);
                if ((getOtchetYearStart(otchet) == date_year) && (getOtchetMonthStart(otchet) == date_month))
                {
                    System.out.println("Yes");
                }
            }
        }
    }
    private int getOtchetYearStart(OtchetNeedReview othcet){
        String date = othcet.getData_start();
        String year = date.split("-")[0];
        return Integer.parseInt(year);
    }
    private int getOtchetMonthStart(OtchetNeedReview othcet){
        String date = othcet.getData_start();
        String month = date.split("-")[1];
        return Integer.parseInt(month);
    }
}