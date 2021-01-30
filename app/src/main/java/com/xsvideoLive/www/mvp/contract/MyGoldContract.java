package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public interface MyGoldContract {

    interface View extends BaseView {
        /**
         * 获取到金币数量
         * @param goldNum
         */
        void onSuccess(Double goldNum);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {
        /**
         * 获取金币数量
         */
        void getMyGoldNum();
    }

    interface Model {
        HttpObservable<BaseResponse<Double>> getMyGoldNum();
    }
}
