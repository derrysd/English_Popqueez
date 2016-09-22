package derry.englishpopqueez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class LevelSelectActivity extends AppCompatActivity {
    ArrayList<Level> levels = new ArrayList<>();
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindowManager();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levelselect);

        mp =MediaPlayer.create(this, R.raw.button);

        SharedPreferences prefs = this.getSharedPreferences(SplashActivity.TAG_SHAREDPREFS, Context.MODE_PRIVATE);
        String savedString = prefs.getString(SplashActivity.TAG_SAVEDSTRING, "");
        if (!savedString.equals("")) {
            //todo retrieve json from sharedpref
            Gson gson = new Gson();
            levels = gson.fromJson(savedString, new TypeToken<ArrayList<Level>>() {
            }.getType());
        }
        InitViews();
    }

    private void InitViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ItemAdapter itemAdapter = new ItemAdapter(getApplicationContext(), levels);
        recyclerView.setAdapter(itemAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                mp.start();
                new MaterialDialog.Builder(this)
                        .title("Reset")
                        .titleGravity(GravityEnum.CENTER)
                        .content("Do you want to reset progress?")
                        .positiveText("Yes")
                        .negativeText("No")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                mp.start();
                                dialog.dismiss();
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SharedPreferences prefs = dialog.getContext().getSharedPreferences(SplashActivity.TAG_SHAREDPREFS, Context.MODE_PRIVATE);
                                GsonBuilder builder = new GsonBuilder();
                                Gson gson = builder.create();
                                String toSave = gson.toJson(InitData());
                                //todo save json to sharedpref
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString(SplashActivity.TAG_SAVEDSTRING, toSave).apply();
                                Intent intent = new Intent(dialog.getContext(), LevelSelectActivity.class);
                                startActivity(intent);
                                mp.start();
                                finish();
                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Level> InitData() {
        ArrayList<Level> levels = new ArrayList<>();
        String[][] possibleAnswers = {
                {"parot","parrots","paroot","paaroot","paaroot","parott","paaroot"},
                {"dee","deers","deeer","dere","derr","der"},
                {"octopu","octopusses","octop","otopus","octopuss","octopuus"},
                {"lio", "loin", "lions","lioon","lionn", "lin"},
                {"lady bug","lady big","lady bag","lady bog","lady beg","ladybig","ladybag","ladybog","ladybeg","lady bu","ledy bug","ledybug","ladyybug"},
                {"pyramd","pyramid","piramid","piramida","pyramiid","pyramyda","pyramyd","piramyd","piaramid","piaramyd"},
                {"oul","owls","ool","owul","owll","owwl","oll","uwl","oowl"},
                {"sake","snakes","snek","snak","snakk","senake","sneik","snke","snak"},
                {"buterfly","butterflies","butterply","buttefly","butterflay","buttervly","batterfly","batterflay","butter fly"},
                {"aant","atns","anst","antss","ent","ents","ant"},
                {"ieagle","igle","egle","eage","eaglle","eagll","eagles"},
                {"boars","baor","boarr","boaa","booar","boaar","boa"},
        };

        String[] correctAnswers = {"parrot", "deer", "octopus", "lion", "ladybug", "pyramid", "owl", "snake", "butterfly", "ants", "eagle", "boar"};

        for(int i = 0; i <= 11; i++){
            Level level = new Level();
            level.setLevelName("Level\n" + (i+1));
            level.setPossibleAnswers(possibleAnswers[i]);
            level.setCorrectAnswer(correctAnswers[i]);
            levels.add(level);
        }
        return levels;
    }

    @Override
    public void onBackPressed() {
        mp.start();
        new MaterialDialog.Builder(this)
                .title("Exit")
                .titleGravity(GravityEnum.CENTER)
                .content("Do you want to exit app?")
                .positiveText("Yes")
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        mp.start();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mp.start();
                        finishAffinity();
                    }
                })
                .show();

    }
}
