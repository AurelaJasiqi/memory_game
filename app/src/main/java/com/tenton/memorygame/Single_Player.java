package com.tenton.memorygame;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenton.memorygame.databinding.SinglePlayerFragmentBinding;

public class Single_Player extends Fragment {

    private SinglePlayerViewModel mViewModel;
    private SinglePlayerAdapter adapter;
    private SinglePlayerFragmentBinding binding;
    private String level;

    public static Single_Player newInstance() {
        return new Single_Player();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.single__player_fragment,container,false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SinglePlayerViewModel.class);



        level=Single_PlayerArgs.fromBundle(getArguments()).getLevel();
        if (level!=null){
            if(level.equals("easy")){

                adapter=new SinglePlayerAdapter(9,250,300,10,0,10,0);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                binding.recyclerView.setAdapter(adapter);
            }
            if(level.equals("hard")){
                adapter=new SinglePlayerAdapter(16,180,230,5,0,10,0);
                binding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));
                binding.recyclerView.setAdapter(adapter);
            }

        }



    }

}
