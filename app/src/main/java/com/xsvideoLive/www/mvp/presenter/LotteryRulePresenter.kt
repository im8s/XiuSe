package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.LotteryRuleContract
import com.xsvideoLive.www.mvp.model.LotteryRuleModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.PrizeResult

class LotteryRulePresenter:BasePresenter<LotteryRuleContract.View>(),LotteryRuleContract.Presenter {

    private val model by lazy { LotteryRuleModel() }

    override fun getPrize(kind: Int) {
        model.getPrize(kind)
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<PrizeResult>() {
                    override fun success(bean: PrizeResult?, response: BaseResponse<PrizeResult>?) {
                        view.onPrizeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}