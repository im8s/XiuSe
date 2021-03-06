package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.MyFollowResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface AttentionContract {

    interface View extends BaseView{

        /**
         * 请求关注列表刷新成功
         * @param myFollows
         */
        void refreshSuccess(RefreshLayout refresh,List<MyFollowResult> myFollows);

        /**
         * 请求关注列表加载成功
         * @param refresh
         * @param myFollows
         */
        void loadMoreSuccess(RefreshLayout refresh, List<MyFollowResult> myFollows);

        /**
         * 接口请求失败
         * @param msg
         */
        void onError(String msg);

    }

    interface Presenter {

        /**
         * 请求查询关注列表接口
         * @param refresh
         */
        void refreshFollow(RefreshLayout refresh);

    }

    interface Model {

        /**
         * 查询·关注用户列表
         * @param current
         * @param size
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<MyFollowResult>>>> getMyFollow(String current, String size);

    }
}
