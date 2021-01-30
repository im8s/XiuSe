package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.MyUserLvConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GradeResult

class MyUserLvModel :MyUserLvConstract.Model {
    override fun getUserGrade(): HttpObservable<BaseResponse<GradeResult>> {
        return HttpClient.getApi().userGrade
    }
}