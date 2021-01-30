package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomBgConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomBgModel: RoomBgConstract.Model {

    override fun setRoomBg(roomId: String?, userId: String?, imgId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().setRoomBg(roomId, userId, imgId)
    }

    override fun getNoble(): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().noble
    }

}