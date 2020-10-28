package com.digua.displayapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.digua.displayapp.way1.PresentationWithMediaRouterActivity;
import com.digua.displayapp.way2.PresentationDisplayManagerActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_mediaRouter)
    Button mBtnMediaRouter;
    @BindView(R.id.btn_displayManager)
    Button mBtnDisplayManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_mediaRouter, R.id.btn_displayManager})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_mediaRouter:
                startActivity(new Intent(this, PresentationWithMediaRouterActivity.class));
                break;
            case R.id.btn_displayManager:
                startActivity(new Intent(this, PresentationDisplayManagerActivity.class));
                break;
        }
    }
}
