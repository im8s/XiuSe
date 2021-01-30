package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.MyFamilyResult;

public interface MaterialContract {

    interface View extends BaseView {

        void onSuccess(MyFamilyResult familyResult);

        void onError(String msg);

    }

    interface Presenter {

        void getMyFamily(String userId);
    }

    interface Model{
        /**
         * 根据userid查询家族信息
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<MyFamilyResult>> getFamilyInfo(String userId);
    }

}
