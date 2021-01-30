package com.xsvideoLive.www.helper;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpClient;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.CreateRoomResult;

public class RoomManager {

    private static final RoomManager mOurInstance = new RoomManager();

    public static final int ERROR_CODE_UNKNOWN = -1;


    public static RoomManager getInstance() {
        return mOurInstance;
    }

    private RoomManager() {


    }


    public void enterRoom(BaseView view, String roomId, ActionCallback callback) {

        HttpClient.getApi().enterRoom(roomId)
                .execOnThread(view.getActLifeRecycle(), new HttpObserver<CreateRoomResult>() {
                    @Override
                    protected void success(CreateRoomResult bean, BaseResponse<CreateRoomResult> response) {

                        if (bean != null) {
                            if (callback != null) {
                                callback.onSuccess(bean);
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailed(ERROR_CODE_UNKNOWN, "进入房间失败");
                            }
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        if (callback != null) {
                            callback.onFailed(ERROR_CODE_UNKNOWN, "未知错误");
                        }
                    }
                });

    }

    /**
     * 创建房间
     *
     * @param view
     * @param callback
     */
    public void createRoom(BaseView view, ActionCallback callback) {


        HttpClient.getApi().createRoom()
                .execOnThread(view.getActLifeRecycle(), new HttpObserver<CreateRoomResult>() {

                    @Override
                    protected void success(CreateRoomResult bean, BaseResponse<CreateRoomResult> response) {

                        if (bean != null) {
                            if (callback != null) {
                                callback.onSuccess(bean);
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailed(ERROR_CODE_UNKNOWN, "房间创建失败");
                            }
                        }
                    }

                    @Override
                    protected void error(String msg) {
                        if (callback != null) {
                            callback.onFailed(ERROR_CODE_UNKNOWN, "未知错误");
                        }
                    }
                });

    }

    // 操作回调
    public interface ActionCallback {

        void onSuccess(CreateRoomResult bean);

        void onFailed(int code, String msg);
    }


}
