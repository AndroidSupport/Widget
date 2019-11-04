package com.uniquext.android.widget.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

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
 * @date 2019/2/26  17:58
 */
public class CropImageView extends AppCompatImageView {

    private static final int CLIP_FRAME_OUT_COLOR = 0xB2000000;
    private static final int TRANSLATE = 0b0;
    private static final int SCALE = 0b1;

    private RectF mClipRectF = new RectF();
    private Paint mRectFPaint = new Paint();
    private Paint mFrameLinePaint = new Paint();

    private int mAction;

    /**
     * 图片矩阵
     */
    private Matrix mMatrix = new Matrix();
    /**
     * 位移参考点
     */
    private PointF mReferencePointF = new PointF();
    /**
     * 缩放手势检测
     */
    private ScaleGestureDetector scaleGestureDetector;

    public CropImageView(Context context) {
        this(context, null);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initRect();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (getDrawable() != null) {
            initMatrix();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRect(canvas);
        drawFrame(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mAction = TRANSLATE;
                mReferencePointF.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mAction = SCALE;
                float x = (event.getX(0) + event.getX(1)) * 0.5f;
                float y = (event.getY(0) + event.getY(1)) * 0.5f;
                mReferencePointF.set(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mAction == TRANSLATE) {
                    postImageTranslation(event.getX() - mReferencePointF.x, event.getY() - mReferencePointF.y);
                    mReferencePointF.set(event.getX(), event.getY());
                } else {
                    scaleGestureDetector.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
            default:
                break;
        }
        return true;
    }

    private void initConfig() {
        setScaleType(ScaleType.MATRIX);
        mFrameLinePaint.setStrokeWidth(3);
        mFrameLinePaint.setColor(Color.WHITE);
        mRectFPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                postImageScale(detector.getScaleFactor(), mReferencePointF.x, mReferencePointF.y);
                return true;
            }

        });
    }

    private void initRect() {
        final float length = Math.min(getWidth(), getHeight()) * 0.8f;
        mClipRectF.left = (getWidth() - length) * 0.5f;
        mClipRectF.top = (getHeight() - length) * 0.5f;
        mClipRectF.right = mClipRectF.left + length;
        mClipRectF.bottom = mClipRectF.top + length;
    }

    private void initMatrix() {
        float scaleX = mClipRectF.width() / (float) getDrawable().getIntrinsicWidth();
        float scaleY = mClipRectF.height() / (float) getDrawable().getIntrinsicHeight();
        float scale = Math.max(scaleX, scaleY);
        postImageScale(scale, 0, 0);
        float px = Math.min(mClipRectF.left - (getDrawable().getIntrinsicWidth() * scale - mClipRectF.width()) * 0.5f, mClipRectF.left);
        float py = Math.min(mClipRectF.top - (getDrawable().getIntrinsicHeight() * scale - mClipRectF.height()) * 0.5f, mClipRectF.top);
        postImageTranslation(px, py);
    }

    private void postImageScale(float scale, float px, float py) {
        mMatrix.postScale(scale, scale, px, py);
        setImageMatrix(mMatrix);
    }

    private void postImageTranslation(float dx, float dy) {
        mMatrix.postTranslate(dx, dy);
        setImageMatrix(mMatrix);
    }

    private void drawRect(Canvas canvas) {
        int layerId = canvas.saveLayer(0, 0, canvas.getWidth(), canvas.getHeight(), null);
        canvas.drawColor(CLIP_FRAME_OUT_COLOR);
        canvas.drawRect(mClipRectF, mRectFPaint);
        canvas.restoreToCount(layerId);
    }

    private void drawFrame(Canvas canvas) {
        canvas.drawLine(mClipRectF.left, mClipRectF.top, mClipRectF.left, mClipRectF.bottom, mFrameLinePaint);
        canvas.drawLine(mClipRectF.left, mClipRectF.top, mClipRectF.right, mClipRectF.top, mFrameLinePaint);
        canvas.drawLine(mClipRectF.right, mClipRectF.top, mClipRectF.right, mClipRectF.bottom, mFrameLinePaint);
        canvas.drawLine(mClipRectF.left, mClipRectF.bottom, mClipRectF.right, mClipRectF.bottom, mFrameLinePaint);
    }

    private RectF getClipMatrixRectF() {
        RectF matrixRectF = new RectF();
        float[] matrixValues = new float[9];
        mMatrix.getValues(matrixValues);
        matrixRectF.left = matrixValues[Matrix.MTRANS_X];
        matrixRectF.top = matrixValues[Matrix.MTRANS_Y];
        matrixRectF.right = matrixValues[Matrix.MTRANS_X] + getDrawable().getIntrinsicWidth() * matrixValues[Matrix.MSCALE_X];
        matrixRectF.bottom = matrixValues[Matrix.MTRANS_Y] + getDrawable().getIntrinsicHeight() * matrixValues[Matrix.MSCALE_Y];

        RectF imageRectF = new RectF();
        imageRectF.setIntersect(matrixRectF, mClipRectF);
        imageRectF.offset(-matrixRectF.left, -matrixRectF.top);
        imageRectF.left = imageRectF.left / matrixValues[Matrix.MSCALE_X];
        imageRectF.top = imageRectF.top / matrixValues[Matrix.MSCALE_X];
        imageRectF.right = imageRectF.right / matrixValues[Matrix.MSCALE_X];
        imageRectF.bottom = imageRectF.bottom / matrixValues[Matrix.MSCALE_X];

        return imageRectF;
    }

    public Bitmap clip() {
        RectF imageRectF = getClipMatrixRectF();
        if (imageRectF.width() > 0 && imageRectF.height() > 0) {
            return Bitmap.createBitmap(((BitmapDrawable) getDrawable()).getBitmap(), (int) imageRectF.left, (int) imageRectF.top, (int) imageRectF.width(), (int) imageRectF.height());
        } else {
            return ((BitmapDrawable) getDrawable()).getBitmap();
        }
    }

}
