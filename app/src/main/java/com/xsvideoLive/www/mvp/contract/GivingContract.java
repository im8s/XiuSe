package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GiftRecordResult;

public interface GivingContract {

    interface View extends BaseView {

        /**
         * 获取礼物接口成功回调
         * @param result
         */
        void onSuccess(GiftRecordResult result);

        /**
         * 接口请求错误回调
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 礼物记录
         * @param userId
         */
        void giftRecord(String userId);

    }

    interface Model {

        /**
         * 获取礼物请求
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<GiftRecordResult>> giftRecord(String userId);

    }

}
