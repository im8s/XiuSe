package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.ChatContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.GiftAllResult

class ChatModel:ChatContract.Model {
    override fun getGift(): HttpObservable<BaseResponse<GiftAllResult>> {
        return HttpClient.getApi().roomGift
    }

    override fun getNoble(): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().noble
    }

}