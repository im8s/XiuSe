package com.xsvideoLive.www.callback

import com.xsvideoLive.www.net.bean.RoomGiftResult

interface OnGiftCallback {

    fun setGiftResult(result: RoomGiftResult?, num: Int,fragmentIndex:Int)

    fun rechargeEntry()

}