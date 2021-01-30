package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.Decks;
import com.xsvideoLive.www.net.bean.MineReslut;

import java.util.List;


public interface CarShopContract {

    interface View extends BaseView {

        /**
         * 请求座驾接口成功
         * @param decks
         */
        void onSuccess(List<Decks> decks);

        /**
         * 购买接口请求成功
         * @param status
         */
        void buyDeckSuccess(Integer status);

        /**
         * 赠送接口请求成功
         * @param status
         */
        void giveAwaySuccess(Integer status, Decks decks, MineReslut mResult);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {
        /**
         * 获取座驾
         */
        void getStoreCar();

        /**
         * 购买头饰或座驾
         * @param deckId
         */
        void buyDeck(String deckId);

        /**
         * 赠送头饰或座驾
         * @param deckId
         * @param userId
         */
        void giveAway(String deckId,String userId,Decks decks,MineReslut mResult);
    }

    interface Model {
        /**
         * 根据dectType 从商店获取头饰或是座驾
         * @param deckType
         * @return
         */
        HttpObservable<BaseResponse<List<Decks>>> store(Integer deckType);

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
