package com.example.drudispuzzle;

import android.annotation.SuppressLint;
import android.content.Context;

import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class PartidaFragmentada extends AppCompatActivity {
    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private String[] tileList;
    private GestureDetectGridView mGridView;
    private int mColumnWidth;
    private int mColumnHeight;

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

                    display();

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

        private void display () {
            ArrayList<Button> buttons = new ArrayList<>();
            Button button;

            for (int i = 0; i < tileList.length; i++) {
                button = new Button(this);

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

    }
