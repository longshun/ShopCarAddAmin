package com.example.res_shunlong.myapplication.anims;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by res-shunlong on 2015/12/23.
 * 带抛物线动画效果的ImageView
 */
public class ArcAnimView extends ImageView
{
    public ArcAnimView(Context context)
    {
        super(context);
    }

    /**
     * 创建一个动画层布局
     *
     * @return 布局
     */
    private ViewGroup createAnimLayout(Activity activity)
    {
        // 1
        ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
        // 2
        LinearLayout animLayout = new LinearLayout(activity);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setBackgroundResource(android.R.color.transparent);
        // 3
        rootView.addView(animLayout);

        return animLayout;
    }

    /**
     * 把将要运动的控件设置到动画层布局上
     *
     * @param activity
     *            activity
     * @param location
     *            当前运动控件添加到动画层的位置
     */
    private void addViewToAnimLayout(Activity activity,int[] location)
    {
        //创建动画层
        ViewGroup animLayout = createAnimLayout(activity);
        // 把控件添加到当前动画层布局
        animLayout.addView(this);
        // 设置控件的位置
        int viewX = location[0];
        int viewY = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = viewX;
        lp.topMargin = viewY;
        this.setLayoutParams(lp);
    }

    /**
     * 供外界调用开启动画
     * @param activity activity
     * @param backPicResId 为运动控件设置的图片
     * @param startLocation 运动控件运动开始的位置
     * @param endLocation 运动控件运动结束的位置
     * @param animationListener 动画运动过程的监听对象
     */
    public void startAnim(Activity activity, int backPicResId,
            int[] startLocation, int[] endLocation,
            Animation.AnimationListener animationListener)
    {
        // 把当前要运动的控件添加到动画层
        addViewToAnimLayout(activity, startLocation);
        // 设置运动控件背景
        this.setImageResource(backPicResId);
        // 计算位移
        int startX = startLocation[0];
        int startY = startLocation[1];
        int endX = endLocation[0];
        int endY = endLocation[1];
        // 位移差值
        int toX = 0 - startX + endX;
        int toY = endY - startY;
        // x方向移位动画
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, toX,
                0, 0);
        translateAnimationX.setInterpolator(new AccelerateInterpolator());
        translateAnimationX.setRepeatCount(0);
        translateAnimationX.setFillAfter(false);
        // y方向移位动画
        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0,
                toY);
        translateAnimationY.setInterpolator(new LinearInterpolator());
        translateAnimationY.setRepeatCount(0);
        translateAnimationY.setFillAfter(false);
        // 两个方向的动画集合
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(translateAnimationX);
        animationSet.addAnimation(translateAnimationY);
        animationSet.setDuration(800);
        //开启动画集合
        this.startAnimation(animationSet);
        // 设置动画运动监听
        animationSet.setAnimationListener(animationListener);
    }

    /**
     * 供外界调用开启动画
     * @param activity activity
     * @param backPicResId 为当前运动控件设置的图片
     * @param startLocation 当前运动控件运动开始的位置
     * @param endPositionView 当前运动控件要运动到这个控件处
     * @param animationListener 动画运动过程的监听对象
     */
    public void startAnim(Activity activity, int backPicResId,
            int[] startLocation, View endPositionView,
            Animation.AnimationListener animationListener)
    {
        // 得到动画结束位置的坐标
        int[] endLocation = new int[2];
        endPositionView.getLocationInWindow(endLocation);

        startAnim(activity, backPicResId, startLocation, endLocation,
                animationListener);
    }
}
