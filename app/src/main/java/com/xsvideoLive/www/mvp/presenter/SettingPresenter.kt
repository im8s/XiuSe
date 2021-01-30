package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.SettingContract
import com.xsvideoLive.www.mvp.model.SettingModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.BindEntity
import com.xsvideoLive.www.net.bean.CheckVersionBean

class SettingPresenter:BasePresenter<SettingContract.View>(),SettingContract.Presenter {

    private val model by lazy { SettingModel() }

    override fun getVersion() {
        model.checkVersion()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<CheckVersionBean>(){
                    override fun success(bean: CheckVersionBean?, response: BaseResponse<CheckVersionBean>?) {
                        view.onVersionSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun getBoundPay() {
        view.showLoading()
        model.getBoundPay()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<BindEntity>() {
                    override fun success(bean: BindEntity?, response: BaseResponse<BindEntity>?) {
                        view.hideLoading()
                        view.onBindSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onBindError(msg)
                    }

                })
    }
}