package com.xsvideoLive.www.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.base.BaseView;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface MsgContract {

    interface View extends BaseView {

    }

    interface Presenter {

        void initFragments(MagicIndicator indicator, ViewPager viewPager);

    }

    interface Model {

    }
}
