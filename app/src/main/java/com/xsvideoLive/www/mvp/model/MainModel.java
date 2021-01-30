package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MainContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

public class MainModel implements MainContract.Model {

    @Override
    public HttpObservable<BaseResponse<Integer>> exitRoom(String roomId) {
        return HttpClient.getApi().exitRoom(roomId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> getTeensStatus(String userId) {
        return HttpClient.getApi().getTeenStatus(userId);
    }
}
