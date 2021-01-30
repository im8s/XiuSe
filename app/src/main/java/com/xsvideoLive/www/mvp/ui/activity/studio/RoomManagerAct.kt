package com.xsvideoLive.www.mvp.ui.activity.studio

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.callback.TitleBarClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.constant.EventConstant
import com.xsvideoLive.www.mvp.contract.RoomManagerContract
import com.xsvideoLive.www.mvp.presenter.RoomManagerPresenter
import com.xsvideoLive.www.mvp.ui.adapter.RoomManagerAdapter
import com.xsvideoLive.www.mvp.ui.view.TitleBarView
import com.xsvideoLive.www.net.bean.Event
import com.xsvideoLive.www.net.bean.RoomManagerEntity
import com.xsvideoLive.www.utils.EventBusUtil
import com.xsvideoLive.www.utils.ToastUtils
import com.xsvideoLive.www.view.TipsDialog

class RoomManagerAct : BaseMvpActivity<RoomManagerPresenter>(), RoomManagerContract.View, TitleBarClickCallback {

    @BindView(R.id.title_bar)
    lateinit var mTitleBar: TitleBarView

    @BindView(R.id.rcy_manager)
    lateinit var mRcyManager: RecyclerView

    private val mAdapter by lazy { RoomManagerAdapter() }

    private var mRoomId: String? = null

    override fun setLayoutId(): Int = R.layout.activity_room_manager


    override fun createPresenter(): RoomManagerPresenter {
        return RoomManagerPresenter()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mRoomId = intent.extras.getString(Constant.ROOM_ID)
        mPresenter.attachView(this)
        mTitleBar.setOnClickCallback(this)
        init()

        mPresenter.getRoomManager(mRoomId)

    }

    fun init() {
        mRcyManager.layoutManager = LinearLayoutManager(this)
        mRcyManager.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->

            val item = mAdapter.getItem(position)

            var tipsDialog = TipsDialog.createDialog(this, R.layout.dialog_room_manager)

            tipsDialog.setText(R.id.tv_name, item.userName)
            tipsDialog.bindClick(R.id.tv_cancel) { v, dialog ->
                dialog.dismiss()
            }
                    .bindClick(R.id.tv_confirm) { v, dialog ->
                        mPresenter.removeRoomManager(mRoomId, item)
                        dialog.dismiss()
                    }
                    .show()
        }


    }

    override fun onRoomManagerSuccess(roomManager: MutableList<RoomManagerEntity>?) {
        mAdapter.data = roomManager!!
        mAdapter.notifyDataSetChanged()
    }

    override fun onRemoveSuccess(status: Int?, user: RoomManagerEntity?) {

        if (status == 1) {
            ToastUtils.showShort("移除管理员成功")
            EventBusUtil.sendEvent(Event(EventConstant.ROOM_REMOVE_MANAGER, user?.userId))
            mAdapter.remove(user!!)
            mAdapter.notifyDataSetChanged()

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
