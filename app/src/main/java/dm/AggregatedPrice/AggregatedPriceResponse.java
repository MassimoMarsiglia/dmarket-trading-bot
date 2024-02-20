package dm.AggregatedPrice;

import java.util.List;
import com.google.gson.annotations.SerializedName;

class AggregatedPriceResponse {
    @SerializedName("AggregatedTitles")
    private List<AggregatedPrice> aggregatedTitles;

    public List<AggregatedPrice> getAggregatedTitles() {
        return aggregatedTitles;
    }
}