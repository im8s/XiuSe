package com.xsvideoLive.www.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.base.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface Squarev1Constract {

    interface View extends BaseView{

    }

    interface Presenter {

        void initFragment(MagicIndicator indicator, ViewPager viewPager);

    }

    interface Model {

    }
}
