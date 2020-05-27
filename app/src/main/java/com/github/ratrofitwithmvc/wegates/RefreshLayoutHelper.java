package com.github.ratrofitwithmvc.wegates;

import android.content.Context;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

public class RefreshLayoutHelper  {

    public static void init(SmartRefreshLayout mSmartRefresh) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();
        mSmartRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();
            }
        });
    }

    public static void init(SmartRefreshLayout mSmartRefresh, RefreshHeader headerView, OnRefreshListener listener) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();
        init(mSmartRefresh, headerView, null, listener, null);
    }


    public static void init(SmartRefreshLayout mSmartRefresh, RefreshFooter footerView, OnLoadmoreListener listener) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();
        init(mSmartRefresh, null, footerView, null, listener);
    }


    public static void init(SmartRefreshLayout mSmartRefresh,
                            RefreshHeader headerView, RefreshFooter footerView,
                            OnRefreshListener onRefreshListener, OnLoadmoreListener onLoadmoreListener) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();
        if (headerView != null) {
            mSmartRefresh.setRefreshHeader(headerView);
        }
        if (footerView != null) {
            mSmartRefresh.setRefreshFooter(footerView);
        }
        if (onRefreshListener != null) {
            mSmartRefresh.setOnRefreshListener(onRefreshListener);
        }
        if (onLoadmoreListener != null) {
            mSmartRefresh.setOnLoadmoreListener(onLoadmoreListener);
        }
        initRefreshLayout(mSmartRefresh);
    }


    public static void initRefreshLayout(RefreshLayout layout) {
        layout.setEnableScrollContentWhenLoaded(false);
        layout.setDragRate(2);
        layout.setReboundDuration(500);
    }


    public static final void initToRefreshStyle(SmartRefreshLayout mSmartRefresh, OnRefreshListener listener) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();

        init(mSmartRefresh, new AvLoadingHeader(mSmartRefresh.getContext()), listener);
    }


    public static final void initToLoadMoreStyle(SmartRefreshLayout mSmartRefresh, OnLoadmoreListener listener) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();
        Context context = mSmartRefresh.getContext();
        init(mSmartRefresh, new AvLoadingFooter(context), listener);
    }

    public static final void initToZoomImageStyle(SmartRefreshLayout mSmartRefresh) {
        initToZoomImageStyle(mSmartRefresh, 0);
    }

    public static final void initToZoomImageStyle(SmartRefreshLayout mSmartRefresh, int dip) {
        if (mSmartRefresh == null) throw new IllegalArgumentException();
        ZoomImageRefreshHeader header = new ZoomImageRefreshHeader(mSmartRefresh.getContext());
        if (dip != 0) {
            ViewGroup.LayoutParams params = header.getLayoutParams();
            params.height = DensityUtils.dip2px(header.getContext(), dip);

        }
        mSmartRefresh.setHeaderMaxDragRate(1.7f);
        RefreshLayoutHelper.init(mSmartRefresh, header, new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
    }
}
