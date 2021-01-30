package com.xsvideoLive.www.mvp.presenter

import com.xsvideoLive.www.base.BasePresenter
import com.xsvideoLive.www.mvp.contract.ReportContract
import com.xsvideoLive.www.mvp.model.ReportModel
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.ReportType
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.util.*

class ReportPresenter : BasePresenter<ReportContract.View>(), ReportContract.Presenter {

    private val model by lazy { ReportModel() }

    override fun getReportType() {
        model.getReportType()
                .execOnThread(view.getActLifeRecycle(), object : HttpObserver<MutableList<ReportType>>() {
                    override fun success(bean: MutableList<ReportType>?, response: BaseResponse<MutableList<ReportType>>?) {
                        view.onReproTypeSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.onError(msg)
                    }

                })
    }

    override fun report(photoPath: MutableList<String>?, userId: String?, reportType: String?, reportKindId: String?, targetId: String?, reportComment: String?) {

        view.showLoading()

        var paramsMap: MutableMap<String, RequestBody>? = HashMap()
        if (photoPath != null) {
            for (datum in photoPath) {
                val file = File(datum)
                val fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                paramsMap!!["files\"; filename=\"" + file.name] = fileBody
            }
        } else {
            paramsMap = null
        }

        model.report(paramsMap!!, userId, reportType, reportKindId, targetId, reportComment)
                .execOnThread(view.getActLifeRecycle(),object :HttpObserver<String>(){
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        view.hideLoading()
                        view.onReportSuccess(bean)
                    }

                    override fun error(msg: String?) {
                        view.hideLoading()
                        view.onError(msg)
                    }

                })

    }

}