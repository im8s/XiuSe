package com.xsvideoLive.www.mvp.ui.activity.mine;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.mvp.contract.BindPayContract;
import com.xsvideoLive.www.mvp.presenter.BindPayPresenter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.utils.ActStartUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.Nullable;

public class BindPayAct extends BaseMvpActivity<BindPayPresenter> implements BindPayContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;

    @Override
    public int setLayoutId() {
        return R.layout.activity_bind_pay;
    }

    @Override
    protected BindPayPresenter createPresenter() {
        return new BindPayPresenter();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);

    }

    @SuppressLint("NonConstantResourceId")
    @OnClick({R.id.con_bind_wx, R.id.con_bind_zfb, R.id.con_bind_yhk})
    public void onClicn(View view) {
        switch (view.getId()) {
            case R.id.con_bind_wx:
                ActStartUtils.startAct(this,BindWeChatAct.class);
                break;
            case R.id.con_bind_zfb:
                ActStartUtils.startAct(this,BindAliAct.class);
                break;
            case R.id.con_bind_yhk:
                ActStartUtils.startAct(this,BindBlackAct.class);
                break;
        }
    }


    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}