package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.LotterSelectionConstract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.PrizeResult

class LotterSelectionModel : LotterSelectionConstract.Model {

    override fun getPrize(kind: Int?): HttpObservable<BaseResponse<PrizeResult>> {
        return HttpClient.getApi().getPrize(kind)
    }

}