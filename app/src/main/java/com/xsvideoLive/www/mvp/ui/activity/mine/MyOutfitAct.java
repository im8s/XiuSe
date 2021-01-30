package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.mvp.contract.MyOutfitConstract;
import com.xsvideoLive.www.mvp.presenter.MyOutfitPresenter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

public class MyOutfitAct extends BaseMvpActivity<MyOutfitPresenter> implements MyOutfitConstract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.mag_index)
    MagicIndicator magIndex;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_outfit;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);


        mPresenter.initFragments(viewpager,magIndex);
    }

    @Override
    protected MyOutfitPresenter createPresenter() {
        return new MyOutfitPresenter();
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}
