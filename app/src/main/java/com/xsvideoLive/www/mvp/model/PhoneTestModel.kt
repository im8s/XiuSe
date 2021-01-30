package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.PhoneTestContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class PhoneTestModel : PhoneTestContract.Model{
    override fun getStatusCode(phone: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().getStatusCode(phone)
    }

    override fun checkCode(phone: String?, code: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().checkCode(phone, code)
    }

}