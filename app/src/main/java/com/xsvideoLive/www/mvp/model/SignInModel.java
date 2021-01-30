package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.SignInContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.SignDayEntity;
import com.xsvideoLive.www.net.bean.SignRewardEntity;

import java.util.List;

public class SignInModel implements SignInContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<SignRewardEntity>>> getSignReward() {
        return HttpClient.getApi().getSignReward();
    }

    @Override
    public HttpObservable<BaseResponse<SignDayEntity>> getSignDay() {
        return HttpClient.getApi().getSignDay();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> sign() {
        return HttpClient.getApi().sign();
    }
}
