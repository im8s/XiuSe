package com.xsvideoLive.www.base;

import android.app.Application;
import android.content.Context;
import android.net.http.HttpResponseCache;
import android.text.TextUtils;
import com.xsvideoLive.www.BuildConfig;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.helper.ConfigHelper;
import com.xsvideoLive.www.helper.RoomFaceManager;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.GlideImageLoader;
import com.xsvideoLive.www.utils.SPUtils;
import com.lzf.easyfloat.EasyFloat;
import com.lzy.ninegrid.NineGridView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qcloud.tim.uikit.TUIKit;

import java.io.File;
import java.io.IOException;


public class BaseApplication extends Application {

    public static String HTTP_BASE_URL = BuildConfig.APP_BASE_URL;

    private static Context application;

    private static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initSVAG();
        initWeChat();
        createNormalRefreshHeader();
        NineGridView.setImageLoader(new GlideImageLoader());
        EasyFloat.init(this, false);
        initRoomFace();
    }

    private void initSVAG() {
        File cacheDir = new File(getApplicationContext().getCacheDir(), "http");
        try {
            HttpResponseCache.install(cacheDir, 1920 * 1200 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRoomFace() {
        RoomFaceManager.Companion.loadFaceFiles();
    }

    private void initWeChat() {
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID);

        /**
         * TUIKit的初始化函数
         *
         * @param context  应用的上下文，一般为对应应用的ApplicationContext
         * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
         * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
         */
        TUIKit.init(this, Constant.IM_APP_KEY, new ConfigHelper().getConfigs());

        String sig = SPUtils.getInstance().getString(SpConstant.APP_SIG);

        if (!TextUtils.isEmpty(sig) && SPUtils.getInstance().getUserInfo() != null) {
            TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(this);
            UserBean userInfo = SPUtils.getInstance().getUserInfo();
            trtcVoiceRoom.login(Constant.IM_APP_KEY, userInfo.getId(), sig, null);
        }

    }

    private void createNormalRefreshHeader() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, R.color.color_5e5e5e);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static Context getApplication() {
        return application;
    }

    public static IWXAPI getApi() {
        return api;
    }


}
