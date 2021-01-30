package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BindEntity;

public interface BoundPayContract {

    interface View extends BaseView {

        void onBoundSuccess(BindEntity result);

        void onBoundError(String msg);

    }

    interface Presenter {

        void getBoundPay();

    }

    interface Model {

        HttpObservable<BaseResponse<BindEntity>> getBoundPay();
    }

}
