package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.GraphiceContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

import java.util.Map;

import okhttp3.RequestBody;

public class GraphiceModel implements GraphiceContract.Model {
    @Override
    public HttpObservable<BaseResponse<String>> releaseFriends(String topicContent, Map<String, RequestBody> params) {
        return HttpClient.getApi().releaseFriends(topicContent, params);
    }
}
