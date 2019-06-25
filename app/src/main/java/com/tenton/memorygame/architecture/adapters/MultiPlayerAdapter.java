package com.tenton.memorygame.architecture.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.GlideApp;
import com.tenton.memorygame.architecture.models.ImageResponse;

import java.util.List;

public class MultiPlayerAdapter extends RecyclerView.Adapter<MultiPlayerAdapter.MultiPlayerViewHolder> {
    List<ImageResponse> imageResponseMultiPlayer;
    List<ImageResponse> imageResponseListLoader;
    Context context;
    Boolean isClicked = false;
    ImageView imgv;
    String photoId;
    int photoTag;
    CardView crv;
    CountDownTimer countDownTimer;


    public MultiPlayerAdapter(List<ImageResponse> imageResponseMultiPlayer, Context context) {
        this.imageResponseMultiPlayer = imageResponseMultiPlayer;
        this.context = context;
    }

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

                        GlideApp.with(context).load(imageResponseMultiPlayer.get(position).getImgUrl()).into(holder.imageView);
                        oa2.start();
                    }
                });
                oa1.start();

                if (isClicked == false) {
                    isClicked = true;
                    photoId = imageResponseMultiPlayer.get(position).getImgId();
                    photoTag = imageResponseMultiPlayer.get(position).getTag();
                    imgv = holder.imageView;
                    crv = holder.cardView;
                } else {
                    if (photoId != imageResponseMultiPlayer.get(position).getImgId() && photoTag ==
                            imageResponseMultiPlayer.get(position).getTag()) {
                       countDownTimer = new CountDownTimer(500, 150) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                if (imageResponseMultiPlayer.get(position).getImgUrl() != null) {
                                    GlideApp.with(context).load(imageResponseMultiPlayer.get(position).getImgUrl()).dontAnimate().into(holder.imageView);
                                }
                            }

                            @Override
                            public void onFinish() {
                                crv.setVisibility(View.INVISIBLE);
                                v.setVisibility(View.INVISIBLE);
                                isClicked = false;
                            }
                        }.start();

                    } else if (photoId == imageResponseMultiPlayer.get(position).getImgId() && photoTag ==
                            imageResponseMultiPlayer.get(position).getTag()) {

                        countDownTimer = new CountDownTimer(500, 150) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                GlideApp.with(context).load(imageResponseMultiPlayer.get(position).getImgUrl()).into(holder.imageView);
                            }

                            @Override
                            public void onFinish() {
                                holder.imageView.setImageResource(R.drawable.back_button);
                                isClicked = false;
                            }
                        }.start();

                    } else {

                        new CountDownTimer(500, 150) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                GlideApp.with(context).load(imageResponseMultiPlayer.get(position).getImgUrl()).into(holder.imageView);
                            }

                            @Override
                            public void onFinish() {
                                holder.imageView.setImageResource(R.drawable.back_button);
                                imgv.setImageResource(R.drawable.back_button);
                                isClicked = false;
                            }
                        }.start();

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



