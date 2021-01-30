package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface DynamicContract {
    interface View extends BaseView {

        /**
         * 刷新数据
         *
         * @param refreshLayout
         */
        void refresh(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults);

        /**
         * 加载更多
         *
         * @param refreshLayout
         */
        void loadMore(RefreshLayout refreshLayout, List<FriendsCircleResult> roomResults);

        /**
         * 获取数据失败
         *
         * @param refreshLayout
         * @param msg
         */
        void onError(RefreshLayout refreshLayout, String msg);

        /**
         * 点赞成功
         *
         * @param msg
         */
        void fabulousSuccess(String msg);

        /**
         * 删除话题成功
         *
         * @param status
         */
        void onDeleteSuccess(String status,FriendsCircleResult result);

    }

    interface Presenter {
        /**
         * 获取所有朋友圈数据数据
         *
         * @param refreshLayout
         */
        void getFriendsCircle(RefreshLayout refreshLayout, String userId);

        /**
         * 点赞/取消
         *
         * @param topicId 朋友圈ID
         */
        void fabulous(String topicId);

        /**
         * 删除话题
         *
         * @param topicId
         */
        void deleteTopic(String topicId,FriendsCircleResult result);
    }

    interface Model {

        /**
         * 获取所有朋友圈
         *
         * @param current
         * @param size
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<FriendsCircleResult>>>> getFriendsCircle(String current, String size, String userId);

        /**
         * 点赞/取消
         *
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<String>> fabulous(String topicId);

        /**
         * 删除话题
         * @param topicId
         * @return
         */
        HttpObservable<BaseResponse<String>> deleteTopic(String topicId);

    }
}
