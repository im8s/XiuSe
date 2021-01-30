package com.xsvideoLive.www.mvp.contract;

import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.callback.OnCarTestDive;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.opensource.svgaplayer.SVGAImageView;

public interface HeadgearContract {

    interface View extends BaseView {

        /**
         * 获取个人基本资料成功
         * @param result
         */
        void MineSuccess(MineReslut result);

        /**
         * 获取个人基本资料失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        void initFragments(ViewPager mViewPager, TextView tvHeadgear, TextView tvCar, MineReslut mResult,String chatType,String groupId, OnCarTestDive onCarTestDive);

        void animation(String url, SVGAImageView mSvga);

        void stopAnimation(SVGAImageView mSvga);

        /**
         * 获取个人基本资料
         * @param userId
         */
        void getMine(String userId);

    }

    interface Model {

        /**
         * 获取个人基本资料
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MineReslut>> getMine(String userId);
    }

}
