package ak.com.projectwords.Services;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ak.com.projectwords.POJOs.Word;

/**
 * Created by artyomkuznetsov on 2/21/18.
 */
@Database(entities = {Word.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {
    static AppDatabase INSTANCE;
    public abstract DatabaseService service();


public static AppDatabase getDatabase(Context context) {
    if(INSTANCE == null) {
        return INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "wordAppDatabase").build();
    }
    return INSTANCE;
}

public static void destroyInstanceOfDB() {
    INSTANCE = null;
}

}

