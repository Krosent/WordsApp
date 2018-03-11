package ak.com.projectwords.POJOs.WordFullData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by artyomkuznetsov on 3/11/18.
 */

public class Rhymes {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("rhymes")
    @Expose
    private Rhymes_ rhymes;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Rhymes_ getRhymes() {
        return rhymes;
    }

    public void setRhymes(Rhymes_ rhymes) {
        this.rhymes = rhymes;
    }


    public class Rhymes_ {

        @SerializedName("all")
        @Expose
        private List<String> all = null;

        public List<String> getAll() {
            return all;
        }

        public void setAll(List<String> all) {
            this.all = all;
        }

    }

}

