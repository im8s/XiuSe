package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.RoomBackgroundConstant
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.RoomBgEntity

class RoomBackgroundModel: RoomBackgroundConstant.Model {


    override fun getRoomBg(current: String?, size: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<RoomBgEntity>>>> {
        return HttpClient.getApi().getRoomBg(current, size)
    }
}