package com.uniquext.android.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

import com.uniquext.android.widget.R;

import androidx.appcompat.widget.AppCompatImageView;

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
    private final Matrix matrix = new Matrix();
    private boolean circle = false;
    private float[] radii = new float[]{0, 0, 0, 0, 0, 0, 0, 0};

    public CornerImageView(Context context) {
        this(context, null);
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initStyleable(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (circle) {
            path.addCircle(getWidth() * 0.5f, getHeight() * 0.5f, Math.min(getWidth(), getHeight()) * 0.5f, Path.Direction.CW);
        } else {
            path.addRoundRect(0, 0, getWidth(), getHeight(), radii, Path.Direction.CW);
        }
        canvas.clipPath(path);
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

    private BitmapShader initBitmapShader(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        matrix.setScale(getWidth() * 1.0f / bitmap.getWidth(), getHeight() * 1.0f / bitmap.getHeight());
        bitmapShader.setLocalMatrix(matrix);
        return bitmapShader;
    }
}
