package derry.englishpopqueez;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alimuzaffar.lib.widgets.AnimatedEditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    ImageView iconQuestion;
    Button answerButton;
    AnimatedEditText answerBox;
    int score = 3;
    ArrayList<Level> levels = new ArrayList<>();
    int position;
    int[] thumbnails = { R.drawable.parrot, R.drawable.deer , R.drawable.octopus, R.drawable.lion, R.drawable.ladybug, R.drawable.pyramid,
            R.drawable.owl, R.drawable.snake, R.drawable.butterfly, R.drawable.ants, R.drawable.eagle, R.drawable.boar};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindowManager();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        final SharedPreferences prefs = this.getSharedPreferences(SplashActivity.TAG_SHAREDPREFS, Context.MODE_PRIVATE);
        String savedString = prefs.getString(SplashActivity.TAG_SAVEDSTRING, "");
        if (!savedString.equals("")) {
            //todo retrieve json from sharedpref
            Gson gson = new Gson();
            levels = gson.fromJson(savedString, new TypeToken<ArrayList<Level>>() {
            }.getType());
        }

        iconQuestion = (ImageView) findViewById(R.id.iconQuestion);
        answerBox = (AnimatedEditText) findViewById(R.id.answerBox);
        answerButton = (Button) findViewById(R.id.answerButton);
        //todo retrieve index from bundle
        //todo get level object from index retrieved in sharedpref and set icon
        position = getIntent().getIntExtra("position", 0);
        iconQuestion.setImageResource(thumbnails[position]);

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.button);
                mp.start();
                String answer = answerBox.getText().toString();
                if(!answer.equals("")){
                    if (cekNilai(answer, levels.get(position).getPossibleAnswers())) {
                        Toast.makeText(view.getContext(), "Almost Correct", Toast.LENGTH_SHORT).show();
                        if(score>1) score--;
                        MediaPlayer mp1 = MediaPlayer.create(view.getContext(), R.raw.almost);
                        mp1.start();
                    } else if (answer.equals(levels.get(position).getCorrectAnswer())) {
                        MediaPlayer mp2 = MediaPlayer.create(view.getContext(), R.raw.correct);
                        mp2.start();
                        final MaterialDialog materialDialog = new MaterialDialog.Builder(view.getContext())
                                .title("You Are Correct!")
                                .positiveText("OK")
                                .buttonsGravity(GravityEnum.CENTER)
                                .titleGravity(GravityEnum.CENTER)
                                .cancelable(false)
                                .customView(R.layout.custom_dialog, false)
                                .show();

                        materialDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                final ImageView star1 = (ImageView) materialDialog.findViewById(R.id.star1);
                                final ImageView star2 = (ImageView) materialDialog.findViewById(R.id.star2);
                                final ImageView star3 = (ImageView) materialDialog.findViewById(R.id.star3);
                                switch (score){
                                    case 1:
                                        star2.setImageResource(R.drawable.ic_star_border);
                                        star3.setImageResource(R.drawable.ic_star_border);
                                        break;
                                    case 2:
                                        star3.setImageResource(R.drawable.ic_star_border);
                                        break;
                                }

                                Animation fadeInAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_fade_in);
                                star1.startAnimation(fadeInAnimation);
                                YoYo.with(Techniques.Pulse)
                                        .duration(1000)
                                        .playOn(star1);
                                fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        levels.get(position).setAnswered(true);
                                        levels.get(position).setStars(score);
                                        GsonBuilder builder = new GsonBuilder();
                                        Gson gson = builder.create();
                                        String toSave = gson.toJson(levels);
                                        //todo save json to sharedpref
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString(SplashActivity.TAG_SAVEDSTRING, toSave).apply();
                                        MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.star1);
                                        mp.start();
                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        star1.setVisibility(View.VISIBLE);
                                        Animation fadeInAnimation2 = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_fade_in);
                                        star2.startAnimation(fadeInAnimation2);
                                        YoYo.with(Techniques.Pulse)
                                                .duration(1000)
                                                .playOn(star2);
                                        fadeInAnimation2.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.star2);
                                                mp.start();
                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                star2.setVisibility(View.VISIBLE);
                                                Animation fadeInAnimation3 = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_fade_in);
                                                star3.startAnimation(fadeInAnimation3);
                                                YoYo.with(Techniques.Pulse)
                                                        .duration(1000)
                                                        .playOn(star3);
                                                fadeInAnimation3.setAnimationListener(new Animation.AnimationListener() {
                                                    @Override
                                                    public void onAnimationStart(Animation animation) {
                                                        MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.star3);
                                                        mp.start();
                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animation animation) {
                                                        star3.setVisibility(View.VISIBLE);
                                                    }

                                                    @Override
                                                    public void onAnimationRepeat(Animation animation) {

                                                    }
                                                });
                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });
                            }
                        });

                        View positive = materialDialog.getActionButton(DialogAction.POSITIVE);
                        positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(view.getContext(), LevelSelectActivity.class);
                                view.getContext().startActivity(intent);
                                Activity a = (Activity) view.getContext();
                                a.finish();
                            }
                        });



                    } else {
                        Toast.makeText(view.getContext(), "Wrong Answer!", Toast.LENGTH_SHORT).show();
                        if(score>1) score--;
                        MediaPlayer mp3 = MediaPlayer.create(view.getContext(), R.raw.wrong);
                        mp3.start();
                    }

                } else {
                    // TODO: error masukkan jawaban
                    Toast.makeText(view.getContext(), "Fill Your Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    boolean cekNilai(String input, String[] possibleAns){
        boolean result = false;
        for (String possibleAn : possibleAns) {
            if (input.equals(possibleAn)) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title("Progress Will Lost")
                .titleGravity(GravityEnum.CENTER)
                .content("Do you want to surender?")
                .positiveText("Yes")
                .negativeText("No")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(dialog.getContext(), LevelSelectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();

    }

}
