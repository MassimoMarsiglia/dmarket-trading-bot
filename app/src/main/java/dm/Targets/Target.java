package dm.Targets;

import com.google.gson.annotations.SerializedName;

public class Target {

    @SerializedName("Price")
    private Price price;

    @SerializedName("Status")
    private String activity;

    @SerializedName("Title")
    private String name;

    @SerializedName("TargetID")
    private String targetID;

    public Target() {
        this.name = name;
        this.price = price;
        this.activity = activity;
    }


    public String getTargetValue() {
        return price.toString();
    }

    public void setTargetValue(String Price) {
        this.price = price;
    }

    public String getActivity() {
        return this.activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "\nTitle: " + name +
        "\nTargetPrice: " + price +
        "\nStatus: " + activity;
    }
}
