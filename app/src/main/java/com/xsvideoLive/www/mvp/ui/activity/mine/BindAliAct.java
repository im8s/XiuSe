package com.xsvideoLive.www.mvp.ui.activity.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.callback.TitleBarClickCallback;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.BindAliContract;
import com.xsvideoLive.www.mvp.presenter.BindAliPresenter;
import com.xsvideoLive.www.mvp.ui.view.TitleBarView;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.CountDownTimerUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.StringUtils;
import com.xsvideoLive.www.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import io.reactivex.annotations.Nullable;

public class BindAliAct extends BaseMvpActivity<BindAliPresenter> implements BindAliContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    TitleBarView titleBar;
    @BindView(R.id.et_wechat_num)
    EditText etWechatNum;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_send_code)
    TextView tvSendCode;
    @BindView(R.id.tv_bind)
    TextView tvBind;

    //倒计时
    private CountDownTimerUtils downTimerUtils;

    @Override
    public int setLayoutId() {
        return R.layout.activity_bind_ali;
    }

    @Override
    protected BindAliPresenter createPresenter() {
        return new BindAliPresenter();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        downTimerUtils = new CountDownTimerUtils(tvSendCode, Constant.WAIT_SMS_TIME, Constant.SMS_INTERVAL);
        titleBar.setOnClickCallback(this);
    }

    @OnTextChanged({R.id.et_wechat_num})
    public void bindChanged(Editable editable) {
        tvBind.setEnabled(etWechatNum.length() > 0 && etCode.length() == 4 && etName.length() > 0);
    }

    @OnTextChanged({R.id.et_name})
    public void nameChanged(Editable editable) {
        tvBind.setEnabled(etWechatNum.length() > 0 && etCode.length() == 4 && etName.length() > 0);
    }

    @OnTextChanged({R.id.et_code})
    public void codeChanged(Editable editable) {
        tvBind.setEnabled(etWechatNum.length() > 0 && etCode.length() == 4 && etName.length() > 0);
    }

    @OnClick({R.id.tv_send_code, R.id.tv_bind})
    public void onClick(View view) {
        UserBean userInfo = SPUtils.getInstance().getUserInfo();
        String userPhone = userInfo.getUserPhone();
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (!TextUtils.isEmpty(userPhone)){
                    if (StringUtils.isPhoneNum(userPhone)) {
                        mPresenter.getCode(userPhone);
                        downTimerUtils.start();
                    } else {
                        ToastUtils.showShort(getResources().getString(R.string.input_phone));
                    }
                } else {
                    ToastUtils.showShort("请绑定手机号码");
                }
                break;
            case R.id.tv_bind:
                String weChatNum = etWechatNum.getText().toString().trim();
                String userName = etName.getText().toString().trim();
                String mCode = etCode.getText().toString().trim();
                mPresenter.bindWeChat(weChatNum,userName,mCode);
                break;
        }
    }

    @Override
    public void codeSuccess() {
        ToastUtils.showShort(getResources().getString(R.string.send_sms_success));
    }

    @Override
    public void codeError(String msg) {
        ToastUtils.showShort(msg);
        downTimerUtils.cancle();
        tvSendCode.setText("获取验证码");
    }

    @Override
    public void onWeChatSuccess(int status) {
        if (status == 1){
            ToastUtils.showShort("绑定成功");
            finish();
        } else {
            ToastUtils.showShort("绑定失败");
        }
    }

    @Override
    public void onWeChatError(String msg) {
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