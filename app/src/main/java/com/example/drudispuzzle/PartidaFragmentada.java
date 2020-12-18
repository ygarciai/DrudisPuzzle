package com.example.drudispuzzle;

import android.annotation.SuppressLint;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class PartidaFragmentada extends AppCompatActivity {
    ImageView ifondo;
    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static String[] tileList;
    private static GestureDetectGridView mGridView;
    private static int mColumnWidth;
    private static int mColumnHeight;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_partida_fragmentada);
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);


        init();
        scramble();
        setDimensions();
    }

    private void init() {

        int drawableResourceId = this.getResources().getIdentifier("gollumentero", "drawable", this.getPackageName());
        ifondo = (ImageView) findViewById(R.id.imageView_fondoPantalla2);
        Drawable res = getResources().getDrawable(drawableResourceId);
        ifondo.setImageResource(drawableResourceId);
        //mGridView = this.<GestureDetectGridView>findViewById(R.id.parent);
       // mGridView.setNumColumns(COLUMNS);

        //tileList = new String[DIMENSIONS];
        //for (int i = 0; i < DIMENSIONS; i++) {
        //    tileList[i] = String.valueOf(i);

        //}
    }

    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

      for (int i = 0; i < tileList.length; i++) {
          index = random.nextInt(i + 1);
          temp = tileList[index];
          tileList[index] = tileList[i];
          tileList[i] = temp;
      }

    }

    private void setDimensions() {
        mGridView = this.<GestureDetectGridView>findViewById(R.id.imageView_fondoPantalla2);
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusBarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusBarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                display(getApplicationContext());

            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static void display(Context context) {
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++) {
            button = new Button(context);

            if (tileList[i].equals("0")) {
                button.setBackgroundResource(R.drawable.golum1);
            } else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.golum2);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.golum3);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.golum4);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.golum5);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.golum6);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.golum7);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.golum8);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.golum9);

            buttons.add(button);

        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));

    }

    private static void swap(Context context, int InPosition, int swap) {
        String newPosition = tileList[InPosition + swap];
        tileList[InPosition + swap] = tileList[InPosition];
        tileList[InPosition] = newPosition;
        display(context);

        if (isSolved()) {
            Toast.makeText(context, "You Win", Toast.LENGTH_SHORT).show();
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        return solved;
    }

    public static void moveTiles(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

            // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }
}


