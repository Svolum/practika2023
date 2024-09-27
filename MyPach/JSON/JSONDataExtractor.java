package MyPach.JSON;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class JSONDataExtractor {
    private ObjectMapper objectMapper;
    private ArrayList<String> supervisorsFIO;
    public JSONDataExtractor(){
        objectMapper = new ObjectMapper();
        supervisorsFIO = new ArrayList<>();
    }
    public ArrayList<String> getSupervisorsFIO(){
        ArrayList<OtchetNeedReview> otchetNeedReviews = getOthcetsNeedReviews();
        for (OtchetNeedReview otchet : otchetNeedReviews){

        }
        return null;
    }
    public ArrayList<OtchetWithReview> getOthcetsWithReviews(){
        ArrayList<OtchetWithReview> otchetWithReviews = new ArrayList<>();
        try {
            otchetWithReviews = jsonReaderWithReview();
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return otchetWithReviews;
    }
    public ArrayList<OtchetNeedReview> getOthcetsNeedReviews(){
        ArrayList<OtchetNeedReview> otchetNeedReviews = new ArrayList<>();
        try {
            otchetNeedReviews = jsonReaderNeedReview();
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return otchetNeedReviews;
    }
    public OtchetNeedReview getOtchetNeedReview(String jsonName){
        for (OtchetNeedReview jsonOtchet : getOthcetsNeedReviews()){
            if (jsonOtchet.getTitle().equals(jsonName))
                return jsonOtchet;
        }
        return null;
    }

    private ArrayList<OtchetWithReview> jsonReaderWithReview() throws JsonProcessingException {
        ArrayList<OtchetWithReview> otchetWithReviews = new ArrayList<>();
        try{
            otchetWithReviews = objectMapper.readValue(new File("Data\\withreview.json"), new TypeReference<ArrayList<OtchetWithReview>>(){});
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return otchetWithReviews;
    }
    private ArrayList<OtchetNeedReview> jsonReaderNeedReview() throws JsonProcessingException {
        ArrayList<OtchetNeedReview> otchetNeedReviews = new ArrayList<>();
        try{
            otchetNeedReviews = objectMapper.readValue(new File("Data\\needreview.json"), new TypeReference<ArrayList<OtchetNeedReview>>(){});
        }catch (Exception e){
            System.out.println(e.getClass().getName());
            System.out.println(e);
        }
        return otchetNeedReviews;
    }
}
