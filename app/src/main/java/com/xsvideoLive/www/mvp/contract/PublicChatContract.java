package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.GiftAllResult;
import com.tencent.liteav.trtcvoiceroom.ui.base.VoiceRoomSeatEntity;

public interface PublicChatContract {

    interface View extends BaseView {

        void onUserInfoSuccess(VoiceRoomSeatEntity.UserInfo userInfo);

        void onUserFollow(VoiceRoomSeatEntity.UserInfo userInfo,Integer sattus);

        void onGiftSuccess(GiftAllResult roomGift);

        void onNobleSuccess(int noble);

        void onFollow(String status);

        void onUnFollow(String status);

        void onError(String msg);

    }

    interface Presenter {

        void getUserInfo(String userId);

        void getFollowStatus(VoiceRoomSeatEntity.UserInfo userInfo);

        void getGift();

        void getSelfNoble();

        /**
         * 关注或是取消关注
         */
        void follow(String userId, String status);

    }

    interface Model {

        HttpObservable<BaseResponse<VoiceRoomSeatEntity.UserInfo>> getUserInfo(String userId);

        /**
         * 查询是否关注某用户
         *
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<Integer>> queryFollow(String userId);

        /**
         * 获取房间礼物
         */
        HttpObservable<BaseResponse<GiftAllResult>> getGift();

        HttpObservable<BaseResponse<Integer>> getNoble();

        /**
         * 关注或是取消关注
         */
        HttpObservable<BaseResponse<String>> follow(String userId,String status);
    }

}
