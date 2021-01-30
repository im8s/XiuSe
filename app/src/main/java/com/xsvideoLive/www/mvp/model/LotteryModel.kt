package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.LotteryContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.LotteryPirzeResult
import com.xsvideoLive.www.net.bean.PrizeResult

class LotteryModel : LotteryContract.Model {

    override fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>> {
        return HttpClient.getApi().getPrize(kind)
    }

    override fun getMyGoldNum(): HttpObservable<BaseResponse<Double>> {
        return HttpClient.getApi().myGoldNum
    }

    override fun lottery(type: Int, kind: Int): HttpObservable<BaseResponse<LotteryPirzeResult>> {
        return HttpClient.getApi().lottery(type,kind)
    }

}