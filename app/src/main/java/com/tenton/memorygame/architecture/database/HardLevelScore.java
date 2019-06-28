package com.tenton.memorygame.architecture.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "hard_level_score_table")
public class HardLevelScore {
        @PrimaryKey(autoGenerate = true)
        private int scores;

        public int getScores() {
            return scores;
        }

        public void setScores(int scores) {
            this.scores = scores;
        }

        public HardLevelScore(int scores) {
            this.scores = scores;
        }
    }

