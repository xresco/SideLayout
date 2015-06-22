package com.abed.cobi.View.Components;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;

/**
 * Created by abed on 6/22/15.
 */
public class RotatableFrameLayout extends FrameLayout implements View.OnTouchListener{

    boolean isBeingAnimated=false;
    private float downX, downY, upX, upY;
    private OnRotateListener rotateListener;
    private int rotation_step=0;

    public RotatableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.setTranslationX(width*0.5f);
    }


    public boolean onTouch(View v, MotionEvent event) {
        final int MIN_DISTANCE = 20;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getRawX();
                downY = event.getRawY();

                return true;
            }
            case MotionEvent.ACTION_UP: {
                upX = event.getRawX();
                upY = event.getRawY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // swipe vertical?
                if(Math.abs(deltaY)> MIN_DISTANCE){
                    if(!isBeingAnimated) {

                        int angle = 0;
                        // top or down
                        if (deltaY < 0) {
                            angle = -90;
                            rotation_step--;
                        }
                        if (deltaY > 0) {
                            angle = 90;
                            rotation_step++;
                        }

                        this.animate().rotationBy(angle)
                                .setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {
                                        isBeingAnimated = true;
                                        if(rotateListener!=null)
                                            rotateListener.onRotateStarted();
                                    }
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        isBeingAnimated = false;
                                        if(rotateListener!=null)
                                            rotateListener.onRotate(rotation_step);
                                    }
                                    @Override
                                    public void onAnimationCancel(Animator animation) {}
                                    @Override
                                    public void onAnimationRepeat(Animator animation) {}
                                })
                                .start();
                    }
                    return true;
                } else { Log.i("Log::", "Swipe was only " + Math.abs(deltaX) + " long, need at least " + MIN_DISTANCE); }
                     return true;
            }
        }
        return true;
    }

    public void setRotateListener(OnRotateListener rotateListener) {
        this.rotateListener = rotateListener;
    }

    public interface OnRotateListener{
         void onRotate(int step);
         void onRotateStarted();
    }

}