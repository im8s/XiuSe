package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.SystemNoticContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.HomeRoomResult
import com.xsvideoLive.www.net.bean.SystemNotic

class SystemNoticModel: SystemNoticContract.Model {
    override fun getSystemNotic(current: String?, size: String?): HttpObservable<BaseResponse<HomeRoomResult<MutableList<SystemNotic>>>> {
        return HttpClient.getApi().getSystemNotic(current,size)
    }

}