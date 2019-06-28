package com.tenton.memorygame.architecture.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.*;

import java.util.List;
@Dao
public interface ScoreDao {

    @Query("SELECT * FROM score_table ORDER BY scores ASC")
    List<Score> fetchTopScores();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertScore(Score score);
}
