package com.example.drudispuzzle;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

public class TactilListener implements View.OnTouchListener {
    private float xDelta;
    private float yDelta;
    private PuzzleActivity activity;

    public TactilListener(PartidaSinFragmentar2 activity) {
        //this.activity = activity;
    }

    public TactilListener() {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getRawX();
        float y = motionEvent.getRawY();

        //calcula una tolerancia del 10% del tamaño diagonal de la pieza.
        // Si la pieza está más cerca que este en la posición original,
        // se ajustará en su lugar y su propiedad canMove se establecerá en falso.
        // De esta manera, la próxima vez que el jugador intente moverlo, ya no funcionará.

        final double tolerancia = sqrt(pow(view.getWidth(), 2) + pow(view.getHeight(), 2)) / 10;


        PuzzlePiece piece = (PuzzlePiece) view;
        if (!piece.canMove) {
            return true;
        }

        //RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        GridLayout.LayoutParams lParams = (GridLayout.LayoutParams) view.getLayoutParams();
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            //la pieza sale al frente de las otras cuando se toca, en caso de que quede
            // parcialmente oscurecida por otras piezas, y se envíe a la parte posterior de la pila
            // cuando encaje en su lugar, por lo que no cubre ninguna otra pieza que pueda ser movida
            case MotionEvent.ACTION_DOWN:
                xDelta = x - lParams.leftMargin;
                yDelta = y - lParams.topMargin;
                piece.bringToFront();
                break;
            case MotionEvent.ACTION_MOVE:
                lParams.leftMargin = (int) (x - xDelta);
                lParams.topMargin = (int) (y - yDelta);
                view.setLayoutParams(lParams);
                break;
            case MotionEvent.ACTION_UP:
                int xDiff = abs(piece.xCoord - lParams.leftMargin);
                int yDiff = abs(piece.yCoord - lParams.topMargin);
                if (xDiff <= tolerancia && yDiff <= tolerancia) {
                    lParams.leftMargin = piece.xCoord;
                    lParams.topMargin = piece.yCoord;
                    piece.setLayoutParams(lParams);
                    piece.canMove = false;
                    enviarAtras(piece);
                    activity.checkGameOver(); //se chequea estado del juego
                }
                break;
        }

        return true;
    }

    public void enviarAtras(final View child) {
        final ViewGroup parent = (ViewGroup)child.getParent();
        if (null != parent) {
            parent.removeView(child);
            parent.addView(child, 0);
        }
    }
}