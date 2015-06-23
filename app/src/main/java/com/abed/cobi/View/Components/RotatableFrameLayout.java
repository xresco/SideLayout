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
    private float downX, downY, upX, upY,firstY;
    private OnRotateListener rotateListener;
    private int rotation_step=0;
    private int rotationOffset=60;
    private int prev_angle;
    private boolean rotation_started=false;

    public RotatableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        prev_angle=(int)this.getRotation();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        this.setTranslationX(width*0.5f);
    }


    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                downX = event.getRawX();
                downY = event.getRawY();
                firstY = event.getRawY();


                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                upY = event.getRawY();
                float deltaY = downY - upY;
                float totalDeltaY = firstY - upY;
                if(Math.abs(totalDeltaY)>300)
                if(!rotation_started && rotateListener!=null) {
                    rotateListener.onRotateStarted();
                    rotation_started=true;
                }

                int angle=(int)((deltaY*90)/(float)getHeight());
                rotate(angle);
                downY=upY;
                return true;
            }
            case MotionEvent.ACTION_UP: {
                balanceRotate();
                return true;
            }
        }
        return true;
    }

    public void rotate(int angle)
    {
        this.setRotation(this.getRotation()+angle);
    }

    public void balanceRotate(){
        rotation_started=false;
        float angle=getRotation();
        int nearest_angle=Math.round((angle-rotationOffset)/90)*90+rotationOffset;

        this.animate().rotation(nearest_angle)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        isBeingAnimated = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        isBeingAnimated = false;
                        if (rotateListener != null) {
                            int new_angle=(int)RotatableFrameLayout.this.getRotation();
                            if(new_angle>prev_angle)
                                rotation_step++;
                            if(new_angle<prev_angle)
                                rotation_step--;
                            prev_angle=new_angle;
                            rotateListener.onRotate(rotation_step);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
    }

    public void setRotateListener(OnRotateListener rotateListener) {
        this.rotateListener = rotateListener;
    }

    public interface OnRotateListener{
         void onRotate(int step);
         void onRotateStarted();
    }

}