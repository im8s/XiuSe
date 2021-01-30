package com.xsvideoLive.www.mvp.ui.fragment.msg;

import android.os.Bundle;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.mvp.contract.MsgContract;
import com.xsvideoLive.www.mvp.model.MsgPresenter;
import com.xsvideoLive.www.view.ScrollViewPager;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

public class MsgFragment extends BaseMvpFragment<MsgPresenter> implements MsgContract.View {

    @BindView(R.id.magicindicator)
    MagicIndicator magicindicator;
    @BindView(R.id.s_view_pager)
    ScrollViewPager mViewPager;

    @Override
    protected MsgPresenter createPresenter() {
        return new MsgPresenter();
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_msg;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mPresenter.attachView(this);

        mPresenter.initFragments(magicindicator,mViewPager);

    }
}
