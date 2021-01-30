package com.xsvideoLive.www.callback

import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity

interface RoomUserCallback {

    fun onRoomUser(userInfo: VoiceRoomSeatEntity.UserInfo?)

}