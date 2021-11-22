package com.mx.ex_imageslider;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mx.ex_kakaolink.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> itemList;

    public ImageSliderAdapter(Context context, ArrayList<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_img_slider, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String itemUrl = itemList.get(position);

        Picasso.get().load(itemUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_slider_item);

            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            switch (v.getId()) {
                case R.id.image_slider_item:
                    Log.e("dddddd", "ddddddddd");
                    break;
            }
        }
    }

}