package com.xsvideoLive.www.mvp.ui.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.EventConstant;
import com.xsvideoLive.www.constant.LoginStatus;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.AccountContract;
import com.xsvideoLive.www.mvp.presenter.AccountPresenter;
import com.xsvideoLive.www.net.bean.Event;
import com.xsvideoLive.www.net.bean.LogonResult;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.StringUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AccountLoginAct extends BaseMvpActivity<AccountPresenter> implements AccountContract.View, IUIKitCallBack {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.tv_logon)
    TextView tvLogon;

    private LogonResult mLogonResult;

//    private boolean isClickLogin = false;

    @Override
    public int setLayoutId() {
        return R.layout.activity_account_login;
    }

    @Override
    protected AccountPresenter createPresenter() {
        return new AccountPresenter();
    }

    @OnTextChanged({R.id.et_phone_num, R.id.et_captcha})
    void loginChanged(Editable editable) {
        tvLogon.setEnabled(etPhoneNum.length() == 11 && etCaptcha.length() >= 1);
        tvLogon.setAlpha(tvLogon.isEnabled() ? 1f : 0.5f);
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
    }

    @OnClick({R.id.iv_back, R.id.tv_logon, R.id.yinshi, R.id.yhxieyi,R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_logon:

//                if (!isClickLogin) {
//                    isClickLogin = true;
                    String phoneNum = etPhoneNum.getText().toString().trim();
                    String psd = etCaptcha.getText().toString().trim();
                    if (StringUtils.isPhoneNum(phoneNum)) {
                        mPresenter.login(phoneNum, psd);
                        tvLogon.setEnabled(false);
                    } else {
                        ToastUtils.showShort(getResources().getString(R.string.input_phone));
                    }
//                }
                break;
            case R.id.yinshi:
                ActStartUtils.webActStart(this,Constant.PRIVACY);
                break;
            case R.id.yhxieyi:
                ActStartUtils.webActStart(this,Constant.USER_AGREEMENT);
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisteAndLoginAct.class));
                break;
        }
    }

    @Override
    public void loginSuccess(LogonResult bean) {

        mLogonResult = bean;

        //这里需要判断是否为登录成功，登录成功后需要获取sig登录腾讯云IM
        if (LoginStatus.getValue(mLogonResult.getStatus()).equals(LoginStatus.SUCCESS)) {
            SPUtils.getInstance().put(SpConstant.APP_TOKEN, mLogonResult.getToken());
            mPresenter.getUserSig(bean);

        } else {
            ActStartUtils.loginStartAct(this, bean, null);
            tvLogon.setEnabled(true);
        }


    }

    @Override
    public void sigSuccess(LogonResult data, String sig) {

        if (!TextUtils.isEmpty(sig)) {
            mPresenter.loginIm(data, sig, this);
        } else {
            ToastUtils.showShort(getString(R.string.login_error));
            tvLogon.setEnabled(true);
        }
    }

    @Override
    public void loginError(String msg) {

//        isClickLogin = false;
        ToastUtils.showShort(msg);

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

    @Override
    public void onSuccess(Object data) {

        if (LoginStatus.getValue(mLogonResult.getStatus()).equals(LoginStatus.SUCCESS)) {
            String strUser = new Gson().toJson(mLogonResult.getUser());
            SPUtils.getInstance().put(SpConstant.USER_INFO, strUser);
            SPUtils.getInstance().put(SpConstant.APP_TOKEN, mLogonResult.getToken());
            ActStartUtils.loginStartAct(this, mLogonResult, null);
            tvLogon.setEnabled(true);
        }

    }

    @Override
    public void onError(String module, int errCode, String errMsg) {
//        isClickLogin = false;
        ToastUtils.showShort(getString(R.string.login_error));
    }
}
