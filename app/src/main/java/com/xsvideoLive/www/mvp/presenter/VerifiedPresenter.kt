package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.VerifiedContract
import com.xsvideoLive.www.mvp.model.VerifiedModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class VerifiedPresenter : BasePresenter<VerifiedContract.View>(), VerifiedContract.Presenter {

    val model by lazy { VerifiedModel() }

    override fun queryVerified(userId: String) {

        view.showLoading()

        model.queryVerified(userId)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String, response: BaseResponse<String>) {
                        view.hideLoading()
                        view.querySuccess(bean)
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }

    override fun verified(userId: String, userRealName: String, userIdentifica: String) {
        view.showLoading()
        model.verified(userId, userRealName, userIdentifica)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<String>() {
                    override fun success(bean: String, response: BaseResponse<String>) {
                        view.hideLoading()
                        view.verifiedSuccess(bean)
                    }

                    override fun error(msg: String) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })
    }
}