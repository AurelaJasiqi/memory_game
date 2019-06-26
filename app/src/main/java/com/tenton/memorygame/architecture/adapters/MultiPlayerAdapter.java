package com.tenton.memorygame.architecture.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.tenton.memorygame.Modules.GlideApp;
import com.tenton.memorygame.R;
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

    Boolean playerOne=true;
    Boolean playerTwo=false;


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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.cardView.animate().withLayer().rotationY(90).setDuration(50).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (imageResponseMultiPlayer.get(position).getImgUrl() != null) {
                            GlideApp.with(context).load(imageResponseMultiPlayer.get(position).getImgUrl()).into(holder.imageView);
                        } else {
                            holder.imageView.setImageResource(imageResponseMultiPlayer.get(position).getSource());
                        }
                        holder.cardView.animate().withLayer().rotationY(0).setDuration(50).start();
                    }
                });

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
                            }

                            @Override
                            public void onFinish() {
                                holder.cardView.animate().withLayer().rotationY(90).setDuration(150).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.imageView.setImageResource(R.drawable.back_button);
                                        holder.cardView.animate().withLayer().rotationY(0).setDuration(50).start();

                                    }
                                });
                                isClicked = false;
                            }
                        }.start();

                    } else {

                        new CountDownTimer(500, 150) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                            }

                            @Override
                            public void onFinish() {
                                holder.cardView.animate().withLayer().rotationY(90).setDuration(150).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        holder.imageView.setImageResource(R.drawable.back_button);
                                        imgv.setImageResource(R.drawable.back_button);
                                        holder.cardView.animate().withLayer().rotationY(0).setDuration(50).start();
                                    }
                                });
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

        public void playerTurn(){
        if(playerOne == true && playerTwo ==  false){
            playerOne = false;
            playerTwo = true;
        }else if(playerOne == false && playerTwo == true){
            playerOne = true;
            playerTwo = false;
        }
        }

    }



