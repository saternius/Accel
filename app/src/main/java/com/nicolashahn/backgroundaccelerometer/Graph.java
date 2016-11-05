package com.nicolashahn.backgroundaccelerometer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by jason on 11/5/16.
 */
public class Graph {
    Style style;
    StyleSheet styleSheet;

    int pos;
    int start = 0;
    int end = 0;
    ArrayList<Float> points = new ArrayList<>();
    public Graph(Style style,int pos){
        this.style = style;
        this.styleSheet = new StyleSheet(style,pos);
        //line.moveTo(0,styleSheet.height);
        //points.add((float)styleSheet.height);
        this.pos = pos;
    }

    public void draw(Canvas stage) {
        stage.drawRect(styleSheet.getRect(),styleSheet.backgroundColor);
        Path line = new Path();
        line.moveTo(0,styleSheet.center);
        int j=0;
        for(int i=start;i<end;i++){
            line.lineTo(j,points.get(i));
            j++;
        };
        stage.drawPath(line, styleSheet.lineColor);
    }

    public void addPoint(float intensity){
        points.add(styleSheet.center+intensity*5);
        end++;
        if(end>1000){
            start++;
        }
    }

    public enum Style{
        DARK,
        LIGHT,
        VERYDARK
    }

    class StyleSheet{
        Paint backgroundColor = new Paint();
        Paint lineColor = new Paint();
        int yPos;
        int height = 300;
        int width = 1350;
        int center;
        StyleSheet(Style style, int pos){
            yPos = pos*height;
            if(style == Style.DARK){
                backgroundColor.setColor(Color.rgb(53, 126, 171));
                lineColor.setColor(Color.rgb(255, 232, 56));

            }else if(style == Style.LIGHT){
                backgroundColor.setColor(Color.rgb(68, 141, 184));
                lineColor.setColor(Color.rgb(255, 255, 87));
            }else{
                backgroundColor.setColor(Color.rgb(37, 87, 125));
                lineColor.setColor(Color.rgb(255,204,0));
                height = 500;
            }
            backgroundColor.setStyle(Paint.Style.FILL);
            lineColor.setStyle(Paint.Style.STROKE);
            lineColor.setStrokeWidth(5);
            center = yPos+height/2;
        }

        Rect getRect(){
            return new Rect(0,yPos,width,yPos+height);
        }
    }

}
