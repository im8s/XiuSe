package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.BlackListMineBean;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface BlackListMineContract {

    interface View extends BaseView {

        /**
         * 请求黑名单列表成功
         */
        void refreshSuccess(RefreshLayout refresh, List<BlackListMineBean> roomBlack);

        /**
         * 请求黑名单列表加载成功
         */
        void loadMoreSuccess(RefreshLayout refresh, List<BlackListMineBean> roomBlack);


        void removeSuccess(int position ,Integer status);


        void onError(String msg);

    }

    interface Presenter {

        /**
         * @param refresh
         * @param currentId     房间id/用户id （黑名单类型为1填房间id，为2填当前用户id）
         */
        void getBlackList(RefreshLayout refresh ,String currentId);


        void removeBlackMine(String id, int position);


    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<BlackListMineBean>>>> getBlackListMine(String current, String size, String blackListType, String currentId);

        HttpObservable<BaseResponse<Integer>> removeBlackMine(String id);
    }
}
