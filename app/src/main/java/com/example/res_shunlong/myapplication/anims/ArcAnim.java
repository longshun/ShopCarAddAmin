package com.example.res_shunlong.myapplication.anims;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * Created by res-shunlong on 2015/12/23.
 * 带抛物线动画效果的工具类
 */
public class ArcAnim
{

    /**
     * 创建一个动画层布局
     *
     * @return 布局
     */
    private static ViewGroup createAnimLayout(Activity activity)
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
     * @param animLayout
     *            动画层布局
     * @param view
     *            控件
     * @param location
     *            控件位置
     */
    private static void addViewToAnimLayout(ViewGroup animLayout, View view,
            int[] location)
    {
        // 把控件添加到当前动画层布局
        animLayout.addView(view);
        // 设置控件的位置
        int viewX = location[0];
        int viewY = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = viewX;
        lp.topMargin = viewY;
        view.setLayoutParams(lp);
    }

    /**
     * 开启抛物线动画效果
     * @param activity activity
     * @param runView 运动的控件
     * @param backPicResId 运动控件的背景
     * @param startLocation 运动控件开始的位置
     * @param endLocation 运动控件结束的位置
     * @param animationListener 动画监听对象
     */
    public static void startAnim(Activity activity, final View runView,
            int backPicResId, int[] startLocation, int[] endLocation,
            Animation.AnimationListener animationListener)
    {
        // 设置运动控件背景
        runView.setBackgroundResource(backPicResId);
        final ViewGroup animLayout = createAnimLayout(activity);
        addViewToAnimLayout(animLayout, runView, startLocation);
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

        runView.startAnimation(animationSet);
        // 设置回调接口处理动画运动过程中的事件
        animationSet.setAnimationListener(animationListener);
    }

    /**
     * 开启抛物线动画效果
     * @param activity activity
     * @param runView 运动的控件
     * @param backPicResId 运动控件的背景
     * @param startLocation 运动控件开始的位置
     * @param endPositionView 运动控件结束的位置上的控件
     * @param animationListener 动画监听对象
     */
    public static void startAnim(Activity activity, final View runView,
            int backPicResId, int[] startLocation, View endPositionView,
            Animation.AnimationListener animationListener)
    {
        //得到动画结束位置的坐标
        int[] endLocation = new int[2];
        endPositionView.getLocationInWindow(endLocation);

        startAnim(activity,runView,backPicResId,startLocation, endLocation,animationListener);
    }
}
