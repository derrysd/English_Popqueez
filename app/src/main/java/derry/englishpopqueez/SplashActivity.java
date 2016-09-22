package derry.englishpopqueez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hanks.htextview.HTextView;
import com.nineoldandroids.animation.Animator;

import java.util.ArrayList;


public class SplashActivity extends AppCompatActivity {
    HTextView hTextView;
    ImageView english, popqueez;
    boolean isStarted;
    boolean isClicked;
    public static final String TAG_SAVEDSTRING = "string";
    public static final String TAG_SHAREDPREFS = "derry.englishpopqueez";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindowManager();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        english = (ImageView) findViewById(R.id.title_english);
        popqueez = (ImageView) findViewById(R.id.title_popqueez);
        hTextView = (HTextView) findViewById(R.id.button_mulai);

        hTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isClicked){
                    isClicked = true;
                    YoYo.with(Techniques.Pulse)
                            .duration(400)
                            .interpolate(new AccelerateDecelerateInterpolator())
                            .withListener(listener3)
                            .playOn(hTextView);
                }
            }
        });

        SharedPreferences prefs = this.getSharedPreferences(TAG_SHAREDPREFS, Context.MODE_PRIVATE);
        String savedString = prefs.getString(TAG_SAVEDSTRING, "");
        if (savedString.equals("")) {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            String toSave = gson.toJson(InitData());
            //todo save json to sharedpref
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(TAG_SAVEDSTRING, toSave).apply();
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

    Animator.AnimatorListener listener1 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.boink1);
            mp.start();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            YoYo.with(Techniques.DropOut)
                    .duration(1000)
                    .playOn(popqueez);
            YoYo.with(Techniques.Swing)
                    .delay(400)
                    .duration(1000)
                    .withListener(listener2)
                    .playOn(popqueez);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    Animator.AnimatorListener listener2 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            popqueez.setVisibility(View.VISIBLE);
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.boink2);
            mp.start();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            YoYo.with(Techniques.FadeIn)
                    .duration(1000)
                    .withListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            hTextView.animateText("Start");
                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.unsheath);
                            mp.start();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    })
                    .playOn(hTextView);
            hTextView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    Animator.AnimatorListener listener3 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.button);
            mp.start();
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            Intent i = new Intent(SplashActivity.this, LevelSelectActivity.class);
            startActivity(i);
            finish();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(!isStarted){
            isStarted = true;

            YoYo.with(Techniques.DropOut)
                    .duration(1000)
                    .playOn(english);
            YoYo.with(Techniques.Swing)
                    .delay(400)
                    .duration(1000)
                    .withListener(listener1)
                    .playOn(english);
            english.setVisibility(View.VISIBLE);
        }

    }
}
