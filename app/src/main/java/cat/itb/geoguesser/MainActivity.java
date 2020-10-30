package cat.itb.geoguesser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final int ARRAY_SIZE = 10;
    private ImageView image;
    private Button b_1, b_2, b_3, b_4;
    private final FlagArray flagArray = new FlagArray();
    private final FlagModel[] flags=flagArray.createFlagArray(ARRAY_SIZE);
    private Button[] buttons;
    boolean[] options;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setTitle("Guess The Flag!!");
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        b_1 = findViewById(R.id.b_1);
        b_1.setOnClickListener(this);
        b_2 = findViewById(R.id.b_2);
        b_2.setOnClickListener(this);
        b_3 = findViewById(R.id.b_3);
        b_3.setOnClickListener(this);
        b_4 = findViewById(R.id.b_4);
        b_4.setOnClickListener(this);
        image = findViewById(R.id.flag);
        buttons = new Button[]{b_1,b_2,b_3,b_4};
        update();



    }


    @Override
    public void onClick(View v) {
        Button b =(Button) v;
        check(b);
    }

    public void check(Button b){
        if (b.getText() == flags[flagArray.getPosition()-1].getName()) {
            Toast.makeText(this, b.getText(), Toast.LENGTH_SHORT).show();


        }

        update();
    }

    public void update(){


        if (flagArray.getPosition()!=ARRAY_SIZE) {
            int r=FlagArray.random(0,3);
            options=new boolean[]{false,false,false,false};
            options[r]=true;
            for (int i = 0; i<buttons.length; i++) {
                if (options[i]){
                    buttons[i].setText(flags[flagArray.getPosition()].getName());

                }else {
                    buttons[i].setText(flagArray.randomName());
                }
            }
            setImage();
            flagArray.nextFlag();
        }
    }

    public void setImage(){
        image.setImageResource(flags[flagArray.getPosition()].getImage());
    }
}