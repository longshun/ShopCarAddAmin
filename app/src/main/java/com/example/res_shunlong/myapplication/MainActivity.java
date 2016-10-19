package com.example.res_shunlong.myapplication;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.res_shunlong.myapplication.anims.ArcAnimView;
import com.example.res_shunlong.myapplication.views.BadgeView;

public class MainActivity extends AppCompatActivity
        implements View.OnTouchListener, AdapterView.OnItemLongClickListener {

    private Button addButton;
    private int addNum=0;
    private BadgeView showNumView;
    private int[] startLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = (Button) findViewById(R.id.btn_activity_main);
        showNumView = new BadgeView(MainActivity.this,addButton);
        showNumView.setTextColor(Color.WHITE);
        showNumView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        ListView lv = (ListView) findViewById(R.id.lv_activity_main);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            list.add("animation" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);
        lv.setOnTouchListener(this);
        lv.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                // 得到当前触摸的位置，作为动画的起始位置
                int startX = (int) event.getX();
                int startY = (int) event.getY();
                startLocation = new int[]{startX, startY};
                Log.d("startLocation", Arrays.toString(startLocation));
        }
        return false;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //增加数量、
        addNum++;
        // 开启动画 方式一 工具类的方式，把需要运动的控件传递进来
       /* final ImageView runView = new ImageView(this);
        ArcAnim.startAnim(this, runView,R.mipmap.ic_launcher, startLocation, addButton, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                runView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                runView.setVisibility(View.GONE);

                showNumView.setText(addNum + "");
                showNumView.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });*/

        //方式二 使用自定义View，让其运动
        final ArcAnimView arcAnimView = new ArcAnimView(this);
        arcAnimView.startAnim(this, R.mipmap.ic_launcher, startLocation, addButton, new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                arcAnimView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                arcAnimView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return true;
    }
}
