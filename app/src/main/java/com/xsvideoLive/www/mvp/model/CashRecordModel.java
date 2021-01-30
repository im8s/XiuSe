package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.CashRecordContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CashEntity;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class CashRecordModel implements CashRecordContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<CashEntity>>>> getCashList(String current, String size, String userId,String date) {
        return HttpClient.getApi().getCashList(current, size, userId,date);
    }
}
