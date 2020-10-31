package cat.itb.geoguesser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Which Flag Is It?");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        tv_result = findViewById(R.id.tv_result);
        tv_score = findViewById(R.id.tv_score);
        tv_counter = findViewById(R.id.tv_counter);
        pb = findViewById(R.id.pb);
        b_hint = findViewById(R.id.b_hint);
        b_hint.setText("HINT\n" + hints);

        b_1 = findViewById(R.id.b_1);
        b_1.setOnClickListener(this);
        b_2 = findViewById(R.id.b_2);
        b_2.setOnClickListener(this);
        b_3 = findViewById(R.id.b_3);
        b_3.setOnClickListener(this);
        b_4 = findViewById(R.id.b_4);
        b_4.setOnClickListener(this);
        b_hint.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (hints != 0) {
                    hints -= 1;
                    b_hint.setText("HINT\n" + hints);
                    usedHint = true;

                    Toast.makeText(MainActivity.this, "hint", Toast.LENGTH_SHORT).show();
                    for (Button button : buttons) {
                        if (button.getText().equals(getCurrentName(1))) {
                            button.setTextColor(getResources().getColor(R.color.colorPrimary));
                        }
                    }

                    if (hints == 0){
                        b_hint.setEnabled(false);
                    }

                }
            }
        });
        image = findViewById(R.id.flag);
        buttons = new Button[]{b_1, b_2, b_3, b_4};
        update();


    }


    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        check(b);
    }

    public void check(Button b) {
        if (b.getText() == flags[flagArray.getPosition() - 1].getName()) {
            tv_result.setText("CORRECT");
            tv_result.setTextColor(getResources().getColor(R.color.colorPrimary));
            if (!usedHint) {
                score++;
            } else {
                usedHint = false;
            }

        } else {
            tv_result.setText("WRONG!");
            tv_result.setTextColor(getResources().getColor(R.color.red));
            score -= 0.5;
        }

        update();
    }

    public void update() {
        if (flagArray.getPosition() != ARRAY_SIZE) {
            for (Button button : buttons) {
                button.setText("");
                button.setTextColor(getResources().getColor(R.color.black));
            }
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
        }
    }

    public String getCurrentName(int back) {
        return flags[flagArray.getPosition() - back].getName();
    }

}