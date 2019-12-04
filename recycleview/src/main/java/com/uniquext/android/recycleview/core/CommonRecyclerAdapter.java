package com.uniquext.android.recycleview.core;

import android.content.res.Resources;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
 * @date 2018/12/7  15:51
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerHolder> implements View.OnClickListener, View.OnLongClickListener {

    /**
     * 数据源
     */
    protected List<T> mData;
    /**
     * 布局文件
     */
    protected SparseArray<Integer> mItemLayoutId;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    private boolean mItemClickable = true;

    public CommonRecyclerAdapter(@NonNull List<T> data, @LayoutRes int... itemLayoutIds) {
        mData = data;
        mItemLayoutId = new SparseArray<>();
        for (int itemLayoutId : itemLayoutIds) {
            mItemLayoutId.put(itemLayoutId, itemLayoutId);
        }
    }

    @NonNull
    @Override
    public CommonRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        try {
            view = LayoutInflater.from(parent.getContext()).inflate(mItemLayoutId.get(viewType), parent, false);
        } catch (NullPointerException | Resources.NotFoundException e) {
            view = new View(parent.getContext());
        }
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new CommonRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonRecyclerHolder holder, final int position) {
        holder.itemView.setTag(holder.itemView.getId(), position);
        convert(holder, position, mData.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mItemLayoutId.keyAt(0);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    protected abstract void convert(CommonRecyclerHolder holder, int position, T item);

    public void setItemClickable(boolean itemClickable) {
        this.mItemClickable = itemClickable;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mItemClickable && mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, Integer.valueOf(String.valueOf(v.getTag(v.getId()))));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mItemClickable && mOnItemLongClickListener != null) {
            mOnItemLongClickListener.onItemLongClick(v, Integer.valueOf(String.valueOf(v.getTag(v.getId()))));
        }
        return true;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

}
