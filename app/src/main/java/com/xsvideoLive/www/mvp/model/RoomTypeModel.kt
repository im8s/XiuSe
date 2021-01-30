package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomTypeContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeSortResult

class RoomTypeModel:RoomTypeContract.Model {

    override fun getHomeSort(): HttpObservable<BaseResponse<MutableList<HomeSortResult>>> {
        return HttpClient.getApi().homeSort
    }

    override fun modifyRoomType(roomId: String?, typeId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().modifyRoomType(roomId,typeId)
    }
}