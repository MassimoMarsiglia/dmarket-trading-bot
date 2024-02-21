package dm.DesiredItems;

import java.util.ArrayList;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class Item {

    private String name;
    private String encodedName;
    private double fee = 0.9;

    public Item(String name){
        this.name = name;
        this.encodedName = encode(name);
        this.fee = fee;
    }

    public String getName(){
        return name;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getFee() {
        return fee;
    }

    public String getEncodedName(){
        return encodedName;
    }

    private String encode(String plainText) {
        String encodedText = "";
    try {
        encodedText = URLEncoder.encode(plainText, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedText;
    }
    
    @Override
    public String toString(){
        return name + " fee: " + String.valueOf(fee);
    }

}
