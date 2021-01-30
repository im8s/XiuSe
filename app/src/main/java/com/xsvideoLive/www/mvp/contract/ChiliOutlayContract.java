package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.ChiliIncomeResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface ChiliOutlayContract {

    interface View extends BaseView {

        void onError(String msg);

        /**
         * 设置时间
         *
         * @param date
         */
        void setTime(String date);

        /**
         * 刷新成功
         * @param refresh
         * @param incomeList
         */
        void refreshSuccess(RefreshLayout refresh, List<ChiliIncomeResult> incomeList);

        /**
         * 加载成功
         * @param refresh
         * @param incomeList
         */
        void loadMoreSuccess(RefreshLayout refresh, List<ChiliIncomeResult> incomeList);

    }

    interface Presenter {
        /**
         * 时间选择
         */
        void selectTime(String date);

        /**
         * 获取收入列表
         * @param date
         */
        void getOutlayList(RefreshLayout refresh, String date);
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<ChiliIncomeResult>>>> getOutlayList(String current, String size, String date);

    }
}
