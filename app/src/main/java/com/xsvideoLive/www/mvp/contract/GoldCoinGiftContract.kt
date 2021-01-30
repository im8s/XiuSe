package com.xsvideoLive.www.mvp.contract

import androidx.viewpager.widget.ViewPager
import com.xsvideoLive.www.base.BaseView
import net.lucode.hackware.magicindicator.MagicIndicator

interface GoldCoinGiftContract {

    interface View : BaseView {

    }

    interface Presenter {

        fun initFragment(viewPager: ViewPager,index:MagicIndicator)

    }

    interface Model {

    }

}