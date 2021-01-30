package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.MyCharmLvConstract
import com.xsvideoLive.www.mvp.model.MyCharmLvModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GradeResult

class MyCharmLvPresenter:BasePresenter<MyCharmLvConstract.View>(),MyCharmLvConstract.Presenter{

    private val mode by lazy { MyCharmLvModel() }

    override fun getCharmGradeUserGrade() {
        mode.getCharmGrade()
                .execOnThread(view.getActLifeRecycle(),object: HttpObserver<GradeResult>() {
                    override fun success(bean: GradeResult?, response: BaseResponse<GradeResult>?) {
                        view.onCharmGradeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                } )
    }

}