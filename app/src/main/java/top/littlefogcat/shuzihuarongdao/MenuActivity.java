package top.littlefogcat.shuzihuarongdao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import top.littlefogcat.shuzihuarongdao.highscore.HighScoreActivity;
import top.littlefogcat.shuzihuarongdao.main.GameActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView mTvStartGame;
    private TextView mTvHighScore;
    private TextView mTvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mTvStartGame = findViewById(R.id.tvStartGame);
        mTvHighScore = findViewById(R.id.tvHighScore);
        mTvExit = findViewById(R.id.tvExit);

        mTvStartGame.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, GameActivity.class);
            startActivity(intent);
        });

        mTvHighScore.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, HighScoreActivity.class);
            startActivity(intent);
        });

        mTvExit.setOnClickListener(v -> finish());
    }
}
