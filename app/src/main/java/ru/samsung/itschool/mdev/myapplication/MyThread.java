package ru.samsung.itschool.mdev.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    private Paint paint;
    // "держатель" SurfaceView
    private SurfaceHolder surfaceHolder;
    public boolean flag;

    MyThread(SurfaceHolder holder) {
        this.surfaceHolder = holder;
        this.flag = false;
        paint = new Paint();
        paint.setAntiAlias(true); // сглаживание
        paint.setStyle(Paint.Style.FILL);
    }

    public long getTime() {
        // Unixtype 01/01/1970
        return System.nanoTime()/1000;
    }

    long redrawTime = 0;

    @Override
    public void run() {
        Canvas canvas;
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 500000) {
                continue;
            }
            // логика отрисовки....
            canvas = surfaceHolder.lockCanvas();
            drawCircle(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);
            redrawTime = getTime();
        }
    }

    public void drawCircle(Canvas c) {
        int height = c.getHeight();
        int width = c.getWidth();
        float maxR = (float)Math.min(height,width);
        c.drawColor(Color.BLACK); // фон
        paint.setColor(Color.RED); // цвет круга
        float koef = (float)Math.random(); // < 1
        c.drawCircle(width/2,height/2,maxR*koef,paint);
    }
}
