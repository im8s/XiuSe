package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.LotteryRecordContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.AwardsEntity
import com.xsvideoLive.www.net.bean.BaseResponse

class LotteryRecordModel: LotteryRecordContract.Model {
    override fun getLotterRecord(kind: Int): HttpObservable<BaseResponse<MutableList<AwardsEntity>>> {
        return HttpClient.getApi().getLotterRecord(kind)
    }

}