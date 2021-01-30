package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.ChiliOutlayContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.ChiliIncomeResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class ChiliOutlayModel implements ChiliOutlayContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getOutlayList(String current, String size, String date) {
        return HttpClient.getApi().getMyChiliOutlayList(current, size, date);
    }
}
