package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeRoomResult;
import com.xsvideoLive.www.net.bean.TopicMsg;

import java.util.List;

public interface SquareConstract {

    interface View extends BaseView{

        void onTopicMsgSuccess(List<TopicMsg> topicMsgs);

        void onError(String msg);
    }

    interface Presenter {

        void getTopicMsg(String userId);

    }

    interface Model {
        /**
         * 话题列表
         * @param current
         * @param size
         * @param userId
         * @return
         */
        HttpObservable<BaseResponse<HomeRoomResult<List<TopicMsg>>>> getTopicMsg(String current,String size,String userId);
    }
}
