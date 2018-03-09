package ak.com.projectwords.Services;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ak.com.projectwords.POJOs.Word;

/**
 * Created by artyomkuznetsov on 2/21/18.
 */
    @Dao
    public interface DatabaseService {
        @Insert
        void insert(Word word);
        @Insert
        void insertAll(Word... words);

        @Query("DELETE FROM word WHERE word = :word")
        void deleteWord(String word);

        @Query("SELECT * FROM word")
        List<Word> fetchAllWords();

        @Query("SELECT * FROM word WHERE word = :word LIMIT 1")
        Word fetchWord(String word);
}
