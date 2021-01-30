package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.GivingContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GiftRecordResult;

public class GivingModel implements GivingContract.Model {
    @Override
    public HttpObservable<BaseResponse<GiftRecordResult>> giftRecord(String userId) {
        return HttpClient.getApi().giftRecord(userId);
    }
}
