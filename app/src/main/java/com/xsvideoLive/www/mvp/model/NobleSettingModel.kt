package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.NobleSettingContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class NobleSettingModel :NobleSettingContract.Model{
    override fun enterRoomStatus(userId: String?, status: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().enterRoomStatus(userId, status)
    }

}