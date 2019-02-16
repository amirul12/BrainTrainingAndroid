package com.mandsti.amirul.braintraining;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int locationAnser;
    private Button button0, button1, button2, button3;
    private TextView tv_question;
    private TextView tv_result;
    private TextView tv_question_count;
    private TextView tv_count_timer;
    private Button btnPlayAgein;
    CountDownTimer countDownTimer;
    boolean gameOvercheck = true;

    MediaPlayer mediaPlayerCorrect;
    MediaPlayer mediaPlayerWrong;

    ScoreStorage storage;
    int resultResult = 0;
    int totalQuestion = 0;

    TextView tv_top_score;
    TextView tv_your_score;
    Button btn_clearScore;


    ArrayList<Integer> answer = new ArrayList<Integer>();

    public void pressAnswer(View view) {

        if (gameOvercheck) {


            Button button = (Button) view;
            String correctAns = button.getTag().toString();

            String correctResult = "";

            Log.d("Correct Ans location", Integer.valueOf(locationAnser).toString());
            Log.d("Correct Ans tag", Integer.valueOf(correctAns).toString());

            if (String.valueOf(locationAnser).equals(correctAns)) {

                resultResult++;
                Log.d("Correct Ans tag", "corrent show");
                correctResult = "Correct !";

            } else {

                playTone(mediaPlayerWrong);

                correctResult = "Incorrect";

            }

            totalQuestion++;
            tv_question_count.setText(String.format("%s/%s", resultResult, totalQuestion));
            tv_result.setVisibility(View.VISIBLE);
            tv_result.setText(correctResult);


            // countDownTimer.cancel();
            questionAnserGenerate();
        } else {


        }


    }

    public void playAgain(View view) {
        gameOvercheck = true;
        btn_clearScore.setVisibility(View.INVISIBLE);
        Log.d("value", Integer.toString(storage.getValue()));


        resultResult = 0;
        totalQuestion = 0;
        tv_question_count.setText(String.format("%s/%s", resultResult, totalQuestion));
        questionAnserGenerate();
        tv_result.setVisibility(View.INVISIBLE);
        btnPlayAgein.setVisibility(View.INVISIBLE);
        Log.d("playAgain", "click");
        timerCountDownMethod();
    }

    private void timerCountDownMethod() {

        countDownTimer = new CountDownTimer(10000, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {

                int leftSecont = (int) millisUntilFinished / 1000;
                tv_count_timer.setText(String.format("%s", leftSecont));

            }

            @Override
            public void onFinish() {



                tv_your_score.setVisibility(View.VISIBLE);
                tv_top_score.setVisibility(View.VISIBLE);


                int temScore = resultResult;
                int currentResutl = storage.getValue();
                if (temScore > currentResutl){
                    storage.saveHighScore(temScore);
                    tv_top_score.setText("Top: " + temScore);
                    tv_your_score.setText("Your: " + temScore);
                }else {
                    tv_top_score.setText("Top: " + currentResutl);
                    tv_your_score.setText("Your: " + temScore);
                    storage.saveHighScore(currentResutl);
                }
                //tv_top_score.setText("Top"+resultResult);


                btn_clearScore.setVisibility(View.VISIBLE);
                btn_clearScore.setTranslationX(1500);
                btn_clearScore.animate().translationXBy(-1500f).setDuration(1000);

                btnPlayAgein.setVisibility(View.VISIBLE);
                btnPlayAgein.setTranslationY(1500);
                btnPlayAgein.animate().translationYBy(-1500f).setDuration(1000);
                gameOvercheck = false;


            }


        }.start();
    }

    /**
     * question answer generator
     */


    public void resetScore(View view){
        storage.getValue();
        tv_top_score.setText("0");
        tv_your_score.setText("0");
    }
    private void questionAnserGenerate() {

        //   timerCountDownMethod();

        Random random = new Random();

        int a = random.nextInt(21);
        int b = random.nextInt(21);


        tv_question.setText(Integer.toString(a) + " + " + Integer.valueOf(b));
        answer.clear();

        locationAnser = random.nextInt(4);
        Log.d("wrong", Integer.valueOf(locationAnser).toString());

        for (int i = 0; i < 4; i++) {

            if (i == locationAnser) {
                answer.add(a + b);
            } else {
                int wrong = random.nextInt(41);
                while (wrong == a + b) {
                    wrong = random.nextInt(41);
                }
                answer.add(wrong);

            }

        }


        button0.setText(Integer.toString(answer.get(0)).toString());
        button1.setText(Integer.toString(answer.get(1)).toString());
        button2.setText(Integer.toString(answer.get(2)).toString());
        button3.setText(Integer.toString(answer.get(3)).toString());
    }


    private void playTone(MediaPlayer player) {


        try {
            player.start();
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            mediaPlayerWrong = MediaPlayer.create(this, R.raw.incorrect);
        } catch (Exception e) {

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = ScoreStorage.getInstance(MainActivity.this);
        tv_question = (TextView) findViewById(R.id.tv_question);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_question_count = (TextView) findViewById(R.id.tv_question_count);
        tv_count_timer = (TextView) findViewById(R.id.tv_count_timer);
        btnPlayAgein = (Button) findViewById(R.id.btnPlayAgain);

        mediaPlayerWrong = MediaPlayer.create(this, R.raw.incorrect);
        mediaPlayerCorrect = MediaPlayer.create(this, R.raw.cointable);


        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);

        tv_your_score = (TextView) findViewById(R.id.tv_your_score);
        tv_top_score = (TextView) findViewById(R.id.tv_top_score);
        btn_clearScore = (Button)findViewById(R.id.btn_clearScore);


        questionAnserGenerate();
        timerCountDownMethod();


    }


}
