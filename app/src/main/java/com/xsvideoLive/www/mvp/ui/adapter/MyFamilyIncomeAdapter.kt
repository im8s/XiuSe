package com.xsvideoLive.www.mvp.ui.adapter

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xsvideoLive.www.R
import com.xsvideoLive.www.net.bean.FamilyIncome
import com.xsvideoLive.www.utils.GlideAppUtil
import com.xsvideoLive.www.view.CircleImageView

class MyFamilyIncomeAdapter : BaseSectionQuickAdapter<FamilyIncome, BaseViewHolder> {


    constructor(data: MutableList<FamilyIncome>?) : super(R.layout.adapter_family_income_headre, data) {
        setNormalLayout(R.layout.adapter_my_family_income)
    }

    override fun convertHeader(holder: BaseViewHolder, item: FamilyIncome) {
        holder.setText(R.id.tv_date, item.date)
    }

    override fun convert(holder: BaseViewHolder, item: FamilyIncome) {

        var mCivAtatar = holder.getView<CircleImageView>(R.id.iv_avater)
        GlideAppUtil.loadImage(context, item.userIncome?.userPictrue, mCivAtatar,R.mipmap.icon_default_avatar)
        holder.setText(R.id.tv_name, item.userIncome?.userName)
        holder.setText(R.id.tv_money, "+${(item.userIncome?.number)?.toInt()}")
    }


}