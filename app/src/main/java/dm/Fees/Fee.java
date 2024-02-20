package dm.Fees;

import com.google.gson.annotations.SerializedName;

public class Fee {
    
    @SerializedName("title")
    String name;

    @SerializedName("fraction")
    String feePercent;

    public String getName(){
        return name;
    }

    public double feeAsDouble(){
        double tmp = Double.parseDouble(feePercent);
        return 1.0-tmp;
    }

    @Override
    public String toString(){
        return name + feePercent;
    }
}
