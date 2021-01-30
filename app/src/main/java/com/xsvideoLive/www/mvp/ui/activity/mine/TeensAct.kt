package com.xsvideoLive.www.mvp.ui.activity.mine

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.constant.URLConstant
import com.xsvideoLive.www.mvp.contract.TeensContract
import com.xsvideoLive.www.mvp.presenter.TeensPresenter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils


class TeensAct : BaseMvpActivity<TeensPresenter>(), TeensContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.iv_teens)
    lateinit var mIvTeens: ImageView

    @BindView(R.id.tv_status_open)
    lateinit var mTvStatusOpen: TextView

    @BindView(R.id.rl_start)
    lateinit var mRlStart: RelativeLayout

    @BindView(R.id.tv_start)
    lateinit var mTvStart: TextView


    var isRequest = false

    var isOpen = false

    override fun setLayoutId(): Int = R.layout.activity_teens

    override fun createPresenter(): TeensPresenter = TeensPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.getTeenStatus(SPUtils.getInstance().userInfo.id)
    }

    override fun teenSuccess(status: String) {
        isRequest = true
        if (status == "0") isOpen = false else if (status == "1") isOpen = true
        setOpenStatus()
    }

    override fun onError(msg: String) {
        isRequest = false
        ToastUtils.showShort(msg)
    }

    private fun setOpenStatus() {
        if (isOpen) {
            mIvTeens.setImageResource(R.mipmap.icon_teens_on)
            mTvStatusOpen.setTextColor(ContextCompat.getColor(this, R.color.color_00a2ff))
            mRlStart.setBackgroundResource(R.drawable.shape_teens_close)
            mTvStart.setTextColor(ContextCompat.getColor(this, R.color.color_484848))

        } else {
            mIvTeens.setImageResource(R.mipmap.icon_teens_off)
            mTvStatusOpen.setTextColor(ContextCompat.getColor(this, R.color.color_484848))
            mRlStart.setBackgroundResource(R.drawable.shape_teens_start)
            mTvStart.setTextColor(ContextCompat.getColor(this, R.color.color_black))
        }
    }

    @OnClick(R.id.rl_start, R.id.tv_aegis)
    fun onClick(v: View) {
        when (v.id) {
            R.id.rl_start -> {
                if (isRequest) {

                    if (isOpen) {    //已开启了青少年保护

                        ActStartUtils.startAct(this, TeensCloseAct::class.java)


                    } else {         //未开启青少年保护

                        ActStartUtils.startAct(this, TeensOpenAct::class.java)
                    }

                }

            }

            R.id.tv_aegis -> {
                var bundle = Bundle()
                bundle.putString(Constant.WEB_URL, URLConstant.PROTECTION_URL)
                bundle.putString(Constant.WEB_TITLE, "护苗计划")
                ActStartUtils.startAct(this, WebActivity::class.java, bundle)
            }
        }
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }

}
