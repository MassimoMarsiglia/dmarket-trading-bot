package dm.Targets;

import java.util.List;
import com.google.gson.annotations.SerializedName;

class TargetFetcherResponse {
    @SerializedName("Items")
    private List<Target> targetedItems;

    public List<Target> getTargetedItems() {
        return targetedItems;
    }
}