package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.ChangePhoneContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class ChangePhoneModel :ChangePhoneContract.Model {
    override fun getStatusCode(phone: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().getStatusCode(phone)
    }

    override fun changePhone(phone: String?, userId: String?, code: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().changePhone(phone, userId, code)
    }
}