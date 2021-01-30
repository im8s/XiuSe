package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.RecordResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface FamilyRecordContract {

    interface View extends BaseView {

        /**
         * 刷新数据
         *
         * @param refreshLayout
         */
        void refresh(RefreshLayout refreshLayout, List<RecordResult> roomResults);

        /**
         * 加载更多
         *
         * @param refreshLayout
         */
        void loadMore(RefreshLayout refreshLayout, List<RecordResult> roomResults);

        /**
         * 接口请求失败
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
        void getRecord(RefreshLayout refreshLayout, String userId);
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<RecordResult>>>> getapplyList(String userId, String begin, String size);
    }
}
