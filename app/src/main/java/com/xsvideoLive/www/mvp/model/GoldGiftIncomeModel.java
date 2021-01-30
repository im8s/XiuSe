package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.GoldGiftIncomeContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GoldGiftResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class GoldGiftIncomeModel implements GoldGiftIncomeContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getGoldGiftList(String current, String size, String date) {
        return HttpClient.getApi().getGoldGiftList(current, size, date);
    }
}
