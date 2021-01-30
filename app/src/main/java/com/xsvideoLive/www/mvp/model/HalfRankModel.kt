package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.HalfRankConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HalfRoomRankResult

class HalfRankModel:HalfRankConstract.Model {
    override fun getHalfRoomRank(roomId: String?): HttpObservable<BaseResponse<HalfRoomRankResult>> {
        return HttpClient.getApi().getHalfRoomRank(roomId)
    }
}