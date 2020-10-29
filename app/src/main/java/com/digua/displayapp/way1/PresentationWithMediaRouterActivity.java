package com.digua.displayapp.way1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Presentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.MediaRouter;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.digua.displayapp.MainActivity;
import com.digua.displayapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * MediaRouter 方式双屏异显
 */
public class PresentationWithMediaRouterActivity extends AppCompatActivity {

    private final String TAG = "PresentationActivity";

    private MediaRouter mMediaRouter;
    private MediaRouterPresentation mPresentation;
    private GLSurfaceView mSurfaceView;

    private RecyclerView mRecycleMainTradeInfoList;

    private LinearLayoutManager mLinearLayoutManager;

    private TradeGoodsInfoAdapter mTradeGoodsInfoAdapter;

    private List<String> mTradeGoodsList = new ArrayList<>();

    private TextView mInfoTextView;
    private boolean mPaused;

    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call {@link android.app.Activity#setContentView setContentView()} to
     * describe what is to be displayed in the screen.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Be sure to call the super class.
        super.onCreate(savedInstanceState);

        // Get the media router service.
        mMediaRouter = (MediaRouter)getSystemService(Context.MEDIA_ROUTER_SERVICE);

        // See assets/res/any/layout/presentation_with_media_router_activity.xml for this
        // view layout definition, which is being set here as
        // the content of our screen.
        setContentView(R.layout.presentation_with_media_router_activity);

        mRecycleMainTradeInfoList = findViewById(R.id.recycle_mainTradeInfoList);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecycleMainTradeInfoList.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mTradeGoodsList.add("卫龙大辣条");
        mTradeGoodsList.add("立白洗衣液3L");
        mTradeGoodsList.add("富士苹果3号");
        mTradeGoodsList.add("金龙鱼食用油2.5L");
        mTradeGoodsList.add("手切面条");
        mTradeGoodsList.add("番茄");
        mTradeGoodsList.add("山东大葱");
        mTradeGoodsInfoAdapter = new TradeGoodsInfoAdapter(this,mTradeGoodsList);
        mRecycleMainTradeInfoList.setAdapter(mTradeGoodsInfoAdapter);


        // Set up the surface view for visual interest.
        mSurfaceView = findViewById(R.id.surface_view);
        mSurfaceView.setRenderer(new CubeRenderer(false));

        // Get a text view where we will show information about what's happening.
        mInfoTextView = findViewById(R.id.info);
    }

    @Override
    protected void onResume() {
        // Be sure to call the super class.
        super.onResume();

        // Listen for changes to media routes.
        mMediaRouter.addCallback(MediaRouter.ROUTE_TYPE_LIVE_VIDEO, mMediaRouterCallback);

        // Update the presentation based on the currently selected route.
        mPaused = false;
        updatePresentation();
    }

    @Override
    protected void onPause() {
        // Be sure to call the super class.
        super.onPause();

        // Stop listening for changes to media routes.
        mMediaRouter.removeCallback(mMediaRouterCallback);

        // Pause rendering.
        mPaused = true;
        updateContents();
    }

    @Override
    protected void onStop() {
        // Be sure to call the super class.
        super.onStop();
        // Dismiss the presentation when the activity is not visible.
        if (mPresentation != null) {
            Log.i(TAG, "Dismissing presentation because the activity is no longer visible.");
            mPresentation.dismiss();
            mPresentation = null;
        }
    }

    private void updatePresentation() {
        // Get the current route and its presentation display.
        MediaRouter.RouteInfo route = mMediaRouter.getSelectedRoute(
                MediaRouter.ROUTE_TYPE_LIVE_VIDEO);
        Display presentationDisplay = route != null ? route.getPresentationDisplay() : null;

        // Dismiss the current presentation if the display has changed.
        if (mPresentation != null && mPresentation.getDisplay() != presentationDisplay) {
            Log.i(TAG, "Dismissing presentation because the current route no longer "
                    + "has a presentation display.");
            mPresentation.dismiss();
            mPresentation = null;
        }

        // Show a new presentation if needed.
        if (mPresentation == null && presentationDisplay != null) {
            Log.i(TAG, "Showing presentation on display: " + presentationDisplay);
            mPresentation = new MediaRouterPresentation(this, presentationDisplay);
            mPresentation.setOnDismissListener(mOnDismissListener);
            try {
                mPresentation.show();
            } catch (WindowManager.InvalidDisplayException ex) {
                Log.w(TAG, "Couldn't show presentation!  Display was removed in "
                        + "the meantime.", ex);
                mPresentation = null;
            }
        }

        // Update the contents playing in this activity.
        updateContents();
    }

    private void updateContents() {
        // Show either the content in the main activity or the content in the presentation
        // along with some descriptive text about what is happening.
        if (mPresentation != null) {
            mInfoTextView.setText(getResources().getString(
                    R.string.presentation_with_media_router_now_playing_remotely,
                    mPresentation.getDisplay().getName()));
            mSurfaceView.setVisibility(View.INVISIBLE);
            mSurfaceView.onPause();
            if (mPaused) {// TODO: [RunningDigua create at 2020/10/29] //获取SurfaceView 修改 Presentation中的View状态
                mPresentation.getSurfaceView().onPause();
            } else {
                mPresentation.getSurfaceView().onResume();
            }
        } else {
            mInfoTextView.setText(getResources().getString(
                    R.string.presentation_with_media_router_now_playing_locally,
                    getWindowManager().getDefaultDisplay().getName()));
            mSurfaceView.setVisibility(View.VISIBLE);
            if (mPaused) {
                mSurfaceView.onPause();
            } else {
                mSurfaceView.onResume();
            }
        }
    }

    private final MediaRouter.SimpleCallback mMediaRouterCallback =
            new MediaRouter.SimpleCallback() {
                @Override
                public void onRouteSelected(MediaRouter router, int type, MediaRouter.RouteInfo info) {
                    Log.d(TAG, "onRouteSelected: type=" + type + ", info=" + info);
                    updatePresentation();
                }

                @Override
                public void onRouteUnselected(MediaRouter router, int type, MediaRouter.RouteInfo info) {
                    Log.d(TAG, "onRouteUnselected: type=" + type + ", info=" + info);
                    updatePresentation();
                }

                @Override
                public void onRoutePresentationDisplayChanged(MediaRouter router, MediaRouter.RouteInfo info) {
                    Log.d(TAG, "onRoutePresentationDisplayChanged: info=" + info);
                    updatePresentation();
                }
            };

    /**
     * Listens for when presentations are dismissed.
     */
    private final DialogInterface.OnDismissListener mOnDismissListener =
            new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (dialog == mPresentation) {
                        Log.i(TAG, "Presentation was dismissed.");
                        mPresentation = null;
                        updateContents();
                    }
                }
            };


}
