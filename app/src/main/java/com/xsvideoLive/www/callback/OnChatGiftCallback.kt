package com.xsvideoLive.www.callback

import com.xsvideoLive.www.net.bean.ChatGiftSeat
import com.xsvideoLive.www.net.bean.RoomGiftResult

interface OnChatGiftCallback {

    fun onSendGiftResult(status: Int?, users: MutableList<String>?, giftResult: RoomGiftResult?, giftNum: Int?, isTotal: Boolean?,mGiveAway: ChatGiftSeat?)

    fun onNobleClick()

    fun onGiftDismiss()
}