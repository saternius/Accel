package com.nicolashahn.backgroundaccelerometer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by jason on 11/5/16.
 */
public class Toggle {
        Paint backgroundColor;
        Paint textColor;
        int textX = 390;
        public Toggle(){
            backgroundColor = new Paint();
            backgroundColor.setColor(Color.rgb(230,230,230));
            textColor = new Paint();
            textColor.setColor(Color.rgb(40,40,40));
            textColor.setTextSize(85);
        }

        public void draw(Canvas stage) {
            stage.drawRect(0,1250,1350,1550,backgroundColor);
            stage.drawText("View Calendar",textX,1375,textColor);
        }

        public boolean hitTest(float x, float y){
            return (x>0 && x<1350 && y>1250 && y<1550);
        }
}
