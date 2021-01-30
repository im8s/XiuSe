package com.xsvideoLive.www.mvp.contract;

import android.webkit.WebView;

import com.xsvideoLive.www.base.BaseView;
import com.xsvideoLive.www.net.HttpObservable;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.NobleResult;

import java.util.List;

public interface NobleWebContract {

    interface View extends BaseView {

        void nobleSuccess(List<NobleResult> results);

        void onError(String msg);

    }

    interface Presenter {

        void initWebView(WebView webView);

        void getNobleList();

    }

    interface Model {

        HttpObservable<BaseResponse<List<NobleResult>>> getNobleList();

    }


}
