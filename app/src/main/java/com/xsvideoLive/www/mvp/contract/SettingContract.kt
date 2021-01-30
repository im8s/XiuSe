package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.BindEntity
import com.xsvideoLive.www.net.bean.CheckVersionBean

interface SettingContract {

    interface View : BaseView {

        fun onVersionSuccess(version: CheckVersionBean?)

        fun onError(msg: String?)

        fun onBindSuccess(result:BindEntity?)

        fun onBindError(msg:String?)

    }

    interface Presenter {

        fun getVersion()

        fun getBoundPay()

    }

    interface Model {

        fun checkVersion(): HttpObservable<BaseResponse<CheckVersionBean>>

        fun getBoundPay(): HttpObservable<BaseResponse<BindEntity>>

    }

}