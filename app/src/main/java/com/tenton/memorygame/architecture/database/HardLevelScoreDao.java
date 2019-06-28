package com.tenton.memorygame.architecture.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface HardLevelScoreDao {
    @Query("SELECT * FROM hard_level_score_table ORDER BY scores ASC")
    List<HardLevelScore> fetchTopScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(HardLevelScore score);
}
