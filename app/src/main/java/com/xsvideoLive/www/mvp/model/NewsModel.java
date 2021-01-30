package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.NewsContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.SystemNotic;

import java.util.List;

public class NewsModel implements NewsContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<SystemNotic>>>> getSystemNotic(String current, String size) {
        return HttpClient.getApi().getSystemNotic(current,size);
    }
}
