package dm.Targets;

import com.google.gson.annotations.SerializedName;

public class Price {
    
    @SerializedName("Amount")
    private String price;

    public String getPrice(){
        return price;
    }

    public double getPriceAsDouble(){
        return Double.parseDouble(price);
    }

    @Override
    public String toString(){
        return price;
    }
}
