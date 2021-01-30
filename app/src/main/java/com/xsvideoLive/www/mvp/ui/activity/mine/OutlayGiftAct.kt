package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.mvp.contract.OutlayGiftContract
import com.xsvideoLive.www.mvp.presenter.OutlayGiftPresenter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import net.lucode.hackware.magicindicator.MagicIndicator

class OutlayGiftAct : BaseMvpActivity<OutlayGiftPresenter>(), OutlayGiftContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var titleBar: TitleBarView

    @BindView(R.id.index)
    lateinit var index: MagicIndicator

    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager

    override fun createPresenter(): OutlayGiftPresenter = OutlayGiftPresenter()

    override fun setLayoutId(): Int = R.layout.activity_outlay_gift

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        titleBar.setOnClickCallback(this)

        mPresenter.initFragment(viewPager = viewPager, index = index)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}
