package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MyGoldContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class MyGoldModel implements MyGoldContract.Model {

    @Override
    public HttpObservable<BaseResponse<Double>> getMyGoldNum() {
        return HttpClient.getApi().getMyGoldNum();
    }
}
