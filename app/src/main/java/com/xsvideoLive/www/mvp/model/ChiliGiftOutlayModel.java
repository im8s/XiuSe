package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.ChiliGiftOutlayContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GoldGiftResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChiliGiftOutlayModel implements ChiliGiftOutlayContract.Model {

    @Nullable
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult>>>> getChiliGiftOutlayList(@Nullable String current, @Nullable String size, @Nullable String date) {
        return HttpClient.getApi().getChiliGiftOutlayList(current, size, date);
    }
}
