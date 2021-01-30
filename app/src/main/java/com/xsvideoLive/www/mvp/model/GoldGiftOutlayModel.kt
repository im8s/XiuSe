package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.GoldGiftOutlayContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GoldGiftResult
import com.xsvideoLive.www.net.bean.HomeRoomResult

class GoldGiftOutlayModel : GoldGiftOutlayContract.Model{
    override fun getGoldGiftOutlayList(current: String?, size: String?, date: String?): HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult?>?>?>?>? {
        return HttpClient.getApi().getGoldGiftOutlayList(current, size, date);
    }
}
