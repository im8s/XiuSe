package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomNotiyContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomNotifyModel:RoomNotiyContract.Model {
    override fun editRoomNotify(roomId: String?, noticeTitle: String?, noticeComment: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().editRoomNotify(roomId, noticeTitle, noticeComment)
    }
}