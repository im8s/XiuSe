package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.HomeSeachContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeSeachEntity;
import com.xsvideoLive.www.net.bean.SeachRoomEntty;

import java.util.List;

public class HomeSeachModel implements HomeSeachContract.Model {

    @Override
    public HttpObservable<BaseResponse<List<SeachRoomEntty>>> getSeachRoomRecord() {
        return HttpClient.getApi().getSeachRoomRecord();
    }

    @Override
    public HttpObservable<BaseResponse<String>> removeEnterRoomRecord(String userId) {
        return HttpClient.getApi().removeEnterRoomRecord(userId);
    }

    @Override
    public HttpObservable<BaseResponse<List<HomeSeachEntity>>> getSeachResult(String searchString) {
        return HttpClient.getApi().getSeachResult(searchString);
    }
}
