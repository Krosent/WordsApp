package ak.com.projectwords.POJOs.WordFullData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by artyomkuznetsov on 2/26/18.
 */

public class Pronunciation {
    @SerializedName("all")
    @Expose
    private String all;

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
