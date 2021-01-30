package com.xsvideoLive.www.net;

import android.text.TextUtils;

import com.xsvideoLive.www.base.BaseApplication;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.utils.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原先的请求
        Request originalRequest = chain.request();
        if(!originalRequest.url().toString().startsWith(BaseApplication.HTTP_BASE_URL)){
            return chain.proceed(originalRequest);
        }

        Request.Builder requestBuilder = originalRequest.newBuilder();


        String token = SPUtils.getInstance().getString(SpConstant.APP_TOKEN);

        if(!TextUtils.isEmpty(token)){
            requestBuilder.header("Authorization", token);
        }

        Request request = requestBuilder.build();
        Response proceed = chain.proceed(request);

        return proceed;

    }

}
