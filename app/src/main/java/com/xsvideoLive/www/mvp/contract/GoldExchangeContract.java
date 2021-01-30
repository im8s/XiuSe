package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public interface GoldExchangeContract {

    interface View extends BaseView {

        /**
         * 成功获取金币数量
         *
         * @param goldNum
         */
        void myGoldSuccess(Double goldNum);

        /**
         * 成功获取钻石数量
         *
         * @param diamondNum
         */
        void onDiamondSuccess(Double diamondNum);

        /**
         * 钻石兑换成功
         * @param status
         */
        void onChangeSuccess(Integer status);

        /**
         * 请求接口失败
         *
         * @param msg
         */
        void onError(String msg);
    }

    interface Presenter {

        /**
         * 获取金币数量
         */
        void getMyGoldNum();

        /**
         * 获取钻石数量
         */
        void getMyDiamondNum();

        /**
         * 金币兑换
         *
         * @param jbNum
         */
        void goldChange(String jbNum);

    }

    interface Model {
        /**
         * 获取金币数量
         *
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyGoldNum();

        /**
         * 获取钻石数量
         *
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyDiamondNum();

        /**
         * 金币兑换
         *
         * @param coinNumber
         */
        HttpObservable<BaseResponse<Integer>> goldChange(String coinNumber);
    }

}
