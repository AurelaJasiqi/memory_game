package com.tenton.memorygame.architecture.adapters;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tenton.memorygame.R;
import com.tenton.memorygame.architecture.models.ImageResponse;
import java.util.List;

public class SinglePlayerAdapter extends RecyclerView.Adapter<SinglePlayerAdapter.MyViewHolder> implements View.OnClickListener {
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
Picasso picasso;



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
picasso=Picasso.get();
picasso.setIndicatorsEnabled(true);
picasso.setLoggingEnabled(true);

        //Flip Animation
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(holder.cardView, "scaleX", 0f, 1f);
        picasso.load(imageResponse.get(0).getImgUrl()).fetch(new Callback() {
            @Override
            public void onSuccess() {
                picasso.load(imageResponse.get(0).getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                picasso.load(imageResponse.get(5).getImgUrl()).fetch(new Callback() {
                    @Override
                    public void onSuccess() {
                        picasso.load(imageResponse.get(5).getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                        picasso.load(imageResponse.get(1).getImgUrl()).fetch(new Callback() {
                            @Override
                            public void onSuccess() {
                                picasso.load(imageResponse.get(1).getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                                picasso.load(imageResponse.get(2).getImgUrl()).fetch(new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        picasso.load(imageResponse.get(2).getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                                        picasso.load(imageResponse.get(3).getImgUrl()).fetch(new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                picasso.load(imageResponse.get(3).getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                                                picasso.load(imageResponse.get(4).getImgUrl()).fetch(new Callback() {
                                                    @Override
                                                    public void onSuccess() {
                                                        picasso.load(imageResponse.get(4).getImgUrl()).networkPolicy(NetworkPolicy.OFFLINE).into(holder.imageView);
                                                        picasso.load(imageResponse.get(5).getImgUrl()).fetch(new Callback() {
                                                            @Override
                                                            public void onSuccess() {
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
                                                                            //Glide.with(context).load(imageResponse.get(position).getImgUrl()).into(holder.imageView);
                                                                            picasso
                                                                                    .load(imageResponse.get(position).getImgUrl())
                                                                                    // .centerCrop()
                                                                                    .into(holder.imageView);

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
                                                                                            //  Glide.with(context).load(imageResponse.get(position).getImgUrl()).into(holder.imageView);
                                                                                            picasso
                                                                                                    .load(imageResponse.get(position).getImgUrl())
                                                                                                    // .resize(50, 50)
                                                                                                    .into(holder.imageView);
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
                                                                                            // Glide.with(context).load(imageResponse.get(position).getImgUrl()).into(holder.imageView);
                                                                                            picasso
                                                                                                    .load(imageResponse.get(position).getImgUrl())
                                                                                                    //    .centerCrop()
                                                                                                    .into(holder.imageView);
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
                                                                                            // Glide.with(context).load(imageResponse.get(position).getImgUrl()).into(holder.imageView);
                                                                                            picasso
                                                                                                    .load(imageResponse.get(position).getImgUrl())
                                                                                                    // .centerCrop()
                                                                                                    .into(holder.imageView);
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
                                                            public void onError(Exception e) {

                                                            }
                                                        });
                                                    }
                                                    @Override
                                                    public void onError(Exception e) {

                                                    }
                                                });
                                            }
                                            @Override
                                            public void onError(Exception e) {

                                            }
                                        });
                                    }
                                    @Override
                                    public void onError(Exception e) {

                                    }
                                });
                            }
                            @Override
                            public void onError(Exception e) {

                            }
                        });
                    }
                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
            @Override
            public void onError(Exception e) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return nrImages;
    }

    @Override
    public void onClick(View v) {

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
