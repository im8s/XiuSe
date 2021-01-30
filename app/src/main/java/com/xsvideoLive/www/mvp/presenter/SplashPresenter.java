package com.xsvideoLive.www.mvp.presenter;

import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.mvp.contract.SplashContract;

public class SplashPresenter extends BasePresenter<SplashContract.View>  implements SplashContract.Presenter{

    public SplashPresenter() {

    }


    @Override
    public void startMainActivity() {
            getView().startMainActivity();
    }
}
