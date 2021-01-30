package com.xsvideoLive.www.mvp.ui.activity.square

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.TopicNotifcContract
import com.xsvideoLive.www.mvp.presenter.TopicNotifcPresenter
import com.xsvideoLive.www.mvp.ui.adapter.TopicNotificAdapter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.TopicMsg
import com.xsvideoLive.www.net.bean.UserBean
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class TopicNotificAct : BaseMvpActivity<TopicNotifcPresenter>(), TopicNotifcContract.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: RefreshLayout

    @BindView(R.id.rcy_msg)
    lateinit var mRcyMsg: RecyclerView

    private var userInfo: UserBean? = null

    private val mAdapter by lazy { TopicNotificAdapter() }

    override fun setLayoutId(): Int = R.layout.activity_topic_notific

    override fun createPresenter(): TopicNotifcPresenter = TopicNotifcPresenter()

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        userInfo = SPUtils.getInstance().userInfo
        init()
        mPresenter.getTopicList(null, userInfo?.id)
    }

    private fun init() {
        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)

        mRcyMsg.layoutManager = LinearLayoutManager(this)
        mRcyMsg.adapter = mAdapter

        mAdapter.setOnItemClickListener{
            adapter, view, position ->
            var item = mAdapter.getItem(position)
            var bundle = Bundle()
            bundle.putString(Constant.TOPIC_ID, item.topicId)
            ActStartUtils.startAct(this, MessageDetailsAct::class.java, bundle)
        }

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter?.getTopicList(refreshLayout, userInfo?.id)

    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getTopicList(refreshLayout, userInfo?.id)

    }

    override fun refreshSuccess(refresh: RefreshLayout?, topicMsgs: MutableList<TopicMsg>) {
        refresh?.finishRefresh()

        mAdapter.setList(topicMsgs)

        if (topicMsgs.size <= 0) {
//            mAdapter.setEmptyView(R.layout.fans_empty)
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }
    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, topicMsgs: MutableList<TopicMsg>) {
        if (topicMsgs.size <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
            mAdapter.addData(topicMsgs)
        }

    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {
    }


}