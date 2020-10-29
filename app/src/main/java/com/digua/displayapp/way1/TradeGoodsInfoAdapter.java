package com.digua.displayapp.way1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.digua.displayapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 交易商品适配
 *
 * @author RunningDigua
 * @date 2020/10/29
 */
public class TradeGoodsInfoAdapter extends RecyclerView.Adapter<TradeGoodsInfoAdapter.TradeGoodsInfoViewHolder> {

    private Context mContext;

    private List<String> mList;

    public TradeGoodsInfoAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public TradeGoodsInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycle_goods_item_layout, parent, false);
        return new TradeGoodsInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TradeGoodsInfoViewHolder holder, int position) {
        holder.mTvGoodsName.setText(mList.get(position));
        holder.mTvSerialNumber.setText(""+position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class TradeGoodsInfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_goodsName)
        TextView mTvGoodsName;
        @BindView(R.id.tv_serialNumber)
        TextView mTvSerialNumber;
        public TradeGoodsInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
