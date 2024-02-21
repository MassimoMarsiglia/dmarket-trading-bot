package dm.AggregatedPrice;

import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("BestPrice")
    private String bestPrice;

    @SerializedName("Count")
    private int count;

    public String getBestPrice() {
        return bestPrice;
    }

    public int getCount() {
        return count;
    }

    public double getBestPriceAsDouble() {
        // Assuming the bestPrice is a valid representation of a double
        return Double.parseDouble(bestPrice);
    }

    @Override
    public String toString() {
        return bestPrice;
    }
}