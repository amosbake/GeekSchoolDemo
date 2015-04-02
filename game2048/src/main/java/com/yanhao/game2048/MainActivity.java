package com.yanhao.game2048;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private TextView tvScore;
    private Button startGame;
    private GameView gameView;
    private int score=0;

    private static MainActivity mainActivity= null;


    public MainActivity(){
        mainActivity=this;
    }

    public static MainActivity getMainActivity() {
        return mainActivity;
    }

    public void clearScore(){
        score=0;
    }
    public void showScore(){
        tvScore.setText(String.valueOf(score));
    }
    public void addScore(int s){
        score+=s;
        tvScore.setText(String.valueOf(score));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvScore= (TextView) findViewById(R.id.tv_score);
        startGame= (Button) findViewById(R.id.startGame);
        gameView= (GameView) findViewById(R.id.gameView);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameView.startGame();
            }
        });
    }


}
