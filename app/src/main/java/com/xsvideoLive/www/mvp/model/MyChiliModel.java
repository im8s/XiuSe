package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MyChiliContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class MyChiliModel implements MyChiliContract.Model {
    @Override
    public HttpObservable<BaseResponse<Double>> getMyChili() {
        return HttpClient.getApi().getMyChili();
    }
}
