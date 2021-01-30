package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FansContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.MyFollowResult;

import java.util.List;

public class FansModel implements FansContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFans(String current, String size) {
        return HttpClient.getApi().getMyFans(current, size);
    }

    @Override
    public HttpObservable<BaseResponse<String>> follow(String userId, String status) {
        return HttpClient.getApi().follow(userId,status);
    }
}
