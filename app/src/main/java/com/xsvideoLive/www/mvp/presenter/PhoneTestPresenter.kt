package com.xsvideoLive.www.mvp.presenter

import android.content.Context
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.PhoneTestContract
import com.xsvideoLive.www.mvp.model.PhoneTestModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class PhoneTestPresenter :BasePresenter<PhoneTestContract.View>(),PhoneTestContract.Presenter {

    private val model by lazy { PhoneTestModel() }

    override fun getStatusCode(phone: String?) {
        model.getStatusCode(phone)
                .execOnThread(view.getActLifeRecycle<BaseResponse<String>>(), object : HttpObserver<String>() {
                    override fun success(bean: String, response: BaseResponse<String>) {
                        if (bean == "1") {
                            view.codeSuccess(bean)
                        } else {
                            view.codeError((view as Context).resources.getString(R.string.send_sms_error))
                        }
                    }

                    override fun error(msg: String) {
                        view.codeError(msg)
                    }
                })
    }

    override fun checkCode(phone: String?, code: String?) {
        model.checkCode(phone,code)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onCheckSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}