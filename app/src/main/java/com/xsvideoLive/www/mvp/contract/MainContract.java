package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public interface MainContract {

    interface View extends BaseView {

        void teenSuccess(String status);

        void onError(String msg);

    }

    interface Presenter {

        /**
         * 退出房间
         */
        void exitRoom(String roomId);

        void getTeensStatus(String userId);
    }

    interface Model {

        HttpObservable<BaseResponse<Integer>> exitRoom(String roomId);

        /**
         * 青少年模式
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<String>> getTeensStatus(String userId);

    }
}
