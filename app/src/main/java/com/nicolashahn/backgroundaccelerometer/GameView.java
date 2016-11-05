package com.nicolashahn.backgroundaccelerometer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class GameView extends SurfaceView {

    //======================================================
    private SurfaceHolder holder;
    public GameLoopThread gameLoopThread;
    static int stage_width=1080;
    static int stage_height=1920;
    static Context ctx;
    static Graph x_graph;
    static Graph y_graph;
    static Graph z_graph;
    static Graph t_graph;
    static int time = 0;
    static int BATCH_SIZE = 5;
    static Toggle toggle = new Toggle();
    public GameView(Context context) {
        super(context);
        init(context);

    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void init(Context context) {
        //

        gameLoopThread=new GameLoopThread(this);
        holder=getHolder();
        holder.addCallback(new Callback() {


            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }

            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                // TODO Auto-generated method stub

            }
        });

        //=====================================================================
        ctx = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        stage_width = size.x;
        stage_height = size.y;
        Log.d("kek","Screen Width:"+size.x);
        Log.d("kek","Screen Height:"+size.y);
        //======================================================================

        x_graph = new Graph(Graph.Style.DARK,0);
        y_graph = new Graph(Graph.Style.LIGHT,1);
        z_graph = new Graph(Graph.Style.DARK,2);
        t_graph = new Graph(Graph.Style.VERYDARK,3);
    }

    public void reinit(){
        x_graph = new Graph(Graph.Style.DARK,0);
        y_graph = new Graph(Graph.Style.LIGHT,1);
        z_graph = new Graph(Graph.Style.DARK,2);
        t_graph = new Graph(Graph.Style.VERYDARK,3);
    }

    public static void addPoints(float x, float y, float z)throws Exception{
        x_graph.addPoint(x);
        y_graph.addPoint(y);
        z_graph.addPoint(z/2);
        time++;
        if(time%BATCH_SIZE==0){
            t_graph.addPoint(getTremorVal());
        }
    }

    public static float getTremorVal(){
        List<Float> lastXs = lastBatchOf(x_graph);
        List<Float> lastYs = lastBatchOf(y_graph);
        List<Float> lastZs = lastBatchOf(z_graph);
        return (float)(Math.random()*6-3);
    }

    public static List<Float> lastBatchOf(Graph graph){
        int size = graph.points.size();
        return graph.points.subList(size-BATCH_SIZE,size);
    }


    protected void onDraw(Canvas stage){
        stage.drawColor(Color.parseColor("#25587F"));
        x_graph.draw(stage);
        y_graph.draw(stage);
        z_graph.draw(stage);
        t_graph.draw(stage);
        toggle.draw(stage);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mouseX = event.getX();
        float mouseY = event.getY();
        if(toggle.hitTest(mouseX,mouseY)){

        }
        return true;
    }

    public static void DIE(int i) {
//        alive = false;
//        curtains.death(i);
    }





}