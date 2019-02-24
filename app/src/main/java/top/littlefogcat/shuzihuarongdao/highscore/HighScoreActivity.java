package top.littlefogcat.shuzihuarongdao.highscore;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import top.littlefogcat.shuzihuarongdao.R;

public class HighScoreActivity extends AppCompatActivity {

    private RecyclerView mHighScoreRecView;
    private HighScoreAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        mAdapter = new HighScoreAdapter();

        mHighScoreRecView = findViewById(R.id.rv);
        mHighScoreRecView.setLayoutManager(new LinearLayoutManager(this));
        mHighScoreRecView.setAdapter(mAdapter);

        loadData();
    }

    private static final String TAG = "HighScoreActivity";

    private void loadData() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String highScoreListJson = pref.getString(getString(R.string.pref_key_high_score), "");
        Log.d(TAG, "loadData: " + highScoreListJson);
        if (highScoreListJson.length() == 0) {
            return;
        }
        Gson gson = new Gson();
        List<HighScore> highScoreList = gson.fromJson(highScoreListJson, new TypeToken<List<HighScore>>() {
        }.getType());
        mAdapter.setData(highScoreList);
    }
}
