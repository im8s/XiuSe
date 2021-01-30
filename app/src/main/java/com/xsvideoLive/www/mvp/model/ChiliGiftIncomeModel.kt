package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.ChiliGiftIncomeContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GoldGiftResult
import com.xsvideoLive.www.net.bean.HomeRoomResult

class ChiliGiftIncomeModel:ChiliGiftIncomeContract.Model {
    override fun getChiliGiftList(current: String?, size: String?, date: String?): HttpObservable<BaseResponse<HomeRoomResult<List<GoldGiftResult?>?>?>?>? {
        return HttpClient.getApi().getChiliGiftList(current, size, date);
    }
}