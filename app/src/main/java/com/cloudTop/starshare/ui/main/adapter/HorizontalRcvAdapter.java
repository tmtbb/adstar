package com.cloudTop.starshare.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cloudTop.starshare.R;
import com.cloudTop.starshare.utils.ImageLoaderUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/8.
 */

public class HorizontalRcvAdapter extends RecyclerView.Adapter<HorizontalRcvAdapter.RelatedGoodsViewHolder> {
    private List<String> mMovieItems;
    private Context mContext;
    public HorizontalRcvAdapter(Context context, List<String> movieItems) {
        mMovieItems = movieItems;
        mContext = context;
    }

    @Override
    public RelatedGoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_horizontal_rcv, null, false);
        return new RelatedGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RelatedGoodsViewHolder holder, final int position) {
        String pic_url = mMovieItems.get(position);
        ImageLoaderUtils.displayWithDefaultImg(mContext, holder.imageView, pic_url,R.drawable.rec_bg);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageClick!=null){
                    imageClick.onClik(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovieItems.size();
    }

    class RelatedGoodsViewHolder extends RecyclerView.ViewHolder {


        protected ImageView imageView;

        public RelatedGoodsViewHolder(View view) {
            super(view);
            imageView = (ImageView)view.findViewById(R.id.image);
        }

    }
    public interface OnImageClick{
        void onClik(int position) ;
    }
    private OnImageClick imageClick ;
    public void setOnImageClick(OnImageClick onImageClick){
        imageClick = onImageClick ;
    }

}
