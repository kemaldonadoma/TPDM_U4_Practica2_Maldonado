package com.example.tpdm_u4_practica2;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

public class Mosca {

    int x ,y ,mx,my,vida ;
    Bitmap  mosca ;

    Thread hilo;


    public Mosca(Lienzo este,int x, int y, int mx, int my) {
        vida= 1;
        this.x = x;
        this.y = y;
        this.mx = mx;
        this.my = my;
        mosca = BitmapFactory.decodeResource(este.getResources(),R.drawable.fly);
    }

    public void mover(){
        x += mx;
        y += my;
    }

    public void pintar(Canvas c, Paint p){
        c.drawBitmap(mosca,x,y,p);
    }

    public void matarMosca(){
         x = 0;
         y = 0;
         mx=0;
         my =0;
         mosca = null;
    }

    public boolean estaEnArea(int posx, int posy){
        if (posx >= x && posx <= x+100){
            if(posy >= y && posy <= y+100 ){

                return true;
            }}
        return false;
    }



}


