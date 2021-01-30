package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomListConstart
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.UserRankResult

class RoomListModel: RoomListConstart.Model {
    override fun getUserRank(roomId: String?, type: Int?): HttpObservable<BaseResponse<MutableList<UserRankResult>>> {
        return HttpClient.getApi().getUserRank(roomId,type)
    }
}