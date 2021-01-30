package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.TeensCloseContract
import com.xsvideoLive.www.mvp.model.TeensCloseModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class TeensClosePresenter :BasePresenter<TeensCloseContract.View>(),TeensCloseContract.Presenter {

    val model by lazy { TeensCloseModel() }

    override fun closeTeens(uesrId: String, password: String) {
        view.showLoading()
        model.startTeens(userId = uesrId,password = password,status = "0")
                .execOnThread(view.getActLifeRecycle(), object: HttpObserver<String>(){
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.hideLoading()
                        view.onSuccess(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onError(msg!!)
                    }

                })

    }
}