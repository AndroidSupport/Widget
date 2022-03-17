package com.uniquext.android.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

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
 * @date 2018/12/11  16:25
 * drawable与text居中
 */
public class CenterDrawableTextView extends AppCompatTextView {

    private int drawablePadding = 0;
    private Rect mTextRect = new Rect();
    private Drawable[] mDrawables = new Drawable[4];

    public CenterDrawableTextView(Context context) {
        this(context, null);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterDrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDrawables = getCompoundDrawables();
        drawablePadding = getCompoundDrawablePadding();
        Drawable[] drawables = getCompoundDrawablesRelative();
        mDrawables[0] = drawables[0] != null ? drawables[0] : mDrawables[0];
        mDrawables[2] = drawables[2] != null ? drawables[2] : mDrawables[2];
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), mTextRect);
        if (mDrawables[0] != null) {    //  left
            final int drawableHeight = mDrawables[0].getIntrinsicHeight();
            final int drawableWidth = mDrawables[0].getIntrinsicWidth();
            final int deltaWidth = (int) ((getWidth() - mTextRect.width()) * 0.5f - drawableWidth * (mDrawables[2] == null ? 0.5f : 1) - drawablePadding - getPaddingStart());
            mDrawables[0].setBounds(deltaWidth, 0, deltaWidth + drawableWidth, drawableHeight);
        }
        if (mDrawables[1] != null) {    //  top
            final int drawableHeight = mDrawables[1].getIntrinsicHeight();
            final int drawableWidth = mDrawables[1].getIntrinsicWidth();
            final int deltaHeight = (int) ((getHeight() - mTextRect.height()) * 0.5f - drawableHeight * (mDrawables[3] == null ? 0.5f : 1) - drawablePadding - getPaddingTop());
            mDrawables[1].setBounds(0, deltaHeight, drawableWidth, deltaHeight + drawableHeight);
        }
        if (mDrawables[2] != null) {    //  right
            final int drawableHeight = mDrawables[2].getIntrinsicHeight();
            final int drawableWidth = mDrawables[2].getIntrinsicWidth();
            final int deltaWidth = (int) ((getWidth() - mTextRect.width()) * 0.5f - drawableWidth * (mDrawables[0] == null ? 0.5f : 1) - drawablePadding - getPaddingEnd());
            mDrawables[2].setBounds(-deltaWidth, 0, -deltaWidth + drawableWidth, drawableHeight);
        }
        if (mDrawables[3] != null) {    //  bottom
            final int drawableHeight = mDrawables[3].getIntrinsicHeight();
            final int drawableWidth = mDrawables[3].getIntrinsicWidth();
            final int deltaHeight = (int) ((getHeight() - mTextRect.height()) * 0.5f - drawableHeight * (mDrawables[1] == null ? 0.5f : 1) - drawablePadding - getPaddingBottom());
            mDrawables[3].setBounds(0, -deltaHeight, drawableWidth, -deltaHeight + drawableHeight);
        }
        super.onDraw(canvas);
    }

}
