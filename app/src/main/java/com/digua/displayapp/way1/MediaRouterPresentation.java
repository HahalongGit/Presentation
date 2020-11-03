package com.digua.displayapp.way1;

import android.app.Presentation;
import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.digua.displayapp.R;

import java.util.LinkedList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * MediaRouter方式Presentation
 *
 * @author RunningDigua
 * @date 2020/10/29
 */
public class MediaRouterPresentation extends Presentation {

    private GLSurfaceView mSurfaceView;

    private RecyclerView mRecycleGoodsInfoList;

    private RelativeLayout mRlGoodsItemLayout;

    private LinearLayoutManager mLinearLayoutManager;

    private TradeGoodsInfoAdapter mTradeGoodsInfoAdapter;

    private List<String> mTradeGoodsList = new LinkedList<>();

    /**
     * 插入新数据
     *
     * @param data
     */
    public void insertNewData(String data) {
        mTradeGoodsList.add(0, data);
        mTradeGoodsInfoAdapter.notifyItemInserted(0);
        mRecycleGoodsInfoList.scrollToPosition(0);
        setGoodsInfoVisible(View.VISIBLE);
    }

    /**
     * 设置商品显示可视
     *
     * @param visible
     */
    public void setGoodsInfoVisible(int visible) {
        mRlGoodsItemLayout.setVisibility(visible);
    }

    public MediaRouterPresentation(Context context, Display display) {
        super(context, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // Get the resources for the context of the presentation.
        // Notice that we are getting the resources from the context of the presentation.
        Resources r = getContext().getResources();
        // Inflate the layout.
        setContentView(R.layout.presentation_with_media_router_content);
        // Set up the surface view for visual interest.
        mSurfaceView = findViewById(R.id.surface_view);
        mRlGoodsItemLayout = findViewById(R.id.rl_goodsItemLayout);
        mSurfaceView.setRenderer(new CubeRenderer(false));

        mRecycleGoodsInfoList = findViewById(R.id.recycle_tradeInfoList);
//        mTradeGoodsList.add("卫龙大辣条");
//        mTradeGoodsList.add("立白洗衣液3L");
//        mTradeGoodsList.add("富士苹果3号");
//        mTradeGoodsList.add("金龙鱼食用油2.5L");
//        mTradeGoodsList.add("手切面条");
//        mTradeGoodsList.add("番茄");
//        mTradeGoodsList.add("山东大葱");

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mTradeGoodsInfoAdapter = new TradeGoodsInfoAdapter(getContext(), mTradeGoodsList);
        mRecycleGoodsInfoList.setLayoutManager(mLinearLayoutManager);
        mRecycleGoodsInfoList.setAdapter(mTradeGoodsInfoAdapter);

    }

    public GLSurfaceView getSurfaceView() {
        return mSurfaceView;
    }
}
