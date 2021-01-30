package com.xsvideoLive.www.mvp.contract;

import android.webkit.WebView;
import android.widget.TextView;

import com.xsvideoLive.www.base.BaseView;

public interface WebContract {

    interface View extends BaseView {

        void onError(String msg);

    }

    interface Presenter {

        void initWebView(WebView webView, TextView textView);


    }

    interface Model {

    }


}
