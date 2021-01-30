package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GradeResult

interface MyCharmLvConstract {

    interface View:BaseView{

        fun onCharmGradeSuccess(result: GradeResult?)

        fun onError(msg:String?)
    }

    interface Presenter {
        fun getCharmGradeUserGrade()
    }

    interface Model {
        fun getCharmGrade(): HttpObservable<BaseResponse<GradeResult>>
    }

}