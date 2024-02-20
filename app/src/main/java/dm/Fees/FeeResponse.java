package dm.Fees;

import java.util.List;
import com.google.gson.annotations.SerializedName;

class FeeResponse {

    @SerializedName("reducedFees")
    private List<Fee> feesees;

    public List<Fee> getFees() {
        return feesees;
    }
}