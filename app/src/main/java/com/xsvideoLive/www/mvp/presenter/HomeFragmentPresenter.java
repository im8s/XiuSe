package com.xsvideoLive.www.mvp.presenter;

import android.text.TextUtils;

import androidx.viewpager.widget.ViewPager;

import com.xsvideoLive.www.base.BaseApplication;
import com.xsvideoLive.www.base.BaseFragment;
import com.xsvideoLive.www.base.BasePresenter;
import com.xsvideoLive.www.callback.HomeSortCallback;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.constant.SpConstant;
import com.xsvideoLive.www.mvp.contract.HomeFragmentContract;
import com.xsvideoLive.www.mvp.model.HomeFragmentModel;
import com.xsvideoLive.www.mvp.ui.adapter.HomeBannerAdapter;
import com.xsvideoLive.www.mvp.ui.adapter.HomeIndexAdapter;
import com.xsvideoLive.www.net.HttpObserver;
import com.xsvideoLive.www.net.bean.BannerResult;
import com.xsvideoLive.www.net.bean.BaseResponse;
import com.xsvideoLive.www.net.bean.HomeSortResult;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.SPUtils;
import com.tencent.liteav.trtcvoiceroom.model.TRTCVoiceRoom;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.List;

public class HomeFragmentPresenter extends BasePresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {

    private HomeFragmentModel model;

    public HomeFragmentPresenter() {
        model = new HomeFragmentModel();
    }

    @Override
    public void getBanner(Banner mBanner) {
        model.getBanner()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<BannerResult>>() {


                    @Override
                    protected void success(List<BannerResult> banners, BaseResponse<List<BannerResult>> response) {

                        String sig = SPUtils.getInstance().getString(SpConstant.APP_SIG);

                        if (!TextUtils.isEmpty(sig) && SPUtils.getInstance().getUserInfo() != null) {
                            TRTCVoiceRoom trtcVoiceRoom = TRTCVoiceRoom.sharedInstance(BaseApplication.getApplication());
                            UserBean userInfo = SPUtils.getInstance().getUserInfo();
                            trtcVoiceRoom.login(Constant.IM_APP_KEY, userInfo.getId(), sig, null);
                        }

                        if (banners == null || mBanner == null) return;

                        HomeBannerAdapter adapter = new HomeBannerAdapter(((BaseFragment) getView()).getActivity(), banners);
                        adapter.setOnBannerListener((OnBannerListener<BannerResult>) (data, position) -> getView().bannerClick(data));


                        mBanner.setAdapter(adapter, true)
                                .setIndicator(new CircleIndicator(((BaseFragment) getView()).getActivity()))
                                .start();


                    }

                    @Override
                    protected void error(String msg) {

                    }
                });
    }

    @Override
    public void getSort(MagicIndicator indicator, ViewPager mViewPager) {
        model.getHomeSort()
                .execOnThread(getView().getActLifeRecycle(), new HttpObserver<List<HomeSortResult>>() {
                    @Override
                    protected void success(List<HomeSortResult> bean, BaseResponse<List<HomeSortResult>> response) {


                        HomeSortResult sortResult = new HomeSortResult();
                        sortResult.setRoomTypeName("全部");
                        bean.add(0, sortResult);

                        CommonNavigator commonNavigator = new CommonNavigator(((BaseFragment) getView()).getActivity());
                        commonNavigator.setAdapter(new HomeIndexAdapter(bean, indicator, new HomeSortCallback<HomeSortResult>() {

                            @Override
                            public void onOpenSuccess(HomeSortResult result, int index) {
                                getView().sortClick(index);
                            }

                        }));

                        indicator.setNavigator(commonNavigator);

                        getView().sortSuccess(bean);

                    }

                    @Override
                    protected void error(String msg) {

                    }
                });
    }
}
