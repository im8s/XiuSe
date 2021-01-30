package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.ReportContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.ReportType
import okhttp3.RequestBody

class ReportModel:ReportContract.Model {

    override fun getReportType(): HttpObservable<BaseResponse<MutableList<ReportType>>> {
        return HttpClient.getApi().reportType
    }

    override fun report(files: Map<String, RequestBody>?, userId: String?, reportType: String?, reportKindId: String?, targetId: String?, reportComment: String?): HttpObservable<BaseResponse<String>> {
        return HttpClient.getApi().report(files, userId, reportType, reportKindId, targetId, reportComment)
    }

}