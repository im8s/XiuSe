package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MyCarContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.DeckResult;

public class MyCarModel implements MyCarContract.Model {
    @Override
    public HttpObservable<BaseResponse<DeckResult>> getMyCar() {
        return HttpClient.getApi().getMyCar();
    }

    @Override
    public HttpObservable<BaseResponse<String>> useCar(String id) {
        return HttpClient.getApi().useCar(id);
    }
}
