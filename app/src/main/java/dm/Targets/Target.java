package dm.Targets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Target {

    @SerializedName("Price")
    private Price price;

    //@SerializedName("Status")
    //private String activity;

    @SerializedName("Title")
    private String name;

    @SerializedName("TargetID")
    private String targetID;

    private long creationTime;

    public Target() {
        this.name = name;
        this.price = price;
        //this.activity = activity;
        this.targetID = targetID;
        this.creationTime = creationTime;
    }

    public void updateCreationTime(){
        this.creationTime = System.currentTimeMillis()/1000;
    }

    public long getCreationTime(){
        return creationTime;
    }

    public String getTargetID(){
        return targetID;
    }

    public void setTargetID(String targetID){
        this.targetID = targetID;
    }

    public String getTargetValue() {
        return price.toString();
    }

    public void setTargetValue(String Price) {
        this.price = price;
    }

    //public String getActivity() {
    //    return this.activity;
    //}

    //public void setActivity(String activity) {
    //    this.activity = activity;
    //}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice(){
        return price;
    }
    
    @Override
    public String toString() {
        return "\nTitle: " + name +
        "\nTargetPrice: " + price +
        //"\nStatus: " + activity +
        "\nTargetID: " + targetID;
    }
}
