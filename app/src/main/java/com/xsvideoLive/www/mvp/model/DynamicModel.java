package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.DynamicContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class DynamicModel implements DynamicContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size, String userId) {
        return HttpClient.getApi().getFriendsCircle(current, size, userId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> fabulous(String topicId) {
        return HttpClient.getApi().fabulous(topicId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> deleteTopic(String topicId) {
        return HttpClient.getApi().deleteTopic(topicId);
    }
}
