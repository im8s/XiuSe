package com.xsvideoLive.www.callback

import com.xsvideoLive.www.net.bean.RoomFace

interface RoomFaceCallback {

    fun onDialogDismiss()

    fun onFaceClickResult(face:RoomFace?,isNobleFace:Boolean?,noble:String?)

}