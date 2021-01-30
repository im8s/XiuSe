package com.xsvideoLive.www.mvp.model

import com.xsvideoLive.www.mvp.contract.TeensCloseContract
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObservable
import com.xsvideoLive.www.net.bean.BaseResponse

class TeensCloseModel : TeensCloseContract.Model {

    override fun startTeens(userId: String, password: String, status: String): HttpObservable<BaseResponse<String>> = HttpClient.getApi().startTeens(userId, password, status)

}