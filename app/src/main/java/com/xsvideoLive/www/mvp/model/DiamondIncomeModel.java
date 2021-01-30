package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.DiamondIncomeContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.IncomeResult;

import java.util.List;

public class DiamondIncomeModel implements DiamondIncomeContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getIncomeList(String current, String size, String date) {
        return HttpClient.getApi().getIncomeList(current, size, date);
    }
}
