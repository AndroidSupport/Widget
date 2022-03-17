package com.uniquext.widget.loopplayer;

import android.content.Context;
import android.content.res.TypedArray;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TextSwitchView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private int index = 0;
    private Timer timer;

    private long period = 0L;
    private float textSize = 20f;
    private int textColor = Color.BLACK;
    private List<String> content = new ArrayList<>();
    private Handler mHandler = new MyHandler(this);

    public TextSwitchView(Context context) {
        this(context, null);
    }

    public TextSwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
        this.initStyleable(attrs);
    }

    private void initStyleable(@Nullable AttributeSet attrs) {
        if (attrs == null) return;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextSwitchView);
        int time = typedArray.getInteger(R.styleable.TextSwitchView_period, 0);
        textSize = typedArray.getDimension(R.styleable.TextSwitchView_contentSize, 20f);
        textColor = typedArray.getColor(R.styleable.TextSwitchView_contentColor, Color.BLACK);
        int arrayRes = typedArray.getResourceId(R.styleable.TextSwitchView_contentArray, -1);
        typedArray.recycle();

        String[] textArray = getResources().getStringArray(arrayRes);
        if (textArray.length > 0) {
            setResources(textArray);
        }
        if (time > 0) {
            setTextStillTime(time);
        }
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
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        return tv;
    }

    private void setInAnimation() {
        TranslateAnimation in = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f);
        in.setDuration(300);
        setInAnimation(in);
    }

    private void setOutAnimation() {
        TranslateAnimation out = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f);
        out.setDuration(300);
        setOutAnimation(out);
    }

    public void setResources(String[] res) {
        content.clear();
        content.addAll(Arrays.asList(res));
    }

    public void setTextStillTime(long time) {
        if (period == 0) {
            period = time;
            timer.schedule(new MyTask(), 1, time);
        }
    }

    void updateText() {
        setText(content.get(index));
        index = (index + 1) % content.size();
    }

    @Override
    protected void onDetachedFromWindow() {
        timer.cancel();
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }

    private static class MyHandler extends Handler {
        private final WeakReference<TextSwitchView> viewWeakReference;

        MyHandler(TextSwitchView view) {
            viewWeakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            TextSwitchView textSwitchView = viewWeakReference.get();
            if (textSwitchView != null && msg.what == 1) {
                textSwitchView.updateText();
            }
        }
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(1);
        }
    }
}
