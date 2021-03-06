package com.xsvideoLive.www.mvp.ui.activity.studio

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.xsvideoLive.www.R
import com.xsvideoLive.www.base.BaseMvpActivity
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.contract.RoomTypeContract
import com.xsvideoLive.www.mvp.presenter.RoomTypePresenter
import com.xsvideoLive.www.mvp.ui.adapter.RoomTypeAdapter
import com.xsvideoLive.www.net.bean.HomeSortResult
import com.xsvideoLive.www.utils.ToastUtils

class RoomTypeAct : BaseMvpActivity<RoomTypePresenter>(), RoomTypeContract.View {


    @BindView(R.id.rcy_type)
    lateinit var mRcyType: RecyclerView

    private var mTypeList: MutableList<HomeSortResult> = ArrayList()

    private var mAdapter: RoomTypeAdapter? = null

    private var mSelectItem: HomeSortResult? = null

    private var mRoomTypeId: String? = null

    private var mRoomId: String? = null


    override fun createPresenter(): RoomTypePresenter {
        return RoomTypePresenter()
    }

    override fun setLayoutId(): Int {
        return R.layout.activity_room_type
    }

    override fun initView(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)

        mRoomTypeId = intent.extras.getString(Constant.ROOM_TYPE_ID)
        mRoomId = intent.extras.getString(Constant.ROOM_ID)

        mAdapter = RoomTypeAdapter(mTypeList)

        mRcyType.layoutManager = GridLayoutManager(this, 4)
        mRcyType.adapter = mAdapter

        mAdapter?.setOnItemClickListener { adapter, view, position ->

            val item = mAdapter?.getItem(position)

            if (mSelectItem == null) {
                item?.isSelect = true
                mSelectItem = item
            } else {
                mSelectItem?.isSelect = false
                item?.isSelect = true
                mSelectItem = item
            }
            mAdapter?.notifyDataSetChanged()

        }


        mPresenter.getSort()
    }

    @OnClick(R.id.iv_finish, R.id.tv_confirm)
    fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_finish -> {
                finish()
            }

            R.id.tv_confirm -> {
                if (mSelectItem == null) {
                    ToastUtils.showShort("?????????????????????")
                } else {
                    mPresenter.setRoomType(mRoomId!!, mSelectItem?.id!!, mSelectItem!!)
                }
            }

        }
    }

    override fun onSortSuccess(sortList: MutableList<HomeSortResult>?) {
        mTypeList.clear()
        if (sortList != null) {
            mTypeList.addAll(sortList)
        }

        if (!TextUtils.isEmpty(mRoomTypeId)) {
            for (item in mTypeList) {
                if (item.id == mRoomTypeId) {
                    item.isSelect = true
                    mSelectItem = item
                    break
                }
            }
        }
        mAdapter?.data = mTypeList!!
        mAdapter?.notifyDataSetChanged()
    }

    override fun onTypeSuccess(status: Int, type: HomeSortResult) {

        if (status == 1) {

            var bundle = Bundle()
            bundle.putSerializable(Constant.ROOM_TYPE, type)

            var intent = Intent()
            intent.putExtras(bundle)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            ToastUtils.showShort("????????????")
        }
    }

    override fun onError(msg: String?) {
        ToastUtils.showShort(msg)
    }
}
