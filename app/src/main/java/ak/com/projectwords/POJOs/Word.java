package ak.com.projectwords.POJOs;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by artyomkuznetsov on 1/18/18.
 */
@Entity
public class Word {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "word")
    private String word;
    @ColumnInfo(name = "definition")
    private String definition;


    public Word(String word) {
        this.word = word;
    }


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


}
