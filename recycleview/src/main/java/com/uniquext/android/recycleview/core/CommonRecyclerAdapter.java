package com.uniquext.android.recycleview.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter implements View.OnClickListener, View.OnLongClickListener {
    protected List<T> mData;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;
    private boolean mItemClickable = true;

    public CommonRecyclerAdapter(@NonNull List<T> data) {
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(viewType), parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return getItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((CommonRecyclerHolder) holder).convert(position, mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    @LayoutRes
    protected abstract int getItemLayout(int viewType);

    protected abstract CommonRecyclerHolder<T> getItemViewHolder(View itemVIew, int viewType);



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


    public void setItemClickable(boolean itemClickable) {
        this.mItemClickable = itemClickable;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


    public static abstract class DebounceOnItemClickListener implements OnItemClickListener {
        private static final long DEFAULT_DELAY_INTERVAL = 300L;
        private long lastClickTime = 0;

        private final long delayInterval;


        public DebounceOnItemClickListener() {
            this(DEFAULT_DELAY_INTERVAL);
        }

        public DebounceOnItemClickListener(long delayInterval) {
            this.delayInterval = delayInterval;
        }

        @Override
        public void onItemClick(View v, int position) {
            if (System.currentTimeMillis() - lastClickTime > delayInterval) {
                doItemClick(v, position);
            }
            lastClickTime = System.currentTimeMillis();
        }

        public abstract void doItemClick(View v, int position);

    }
}