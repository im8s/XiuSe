package com.xsvideoLive.www.mvp.ui.activity.login;


import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.EventConstant;
import com.xsvideoLive.www.mvp.contract.RegisterAndLoginContract;
import com.xsvideoLive.www.mvp.presenter.RegisteAndLoginPresenter;
import com.xsvideoLive.www.net.bean.Event;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.CountDownTimerUtils;
import com.xsvideoLive.www.utils.StringUtils;
import com.xsvideoLive.www.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class RegisteAndLoginAct extends BaseMvpActivity<RegisteAndLoginPresenter> implements RegisterAndLoginContract.View {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_captcha)
    EditText etCaptcha;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_logon)
    TextView tvLogon;
    @BindView(R.id.yhxieyi)
    TextView yhxieyi;

    //倒计时
    private CountDownTimerUtils downTimerUtils;

    private boolean isSend;

    @Override
    public int setLayoutId() {
        return R.layout.activity_registe_and_login;
    }


    @Override
    protected RegisteAndLoginPresenter createPresenter() {
        return new RegisteAndLoginPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        downTimerUtils = new CountDownTimerUtils(tvGetCode, Constant.WAIT_SMS_TIME, Constant.SMS_INTERVAL);
    }

    @OnTextChanged({R.id.et_phone_num})
    void phoneNumChanged(Editable editable) {
        if (!isSend) {
            tvGetCode.setEnabled(etPhoneNum.length() == 11);
        } else {
            tvGetCode.setEnabled(false);
        }
        tvGetCode.setAlpha(tvGetCode.isEnabled() ? 1f : 0.5f);
    }

    @OnTextChanged({R.id.et_phone_num, R.id.et_captcha})
    void login(Editable editable) {
        tvLogon.setEnabled(etPhoneNum.length() == 11 && etCaptcha.length() == 4);
        tvLogon.setAlpha(tvLogon.isEnabled() ? 1f : 0.5f);
    }

    @OnClick({R.id.iv_back, R.id.tv_get_code, R.id.tv_logon, R.id.yhxieyi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                String phone = etPhoneNum.getText().toString().trim();
                if (StringUtils.isPhoneNum(phone)) {
                    mPresenter.getCode(phone);
                    downTimerUtils.start();
                    isSend = true;
                } else {
                    ToastUtils.showShort(getResources().getString(R.string.input_phone));
                }

                break;
            case R.id.tv_logon:
                String phoneNum = etPhoneNum.getText().toString().trim();
                String captchaNum = etCaptcha.getText().toString().trim();
                mPresenter.register(phoneNum, captchaNum);
                break;
            case R.id.yhxieyi:
                ActStartUtils.webActStart(this,Constant.USER_AGREEMENT);
                break;
        }
    }

    @Override
    public void registerSuccess(String bean) {
        String phoneNumber = etPhoneNum.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.PHONE_NUM, phoneNumber);
        ActStartUtils.startAct(this, RegisterActivity.class, bundle);
    }

    @Override
    public void registerError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void codeSuccess() {
        ToastUtils.showShort(getResources().getString(R.string.send_sms_success));
    }

    @Override
    public void codeError(String msg) {
        ToastUtils.showShort(msg);
        downTimerUtils.cancle();
        tvGetCode.setText(getResources().getString(R.string.get_code_num));
        tvGetCode.setEnabled(true);
        isSend = false;
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
}
