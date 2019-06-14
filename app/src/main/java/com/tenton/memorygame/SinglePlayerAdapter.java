package com.tenton.memorygame;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class SinglePlayerAdapter extends RecyclerView.Adapter<SinglePlayerAdapter.MyViewHolder> {
    @NonNull
    @Override
    public SinglePlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull SinglePlayerAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView ;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
            imageView = (ImageView) itemView.findViewById(R.id.img_id);


        }
    }
}
