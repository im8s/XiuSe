package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.NobleSettingContract
import com.xsvideoLive.www.mvp.model.NobleSettingModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class NobleSettingPresenter:BasePresenter<NobleSettingContract.View>(),NobleSettingContract.Presenter {

    private val model by lazy { NobleSettingModel() }

    override fun setRoomStatus(userId: String?, status: String?) {
        model.enterRoomStatus(userId,status)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.onStatusSuccess(status,bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }
}