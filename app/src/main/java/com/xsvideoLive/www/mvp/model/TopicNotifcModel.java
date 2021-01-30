package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.TopicNotifcContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.TopicMsg;

import java.util.List;

public class TopicNotifcModel implements TopicNotifcContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<TopicMsg>>>> getTopicMsg(String current, String size, String userId) {
        return HttpClient.getApi().getTopicMsg(current, size, userId);
    }
}
