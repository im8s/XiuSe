package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.FeedBackContract
import com.xsvideoLive.www.mvp.model.FeedBackModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse

class FeedBackPresenter:BasePresenter<FeedBackContract.View>() ,FeedBackContract.Presenter{
    private val model by lazy { FeedBackModel() }

    override fun feedBack(message: String, type: String, num: String, userId: String) {
        view.showLoading()
        model.feedBack(message,type,num,userId)?.execOnThread(view.getActLifeRecycle(),object : HttpObserver<Int>() {
            override fun success(bean: Int?, response: BaseResponse<Int>?) {
                 view.hideLoading()
                if (bean!=null){
                    if (bean==1){
                        view.feedBackSuccess()
                    }else{
                        view.feedBackError("反馈提交失败")
                    }
                }
            }

            override fun error(msg: String?) {
                view.hideLoading()
                if (msg != null) {
                    view.feedBackError(msg)
                }
            }

        })
    }


}