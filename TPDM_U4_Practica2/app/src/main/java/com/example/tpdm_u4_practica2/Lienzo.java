package com.example.tpdm_u4_practica2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Lienzo extends View {
        Mosca puntero = null;
        ArrayList<Mosca> mooscas = new ArrayList<Mosca>();
        CountDownTimer timer,timerboss;
        Thread hilo;

        int contador = 29;
        int tiempo=60;
        int vida=5;
    String mensaje = "";

        public Lienzo(Context context){
            super(context);
            System.out.println("hola mundo");

            mooscas.add(new Mosca(this,((int)(Math.random()*700)+5),((int)(Math.random()*700)+5),((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10,((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10));

            hilo = new Thread(){
                public void run(){
                    while(true){
                        if(contador ==30){
                            contador++;
                            tiempo = 10;
                            mooscas.removeAll(mooscas);
                            timer.cancel();
                            timerboss.start();
                            mooscas.add(new Mosca(Lienzo.this,((int)(Math.random()*700)+5),((int)(Math.random()*700)+5),((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10,((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10));
                            mooscas.get(0).mosca =  BitmapFactory.decodeResource(getResources(),R.drawable.flyboss);
                            mooscas.get(0).vida = 5;
                        }
                        //mooscas.add(new Mosca(Lienzo.this,((int)(Math.random()*700)+5),((int)(Math.random()*700)+5),((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10,((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10));

                        for(int i = 0;i<mooscas.size();i++){
                            mooscas.get(i).mover();
                        }
                        try{
                            sleep(50);

                        }catch (InterruptedException e){
                            //error
                        }
                        invalidate();

                    }

                }
            };

            hilo.start();
            //timer moscas
            timer = new CountDownTimer(1500,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tiempo--;
                }

                @Override
                public void onFinish() {
                    mooscas.add(new Mosca(Lienzo.this,((int)(Math.random()*700)+5),((int)(Math.random()*700)+5),((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10,((int)(Math.random()*50)+10)-(int)(Math.random()*50)+10));
                    start();
                }
            };
            timer.start();

            //timerboss
            timerboss = new CountDownTimer(10000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    tiempo--;
                    if(mooscas.isEmpty()){
                        mensaje = "ganaste";
                        cancel();
                    }
                }

                @Override
                public void onFinish() {

                    if(mooscas.isEmpty()){
                        mensaje = "ganaste";
                    }else{
                        mensaje ="perdiste";
                    }
                    //start();
                    cancel();
                }
            };






        }




        public void colision(Mosca p, Canvas c){
            if(p.x >=c.getWidth()){
                p.mx=p.mx*-1 ;
            }
            if(p.x<=1){
                p.mx=p.mx*-1 ;
            }
            if(p.y >= c.getHeight()){
                p.my=p.my*-1 ;
            }
            if(p.y <=1){
                p.my=p.my*-1 ;
            }

        }

        protected void onDraw(Canvas c){


            Paint p = new Paint();

            p.setTextSize(50);

            c.drawText("Puntuacion: "+contador,70,70,p);
            c.drawText("Tiempo restante: "+tiempo,70,140,p);
            c.drawText(mensaje,300,300,p);
            for(int i =0;i<mooscas.size();i++){
                colision(mooscas.get(i),c);
                mooscas.get(i).pintar(c,p);
            }
        }

    public boolean onTouchEvent(MotionEvent me){
        int accion = me.getAction();
        int posx =(int) me.getX();
        int posy =(int) me.getY();
        System.out.println(accion);
        System.out.println(posx);
        System.out.println(posy);
        switch(accion) {
        case MotionEvent.ACTION_UP :
        {
            for(int i =0;i<mooscas.size();i++){
                if (posx >= mooscas.get(i).x && posx <= mooscas.get(i).x+200){
                    if(posy >= mooscas.get(i).y && posy <= mooscas.get(i).y+200 ){
                        {
                            System.out.println("if del ciclo");
                            System.out.println("mooscas.get(i).vida"+mooscas.get(i).vida);
                            System.out.println( mooscas.get(i).estaEnArea(posx,posy));
                            mooscas.get(i).vida--;
                            if(mooscas.get(i).vida==0){
                                contador++;
                            mooscas.remove(i);}
                            break;
                        }
                    }}


            }


        }
        }

        invalidate();
        return true;
    }


    }

