package MyPach;

public class EndData {
    private int[] ids;
    private String review;
    public EndData(int[] ids, String review){
        this.ids = ids;
        this.review = review;
    }
    public EndData(String review) {
        this.ids = new int[]{-1, -1};
        this.review = review;
    }
    public boolean addId(int id){
        if (ids[0] == -1)
        {
            ids[0] = id;
            return true;
        }
        else if (ids[1] == -1)
        {
            ids[1] = id;
            return true;
        }
        return false;
    }
}
