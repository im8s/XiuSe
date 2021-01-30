package com.xsvideoLive.www.mvp.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xsvideoLive.www.R
import com.xsvideoLive.www.net.bean.FunButtonEntity

class SeatButtonAdapter : BaseQuickAdapter<FunButtonEntity, SeatButtonAdapter.Holder>(R.layout.adapter_item_seat_button) {

    override fun convert(holder: Holder, item: FunButtonEntity) {
        if (item.isSelect!!) {
            holder.mIvIcon?.setImageResource(item.lordIcon!!)
        } else {
            holder.mIvIcon?.setImageResource(item.viceIcon!!)
        }

        holder.mTvName?.text = item.buttonName
    }

    inner class Holder : BaseViewHolder {

        var mIvIcon: ImageView? = null

        var mTvName: TextView? = null

        constructor(view: View) : super(view) {
            mIvIcon = view.findViewById(R.id.iv_icon)
            mTvName = view.findViewById(R.id.tv_name)

        }
    }


}