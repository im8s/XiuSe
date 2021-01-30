package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.RechargeRecordContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CashEntity;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class RechargeRecordModel implements RechargeRecordContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getGoldRecharge(String current, String size, String userId, String date) {
        return HttpClient.getApi().getGoldRecharge(current, size, userId, date);
    }
}
