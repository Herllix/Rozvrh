package cz.uhk;

import com.google.gson.annotations.SerializedName;

public class TimeValue {
    @SerializedName("value")
    private String value;

    public String getValue() {
        return value != null ? value : "??:??";
    }
}
