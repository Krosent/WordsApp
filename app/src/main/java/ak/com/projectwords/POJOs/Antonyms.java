package ak.com.projectwords.POJOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by artyomkuznetsov on 2/19/18.
 */

public class Antonyms {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("antonyms")
    @Expose
    private List<String> antonyms = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(List<String> antonyms) {
        this.antonyms = antonyms;
    }
}
