package cat.itb.geoguesser;

import java.util.ArrayList;
import java.util.List;

public class FlagArray {
    private int position=0;

    public FlagModel[] flags = new FlagModel[]{
            new FlagModel("andorra", R.drawable.andorra),
            new FlagModel("argentina", R.drawable.argentina),
            new FlagModel("belgium", R.drawable.belgium),
            new FlagModel("brazil", R.drawable.brazil),
            new FlagModel("chile", R.drawable.chile),
            new FlagModel("china", R.drawable.china),
            new FlagModel("colombia", R.drawable.colombia),
            new FlagModel("cuba", R.drawable.cuba),
            new FlagModel("czech Republic", R.drawable.czech_republic),
            new FlagModel("denmark", R.drawable.denmark),
            new FlagModel("ecuador", R.drawable.ecuador),
            new FlagModel("egypt", R.drawable.egypt),
            new FlagModel("el Salvador", R.drawable.el_salvador),
            new FlagModel("ethiopia", R.drawable.ethiopia),
            new FlagModel("finland", R.drawable.finland),
            new FlagModel("france", R.drawable.france),
            new FlagModel("germany", R.drawable.germany),
            new FlagModel("greece", R.drawable.greece),
            new FlagModel("hawaii", R.drawable.hawaii),
            new FlagModel("hungary", R.drawable.hungary),
            new FlagModel("india", R.drawable.india),
            new FlagModel("iraq", R.drawable.iraq),
            new FlagModel("ireland", R.drawable.ireland),
            new FlagModel("italy", R.drawable.italy),
            new FlagModel("jamaica", R.drawable.jamaica),
            new FlagModel("japan", R.drawable.japan),
            new FlagModel("lebanon", R.drawable.lebanon),
            new FlagModel("malta", R.drawable.malta),
            new FlagModel("mexico", R.drawable.mexico),
            new FlagModel("nepal", R.drawable.nepal),
            new FlagModel("netherlands", R.drawable.netherlands),
            new FlagModel("north korea", R.drawable.north_korea),
            new FlagModel("peru", R.drawable.peru),
            new FlagModel("philippines", R.drawable.philippines),
            new FlagModel("poland", R.drawable.poland),
            new FlagModel("portugal", R.drawable.portugal),
            new FlagModel("puerto rico", R.drawable.puerto_rico),
            new FlagModel("qatar", R.drawable.qatar),
            new FlagModel("romania", R.drawable.romania),
            new FlagModel("senegal", R.drawable.senegal),
            new FlagModel("slovenia", R.drawable.slovenia),
            new FlagModel("south africa", R.drawable.south_africa),
            new FlagModel("south korea", R.drawable.south_korea),
            new FlagModel("spain", R.drawable.spain),
            new FlagModel("sweden", R.drawable.sweden),
            new FlagModel("switzerland", R.drawable.switzerland),
            new FlagModel("syria", R.drawable.syria),
            new FlagModel("ukraine", R.drawable.ukraine),
            new FlagModel("united kingdom", R.drawable.united_kingdom),
            new FlagModel("uruguay", R.drawable.uruguay),
            new FlagModel("usa", R.drawable.usa),
            new FlagModel("venezuela", R.drawable.venezuela),
            new FlagModel("wales", R.drawable.wales),

    };

    public FlagModel[] createFlagArray(int size) {
        List<Integer> used = new ArrayList<Integer>() {
        };

        FlagModel[] flagArray = new FlagModel[size];
        for (int i = 0; i < size; i++) {
            int rand=random(0,flagArray.length);
            if (!used.contains(rand)){
                used.add(rand);
                flagArray[i]=flags[rand];
            }else{
                i-=1;
            }
        }
        return flagArray;
    }

    public int getPosition(){
        return position;
    }

    public void nextFlag(){
        position++;
    }

    public static int random(int min,int max){
        return (int)Math.floor(Math.random()*(max-min+1)+(min));
    }

    public String randomName(){
        return flags[random(0,flags.length-1)].getName();
    }
}
