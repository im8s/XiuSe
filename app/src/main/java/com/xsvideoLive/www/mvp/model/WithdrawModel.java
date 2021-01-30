package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.WithdrawContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BindEntity;
import com.xsvideoLive.www.net.bean.NewGoldResult;

import java.util.List;

public class WithdrawModel implements WithdrawContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<NewGoldResult>>> getCashList() {
        return HttpClient.getApi().getCashList();
    }

    @Override
    public HttpObservable<BaseResponse<BindEntity>> getBoundPay() {
        return HttpClient.getApi().getBoundPay();
    }

    @Override
    public HttpObservable<BaseResponse<Integer>> withdraw(String cashConfigId) {
        return HttpClient.getApi().withdraw(cashConfigId);
    }

    @Override
    public HttpObservable<BaseResponse<Double>> getMyDiamondNum() {
        return HttpClient.getApi().getMyDiamondNum();
    }
}
