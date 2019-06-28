package com.tenton.memorygame.architecture.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "score_table")
public class Score {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int scores;

    public Score(int scores) {
        this.scores = scores;
    }
}
