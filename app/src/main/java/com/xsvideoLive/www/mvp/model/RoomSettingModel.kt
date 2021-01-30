package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomSettingContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class RoomSettingModel : RoomSettingContract.Model {
    override fun modifyName(roomId: String?, roomName: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().modifyName(roomId, roomName)
    }

    override fun modifyPsw(roomId: String?, type: String?, password: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().modifyPsw(roomId, type, password)
    }

    override fun setPublic(roomId: String?, type: Int): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().setRoomPublic(roomId, type)
    }

    override fun pickSwitch(roomId: String?, type: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().pickSwitch(roomId, type)
    }

}