package com.uniquext.android.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.appcompat.widget.AppCompatImageView;

import com.uniquext.android.widget.R;

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
 * @date 2019/6/5  10:33
 */
public class CornerImageView extends AppCompatImageView {

    private final Path path = new Path();
    private final Paint paint = new Paint();

    private boolean circle = false;
    private float[] radii = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

    @ColorInt
    private int strokeColor = Color.TRANSPARENT;
    private float strokeWidth = 0;

    public CornerImageView(Context context) {
        this(context, null);
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initStyleable(attrs);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (circle) {
            path.addCircle(getWidth() * 0.5f, getHeight() * 0.5f, Math.min(getWidth(), getHeight()) * 0.5f, Path.Direction.CW);
        } else {
            path.addRoundRect(0, 0, getWidth(), getHeight(), radii, Path.Direction.CW);
        }
        canvas.clipPath(path);
        addStroke(canvas);
        super.onDraw(canvas);
    }

    private void initStyleable(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CornerImageView);
        circle = typedArray.getBoolean(R.styleable.CornerImageView_circle, false);

        float cornerRadius = typedArray.getDimension(R.styleable.CornerImageView_cornerRadius, 0);
        float topLeftRadius = typedArray.getDimension(R.styleable.CornerImageView_topLeftRadius, 0);
        float topRightRadius = typedArray.getDimension(R.styleable.CornerImageView_topRightRadius, 0);
        float bottomLeftRadius = typedArray.getDimension(R.styleable.CornerImageView_bottomLeftRadius, 0);
        float bottomRightRadius = typedArray.getDimension(R.styleable.CornerImageView_bottomRightRadius, 0);

        strokeColor = typedArray.getColor(R.styleable.CornerImageView_strokeColor, Color.TRANSPARENT);
        strokeWidth = typedArray.getDimension(R.styleable.CornerImageView_strokeWidth, 0);

        if (cornerRadius != 0) {
            for (int i = 0; i < 8; ++i) {
                radii[i] = cornerRadius;
            }
        } else {
            if (topLeftRadius != 0) {
                radii[0] = radii[1] = topLeftRadius;
            }
            if (topRightRadius != 0) {
                radii[2] = radii[3] = topRightRadius;
            }
            if (bottomRightRadius != 0) {
                radii[4] = radii[5] = bottomRightRadius;
            }
            if (bottomLeftRadius != 0) {
                radii[6] = radii[7] = bottomLeftRadius;
            }
        }

        typedArray.recycle();
        postInvalidate();
    }

    private void addStroke(Canvas canvas) {
        if (strokeWidth > 0) {
            paint.setColor(strokeColor);
            paint.setStrokeWidth(strokeWidth);
            canvas.drawPath(path, paint);
        }
    }

    public void setCircle(boolean circle) {
        this.circle = circle;
    }

    public void setCornerRadius(float cornerRadius) {
        for (int i = 0; i < 8; ++i) {
            radii[i] = cornerRadius;
        }
        postInvalidate();
    }

    public void setTopLeftRadius(float topLeftRadius) {
        radii[0] = radii[1] = topLeftRadius;
        postInvalidate();
    }

    public void setTopRightRadius(float topRightRadius) {
        radii[2] = radii[3] = topRightRadius;
        postInvalidate();
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        radii[6] = radii[7] = bottomLeftRadius;
        postInvalidate();
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        radii[4] = radii[5] = bottomRightRadius;
        postInvalidate();
    }

    public void setCornerRadius(float topLeftRadius, float topRightRadius, float bottomLeftRadius, float bottomRightRadius) {
        radii[0] = radii[1] = topLeftRadius;
        radii[2] = radii[3] = topRightRadius;
        radii[6] = radii[7] = bottomLeftRadius;
        radii[4] = radii[5] = bottomRightRadius;
        postInvalidate();
    }

    public void setStrokeColor(@ColorInt int strokeColor) {
        this.strokeColor = strokeColor;
        postInvalidate();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        postInvalidate();
    }

    public void setStroke(float strokeWidth, @ColorInt int strokeColor) {
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        postInvalidate();
    }
}
