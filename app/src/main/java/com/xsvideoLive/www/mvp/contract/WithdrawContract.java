package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BindEntity;
import com.xsvideoLive.www.net.bean.NewGoldResult;

import java.util.List;

public interface WithdrawContract {


    interface View extends BaseView {

        /**
         * 请求提现列表成功
         * @param results
         */
        void cashSuccess(List<NewGoldResult> results);

        /**
         * 成功获取钻石数量
         * @param diamondNum
         */
        void onDiamondSuccess(Double diamondNum);

        /**
         * 请求失败
         * @param msg
         */
        void onError(String msg);

        void onBoundSuccess(BindEntity result);

        void onBoundError(String msg);

        void onWithdrawSuccess(int status);

        void onWithdrawError(String msg);
    }

    interface Presenter {
        /**
         * 请求提现列表
         */
        void getCashList();

        void getBoundPay();

        void withdraw(String cashConfigId);

        /**
         * 获取钻石数量
         */
        void getMyDiamondNum();

    }

    interface Model {
        /**
         * 请求提现列表接口
         * @return
         */
        HttpObservable<BaseResponse<List<NewGoldResult>>> getCashList();

        HttpObservable<BaseResponse<BindEntity>> getBoundPay();

        HttpObservable<BaseResponse<Integer>> withdraw(String cashConfigId);

        /**
         * 获取钻石数量
         * @return
         */
        HttpObservable<BaseResponse<Double>> getMyDiamondNum();

    }
}
