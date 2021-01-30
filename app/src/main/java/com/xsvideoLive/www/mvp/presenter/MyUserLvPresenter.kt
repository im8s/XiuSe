package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.MyUserLvConstract
import com.xsvideoLive.www.mvp.model.MyUserLvModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GradeResult

class MyUserLvPresenter:BasePresenter<MyUserLvConstract.View>(),MyUserLvConstract.Presenter{

    private val model by lazy { MyUserLvModel() }

    override fun getUserGrade() {
        model.getUserGrade()
                .execOnThread(view.getActLifeRecycle(),object:HttpObserver<GradeResult>() {
                    override fun success(bean: GradeResult?, response: BaseResponse<GradeResult>?) {
                        view.onUserGradeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                } )
    }
}