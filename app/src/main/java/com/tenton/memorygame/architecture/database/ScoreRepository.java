package com.tenton.memorygame.architecture.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreRepository {
    private ScoreDao myScoreDao;
    private LiveData<List<Score>> scoreList;
 public    ScoreRepository(Application application) {
        ScoreDatabase database = ScoreDatabase.getDatabase(application);
        myScoreDao = database.scoreDao();
//        scoreList = myScoreDao.fetchTopScores();
    }

   public LiveData<List<Score>> getScoreList() {
        return scoreList;
    }
    public void insert (Score score) {

// Run on a background thread//

        new newAsyncTask(myScoreDao).execute(score);
    }
    private static class newAsyncTask extends AsyncTask<Score, Void, Void> {

        private ScoreDao myAsyncDao;

        newAsyncTask(ScoreDao dao) {
            myAsyncDao = dao;
        }

        @Override
        protected Void doInBackground(final Score... params) {
            myAsyncDao.insertScore(params[0]);
            return null;
        }

    }

}
