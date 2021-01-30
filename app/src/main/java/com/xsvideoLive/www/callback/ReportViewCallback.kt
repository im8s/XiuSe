package com.xsvideoLive.www.callback

import com.xsvideoLive.www.net.bean.ReportFoot

interface ReportViewCallback {

    fun reportResult(result: ReportFoot)

}