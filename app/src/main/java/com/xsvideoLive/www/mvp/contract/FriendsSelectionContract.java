package com.xsvideoLive.www.mvp.contract;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.callback.OnFriendsSelectCallback;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;

import net.lucode.hackware.magicindicator.MagicIndicator;

public interface FriendsSelectionContract {


    interface View extends BaseView {

        /**
         * 赠送接口请求成功
         * @param status
         */
        void giveAwaySuccess(Integer status);

        /**
         * 接口请求失败
         *
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 初始化fragments
         * @param indicator
         * @param viewPager
         * @param callback
         */
        void initFragment(MagicIndicator indicator, ViewPager viewPager, OnFriendsSelectCallback callback);

        /**
         * 赠送头饰或座驾
         * @param deckId
         * @param userId
         */
        void giveAway(String deckId,String userId);
    }

    interface Model {

        /**
         * 购买头饰/座驾
         *
         * @param deckId
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<Integer>> buyDeck(String deckId, String userId);
    }


}
