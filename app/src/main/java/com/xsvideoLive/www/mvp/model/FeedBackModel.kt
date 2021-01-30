package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.FeedBackContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class FeedBackModel:FeedBackContract.Model{
    override fun feedBack(message: String, type: String, num: String, userId: String): HttpObservable<BaseResponse<Int>>? {
        return HttpClient.getApi().feedBack(message,type,num,userId)
    }

}