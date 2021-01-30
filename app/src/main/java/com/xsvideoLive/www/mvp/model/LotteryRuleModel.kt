package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.LotteryRuleContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.PrizeResult

class LotteryRuleModel : LotteryRuleContract.Model {

    override fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>> {
        return HttpClient.getApi().getPrize(kind)
    }

}