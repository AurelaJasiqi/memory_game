package com.tenton.memorygame.architecture.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EasyLevelScoreDao {
    @Query("SELECT * FROM easy_level_score_table ORDER BY scores ASC")
    LiveData<List<EasyLevelScore>> fetchTopScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(EasyLevelScore easyLevelScore);
}
