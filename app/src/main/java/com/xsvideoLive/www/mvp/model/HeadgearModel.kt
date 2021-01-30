package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.HeadgearContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.MineReslut

class HeadgearModel:HeadgearContract.Model {

    override fun getMine(userId: String?): HttpObservable<BaseResponse<MineReslut>> {
        return HttpClient.getApi().getMine(userId)
    }
}