package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.SignDayEntity;
import com.xsvideoLive.www.net.bean.SignRewardEntity;

import java.util.List;

public interface SignInContract {

    interface View extends BaseView {

        void onSignRewardSuccess(List<SignRewardEntity> results);

        void onSignDaySuccess(SignDayEntity signDayResult);

        void onSginSuccess(Integer status);

        void onError(String msg);

    }

    interface Presenter {

       void getSignReward();

       void getSignDay();

       void sign();

    }

    interface Model {

        HttpObservable<BaseResponse<List<SignRewardEntity>>> getSignReward();

        HttpObservable<BaseResponse<SignDayEntity>> getSignDay();

        HttpObservable<BaseResponse<Integer>> sign();

    }

}
