package com.xsvideoLive.www.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.SplashContract;
import com.xsvideoLive.www.mvp.presenter.SplashPresenter;
import com.xsvideoLive.www.mvp.ui.activity.login.LoginActivity;
import com.xsvideoLive.www.mvp.ui.activity.main.MainActivity;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.AppUtils;
import com.xsvideoLive.www.utils.SPUtils;

import io.reactivex.annotations.Nullable;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements SplashContract.View {


    private String token;

    @Override
    public int setLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        mPresenter.attachView(this);
        mPresenter.startMainActivity();
        token = SPUtils.getInstance().getString(SpConstant.APP_TOKEN);
    }

    @Override
    protected SplashPresenter createPresenter() {
        return new SplashPresenter();
    }


    @Override
    public void startMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String version = SPUtils.getInstance().getString(Constant.VERSION);
                String appVersionName = AppUtils.getAppVersionName(SplashActivity.this);
                if (TextUtils.isEmpty(version) || !version.equals(appVersionName)) {
                    ActStartUtils.startAct(SplashActivity.this, GuideAct.class);
                } else {

                    if (TextUtils.isEmpty(token) || SPUtils.getInstance().getUserInfo() == null) {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                    } else {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));

                    }
                }
                finish();


            }
        }, 1500);
    }
}
