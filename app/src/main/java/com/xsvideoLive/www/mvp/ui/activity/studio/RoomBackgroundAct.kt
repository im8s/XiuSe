package com.xsvideoLive.www.mvp.ui.activity.studio

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.RoomBackgroundConstant
import com.xsvideoLive.www.mvp.presenter.RoomBackgroundPresenter
import com.xsvideoLive.www.mvp.ui.adapter.RoomBgAdapter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.RoomBgEntity
import com.xsvideoLive.www.utils.ActStartUtils
import com.xsvideoLive.www.view.GridDecoration
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class RoomBackgroundAct : BaseMvpActivity<RoomBackgroundPresenter>(), RoomBackgroundConstant.View, TitleBarClickCallback, OnRefreshLoadMoreListener {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.refresh)
    lateinit var mRefresh: SmartRefreshLayout

    @BindView(R.id.rcy_room_bg)
    lateinit var mRcyRoomBg: RecyclerView

    private val mAdapter by lazy { RoomBgAdapter() }

    private var mRoomId: String? = null

    override fun setLayoutId(): Int = R.layout.activity_room_background


    override fun createPresenter(): RoomBackgroundPresenter = RoomBackgroundPresenter()


    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        mRoomId = intent.extras.getString(Constant.ROOM_ID)
        init()
        mPresenter.getRoomBg(null)
    }

    fun init() {
        mTitleBar.setOnClickCallback(this)
        mRefresh.setOnRefreshLoadMoreListener(this)

        mRcyRoomBg.layoutManager = GridLayoutManager(this, 3)
        mRcyRoomBg.addItemDecoration(GridDecoration(15, 20, 30, 30, 3))
        mRcyRoomBg.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->

            val item = mAdapter.getItem(position)
            var bundle = Bundle()
            bundle.putSerializable(Constant.ROOM_BG, item)
            bundle.putString(Constant.ROOM_ID, mRoomId)
            ActStartUtils.startAct(this@RoomBackgroundAct, RoomBgAct::class.java, bundle)


        }

    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        mPresenter.mPage = mPresenter.initPage
        mPresenter.getRoomBg(refreshLayout)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        mPresenter.getRoomBg(refreshLayout)
    }

    override fun refreshSuccess(refresh: RefreshLayout?, roomBg: MutableList<RoomBgEntity>) {
        refresh?.finishRefresh()

        mAdapter.setList(roomBg)

        if (roomBg.size <= 0) {
//            mAdapter.setEmptyView(R.layout.fans_empty)
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.resetNoMoreData()
        }
    }

    override fun loadMoreSuccess(refresh: RefreshLayout?, roomBg: MutableList<RoomBgEntity>) {
        if (roomBg.size <= 0) {
            refresh?.finishLoadMoreWithNoMoreData()
        } else {
            refresh?.finishLoadMore()
            mAdapter.addData(roomBg)
        }
    }

    override fun onError(msg: String?) {

    }

    override fun titleLeftClick() {
        finish()
    }

    override fun titleRightClick(status: Int) {

    }


}
