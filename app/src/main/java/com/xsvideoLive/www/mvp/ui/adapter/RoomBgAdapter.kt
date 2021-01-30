package com.xsvideoLive.www.mvp.ui.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xsvideoLive.www.R
import com.xsvideoLive.www.mvp.ui.view.RoomBgNobleView
import com.xsvideoLive.www.net.bean.RoomBgEntity
import com.xsvideoLive.www.utils.GlideAppUtil
import com.xsvideoLive.www.view.CircleImageView

class RoomBgAdapter : BaseQuickAdapter<RoomBgEntity, RoomBgAdapter.Holder>(R.layout.adapter_item_room_bg) {

    override fun convert(holder: Holder, item: RoomBgEntity) {
        GlideAppUtil.loadImage(context, item.backgroundUrl, holder.mIvAvatar)
        holder.mRoomNoble?.setResult(item)
        holder.mTvName?.text = item.backgroundName

        if (item.status == 0) {
            holder.mTvFlag?.visibility = View.VISIBLE
        } else {
            holder.mTvFlag?.visibility = View.GONE
        }

    }

    inner class Holder : BaseViewHolder {

        var mIvAvatar: CircleImageView? = null
        var mRoomNoble: RoomBgNobleView? = null
        var mTvFlag: TextView? = null
        var mTvName: TextView? = null

        constructor(v: View) : super(v) {
            mIvAvatar = v.findViewById(R.id.civ_room_bg)
            mRoomNoble = v.findViewById(R.id.bnb_noble)
            mTvFlag = v.findViewById(R.id.tv_flag)
            mTvName = v.findViewById(R.id.tv_name)
        }
    }


}