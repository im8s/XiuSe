package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomMyGiftConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomGiftResult

class RoomMyGiftModel:RoomMyGiftConstract.Model {

    override fun getPackageGift(): HttpObservable<BaseResponse<MutableList<RoomGiftResult>>> {
        return HttpClient.getApi().packageGift
    }

    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myGoldNum
    }

    override fun getMyChili(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myChili
    }

    override fun getTotalValue(): HttpObservable<BaseResponse<Int?>?>? {
        return HttpClient.getApi().totalValue
    }

}