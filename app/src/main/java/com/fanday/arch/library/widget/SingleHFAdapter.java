package com.fanday.arch.library.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 带有头布局和脚布局的adapter
 */
public class SingleHFAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static final int HEAD_TYPE = 0;
    public static final int ITEM_TYPE = 1;
    public static final int FOOTER_TYPE = 2;

    private int HF_COUNT = 0;

    /**
     * 头布局的holder
     */
    private HeadAndFooterHolder headHolder;
    private HeadAndFooterHolder footerHolder;
    RecyclerView.Adapter adapter;

    /**
     * @param headView   头布局
     * @param adapter    recyclerView的适配器
     * @param footerView 脚布局
     */
    public SingleHFAdapter(View headView, RecyclerView.Adapter adapter, View footerView) {
        if (headView != null) {
            this.headHolder = new HeadAndFooterHolder(headView);
            HF_COUNT++;
        }
        if (footerView != null) {
            this.footerHolder = new HeadAndFooterHolder(footerView);
            HF_COUNT++;
        }
        this.adapter = adapter;
    }

    /**
     * @param headView 头布局
     * @param adapter
     */
    public SingleHFAdapter(View headView, RecyclerView.Adapter adapter) {
        this(headView, adapter, null);
    }

    /**
     * @param adapter
     * @param footerView 脚布局
     */
    public SingleHFAdapter(RecyclerView.Adapter adapter, View footerView) {
        this(null, adapter, footerView);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE)
            return headHolder;
        if (viewType == FOOTER_TYPE)
            return footerHolder;
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type != HEAD_TYPE && type != FOOTER_TYPE) {
            if (headHolder != null) position--;
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (headHolder != null && position == 0)
            return HEAD_TYPE;
        if (footerHolder != null && position == getItemCount() - 1)
            return FOOTER_TYPE;
        return ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount() + HF_COUNT;
    }


    public void notifyDataChanged() {
        adapter.notifyDataSetChanged();
        this.notifyDataSetChanged();
    }

    class HeadAndFooterHolder extends RecyclerView.ViewHolder {
        public HeadAndFooterHolder(View itemView) {
            super(itemView);
        }
    }
}
