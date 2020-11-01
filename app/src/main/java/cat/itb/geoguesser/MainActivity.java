package cat.itb.geoguesser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AlertDialog.Builder dialog;
    private final int ARRAY_SIZE = 30;
    private int hints = 3;
    private ImageView image;
    private TextView tv_result, tv_score, tv_counter;
    private ProgressBar pb;
    private Button b_1, b_2, b_3, b_4, b_hint;
    private final FlagArray flagArray = new FlagArray();
    private final FlagModel[] flags = flagArray.createFlagArray(ARRAY_SIZE);
    private Button[] buttons;
    private double score = 0;
    private boolean usedHint = false;
    private MyCountDownTimer myCountDownTimer;
    private int timerProgress = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Which Flag Is It?");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        dialog = new AlertDialog.Builder(this);
        tv_result = findViewById(R.id.tv_result);
        tv_score = findViewById(R.id.tv_score);
        tv_counter = findViewById(R.id.tv_counter);
        pb = findViewById(R.id.pb);
        b_hint = findViewById(R.id.b_hint);
        b_hint.setText("HINT\n" + hints);
        image = findViewById(R.id.flag);

        b_1 = findViewById(R.id.b_1);
        b_1.setOnClickListener(this);
        b_2 = findViewById(R.id.b_2);
        b_2.setOnClickListener(this);
        b_3 = findViewById(R.id.b_3);
        b_3.setOnClickListener(this);
        b_4 = findViewById(R.id.b_4);
        b_4.setOnClickListener(this);
        b_hint.setOnClickListener(this);

        buttons = new Button[]{b_1, b_2, b_3, b_4};

        if (savedInstanceState!=null){
            flagArray.setPosition(savedInstanceState.getInt("arrayPosition"));
            score = savedInstanceState.getDouble("score");
        }

        update();
    }

    @Override
    public void onClick(View v) {

        Button b = (Button) v;
        if (b == b_hint) {
            if (hints != 0) {
                hints -= 1;
                b_hint.setText("HINT\n" + hints);
                usedHint = true;

                for (Button button : buttons) {
                    if (button.getText().equals(getCurrentName(1))) {
                        b_hint.setEnabled(false);
                        check(button);
                        tv_result.setText(getCurrentName(1).toUpperCase());
                    }
                }

                //Stop the timer on hint used
                myCountDownTimer.cancel();
            }
        } else {
            check(b);
        }
    }

    public void check(Button b) {
        b_hint.setEnabled(false);

        for (Button button : buttons) {
            button.setVisibility(View.INVISIBLE);
            button.setEnabled(false);
        }

        if (b.getText() == getCurrentName(1)) {
            b.setVisibility(View.VISIBLE);
            tv_result.setText("CORRECT");
            tv_result.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (!usedHint) {
                score++;
            } else {
                usedHint = false;
            }
        } else {
            for (Button button : buttons) {
                if (button.getText().equals(getCurrentName(1))) {
                    button.setVisibility(View.VISIBLE);
                }

            }
            tv_result.setText("WRONG!");
            tv_result.setTextColor(getResources().getColor(R.color.red));
            score -= 0.5;
        }

        myCountDownTimer.cancel();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                update();
            }
        }, 1800);
    }

    public void update() {
        if (flagArray.getPosition() != ARRAY_SIZE) {
            if (hints != 0) {
                b_hint.setEnabled(true);
            }
            //Restart countdown
            pb.setProgress(timerProgress);
            myCountDownTimer = new MyCountDownTimer(5000, 100);
            myCountDownTimer.start();

            //Restart buttons
            for (Button button : buttons) {
                button.setText("");
                button.setVisibility(View.VISIBLE);
                button.setEnabled(true);
                button.setTextColor(getResources().getColor(R.color.black));
            }

            //Using list to check for repeated
            List<String> added = new ArrayList<>();
            int r = FlagArray.random(0, 3);
            String ok = getCurrentName(0);
            buttons[r].setText(ok);
            added.add(ok);
            for (Button button : buttons) {
                boolean repeat = true;
                String rand = "";
                while (repeat) {
                    rand = flagArray.randomName();
                    if (!added.contains(rand)) {
                        repeat = false;
                        added.add(rand);
                    }
                }
                if (!button.getText().equals(ok)) {
                    button.setText(rand);
                }
            }

            image.setImageResource(flags[flagArray.getPosition()].getImage());
            flagArray.nextFlag();
            String counter = flagArray.getPosition() + "/" + ARRAY_SIZE;
            tv_counter.setText(counter);
            String strcore = "SCORE\n" + score;
            tv_score.setText(strcore);
        } else {
            dialog();
        }
    }

    public String getCurrentName(int back) {
        return flags[flagArray.getPosition() - back].getName();
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            int progress = (int) (millisUntilFinished / 50);
            pb.setProgress(progress);
        }

        @Override
        public void onFinish() {

            pb.setProgress(0);
            check(b_hint);
            tv_result.setText("TIME OUT!");

        }

    }

    public void dialog() {
        dialog.setTitle("Scored " + score + " out of " + ARRAY_SIZE + "!");
        dialog.setMessage("Do you want to try again?");
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = getIntent();
                finish();
                startActivity(intent);

            }
        });
        dialog.show();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("score",score);
        outState.putInt("arrayPosition",flagArray.getPosition());
        //outState.clone();

    }


}
