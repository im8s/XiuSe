package com.xsvideoLive.www.callback

interface RoomInPutCallback {
    fun onInPutResult(msg: String?)

    fun onInputDismiss()
}