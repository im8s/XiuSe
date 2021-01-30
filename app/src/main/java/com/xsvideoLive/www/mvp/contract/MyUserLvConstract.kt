package com.xsvideoLive.www.mvp.contract

import com.xsvideoLive.www.base.BaseView
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GradeResult

interface MyUserLvConstract {

    interface View:BaseView{

        fun onUserGradeSuccess(result:GradeResult?)

        fun onError(msg:String?)
    }

    interface Presenter {
        fun getUserGrade()
    }

    interface Model {
        fun getUserGrade(): HttpObservable<BaseResponse<GradeResult>>
    }

}