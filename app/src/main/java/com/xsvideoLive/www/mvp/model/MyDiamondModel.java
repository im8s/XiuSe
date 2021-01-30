package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MyDiamondContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class MyDiamondModel implements MyDiamondContract.Model {
    @Override
    public HttpObservable<BaseResponse<Double>> getMyDiamondNum() {
        return HttpClient.getApi().getMyDiamondNum();
    }
}
