package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.AlipayTradeAppPayResponse;
import com.xsvideoLive.www.net.bean.PayResultEntity;

import butterknife.BindView;

public class PayResultAct extends BaseMvpActivity implements TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView mTitleBar;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    @BindView(R.id.tv_time)
    TextView mTvTime;

    @Override
    public int setLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitleBar.setOnClickCallback(this);
        Bundle extras = getIntent().getExtras();
        String payResult = extras.getString(Constant.PAY_RESULT);
        PayResultEntity payResultEntity = new Gson().fromJson(payResult, PayResultEntity.class);
        if (payResultEntity != null) {
            AlipayTradeAppPayResponse alipay_trade_app_pay_response = payResultEntity.getAlipay_trade_app_pay_response();
            setPayResult(alipay_trade_app_pay_response);
        }

    }

    private void setPayResult(AlipayTradeAppPayResponse entity) {
        mTvMoney.setText(entity.getTotal_amount());
        mTvTime.setText(entity.getTimestamp());
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public void titleRightClick(int status) {

    }
}