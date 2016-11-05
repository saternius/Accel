package com.nicolashahn.backgroundaccelerometer;

/**
 * Created by jason on 11/5/16.
 */
import android.annotation.SuppressLint;
import android.graphics.Canvas;



@SuppressLint("WrongCall")
public class GameLoopThread extends Thread {
    private Object mPauseLock;
    private boolean mPaused;
    static final long FPS=40;
    private GameView view;
    public boolean running = false;

    public GameLoopThread(GameView view) {
        this.view = view;
        mPauseLock = new Object();
        mPaused = false;
    }

    public void setRunning(boolean run) {
        running = run;
    }
    @Override
    public void run() {
        long ticksPS=1000/FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime= System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    if (c != null) {
                        view.onDraw(c);
                    }
                }

            } finally {

                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);

                }

            }

            sleepTime=ticksPS-(System.currentTimeMillis()-startTime);
            try{
                if(sleepTime>0)
                    sleep(sleepTime);
                else
                    sleep(10);

            }catch(Exception e){}


            synchronized (mPauseLock) {
                while (mPaused) {
                    try {
                        mPauseLock.wait();
                    } catch (InterruptedException e) {
                    }
                }
            }

        }

    }

    /**
     * Call this on pause.
     */
    public void onPause() {
        synchronized (mPauseLock) {
            mPaused = true;
        }
    }

    /**
     * Call this on resume.
     */
    public void onResume() {
        synchronized (mPauseLock) {
            mPaused = false;
            mPauseLock.notifyAll();
        }
    }

}
