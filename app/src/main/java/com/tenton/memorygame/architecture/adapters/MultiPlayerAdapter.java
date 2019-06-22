package com.tenton.memorygame.architecture.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.models.ImageResponse;
import com.tenton.memorygame.architecture.viewmodels.MultiPlayerViewModel;

import java.net.UnknownHostException;
import java.util.List;

public class MultiPlayerAdapter extends RecyclerView.Adapter<MultiPlayerAdapter.MultiPlayerViewHolder> {
    List<ImageResponse> imageResponseMultiPlayer;
    List<ImageResponse> imageResponseListLoader;
    Context context;
    Boolean isClicked = false;
    ImageView imgv;
    String photoId;
    int photoTag;


    public MultiPlayerAdapter(List<ImageResponse> imageResponseMultiPlayer, Context context){
        this.imageResponseMultiPlayer=imageResponseMultiPlayer;
        this.context=context;
    }
    //Collections.shuffle(imageResponse);

    @NonNull
    @Override
    public MultiPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_view, parent, false);

        return new MultiPlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiPlayerViewHolder holder, int position) {

        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 0f, 1f);
        final ObjectAnimator oa3 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 0f, 0f);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(imageResponseMultiPlayer.get(position).getImgUrl() != null){
                            Glide.with(context).load(imageResponseMultiPlayer.get(position).getImgUrl()).into(holder.imageView);
                        }
                        oa2.start();
                    }
                });
                oa1.start();

                if(!isClicked){
                    isClicked=true;
                    photoId=imageResponseMultiPlayer.get(position).getImgId();
                    photoTag=imageResponseMultiPlayer.get(position).getTag();
                    imgv=holder.imageView;
                }else{
                    if(photoId == imageResponseMultiPlayer.get(position).getImgId() && photoTag == imageResponseMultiPlayer.get(position).getTag() ){
                        oa1.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                if(imageResponseMultiPlayer.get(position).getImgUrl() != null){
                                   holder.imageView.setImageResource(R.drawable.back_button);

                                }
                                oa2.start();
                            }
                        });
                        oa1.start();
                        isClicked=false;
                    }else if(photoId != imageResponseMultiPlayer.get(position).getImgId() && photoTag == imageResponseMultiPlayer.get(position).getTag()){
                        oa1.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                if(imageResponseMultiPlayer.get(position).getImgUrl() != null){
                                    oa3.start();
                                }
                                oa2.start();
                            }
                        });
                        oa1.start();
                        isClicked = false;
                    }else{
                        oa1.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                if(imageResponseMultiPlayer.get(position).getImgUrl() != null){

                                }
                                oa2.start();
                            }
                        });
                        oa1.start();
                        imgv.setImageResource(R.drawable.back_button);
                        holder.imageView.setImageResource(R.drawable.back_button);
                        isClicked = false;
                    }
                }
            }

        });


    }

    @Override
    public int getItemCount() {
        return imageResponseMultiPlayer.size();
    }

    public static class MultiPlayerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        public MultiPlayerViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_id);
            imageView = itemView.findViewById(R.id.img_id);
        }
    }
}


