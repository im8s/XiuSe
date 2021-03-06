package com.xsvideoLive.www.mvp.ui.fragment.square;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.callback.HomeSortCallback;
import com.xsvideoLive.www.mvp.contract.SquareConstract;
import com.xsvideoLive.www.mvp.presenter.SquarePresenter;
import com.xsvideoLive.www.mvp.ui.activity.square.TopicNotificAct;
import com.xsvideoLive.www.mvp.ui.adapter.HomeRadioPageAdapter;
import com.xsvideoLive.www.mvp.ui.adapter.SquareIndexAdapter;
import com.xsvideoLive.www.net.bean.TopicMsg;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.ScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SquareFragment extends BaseMvpFragment<SquarePresenter> implements SquareConstract.View {


    @BindView(R.id.magicindicator)
    MagicIndicator magicindicator;
    @BindView(R.id.s_view_pager)
    ScrollViewPager sViewPager;
    @BindView(R.id.iv_tz)
    ImageView ivTz;
    @BindView(R.id.tv_notice_num)
    TextView tvNoticeNum;


    @Override
    protected SquarePresenter createPresenter() {
        return new SquarePresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_square;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        SquareV1Fragment squareV1Fragment = new SquareV1Fragment();
        HallFragment hallFragment = new HallFragment();
        FamilyFragment familyFragment = new FamilyFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(squareV1Fragment);
        fragments.add(hallFragment);
        fragments.add(familyFragment);

        FragmentManager supportFragmentManager = getChildFragmentManager();

        HomeRadioPageAdapter adapter = new HomeRadioPageAdapter(supportFragmentManager, fragments);

        sViewPager.setAdapter(adapter);
        sViewPager.setOffscreenPageLimit(3);

        List<String> tables = new ArrayList<>();
        tables.add("??????");
        tables.add("??????");
        tables.add("??????");


        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new SquareIndexAdapter(tables, magicindicator, new HomeSortCallback<String>() {

            @Override
            public void onOpenSuccess(String result, int index) {
                sViewPager.setCurrentItem(index);
            }

        }));

        magicindicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magicindicator, sViewPager);


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {// ???????????????????????????
            mPresenter.getTopicMsg(SPUtils.getInstance().getUserInfo().getId());
        }
    }

    @Override
    public void onTopicMsgSuccess(List<TopicMsg> topicMsgs) {
        if (topicMsgs == null || topicMsgs.size() <= 0) {
            tvNoticeNum.setVisibility(View.GONE);
        } else {
            TopicMsg topicMsg = topicMsgs.get(0);
            if (topicMsg == null) {
                tvNoticeNum.setVisibility(View.GONE);
            } else {
                Integer unReadCount = topicMsg.getUnReadCount();
                if (unReadCount == null || unReadCount <= 0) {
                    tvNoticeNum.setVisibility(View.GONE);
                } else {
                    tvNoticeNum.setVisibility(View.VISIBLE);
                    if (unReadCount > 99) {
                        tvNoticeNum.setText("99+");
                    } else {
                        tvNoticeNum.setText(unReadCount + "");
                    }
                }
            }
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick({R.id.ct_tz})
    public void onClick(View v) {
        ActStartUtils.startAct(getContext(), TopicNotificAct.class);
    }
}
