package com.uniquext.widget.loopplayer;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TextTest extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private int index = 0;
    private Timer timer;

    private List<String> content = new ArrayList<>();


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    updateText();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    public TextTest(Context context) {
        this(context, null);
    }

    public TextTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        timer = new Timer();
        setFactory(this);
        setInAnimation();
        setOutAnimation();
    }

    @Override
    public View makeView() {
        TextView tv = new TextView(getContext());
        tv.setTextColor(Color.parseColor("#FF0000"));
        tv.setTextSize(22);
        return tv;
    }

    private void setInAnimation() {
        TranslateAnimation in = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f);
        setInAnimation(in);
    }

    private void setOutAnimation() {
        TranslateAnimation out = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
        setOutAnimation(out);
    }

    public void setResources(String[] res) {
        content.addAll(Arrays.asList(res));
    }

    public void setTextStillTime(long time) {
        timer.schedule(new MyTask(), 1, time);
    }

    private void updateText() {
        setText(content.get(index));
        index = (index + 1) % content.size();
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }
}
