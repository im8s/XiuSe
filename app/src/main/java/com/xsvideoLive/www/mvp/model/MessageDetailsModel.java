package com.xsvideoLive.www.mvp.model;

import com.xsvideoLive.www.mvp.contract.MessageDetailsContract;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CommentResult;
import com.xsvideoLive.www.net.bean.FriendsCircleResult;
import com.xsvideoLive.www.net.bean.HomeRoomResult;

import java.util.List;

public class MessageDetailsModel implements MessageDetailsContract.Model {
    @Override
    public HttpObservable<BaseResponse<HomeRoomResult<List<CommentResult>>>> getComment(String current, String size, String topicId) {
        return HttpClient.getApi().getComment(current, size, topicId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> fabulous(String topicId) {
        return HttpClient.getApi().fabulous(topicId);
    }

    @Override
    public HttpObservable<BaseResponse<String>> comment(String topicId, String commentContent, String commentType, String fatherId) {
        return HttpClient.getApi().comment(topicId, commentContent, commentType, fatherId);
    }

    @Override
    public HttpObservable<BaseResponse<CommentResult>> getSecondComment(String commentId) {
        return HttpClient.getApi().getSecondComment(commentId);
    }

    @Override
    public HttpObservable<BaseResponse<FriendsCircleResult>> getFriendOne(String topicId) {
        return HttpClient.getApi().getFriendOne(topicId);
    }
}
