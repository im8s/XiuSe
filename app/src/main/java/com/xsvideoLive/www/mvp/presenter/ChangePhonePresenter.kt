package com.xsvideoLive.www.mvp.presenter

import android.content.Context
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.ChangePhoneContract
import com.xsvideoLive.www.mvp.model.ChangePhoneModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class ChangePhonePresenter :BasePresenter<ChangePhoneContract.View>(),ChangePhoneContract.Presenter {

    private val model by lazy { ChangePhoneModel() }

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

    override fun changePhone(phone: String?, userId: String?, code: String?) {
        model.changePhone(phone, userId, code)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>(){
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onChangeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}