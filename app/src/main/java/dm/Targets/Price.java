package dm.Targets;

import com.google.gson.annotations.SerializedName;

public class Price {
    
    @SerializedName("Amount")
    private String price;

    public String getPrice(){
        return price;
    }

    @Override
    public String toString(){
        return price;
    }
}
