package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.ChatBlackContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class ChatBlackModel:ChatBlackContract.Model {
    override fun isBlack(userId: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().isBlack(userId)
    }

    override fun addBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().addBlackList(blacklistType, currentId, blacklistUserId)
    }

    override fun removeBlackList(blacklistType: String?, currentId: String?, blacklistUserId: String?): HttpObservable<BaseResponse<Int>> {
        return HttpClient.getApi().removeBlackList(blacklistType, currentId, blacklistUserId)
    }

}