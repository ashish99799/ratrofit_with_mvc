package com.github.ratrofitwithmvc.wegates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.github.ratrofitwithmvc.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

public class AvLoadingHeader extends RelativeLayout implements RefreshHeader {

    private LottieAnimationView lottieAnimation;

    public AvLoadingHeader(Context context) {
        super(context);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflate = mInflater.inflate(R.layout.loading_header, this);
        lottieAnimation = (LottieAnimationView) inflate.findViewById(R.id.my_loading_view);
    }

    public void playAnimation() {
        if (lottieAnimation == null) {
            return;
        }
        if (lottieAnimation.isAnimating()) {
            return;
        }
        lottieAnimation.setAnimation("loading.json");
        lottieAnimation.playAnimation();
    }

    public void cancelAnimation() {
        if (lottieAnimation == null) {
            return;
        }
        if (lottieAnimation.isAnimating()) {
            lottieAnimation.cancelAnimation();
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
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(@ColorInt int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
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
//        LogUtils.i("xiaowu==" + oldState.name() + "       " + newState.name());
        if (oldState == RefreshState.None && newState == RefreshState.PullDownToRefresh) {
            playAnimation();
        } else if (oldState == RefreshState.RefreshFinish && newState == RefreshState.None) {
            cancelAnimation();
        }
    }

}
