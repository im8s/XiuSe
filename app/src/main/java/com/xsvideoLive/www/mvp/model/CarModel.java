package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.CarContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.DeckResult;

public class CarModel implements CarContract.Model {
    @Override
    public HttpObservable<BaseResponse<DeckResult>> carRecord(String userId) {
        return HttpClient.getApi().carRecord(userId);
    }
}
