package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.SettingContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.BindEntity
import com.xsvideoLive.www.net.bean.CheckVersionBean

class SettingModel:SettingContract.Model {

    override fun checkVersion(): HttpObservable<BaseResponse<CheckVersionBean>> {
        return HttpClient.getApi().checkVersion()
    }

    override fun getBoundPay(): HttpObservable<BaseResponse<BindEntity>> {
        return HttpClient.getApi().boundPay
    }
}