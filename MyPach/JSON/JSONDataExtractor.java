package MyPach.JSON;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;

public class JSONDataExtractor {
    private ObjectMapper objectMapper;
    public JSONDataExtractor(){
        objectMapper = new ObjectMapper();
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
