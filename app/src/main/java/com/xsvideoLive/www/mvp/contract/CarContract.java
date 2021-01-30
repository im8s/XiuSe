package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.DeckResult;

public interface CarContract  {

    interface View extends BaseView {

        /**
         * 获取历史接口请求成功
         * @param deckResult
         */
        void carSuccess(DeckResult deckResult);

        /**
         * 请求失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 请求获取座驾历史
         * @param userId
         */
        void carRecord(String userId);

    }

    interface Model {
        /**
         * 个人资料·座驾列表
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<DeckResult>> carRecord(String userId);
    }

}
