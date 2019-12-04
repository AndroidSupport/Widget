package com.uniquext.android.recycleview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.uniquext.android.recycleview.core.CommonRecyclerAdapter;
import com.uniquext.android.recycleview.core.CommonRecyclerHolder;

import java.util.List;

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
 * @date 2018/12/10  12:21
 */
public abstract class CommonRecycleLoadAdapter<T> extends CommonRecyclerAdapter<T> {

    private static final int END = Integer.MAX_VALUE;

    private View mLoadMoreView;
    private boolean mComplete = false;
    private OnLoadMoreListener mOnLoadMoreListener;

    public CommonRecycleLoadAdapter(@NonNull List<T> data, int itemLayoutId) {
        super(data, itemLayoutId);
    }

    public void setLoadMoreView(@NonNull View loadMoreView, @NonNull OnLoadMoreListener loadMoreListener) {
        this.mLoadMoreView = loadMoreView;
        this.mOnLoadMoreListener = loadMoreListener;
    }

    public void load(){
        mOnLoadMoreListener.onLoadMoreRequested();
    }

    public void openLoadMore() {
        if (hasLoadMoreView() && !mComplete && !mData.isEmpty()) {
            load();
        }
    }

    public void loadComplete() {
        this.mComplete = true;
        mOnLoadMoreListener.onLoadComplete();
    }

    @NonNull
    @Override
    public CommonRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == END) {
            return new CommonRecyclerHolder(mLoadMoreView);
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerHolder holder, int position) {
        if (isLoadMoreView(position)) {
            openLoadMore();
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return isLoadMoreView(position) ? END : super.getItemViewType(position);
    }

    @Deprecated
    @Override
    public int getItemCount() {
        return super.getItemCount() + (hasLoadMoreView() ? 1 : 0);
    }

    public int getDataItemCount() {
        return super.getItemCount();
    }

    private boolean hasLoadMoreView() {
        return mLoadMoreView != null;
    }

    private boolean isLoadMoreView(int position) {
        return position >= getDataItemCount();
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();

        void onLoadComplete();
    }
}
