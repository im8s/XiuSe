package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.DiamondExpensesContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.IncomeResult;

import java.util.List;

public class DiamondExpensesModel implements DiamondExpensesContract.Model {

    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<IncomeResult>>>> getIncomeList(String current, String size, String date) {
        return HttpClient.getApi().getOutComeList(current, size, date);
    }
}
