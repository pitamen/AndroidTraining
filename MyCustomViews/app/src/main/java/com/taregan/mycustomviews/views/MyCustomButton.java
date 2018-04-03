package com.taregan.mycustomviews.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.taregan.mycustomviews.R;

/**
 * Created by pitambar on 4/3/18.
 */

public class MyCustomButton extends AppCompatButton {

    //constructors matching super

    public MyCustomButton(Context context) {
        super(context);
    }

    public MyCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public MyCustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Override onDraw Method


    @Override
    protected void onDraw(Canvas canvas) {
        if(isPressed()){

            setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_is_pressed));

        }else {

            setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_is_not_pressed));

        }

        //set text color for custom button
        setTextColor(Color.parseColor("#ffffff"));
        //set text for custom button
        setText("My custom Button");

        super.onDraw(canvas);


    }
}
