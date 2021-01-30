package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.SystemNotic;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;

import java.util.List;

public interface SpeakContract {

    interface View extends BaseView {

        void onSystemNoicSuccess(List<SystemNotic> notics);

        void onError(String msg);

    }

    interface Presenter {

       void initIM(ConversationLayout mConversationLayout);

       void getSystemNoic();
    }

    interface Model {

        HttpObservable<BaseResponse<HomeRoomResult<List<SystemNotic>>>> getSystemNotic(String current, String size);

    }

}
