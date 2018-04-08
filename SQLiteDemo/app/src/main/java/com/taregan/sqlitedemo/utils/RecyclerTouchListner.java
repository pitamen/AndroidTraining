package com.taregan.sqlitedemo.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pitambar on 4/5/18.
 */

public class RecyclerTouchListner implements RecyclerView.OnItemTouchListener {

    private ClickListner clickListner;
    private GestureDetector gestureDetector;

    public RecyclerTouchListner(Context context,final RecyclerView recyclerView,final ClickListner clickListner) {

        this.clickListner = clickListner;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && clickListner!=null){
                    clickListner.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if(child!=null && clickListner!=null && gestureDetector.onTouchEvent(e))
            clickListner.onClick(child,rv.getChildAdapterPosition(child));
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListner{

        public void onClick(View v, int position);
        public void onLongClick(View v, int position);

    }
}
