package com.xsvideoLive.www.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.base.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface MyOutfitConstract {

    interface View extends BaseView{

    }

    interface Presenter {
        /**
         * 初始化fragment
         * @param viewPager
         * @param indicator
         */
        void initFragments(ViewPager viewPager, MagicIndicator indicator);
    }

    interface Model {

    }

}
