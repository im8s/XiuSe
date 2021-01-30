package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;

public interface SplashContract {

    interface View extends BaseView {
        void startMainActivity();

    }

    interface Presenter {
        void startMainActivity();
    }

    interface Model {

    }


}
