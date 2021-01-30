package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.FamilyRankResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

public interface FamilySquareContract {


    interface View extends BaseView {

        /**
         * 成功获取家族魅力榜
         *
         * @param results
         */
        void familyRankSuccess(List<FamilyRankResult> results,RefreshLayout refreshLayout);

        /**
         * 请求失败
         * @param refreshLayout
         */
        void onError(RefreshLayout refreshLayout,String msg);

    }

    interface Presenter {
        /**
         * 获取家族魅力排行榜
         *
         * @return
         */
        void getFamilyRank(RefreshLayout refreshLayout);
    }

    interface Model {
        /**
         * 获取家族魅力排行榜
         *
         * @return
         */
        HttpObservable<BaseResponse<List<FamilyRankResult>>> getFamilyRank();
    }
}
