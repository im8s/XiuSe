package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.GradeContract
import com.xsvideoLive.www.mvp.presenter.GradePresenter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import net.lucode.hackware.magicindicator.MagicIndicator

class GradeAct : BaseMvpActivity<GradePresenter>(), GradeContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.index)
    lateinit var mIndex: MagicIndicator

    @BindView(R.id.viewpager)
    lateinit var mViewPager:ViewPager

    private var mGradeKey = ""

    override fun setLayoutId(): Int = R.layout.activity_grade

    override fun createPresenter(): GradePresenter = GradePresenter()


    override fun initView(savedInstanceState: Bundle?) {
        mGradeKey =  intent.extras.getString(Constant.GRADE_KEY)
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        mPresenter.initFragment(mViewPager,mIndex)

        if (mGradeKey == Constant.GRADE_USER_LV) {
            mViewPager.currentItem = 0
        } else if (mGradeKey == Constant.GRADE_CHARM_LV) {
            mViewPager.currentItem = 1
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }
}