package com.tenton.memorygame.architecture.fragments;

import androidx.databinding.DataBindingUtil;
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
    private ScoreDao scoreDao;
    private List<Score> scores=new ArrayList<>();


    public static ScoreFragment newInstance() {
        return new ScoreFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.score_fragment, container, false);
        binding.setLifecycleOwner(this);
        application=this.getActivity().getApplication();
        scoreDao= ScoreDatabase.getDatabase(application).scoreDao();
        mViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        binding.setViewmodel(mViewModel);

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new GetScores().execute();

    }
    private class GetScores extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            scores.addAll(scoreDao.fetchTopScores());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(scores.size()>1){
                binding.easyLevel1.setText(String.valueOf(scores.get(0).scores)+" seconds");
            }
            if(scores.size()>2){
                binding.easyLevel2.setText(String.valueOf(scores.get(1).scores)+" seconds");
            }
            if(scores.size()>3){
                binding.easyLevel3.setText(String.valueOf(scores.get(2).scores)+" seconds");
            }
            super.onPostExecute(aVoid);
        }
    }

}
