package com.xsvideoLive.www.callback;

import com.xsvideoLive.www.net.bean.MyFollowResult;

public interface OnFriendsSelectCallback {
    void onFriendsSelect(MyFollowResult result);
    void onFriendsClick(MyFollowResult result);
}
