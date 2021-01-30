package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.OnLineContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.OnLineUserResult

class OnLineModel:OnLineContract.Model {
    override fun getOnLineUser(current: String?, size: String?, roomId: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<OnLineUserResult>>>> {
        return HttpClient.getApi().getOnLineUser(current, size, roomId)
    }

}