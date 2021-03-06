package com.xsvideoLive.www.mvp.ui.fragment.mine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpFragment
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.constant.URLConstant
import com.xsvideoLive.www.mvp.contract.MyCharmLvConstract
import com.xsvideoLive.www.mvp.presenter.MyCharmLvPresenter
import com.xsvideoLive.www.mvp.ui.activity.mine.WebActivity
import com.xsvideoLive.www.net.bean.GradeResult
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.GlideAppUtil
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils

class MyCharmLvFragment : BaseMvpFragment<MyCharmLvPresenter>(), MyCharmLvConstract.View {

    @BindView(R.id.iv_avatar)
    lateinit var mIvAvatar: ImageView

    @BindView(R.id.tv_lv_num)
    lateinit var mTvLvNum: TextView

    @BindView(R.id.pb_lv)
    lateinit var mPbLv: ProgressBar

    @BindView(R.id.tv_experience)
    lateinit var mTvExperience: TextView

    @BindView(R.id.tv_distance)
    lateinit var mTvDistance: TextView

    @BindView(R.id.tv_description)
    lateinit var mTvDescription: TextView


    companion object {
        fun newInstance(): MyCharmLvFragment {

            return MyCharmLvFragment()
        }
    }

    override fun createPresenter(): MyCharmLvPresenter = MyCharmLvPresenter()

    override fun setLayoutId(): Int = R.layout.fragment_my_charm_lv

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        GlideAppUtil.loadImage(activity, SPUtils.getInstance().userInfo.userPicture,mIvAvatar)
        mPresenter.getCharmGradeUserGrade()
    }

    override fun onCharmGradeSuccess(result: GradeResult?) {
        if (result == null) return
        mTvLvNum.text = "Lv.${result.level}"

        if (result.rate != null) {
            var rate = result.rate
            var schedule = (rate!! * 100).toInt()
            mPbLv.progress = schedule
        }

        mTvExperience.text = "${result.level}"

        mTvDistance.text = "${result.disparity}"
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    @OnClick(R.id.tv_description)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_description -> {
                var bundle = Bundle()
                bundle.putString(Constant.WEB_URL,URLConstant.CHARM_URL)
                bundle.putString(Constant.WEB_TITLE,"????????????")
                ActStartUtils.startAct(activity, WebActivity::class.java,bundle)
            }
        }
    }
}