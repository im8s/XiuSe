package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.RoomMyGiftConstract
import com.xsvideoLive.www.mvp.model.RoomMyGiftModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RoomGiftResult

class RoomMyGiftPresenter:BasePresenter<RoomMyGiftConstract.View>(),RoomMyGiftConstract.Presenter {

    private  val model:RoomMyGiftModel by lazy { RoomMyGiftModel() }

    override fun getPackageGift() {
        model.getPackageGift()
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<MutableList<RoomGiftResult>>(){
                    override fun success(bean: MutableList<RoomGiftResult>, response: BaseResponse<MutableList<RoomGiftResult>>) {
                        view.onGiftSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun getMyGoldNum() {
        model.getMyGoldNum()
                ?.execOnThread(view.getActLifeRecycle(), object : HttpObserver<Double?>() {
                    override fun success(bean: Double?, response: BaseResponse<Double?>?) {

                        if (bean != null){
                        view.onGoldSuccess(bean)

                        } else {
                            view.onError("请求错误")
                        }
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })

    }

    override fun getMyChili() {
        model.getMyChili()
                ?.execOnThread(view.getActLifeRecycle(),object:HttpObserver<Double>(){
                    override fun success(bean: Double?, response: BaseResponse<Double>?) {
                        view.onChiliSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun getTotalValue() {
        model.getTotalValue()
                ?.execOnThread(view.getActLifeRecycle(),object :HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        view.onTotalSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onTotalError(msg)
                    }

                })
    }
}