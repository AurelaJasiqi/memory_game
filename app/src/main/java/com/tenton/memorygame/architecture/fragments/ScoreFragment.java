package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.database.EasyLevelScore;
import com.tenton.memorygame.architecture.database.EasyLevelScoreDao;
import com.tenton.memorygame.architecture.database.HardLevelScore;
import com.tenton.memorygame.architecture.database.HardLevelScoreDao;
import com.tenton.memorygame.architecture.database.Score;
import com.tenton.memorygame.architecture.database.ScoreDao;
import com.tenton.memorygame.architecture.database.ScoreDatabase;
import com.tenton.memorygame.architecture.viewmodels.ScoreViewModel;
import com.tenton.memorygame.databinding.ScoreFragmentBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ScoreFragment extends Fragment {

    private ScoreViewModel mViewModel;
    private ScoreFragmentBinding binding;
    private Application application;
    private EasyLevelScoreDao easyLevelScoreDao;
    private HardLevelScoreDao hardLevelScoreDao;
    private List<EasyLevelScore> easyLevelScores=new ArrayList<>();
    private List<HardLevelScore> hardLevelScores=new ArrayList<HardLevelScore>();


    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false);
        binding.setLifecycleOwner(this);
        application=this.getActivity().getApplication();
        easyLevelScoreDao= ScoreDatabase.getDatabase(application).easyLevelScore();
        hardLevelScoreDao= ScoreDatabase.getDatabase(application).hardLevelScoreDao();
        mViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        binding.setViewmodel(mViewModel);

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetScores().execute();

        easyLevelScoreDao.fetchTopScores().observe(this, response -> {
            easyLevelScores.addAll(response);
            setEasyScores();

        });
        hardLevelScoreDao.fetchTopScores().observe(this, response -> {
            hardLevelScores.addAll(response);
            setHardScores();

        });

    }
    private class GetScores extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {



            easyLevelScoreDao.fetchTopScores();
          hardLevelScoreDao.fetchTopScores();
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
        }
    }
    private void setEasyScores(){
        if(easyLevelScores.size()>0  ){
            binding.easyLevel1.setText(String.valueOf(easyLevelScores.get(0).getScores())+" seconds");
        }
        if(easyLevelScores.size()>1 ){
            binding.easyLevel2.setText(String.valueOf(easyLevelScores.get(1).getScores())+" seconds");
        }
        if(easyLevelScores.size()>2){
            binding.easyLevel3.setText(String.valueOf(easyLevelScores.get(2).getScores())+" seconds");
        }

    }
    private void setHardScores(){
        if (hardLevelScores.size()>0){
            binding.hardLevel11.setText(String.valueOf(hardLevelScores.get(0).getScores())+" seconds");
        }
        if (hardLevelScores.size()>1){
            binding.hardLevel2.setText(String.valueOf(hardLevelScores.get(1).getScores())+" seconds");
        }
        if (hardLevelScores.size()>2){
            binding.hardLevel3.setText(String.valueOf(hardLevelScores.get(2).getScores())+" seconds");
        }
    }

}
