package com.uniquext.android.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.uniquext.android.widget.R;
import com.uniquext.android.widget.util.Convert;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * 　 　　   へ　　　 　／|
 * 　　    /＼7　　　 ∠＿/
 * 　     /　│　　 ／　／
 * 　    │　Z ＿,＜　／　　   /`ヽ
 * 　    │　　　 　　ヽ　    /　　〉
 * 　     Y　　　　　   `　  /　　/
 * 　    ｲ●　､　●　　⊂⊃〈　　/
 * 　    ()　 へ　　　　|　＼〈
 * 　　    >ｰ ､_　 ィ　 │ ／／      去吧！
 * 　     / へ　　 /　ﾉ＜| ＼＼        比卡丘~
 * 　     ヽ_ﾉ　　(_／　 │／／           消灭代码BUG
 * 　　    7　　　　　　　|／
 * 　　    ＞―r￣￣`ｰ―＿
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * @author UniqueXT
 * @version 1.0
 * @date 2018/12/3  11:22
 */
public class ClearEditView extends AppCompatEditText {
    /**
     * 边距
     */
    private static final int PADDING = 15;
    /**
     * 清除图标
     */
    private Bitmap mClearIcon;
    /**
     * drawable集
     */
    private Drawable[] mDrawables;
    /**
     * 边距，单位dp
     */
    private int mPadding;
    /**
     * 文本内容是否为空
     */
    private boolean mIsEmpty = true;
    /**
     * 判断摁下和抬起的区域是否为图标所属区域
     */
    private boolean mIsDown, mIsUp;
    /**
     * 图标左上角坐标
     */
    private float[] mIconPos = new float[2];
    /**
     * 清除事件监听
     */
    private OnDeleteListener mOnDeleteListener;

    public ClearEditView(Context context) {
        this(context, null);
    }

    public ClearEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawables = getCompoundDrawablesRelative();
        mPadding = (int) Convert.dp(getContext(), PADDING) + getPaddingEnd();
        setPadding(getPaddingLeft(), getPaddingTop(), mPadding, getPaddingBottom());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mClearIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ui_icon_delete);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mIconPos[0] = w - mClearIcon.getWidth() - mPadding;
        mIconPos[1] = (h - mClearIcon.getHeight()) / 2;
    }

    @Override
    protected void onDetachedFromWindow() {
        mClearIcon.recycle();
        mClearIcon = null;
        super.onDetachedFromWindow();
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (mIsEmpty != TextUtils.isEmpty(text)) {
            mIsEmpty = TextUtils.isEmpty(text);
            refresh(!mIsEmpty);
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        refresh(focused && !TextUtils.isEmpty(getText()));
    }

    /**
     * 刷新
     *
     * @param isShow 是否展示清除键
     */
    private void refresh(boolean isShow) {
        if (mDrawables != null) {
            if (isShow) {
                Drawable clear = getResources().getDrawable(R.mipmap.ui_icon_delete);
                setCompoundDrawablesWithIntrinsicBounds(mDrawables[0], mDrawables[1], clear, mDrawables[3]);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(mDrawables[0], mDrawables[1], null, mDrawables[3]);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float POSITION_X = event.getX();
        final float POSITION_Y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsDown = isContain(POSITION_X, POSITION_Y);
                break;
            case MotionEvent.ACTION_UP:
                mIsUp = isContain(POSITION_X, POSITION_Y);
                break;
            default:
                break;
        }
        if (mIsDown && mIsUp) {
            this.setText("");
            mIsDown = mIsUp = false;
            if (mOnDeleteListener != null) {
                mOnDeleteListener.onDelete();
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 判断是否在icon区域内
     *
     * @param x x坐标
     * @param y y坐标
     * @return 是否在区域内
     */
    private boolean isContain(float x, float y) {
        return (x > mIconPos[0] - mPadding) && (x < mIconPos[0] + mClearIcon.getWidth() + mPadding)
                && (y > mIconPos[1] - mPadding) && (y < mIconPos[1] + mClearIcon.getHeight() + mPadding);
    }

    /**
     * 设置clear点击事件
     *
     * @param deleteListener 点击回调
     */
    public void setOnDeleteListener(OnDeleteListener deleteListener) {
        this.mOnDeleteListener = deleteListener;
    }

    /**
     * 清除事件接口
     */
    public interface OnDeleteListener {
        /**
         * 清除事件回调
         */
        void onDelete();
    }
}

