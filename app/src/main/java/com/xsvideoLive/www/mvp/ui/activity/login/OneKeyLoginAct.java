package com.xsvideoLive.www.mvp.ui.activity.login;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.EventConstant;
import com.xsvideoLive.www.constant.LoginStatus;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.OneKeyLogonContract;
import com.xsvideoLive.www.mvp.presenter.OneKeyLoginPresenter;
import com.xsvideoLive.www.net.bean.Event;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import butterknife.BindView;
import butterknife.OnClick;


public class OneKeyLoginAct extends BaseMvpActivity<OneKeyLoginPresenter> implements OneKeyLogonContract.View, IUIKitCallBack {

    @BindView(R.id.singin)
    Button singin;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.yinshi)
    TextView yinshi;
    @BindView(R.id.yhxieyi)
    TextView yhxieyi;

    private String phoneNumber;
    private String maskNumber;

    private LogonResult mLogonResult;

    @Override
    protected OneKeyLoginPresenter createPresenter() {
        return new OneKeyLoginPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.activity_one_key_logon;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");

        maskNumber = phoneNumber.substring(0, 3) + "****" + phoneNumber.substring(7, phoneNumber.length());
        tvPhoneNum.setText(maskNumber);
    }

    @Override
    public void onSuccess(LogonResult data) {

        mLogonResult = data;

        //这里需要判断是否为登录成功，登录成功后需要获取sig登录腾讯云IM
        if (LoginStatus.getValue(mLogonResult.getStatus()).equals(LoginStatus.SUCCESS)) {
            SPUtils.getInstance().put(SpConstant.APP_TOKEN, mLogonResult.getToken());
            mPresenter.getUserSig(data);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.PHONE_NUM, phoneNumber);
            ActStartUtils.loginStartAct(this, data, bundle);
        }


    }

    @Override
    public void sigSuccess(LogonResult data, String sig) {

        if (!TextUtils.isEmpty(sig)) {
            mPresenter.loginIm(data, sig, this);
        } else {
            ToastUtils.showShort(getString(R.string.login_error));
        }

    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }


    @OnClick({R.id.singin, R.id.yinshi, R.id.yhxieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.singin:
                mPresenter.oneKeyLogin(phoneNumber);
//                mPresenter.oneKeyLogin("18811398512");
//                mPresenter.oneKeyLogin("15342597207");
                break;
            case R.id.yinshi:
                ActStartUtils.webActStart(this,Constant.PRIVACY);
                break;
            case R.id.yhxieyi:
                ActStartUtils.webActStart(this,Constant.USER_AGREEMENT);
                break;
        }
    }

    @Override
    public boolean isRegisterEventBus() {
        return true;
    }

    @Override
    public void onEventBusCome(Event event) {
        if (event.getCode() == EventConstant.LOGIN_SUCCESS) {
            finish();
        }
    }

    /**
     * 腾讯云IM 登录成功
     *
     * @param data
     */
    @Override
    public void onSuccess(Object data) {

        if (LoginStatus.getValue(mLogonResult.getStatus()).equals(LoginStatus.SUCCESS)) {
            String strUser = new Gson().toJson(mLogonResult.getUser());
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
            SPUtils.getInstance().put(SpConstant.APP_TOKEN, mLogonResult.getToken());
            ActStartUtils.loginStartAct(this, mLogonResult, null);
        }


    }

    /**
     * 腾讯云登录失败
     *
     * @param module
     * @param errCode
     * @param errMsg
     */
    @Override
    public void onError(String module, int errCode, String errMsg) {
        ToastUtils.showShort(getString(R.string.login_error));
    }
}
