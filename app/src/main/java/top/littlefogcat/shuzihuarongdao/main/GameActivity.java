package top.littlefogcat.shuzihuarongdao.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import top.littlefogcat.shuzihuarongdao.highscore.HighScore;
import top.littlefogcat.shuzihuarongdao.R;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";

    private TextView mMaskView;
    private BoardView mBoardView;
    private TextView mCheat;
    private int mCheatCount = 0;
    private long mLastCheatTime;
    private Handler mHandler = new Handler();
    //    private Runnable downTask = new Runnable() {
//        @SuppressLint("SetTextI18n")
//        @Override
//        public void run() {
//            Log.d(getClass().getName(), "run: " + mDownNumber);
//            if (mDownNumber <= 4 && mDownNumber > 0) {
//                mMaskView.setText(String.valueOf(mDownNumber));
//                mHandler.postDelayed(this, 1000);
//            } else if (mDownNumber == 0) {
//                mMaskView.setText("GO!");
//                mHandler.postDelayed(this, 1000);
//            } else {
//                startGame();
//            }
//            mDownNumber--;
//        }
//    };
    private int mDownNumber = 4;
    private long mStartTime;
    private long mEndTime;
    /**
     * 高分排行榜
     */
    private List<HighScore> mHighScoreList;

    private SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mMaskView = findViewById(R.id.mask);
        mBoardView = findViewById(R.id.board);
        mCheat = findViewById(R.id.tvCheat);
        mCheat.setOnClickListener(v -> {
            if (mLastCheatTime == 0 || mLastCheatTime - System.currentTimeMillis() > 1000) {
                mLastCheatTime = System.currentTimeMillis();
                mCheatCount = 0;
            } else if (mCheatCount < 10) {
                mCheatCount++;
            } else {
                saveHighScore(30000, 999);
            }
        });

//        mHandler.post(downTask);

        mBoardView.setOnFinishedListener((step) -> {
            mEndTime = System.currentTimeMillis();
            long useTime = mEndTime - mStartTime;
            saveHighScore(useTime, step);
        });

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        startGenerateBoard();
        loadHighScore();
    }

    private void startGenerateBoard() {
        int sizeX = mBoardView.getSizeX();
        int sizeY = mBoardView.getSizeY();
        BoardGenerator generator = new BoardGenerator(sizeX, sizeY);
        generator.setOnGeneratedListener(board -> mHandler.post(() -> {
            List<Integer> data = new ArrayList<>();
            for (int[] line : board) {
                for (int aInt : line) {
                    data.add(aInt);
                }
            }
            mBoardView.setData(data);
            startGame();
        }));
        new Thread(generator::generate).start();
    }

    private void loadHighScore() {
        String highScoreJson = mPref.getString(getString(R.string.pref_key_high_score), null);
        Log.d(TAG, "loadHighScore: " + highScoreJson);
        if (highScoreJson != null) {
            Gson gson = new Gson();
            mHighScoreList = gson.fromJson(highScoreJson, new TypeToken<List<HighScore>>() {
            }.getType());
        } else {
            mHighScoreList = new ArrayList<>();
        }
        Log.d(TAG, "loadHighScore: " + mHighScoreList);
    }


    private void saveHighScore(long useTime, int step) {
        if (mHighScoreList == null) {
            return;
        }
        int highScoreCount = mHighScoreList.size();
        int scoreRank = -1;
        if (mHighScoreList.size() == 0) {
            scoreRank = 0;
        } else {
            for (int i = 0; i < highScoreCount; i++) {
                HighScore score = mHighScoreList.get(i);
                if (useTime < score.useTime) {
                    scoreRank = i;
                    break;
                }
            }
        }
        if (scoreRank == -1) {
            new AlertDialog.Builder(this)
                    .setTitle("恭喜完成")
                    .show();
            return;
        }

        HighScore newScore = new HighScore();
        newScore.useTime = useTime;
        newScore.time = mEndTime;
        newScore.useStep = step;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("恭喜打破记录！");
        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_high_score, null, false);
        EditText et = contentView.findViewById(R.id.etName);
        TextView tv = contentView.findViewById(R.id.tvUseTime);
        String msg = "用时" + useTime / 1000 + "秒，排名第" + (scoreRank + 1);
        tv.setText(msg);
        dialog.setView(contentView);
        int rank = scoreRank;
        dialog.setPositiveButton("确定", (d, which) -> {
            String name = et.getText().toString();
            if (name.length() == 0) {
                name = "匿名";
            }
            newScore.name = name;
            mHighScoreList.add(rank, newScore);
            while (mHighScoreList.size() > 10) {
                mHighScoreList.remove(10);
            }
            Gson gson = new Gson();
            String json = gson.toJson(mHighScoreList);
            mPref.edit().putString(getString(R.string.pref_key_high_score), json).apply();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void startGame() {
        mMaskView.setVisibility(View.GONE);
        mStartTime = System.currentTimeMillis();
    }
}
