package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.RoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface HomeRadioContract {

    interface View extends BaseView {

        /**
         * 刷新数据
         *
         * @param refreshLayout
         */
        void refreshRoom(RefreshLayout refreshLayout, List<RoomResult> roomResults);

        /**
         * 加载更多
         *
         * @param refreshLayout
         */
        void loadMoreRoom(RefreshLayout refreshLayout, List<RoomResult> roomResults);

        /**
         * 获取数据失败
         *
         * @param refreshLayout
         * @param msg
         */
        void onError(RefreshLayout refreshLayout, String msg);

    }

    interface Presenter {

        /**
         * 获取数据
         *
         * @param refreshLayout
         */
        void getRooms(RefreshLayout refreshLayout,String tpeId);


    }

    interface Model {
        /**
         * 获取首页当前分类中开放的房间
         * @param current
         * @param size
         * @param typeId
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<RoomResult>>>> getRooms(String current, String size, String typeId);
    }

}
