package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomGiftContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomGiftModel:RoomGiftContract.Model {
    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myGoldNum
    }

    override fun getMyChili(): HttpObservable<BaseResponse<Double?>?>? {
        return HttpClient.getApi().myChili
    }
}