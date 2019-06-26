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
public class SinglePlayerAdapter extends RecyclerView.Adapter<SinglePlayerAdapter.MyViewHolder> implements View.OnClickListener {
    //constructor args
    int nrImages;


    //other variables
    CardView crv;
    ImageView imgv;
    String photoId;
    int photoTag;
    int pos;
    int points=0;

    //view holder variables
    ImageView imageView_holder;
    CardView cardView_holder;
    boolean isClicked = false;
    List<ImageResponse> imageResponse;
    Context context;
    Listener listener;

    @NonNull
    @Override
    public SinglePlayerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        view = layoutInflater.inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    public SinglePlayerAdapter(List<ImageResponse> imageResponse, Context context, int nrImages, Listener listener) {
        this.imageResponse = imageResponse;
        this.context = context;
        this.nrImages = nrImages;

        this.listener=listener;
    }
    @Override
    public void onBindViewHolder(@NonNull SinglePlayerAdapter.MyViewHolder holder, int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardView.animate().withLayer().rotationY(90).setDuration(50).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        if (imageResponse.get(position).getImgUrl() != null) {
                            GlideApp.with(context).load(imageResponse.get(position).getImgUrl()).into(holder.imageView);
                        } else {
                            holder.imageView.setImageResource(imageResponse.get(position).getSource());
                        }
                        holder.cardView.animate().withLayer().rotationY(0).setDuration(50).start();
                    }
                });
                if (!isClicked) {
                    isClicked = true;
                    photoId = imageResponse.get(position).getImgId();
                    photoTag = imageResponse.get(position).getTag();
                    imgv = holder.imageView;
                    crv = holder.cardView;
                    pos = position;
                } else {
                    if (photoId != imageResponse.get(position).getImgId() && photoTag == imageResponse.get(position).getTag()) {
                       new CountDownTimer(500,500){
                           @Override
                           public void onTick(long millisUntilFinished) {
                           }
                           @Override
                           public void onFinish() {
                               v.setClickable(false);
                               crv.setClickable(false);
                               v.setVisibility(View.INVISIBLE);
                               crv.setVisibility(View.INVISIBLE);
                               isClicked = false;
                               points++;
                               listener.itemClicked(points);
                           }
                       }.start();
                    }
                    else if (photoId == imageResponse.get(position).getImgId()) {
                        new CountDownTimer(500,500){
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
                    }
                    else {
                        new CountDownTimer(500,500){
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
   public interface Listener{
     void itemClicked(int points);
    }
}
