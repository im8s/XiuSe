package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.VerifiedContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class VerifiedModel:VerifiedContract.Model {
    override fun queryVerified(userId: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().queryVerified(userId)
    }

    override fun verified(userId: String, userRealName: String, userIdentifica: String): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().verified(userId, userRealName, userIdentifica)
    }
}