package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.TeensOpenContract
import com.xsvideoLive.www.mvp.model.TeensOpenModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class TeensOpenPresenter : BasePresenter<TeensOpenContract.View>(), TeensOpenContract.Presenter {

    val model by lazy { TeensOpenModel() }

    override fun openTeens(uesrId: String, password: String) {
    view.showLoading()
        model.startTeens(userId = uesrId,password = password,status = "1")
                .execOnThread(view.getActLifeRecycle(), object:HttpObserver<String>(){
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