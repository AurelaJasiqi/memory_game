package com.tenton.memorygame.architecture.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
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
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.models.ImageResponse;
import java.util.List;


public class SinglePlayerAdapter extends RecyclerView.Adapter<SinglePlayerAdapter.MyViewHolder> {
    //constructor args
    int nrImages;
    int width;
    int height;

    //other variables
    CardView crv;
    ImageView imgv;
    String photoId;
    int photoTag;
    int pos;

    //view holder variables
    ImageView imageView_holder;
    CardView cardView_holder;
    boolean isClicked=false;
    List<ImageResponse> imageResponse;
    Context context;
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
        //Flip Animation
        holder.cardView.setVisibility(View.INVISIBLE);
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 0f, 1f);

        Glide.with(context).asBitmap().load(imageResponse.get(position).getImgUrl()).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                holder.cardView.setVisibility(View.VISIBLE);
                Drawable drawable;
                drawable=new BitmapDrawable(context.getResources(),resource);
                holder.imageView.setImageDrawable(drawable);
                return false;
            }
        }).into(new CustomTarget<Bitmap>() {

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                holder.imageView.setImageResource(R.drawable.back_button);

            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//
//                oa1.setInterpolator(new DecelerateInterpolator());
//                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
//                oa1.addListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        super.onAnimationEnd(animation);
                            if(imageResponse.get(position).getImgUrl() != null){
                                Glide.with(context).asBitmap().load(imageResponse.get(position).getImgUrl()).into(new CustomTarget<Bitmap>() {

                                    @Override
                                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                        Drawable drawable;
                                        drawable=new BitmapDrawable(context.getResources(),resource);
                                        holder.imageView.setImageDrawable(drawable);

                                    }

                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {

                                    }
                                });

                            }
                            else {
                                holder.imageView.setImageResource(imageResponse.get(position).getSource());
                            }

//                        oa2.setDuration(50);
//                        oa2.start();
//                    }
//                });
//                oa1.setDuration(50);
//                oa1.start();

                    if(!isClicked){
                        isClicked=true;
                        photoId=imageResponse.get(position).getImgId();
                        photoTag=imageResponse.get(position).getTag();
                        imgv=holder.imageView;
                        crv=holder.cardView;
                        pos=position;

                    }else{
                        //id e ndryshme tagu i njejt
                        if(photoId != imageResponse.get(position).getImgId() && photoTag == imageResponse.get(position).getTag() ){
                            new CountDownTimer(300, 300) {
                                @Override
                                public void onTick(long millisUntilFinished) {
//                                    oa1.addListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);
                                    if(imageResponse.get(position).getImgUrl() != null){
                                        Glide.with(context).asBitmap().load(imageResponse.get(position).getImgUrl()).into(new CustomTarget<Bitmap>() {

                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                Drawable drawable;
                                                drawable=new BitmapDrawable(context.getResources(),resource);
                                                holder.imageView.setImageDrawable(drawable);

                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {

                                            }
                                        });

                                    }
                                    else {
                                        holder.imageView.setImageResource(imageResponse.get(position).getSource());
                                    }
//                                            oa2.setDuration(50);
//                                            oa2.start();
//                                        }
//                                    });
//                                    oa1.setDuration(50);
//                                    oa1.start();

                                }
                                @Override
                                public void onFinish() {
                                    v.setClickable(false);
                                    crv.setClickable(false);
                                    v.setVisibility(View.INVISIBLE);
                                    crv.setVisibility(View.INVISIBLE);
                                    isClicked=false;
                                }
                            }.start();
                        }
                        ///
                        else if(photoId==imageResponse.get(position).getImgId()){
                            new CountDownTimer(300, 300) {

                                @Override
                                public void onTick(long millisUntilFinished) {
//                                    oa1.addListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);
                                    if(imageResponse.get(position).getImgUrl() != null){
                                        Glide.with(context).asBitmap().load(imageResponse.get(position).getImgUrl()).into(new CustomTarget<Bitmap>() {

                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                Drawable drawable;
                                                drawable=new BitmapDrawable(context.getResources(),resource);
                                                holder.imageView.setImageDrawable(drawable);

                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {

                                            }
                                        });

                                    }
                                    else {
                                        holder.imageView.setImageResource(imageResponse.get(position).getSource());
                                    }
//                                            oa2.setDuration(50);
//                                            oa2.start();
//                                        }
//                                    });
//                                    oa1.setDuration(50);
//                                    oa1.start();

                                }

                                @Override
                                public void onFinish() {
//                                    oa1.addListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);
                                    holder.imageView.setImageResource(R.drawable.back_button);
                                    isClicked=false;
                                    oa2.setDuration(50);
                                    oa2.start();
                                }
//                                    });
//                                }
                            }.start();
                        }
                        //tjter rast
                        else {
                            new CountDownTimer(300, 300) {

                                @Override
                                public void onTick(long millisUntilFinished) {
//                                    oa1.addListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);
                                    if(imageResponse.get(position).getImgUrl() != null){
                                        Glide.with(context).asBitmap().load(imageResponse.get(position).getImgUrl()).into(new CustomTarget<Bitmap>() {

                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                                                Drawable drawable;
                                                drawable=new BitmapDrawable(context.getResources(),resource);
                                                holder.imageView.setImageDrawable(drawable);

                                            }

                                            @Override
                                            public void onLoadCleared(@Nullable Drawable placeholder) {

                                            }
                                        });

                                    }
                                    else {
                                        holder.imageView.setImageResource(imageResponse.get(position).getSource());
                                    }
//                                            oa2.setDuration(50);
//                                            oa2.start();
//                                        }
//                                    });
//                                    oa1.setDuration(50);
//                                    oa1.start();

                                }

                                @Override
                                public void onFinish() {
//                                    oa1.addListener(new AnimatorListenerAdapter() {
//                                        @Override
//                                        public void onAnimationEnd(Animator animation) {
//                                            super.onAnimationEnd(animation);

                                    holder.imageView.setImageResource(R.drawable.back_button);
                                    imgv.setImageResource(R.drawable.back_button);
                                    isClicked=false;
                                    oa2.setDuration(50);
                                    oa2.start();

                                }
//                                    });
//                                    oa1.setDuration(50);
//                                    oa1.start();

//                                }
                            }.start();

                        }
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