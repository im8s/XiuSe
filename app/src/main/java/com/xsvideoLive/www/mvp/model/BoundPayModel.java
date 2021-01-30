package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.BoundPayContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BindEntity;

public class BoundPayModel implements BoundPayContract.Model {
    @Override
    public HttpObservable<BaseResponse<BindEntity>> getBoundPay() {
        return HttpClient.getApi().getBoundPay();
    }
}
