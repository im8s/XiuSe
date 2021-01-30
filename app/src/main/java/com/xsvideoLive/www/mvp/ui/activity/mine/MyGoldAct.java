package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.mvp.contract.MyGoldContract;
import com.xsvideoLive.www.mvp.presenter.MyGoldPresenter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MyGoldAct extends BaseMvpActivity<MyGoldPresenter> implements MyGoldContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.tv_gold_num)
    TextView tvGoldNum;

    @Override
    public int setLayoutId() {
        return R.layout.activity_my_gold;
    }

    @Override
    protected MyGoldPresenter createPresenter() {
        return new MyGoldPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        titleBar.setOnClickCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMyGoldNum();
    }

    @OnClick({R.id.tv_exchange, R.id.rl_recharge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_exchange:
                ActStartUtils.startAct(this, GoldExchangeAct.class);
                break;
            case R.id.rl_recharge:
                ActStartUtils.startAct(this, GoldRechargeAct.class);
                break;
        }
    }

    @Override
    public void onSuccess(Double goldNum) {
        tvGoldNum.setText(String.format("%.1f",goldNum));
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}
