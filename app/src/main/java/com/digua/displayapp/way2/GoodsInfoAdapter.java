package com.digua.displayapp.way2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.digua.displayapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 商品适配
 *
 * @author RunningDigua
 * @date 2020/10/28
 */
public class GoodsInfoAdapter extends RecyclerView.Adapter<GoodsInfoAdapter.GoodsItemViewHolder>{

    private Context mContext;

    public GoodsInfoAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public GoodsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_goods_item_layout,parent,false);

        return new GoodsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 28;
    }

    protected class GoodsItemViewHolder extends RecyclerView.ViewHolder{

        public GoodsItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
