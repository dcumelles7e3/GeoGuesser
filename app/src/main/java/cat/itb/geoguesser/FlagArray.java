package cat.itb.geoguesser;

import java.util.ArrayList;
import java.util.List;

public class FlagArray {
    private int position=0;

    public FlagModel[] flags = new FlagModel[]{
            new FlagModel("Andorra", R.drawable.andorra),
            new FlagModel("Argentina", R.drawable.argentina),
            new FlagModel("Belgium", R.drawable.belgium),
            new FlagModel("Brazil", R.drawable.brazil),
            new FlagModel("Chile", R.drawable.chile),
            new FlagModel("China", R.drawable.china),
            new FlagModel("Colombia", R.drawable.colombia),
            new FlagModel("Cuba", R.drawable.cuba),
            new FlagModel("Czech Republic", R.drawable.czech_republic),
            new FlagModel("Denmark", R.drawable.denmark),
            new FlagModel("Ecuador", R.drawable.ecuador),
            new FlagModel("Egypt", R.drawable.egypt),
            new FlagModel("El Salvador", R.drawable.el_salvador),
            new FlagModel("Ethiopia", R.drawable.ethiopia),
            new FlagModel("Finland", R.drawable.finland),
            new FlagModel("France", R.drawable.france),
            new FlagModel("Germany", R.drawable.germany),
            new FlagModel("Greece", R.drawable.greece),
            new FlagModel("Hawaii", R.drawable.hawaii),
            new FlagModel("Hungary", R.drawable.hungary),
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

    public int listSize(){
        return flags.length;
    }

    public String randomName(){
        return flags[random(0,flags.length-1)].getName();
    }
}
