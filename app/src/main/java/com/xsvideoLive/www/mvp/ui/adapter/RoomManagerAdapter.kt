package com.xsvideoLive.www.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xsvideoLive.www.R
import com.xsvideoLive.www.net.bean.RoomManagerEntity
import com.xsvideoLive.www.utils.GlideAppUtil
import com.xsvideoLive.www.view.CircleImageView

class RoomManagerAdapter() : BaseQuickAdapter<RoomManagerEntity, RoomManagerAdapter.Holder>(R.layout.adapter_item_room_manager) {


    override fun convert(holder: Holder, item: RoomManagerEntity) {
        GlideAppUtil.loadImage(context, item.userPicture, holder.mIvAvatar, R.mipmap.icon_default_avatar)
        holder.mTvName?.text = item.userName
    }

    inner class Holder : BaseViewHolder {

        var mIvAvatar: CircleImageView? = null
        var mTvName: TextView? = null

        constructor(v: View) : super(v) {
            mIvAvatar = v.findViewById(R.id.civ_avatar)
            mTvName = v.findViewById(R.id.tv_name)
        }
    }
}