package ak.com.projectwords.POJOs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by artyomkuznetsov on 2/20/18.
 */

public class Synonyms {
    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("synonyms")
    @Expose
    private List<String> synonyms = null;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }
}
