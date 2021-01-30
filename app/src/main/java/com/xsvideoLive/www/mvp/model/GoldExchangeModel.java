package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.GoldExchangeContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class GoldExchangeModel implements GoldExchangeContract.Model {
    @Override
    public HttpObservable<BaseResponse<Double>> getMyGoldNum() {
        return HttpClient.getApi().getMyGoldNum();
    }

    @Override
    public HttpObservable<BaseResponse<Double>> getMyDiamondNum() {
        return HttpClient.getApi().getMyDiamondNum();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> goldChange(String coinNumber) {
        return HttpClient.getApi().goldChange(coinNumber);
    }


}
