package com.xsvideoLive.www.mvp.contract;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.AvatarResult;
import com.xsvideoLive.www.net.bean.BaseResponse;

import okhttp3.MultipartBody;

public interface CompileMateriaContract {

    interface View extends BaseView{

        /**
         * 照相机
         */
        void camera();

        /**
         * 图库
         */
        void gallery();

        /**
         * 设置时间
         *
         * @param date
         */
        void setTime(String date);

        /**
         * 上传头像请求成功
         * @param result
         */
        void avatarSuccess(AvatarResult result);

        /**
         * 请求失败
         * @param msg
         */
        void onError(String msg);

        /**
         * 请求修改生日接口成功
         * @param birthday
         */
        void birthdaySuccess(String birthday);

    }

    interface Presenter {

        /**
         * 图片选择
         */
        void selectPhoto();

        /**
         * 时间选择
         */
        void selectTime(String date);

        /**
         * 上传头像
         * @param avatarPath
         */
        void upAvatar(String avatarPath);

        /**
         * 修改生日
         * @param birthday
         */
        void editBirthday(String birthday);

    }

    interface Model {
        /**
         * 修改头像
         * @param file
         * @return
         */
        HttpObservable<BaseResponse<AvatarResult>> upAvatar(MultipartBody.Part file);

        /**
         * 修改生日
         * @param birthday
         * @return
         */
        HttpObservable<BaseResponse<String>> editBirthday(String birthday);
    }
}
