package com.github.ratrofitwithmvc.wegates;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class ZoomImageRefreshHeader extends ImageView implements RefreshHeader {

    public ZoomImageRefreshHeader(Context context) {
        this(context, null);
    }

    public ZoomImageRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomImageRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setScaleType(ScaleType.CENTER_CROP);
        if (attrs == null) {
            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Scale;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        kernel.setStateRefresing();
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        return 0;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }
}
