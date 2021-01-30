package com.xsvideoLive.www.callback

import com.xsvideoLive.www.net.bean.GiftViewSeat
import com.xsvideoLive.www.net.bean.RoomGiftResult

interface OnGiftSendCallback {

    fun onSendGiftResult(status: Int?, users: MutableList<GiftViewSeat>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?)

    fun onNobleClick()

    fun onGiftDismiss()
}