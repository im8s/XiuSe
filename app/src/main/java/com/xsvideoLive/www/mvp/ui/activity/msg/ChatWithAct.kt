package com.xsvideoLive.www.mvp.ui.activity.msg

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.constant.EventConstant
import com.xsvideoLive.www.mvp.contract.ChatWithContract
import com.xsvideoLive.www.mvp.presenter.ChatWithPresenter
import com.xsvideoLive.www.net.bean.Event
import net.lucode.hackware.magicindicator.MagicIndicator

class ChatWithAct : BaseMvpActivity<ChatWithPresenter>(), ChatWithContract.View {

    @BindView(R.id.magic_indicator)
    lateinit var magicIndicator: MagicIndicator

    @BindView(R.id.ll_index)
    lateinit var llIndex: LinearLayout

    @BindView(R.id.ns_view_pager)
    lateinit var nsViewPager: ViewPager

    override fun setLayoutId(): Int = R.layout.activity_chat_with

    override fun createPresenter(): ChatWithPresenter = ChatWithPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mPresenter.initFragment(magicIndicator, nsViewPager)
    }

    @OnClick(R.id.view_finish)
    fun onClick(v: View?){
        when(v?.id) {
            R.id.view_finish -> {
                finish()
            }
        }
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    override fun receiveEvent(event: Event<*>?) {
      if (event?.code == EventConstant.FINIS_CHAT_WITH){
          finish()
      }
    }


}