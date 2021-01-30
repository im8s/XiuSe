package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.TeensContract
import com.xsvideoLive.www.mvp.model.TeenModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class TeensPresenter :BasePresenter<TeensContract.View>(),TeensContract.Presenter {


    private val model:TeenModel by lazy { TeenModel() }

    override fun getTeenStatus(userId: String) {
        model.getTeenStatus(userId)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.teenSuccess(bean!!)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg!!)
                    }

                })
    }
}