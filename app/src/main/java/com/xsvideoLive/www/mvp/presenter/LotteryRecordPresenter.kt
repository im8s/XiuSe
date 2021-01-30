package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.LotteryRecordContract
import com.xsvideoLive.www.mvp.model.LotteryRecordModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.AwardsEntity
import com.xsvideoLive.www.net.bean.BaseResponse

class LotteryRecordPresenter:BasePresenter<LotteryRecordContract.View>(),LotteryRecordContract.Presenter {

    private val model by lazy { LotteryRecordModel() }
    override fun getLotterRecord(kind: Int) {
        model.getLotterRecord(kind)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<MutableList<AwardsEntity>>(){
                    override fun success(bean: MutableList<AwardsEntity>?, response: BaseResponse<MutableList<AwardsEntity>>?) {
                        view.onRecordSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

}