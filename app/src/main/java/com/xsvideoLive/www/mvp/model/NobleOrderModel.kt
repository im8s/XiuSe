package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.NobleOrderContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.BuyGoldResult

class NobleOrderModel : NobleOrderContract.Model {

    /**
     * 获取我的金币数量
     */
    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double>> = HttpClient.getApi().myGoldNum

    /**
     * 获取我的贵族身份
     */
    override fun getNoble(): HttpObservable<BaseResponse<Int>> = HttpClient.getApi().noble

    /**
     * 金币购买
     */
    override fun goldBuyNoble(nobleId: String): HttpObservable<BaseResponse<Int>> = HttpClient.getApi().goldBuyNoble(nobleId)

    /**
     * 获取令牌
     */
    override fun buyNoble(payType: String, payTargetId: String): HttpObservable<BaseResponse<BuyGoldResult>> = HttpClient.getApi().buyNoble(payType, payTargetId)
}