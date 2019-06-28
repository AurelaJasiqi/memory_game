package com.tenton.memorygame.architecture.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "easy_level_score_table")
public class EasyLevelScore {
    @PrimaryKey(autoGenerate = true)
    private int scores;

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public EasyLevelScore(int scores) {
        this.scores = scores;
    }
}
