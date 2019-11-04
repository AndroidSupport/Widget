package com.uniquext.android.widget.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

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
 * @date 2018/12/4  18:14
 * 适合少量数据的List 比如menu
 */
public class LightList extends LinearLayout {

    /**
     * 下划线颜色值
     */
    private final int DIVIDING_COLOR = Color.parseColor("#d6d6d6");
    /**
     * 适配器
     */
    private BaseAdapter mAdapter;
    /**
     * item点击事件
     */
    private OnItemClickListener mOnItemClickListener;
    /**
     * 数据绑定
     */
    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            removeAllViews();
            for (int i = 0; i < mAdapter.getCount(); i++) {
                createItemView(i);
            }
        }

        @Override
        public void onInvalidated() {
            removeAllViews();
        }
    };

    public LightList(Context context) {
        this(context, null);
    }

    public LightList(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LightList(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
    }

    public BaseAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 设置适配器
     *
     * @param adapter 适配器
     */
    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
        mAdapter.registerDataSetObserver(mDataSetObserver);
        removeAllViews();
        int itemCount = mAdapter.getCount();
        for (int i = 0; i < itemCount; i++) {
            createItemView(i);
        }
    }

    /**
     * 创建item
     *
     * @param position 下标
     */
    private void createItemView(final int position) {
        View view = mAdapter.getView(position, null, this);
        view.setOnClickListener(v -> {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, position, mAdapter.getItemId(position));
            }
        });
        addView(view);
    }

    /**
     * 设置item监听
     *
     * @param onClickListener 监听接口
     */
    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.mOnItemClickListener = onClickListener;
    }

    /**
     * Item点击事件接口
     */
    public interface OnItemClickListener {
        /**
         * item点击回调
         *
         * @param view     view
         * @param position 下标
         * @param id       uid标识符
         */
        void onItemClick(View view, int position, long id);
    }

}
