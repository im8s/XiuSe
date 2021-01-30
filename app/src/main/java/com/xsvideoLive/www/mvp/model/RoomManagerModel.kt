package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomManagerContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomManagerEntity

class RoomManagerModel:RoomManagerContract.Model {
    override fun getManager(roomId: String?): HttpObservable<BaseResponse<MutableList<RoomManagerEntity>>> {
        return HttpClient.getApi().getManager(roomId)
    }

    override fun removeManager(roomId: String?, userId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeManager(roomId, userId)
    }
}