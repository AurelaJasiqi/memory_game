package com.tenton.memorygame.architecture.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {EasyLevelScore.class,HardLevelScore.class}, version = 2, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {
    public static ScoreDatabase  INSTANCE;
    public abstract EasyLevelScoreDao easyLevelScore();
    public abstract HardLevelScoreDao hardLevelScoreDao();
   public static ScoreDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (ScoreDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScoreDatabase.class, "score_database")
                            .build();
                }
            }

        }
        return INSTANCE;
    }
}
