package cat.itb.geoguesser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OptionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button b_easy, b_medium, b_hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        getSupportActionBar().setTitle("Choose Difficulty!");
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        b_easy=findViewById(R.id.b_easy);
        b_easy.setOnClickListener(this);
        b_medium=findViewById(R.id.b_medium);
        b_medium.setOnClickListener(this);
        b_hard=findViewById(R.id.b_hard);
        b_hard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        if (b==b_easy){
            next(5,15);
        }else if (b==b_medium){
            next(3, 25);
        }else{
            next(2,40);
        }
    }

    public void next(int hints, int flags){
        Intent intent = new Intent(OptionActivity.this, MainActivity.class);
        intent.putExtra("hints", hints);
        intent.putExtra("flags", flags);
        startActivity(intent);
    }
}