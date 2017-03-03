package org.protaxiandroidapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by DIEGO on 15/02/2017.
 */
public class RecyclerListener implements RecyclerView.OnItemTouchListener{

    private OnItemClickListener myListener;

    public interface OnItemClickListener{
        public void onItemClick(View view, int position);
    }

    GestureDetector myGestureDetector;


    public RecyclerListener(Context context, OnItemClickListener listener){
        myListener = listener;
//        myGestureDetector = new GestureDetector(context, (GestureDetector.SimpleOnGestureListener) onSingleTapUp(e) )
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if(childView != null && myListener != null && myGestureDetector.onTouchEvent(e)){
            myListener.onItemClick(childView, view.getChildLayoutPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
