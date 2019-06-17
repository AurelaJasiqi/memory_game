package com.tenton.memorygame.architecture.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.models.ImageResponse;

import java.util.Collections;
import java.util.List;


public class SinglePlayerAdapter extends RecyclerView.Adapter<SinglePlayerAdapter.MyViewHolder> {
    int nrImages;
    int width;
    int height;

    List<ImageResponse> imageResponse;
    Context context;

    String photoId;
    int photoTag;
    Boolean isClicked = false;

    @NonNull
    @Override
    public SinglePlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(view);
    }

    public SinglePlayerAdapter(List<ImageResponse> imageResponse, Context context, int nrImages, int width, int height) {
        this.imageResponse=imageResponse;
        this.context=context;
        this.nrImages = nrImages;
        this.width = width;
        this.height = height;

    }

    @Override
    public void onBindViewHolder(@NonNull SinglePlayerAdapter.MyViewHolder holder, int position) {
        holder.cardView.getLayoutParams().height = height;
        holder.cardView.getLayoutParams().width = width;

        //Flip Animation
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 0f, 1f);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(imageResponse.get(position).getImgUrl() != null){
                        Glide.with(context).load(imageResponse.get(position).getImgUrl()).into(holder.imageView);

                        }
                        else {
                            holder.imageView.setImageResource(imageResponse.get(position).getSource());
                        }
                        oa2.start();
                    }
                });
                oa1.start();

               if(!isClicked){
                   photoId=imageResponse.get(position).getImgId();
                   photoTag=imageResponse.get(position).getTag();
                   isClicked=true;


               }else{

               }



            }
        });


    }

    @Override
    public int getItemCount() {
        return nrImages;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_id);
            imageView = itemView.findViewById(R.id.img_id);
        }
    }
}
