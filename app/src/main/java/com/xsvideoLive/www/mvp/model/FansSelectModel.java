package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.FansSelectContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.MyFollowResult;

import java.util.List;

public class FansSelectModel implements FansSelectContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFans(String current, String size) {
        return HttpClient.getApi().getMyFans(current, size);
    }

}
