package com.example.drudispuzzle;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class PartidaFragmentada extends AppCompatActivity {
    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static String[] tileList;
    private static GestureDetectGridView mGridView;
    private static int mColumnWidth;
    private static int mColumnHeight;


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_partida_fragmentada);

        init();

        scramble();

        setDimensions();

    }
        private void setDimensions () {
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

        private int getStatusBarHeight (Context context){
            int result = 0;
            int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");

            int resourceId = 0;
            if (resourceId > 0) {
                result = context.getResources().getDimensionPixelSize(resourceId);
            }
            return result;
        }

        private static void display (Context context) {
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

        private void scramble () {
            int index;
            String temp;
            Random random = new Random();

            for (int i = tileList.length - 1; i > 0; i++) {
                index = random.nextInt(i + 1);
                temp = tileList[index];
                tileList[index] = tileList[i];
                tileList[i] = temp;

            }
        }


        @SuppressLint("WrongViewCast")
        private void init () {
            mGridView = (GestureDetectGridView) findViewById(R.id.fragmentada);
            mGridView.setNumColumns(COLUMNS);

            tileList = new String[DIMENSIONS];
            for (int i = 0; i < DIMENSIONS; i++) {
                tileList[i] = String.valueOf(i);

            }
        }

        private static void swap(Context context, int position, int swap) {
            String newPosition = tileList[position + swap];
            tileList[position + swap] = tileList[position];
            tileList[position] = newPosition;
            display(context);

            if (isSolved()) {
                Toast.makeText(context, "You Win", Toast.LENGTH_SHORT);
            }
        }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved =true;
            } else {
                solved = false;
                break;
            }
        }
        return solved;
    }

    public static void moveTiles (Context context, String direction, int position) {
            if (position ==0) {
                if (direction.equals("right")) {
                    swap(context, position, 1);
                } else if (direction.equals("down")) {
                    swap(context, position, COLUMNS);
                } else {
                    Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
                }
            } else swap (context, position, COLUMNS ) ;

            }
        }


