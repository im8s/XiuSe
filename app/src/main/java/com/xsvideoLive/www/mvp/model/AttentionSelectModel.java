package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.AttentionSelectContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.MyFollowResult;

import java.util.List;

public class AttentionSelectModel implements AttentionSelectContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFollow(String current, String size) {
        return HttpClient.getApi().getMyFollow(current, size);
    }
}
