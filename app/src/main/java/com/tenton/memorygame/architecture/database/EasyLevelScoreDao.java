package com.tenton.memorygame.architecture.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EasyLevelScoreDao {
    @Query("SELECT * FROM easy_level_score_table ORDER BY scores ASC")
    List<Score> fetchTopScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(Score score);
}
