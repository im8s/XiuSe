package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.GoldRechargeContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BuyGoldResult;
import com.xsvideoLive.www.net.bean.GoldResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class GoldRechargeModel implements GoldRechargeContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<GoldResult>>>> getGoldList(String current, String size, String userId) {
        return HttpClient.getApi().getGoldList(current, size, userId);
    }

    @Override
    public HttpObservable<BaseResponse<BuyGoldResult>> buyGold(int payType, String payTargetId) {
        return HttpClient.getApi().buyGold(payType,payTargetId);
    }

    @Override
    public HttpObservable<BaseResponse<Double>> getMyGoldNum() {
        return HttpClient.getApi().getMyGoldNum();
    }
}
