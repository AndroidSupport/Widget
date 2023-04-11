package com.uniquext.android.widget.layout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ListViewCompat;

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
 * ━━━━━━━━━━感觉萌萌哒━━━━━━━━━━
 *
 * @author UniqueXT
 * @version 1.0
 * @date 2022/3/4 - 17:07
 */
public class RefreshLayout extends ViewGroup implements NestedScrollingParent {

    /**
     * 目标滚动控件
     * ps:RecyclerView
     */
    private View mTarget = null;
    /**
     * 刷新头
     * 应该是一个与父布局等大的group
     * header里面的layout为底部对齐
     */
    private View mHeader = null;

    /**
     * header在viewGroup中的索引
     */
    private int mHeaderIndex = -1;


    private NestedScrollingParentHelper mNestedScrollingParentHelper;



    public RefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public RefreshLayout(@NonNull Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        createHeader(context);
        setChildrenDrawingOrderEnabled(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!ensureTarget()) return;

        final int widthSize = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        final int heightSize = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        mTarget.measure(
                MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY)
        );
        mHeader.measure(
                MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY)
        );

        //  计算mHeader正确索引
        for (int i = 0; i < getChildCount(); ++i) {
            if (getChildAt(i) == mHeader) {
                mHeaderIndex = i;
                break;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0 || !ensureTarget()) return;

        final int headerLeft = getPaddingLeft();
        final int headerTop = getPaddingTop();
        final int headerWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        final int headerHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        mHeader.layout(headerLeft, headerTop - headerHeight, headerLeft + headerWidth, headerTop);
        mTarget.layout(headerLeft, headerTop, headerLeft + headerWidth, headerTop + headerHeight);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mHeaderIndex < 0) return i;
        final int position = indexOfChild(mHeader);
        if (i < position) {
            return i;
        } else if (position == i) {
            return childCount - 1;
        } else {
            return i - 1;
        }
    }


    private final int INVALID_POINTER = -1;

    private int mActivePointerId = INVALID_POINTER;


    /**
     * 滑动倍率
     */
    private final float DRAG_RATE = 0.5f;

    private boolean mIsBeingDragged = false;

    private int mTouchSlop;
    /**
     * 接触屏幕时的Y值
     */
    private float mInitialDownY = 0;
    /**
     * 初始下滑时的Y值
     */
    private float mInitialMotionY = 0;

    private boolean mRefreshing = false;

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes) {
        //  false 不会传递事件
        return isEnabled() && !mRefreshing
                && (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        mNestedScrollInProgress = true;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        Log.e("####", "onNestedPreScroll " + dy + " # " + mTotalUnconsumed + " # ");
        //  dy > 0 上滑 并且 父控件有滑动消费
        if (dy > 0 && mTotalUnconsumed > 0) {
            if (dy > mTotalUnconsumed) {
                consumed[1] = dy - (int) mTotalUnconsumed; // SwipeRefreshLayout 就吧子视图位消费的距离全部消费了。
                mTotalUnconsumed = 0;
            } else {
                mTotalUnconsumed -= dy; // 消费的 y 轴的距离
                consumed[1] = dy;
            }
            moveSpinner(mTotalUnconsumed);
        }
    }

    private float mTotalUnconsumed;
    private boolean mNestedScrollInProgress;
    private final int[] mParentOffsetInWindow = new int[2];
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        //  下拉并且子view到顶不能滑动
        if (dy < 0 && !canChildScrollUp()) {
            mTotalUnconsumed += Math.abs(dy);
            moveSpinner(mTotalUnconsumed);
        }
    }

    @Override
    public void onStopNestedScroll(@NonNull View child) {
        mNestedScrollInProgress = false;
        if (mTotalUnconsumed > 0) {
            finish(mTotalUnconsumed);
            mTotalUnconsumed = 0;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isEnabled() || canChildScrollUp()  || mRefreshing || mNestedScrollInProgress) {
            // Fail fast if we're not in a state where a swipe is possible
            return false;
        }

        int pointerIndex;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mIsBeingDragged = false;

                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                mInitialDownY = ev.getY(pointerIndex);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER) {
                    return false;
                }

                pointerIndex = ev.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }
                final float y = ev.getY(pointerIndex);
                startDragging(y);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                break;
        }
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mIsBeingDragged = false;
                mActivePointerId = event.getPointerId(0);
                break;

            case MotionEvent.ACTION_MOVE:
                pointerIndex = event.findPointerIndex(mActivePointerId);
                if (pointerIndex < 0) {
                    return false;
                }

                final float y = event.getY(pointerIndex);
                startDragging(y);
                if (mIsBeingDragged) {
                    final float overscrollTop = (y - mInitialMotionY) * DRAG_RATE;
                    if (overscrollTop > 0) {
                        moveSpinner(overscrollTop);
                    } else {
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                    pointerIndex = event.findPointerIndex(mActivePointerId);
                    if (pointerIndex < 0) {
                        return false;
                    }

                    if (mIsBeingDragged) {
                        mIsBeingDragged = false;
                        final float overscrollTop = (event.getY(pointerIndex) - mInitialMotionY) * DRAG_RATE;
                        finish(overscrollTop);
                    }
                    mActivePointerId = INVALID_POINTER;
                    break;
        }
        return true;
    }

    public boolean canChildScrollUp() {
        if (mTarget instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mTarget, -1);
        }
        return mTarget.canScrollVertically(-1);
    }

    private void startDragging(final float y) {
        final float yDiff = y - mInitialDownY;
        if (yDiff > mTouchSlop && !mIsBeingDragged) {
            mInitialMotionY = mInitialDownY + mTouchSlop;
            mIsBeingDragged = true;
        }
    }

    private void moveSpinner(float overscrollTop) {
        mHeader.setTranslationY(overscrollTop);
        mTarget.setTranslationY(overscrollTop);
    }

    private void finish(float overscrollTop) {
        // TODO: 2022/3/11 如果大于 mMinDragDistance 则表明可以刷新
        //  否则不允许刷新 直接回弹
        //  mMinDragDistance 判断标准   小于展示 向下   等于大于向上松手  等于回到mMinDragDistance loading
        //  mTotalDragDistance 最大区域 大于这个不再滑动
        mHeader.animate().translationY(0).start();
        mTarget.animate().translationY(0).start();
    }

    private void createHeader(@NonNull Context context) {
        mHeader = new View(context);
        // TODO: 2022/3/11  xml 增加属性背景色
        mHeader.setBackgroundColor(Color.BLACK);
        addView(mHeader);
    }

    private boolean ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mHeader)) {
                    mTarget = child;
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }


}
