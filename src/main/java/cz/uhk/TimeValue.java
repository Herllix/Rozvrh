package cz.uhk;

import com.google.gson.annotations.SerializedName;

public class TimeValue {
    private String value;

    public String getValue() {
        return value != null ? value : "??:??";
    }
}
