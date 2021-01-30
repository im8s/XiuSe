package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.MyCharmLvConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GradeResult

class MyCharmLvModel :MyCharmLvConstract.Model {
    override fun getCharmGrade(): HttpObservable<BaseResponse<GradeResult>> {
        return HttpClient.getApi().charmGrade
    }

}