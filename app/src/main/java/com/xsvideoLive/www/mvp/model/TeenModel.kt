package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.TeensContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class TeenModel : TeensContract.Model {
    override fun getTeenStatus(userId: String): HttpObservable<BaseResponse<String>> = HttpClient.getApi().getTeenStatus(userId)
}