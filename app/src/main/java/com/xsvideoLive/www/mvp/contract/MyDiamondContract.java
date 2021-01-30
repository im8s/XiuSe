package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public interface MyDiamondContract {

    interface View extends BaseView {

        /**
         * 成功获取钻石数量
         * @param diamondNum
         */
        void onDiamondSuccess(Double diamondNum);

        /**
         * 请求接口失败
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {

        void replace(BaseMvpFragment fragment);

        /**
         * 获取钻石数量
         */
        void getMyDiamondNum();

    }

    interface Model {
        /**
         * 获取钻石数量
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyDiamondNum();
    }
}
