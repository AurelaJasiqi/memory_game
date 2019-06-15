package com.tenton.memorygame.architecture.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tenton.memorygame.R;


public class SinglePlayerAdapter extends RecyclerView.Adapter<SinglePlayerAdapter.MyViewHolder> {
    int nrImages;
    int width;
    int height;
    int top,left,bottom,right;

    @NonNull
    @Override
    public SinglePlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        view=layoutInflater.inflate(R.layout.item_view,parent,false);

        return new MyViewHolder(view);
    }

public SinglePlayerAdapter(int nrImages, int width, int height, int top, int right, int bottom, int left){
this.nrImages=nrImages;
    this.width=width;
    this.height=height;
    this.top=top;
    this.left=left;
    this.bottom=bottom;
    this.right=right;
}
    @Override
    public void onBindViewHolder(@NonNull SinglePlayerAdapter.MyViewHolder holder, int position) {
        holder.cardView.getLayoutParams().height=height;
        holder.cardView.getLayoutParams().width=width;
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 0f, 1f);
        //ketu ndryshojme gjithashtu edhe margjinat e fotove varesisht prej vlerave qe i kemi dhene gjate deklarimit te konstruktroit
        ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();
        marginParams.setMargins(left, top, right, bottom);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        holder.imageView.setImageResource(R.drawable.index);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nrImages;
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
