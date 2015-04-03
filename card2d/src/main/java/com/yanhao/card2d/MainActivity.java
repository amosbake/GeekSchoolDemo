package com.yanhao.card2d;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {
    public static final int ANIMATION_DURATION = 500;
    private FrameLayout root;
    private ImageView imageA;
    private ImageView imageB;
    private ScaleAnimation sato0=new ScaleAnimation(1,0,1,1, Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
    private ScaleAnimation sato1=new ScaleAnimation(0,1,1,1, Animation.RELATIVE_TO_PARENT,0.5f,Animation.RELATIVE_TO_PARENT,0.5f);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageA.getVisibility()==View.VISIBLE){
                    imageA.startAnimation(sato0);
                }else{
                    imageB.startAnimation(sato0);
                }

            }
        });

    }
    private void showImageA(){
        imageA.setVisibility(View.VISIBLE);
        imageB.setVisibility(View.GONE);
    }
    private void showImageB(){
        imageB.setVisibility(View.VISIBLE);
        imageA.setVisibility(View.GONE);
    }
    private void initView(){
        imageA= (ImageView) findViewById(R.id.iv_A);
        imageB= (ImageView) findViewById(R.id.iv_B);
        root= (FrameLayout) findViewById(R.id.root);
        showImageA();
        sato0.setDuration(ANIMATION_DURATION);
        sato1.setDuration(ANIMATION_DURATION);
        sato0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (imageA.getVisibility()==View.VISIBLE){
                    imageA.setAnimation(null);
                   showImageB();
                    imageB.startAnimation(sato1);
                }else{
                    imageB.setAnimation(null);
                    showImageA();
                    imageA.startAnimation(sato1);
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


}
