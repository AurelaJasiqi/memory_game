package com.tenton.memorygame.architecture.database;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface HardLevelScoreDao {
    @Query("SELECT * FROM hard_level_score_table ORDER BY scores ASC")
    List<Score> fetchTopScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(Score score);
}
