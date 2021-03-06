package com.xsvideoLive.www.mvp.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xsvideoLive.www.R
import com.xsvideoLive.www.callback.OnRowMikeCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.mvp.ui.adapter.RowSeatViewAdapter
import com.xsvideoLive.www.net.HttpClient
import com.xsvideoLive.www.net.HttpObserver
import com.xsvideoLive.www.net.bean.BaseResponse
import com.xsvideoLive.www.net.bean.RowSeatResult
import com.xsvideoLive.www.utils.SPUtils
import com.xsvideoLive.www.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RoomRowMikeView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    lateinit var mTvTis: TextView
    lateinit var mTvTis2: TextView

    lateinit var mRcySeatUser: RecyclerView

    lateinit var mTvSingUp: TextView

    private var mKind = -1

    private var mRoomId: String? = null

    private var mAdapter: RowSeatViewAdapter? = null

    private var userList: MutableList<RowSeatResult>? = null

    private var mCallback: OnRowMikeCallback? = null

    companion object {
        fun newInstance(kind: Int, roomId: String?): RoomRowMikeView {
            var mRoomRowMike = RoomRowMikeView()
            var bundle = Bundle()
            bundle.putInt(Constant.ROOM_SELF_KIND, kind)
            bundle.putString(Constant.ROOM_ID, roomId)
            mRoomRowMike.arguments = bundle
            return mRoomRowMike
        }
    }

    fun setOnRowMikeCallback(callback: OnRowMikeCallback){
        this.mCallback = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mKind = arguments?.getInt(Constant.ROOM_SELF_KIND, -1)!!
        mRoomId = arguments?.getString(Constant.ROOM_ID)
        mAdapter = RowSeatViewAdapter(mKind)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var mView = inflater.inflate(R.layout.view_room_row_mike, container, false)

        initView(mView)

        return mView

    }

    private fun initView(view: View) {
        mTvTis = view.findViewById(R.id.tv_tis)
        mTvTis2 = view.findViewById(R.id.tv_tis2)
        mRcySeatUser = view.findViewById(R.id.rcy_seat_user)
        mTvSingUp = view.findViewById(R.id.tv_sing_up)

        mTvSingUp.setOnClickListener(this)

        mRcySeatUser.layoutManager = LinearLayoutManager(context)
        mRcySeatUser.adapter = mAdapter

        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            if (mKind == 1 || mKind == 2) {
                val item = mAdapter?.getItem(position)
                managerRemove(item)
            }

        }


        getSeatUser(mRoomId!!)
    }

    fun managerRemove(rowSeat: RowSeatResult?) {
        HttpClient.getApi().removeSing(rowSeat?.id, rowSeat?.userId, rowSeat?.roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (bean == "1") {
                            getSeatUser(mRoomId!!)
                            mCallback?.onRowMikeCallback(rowSeat)

                        } else {
                            ToastUtils.showShort("??????????????????")
                        }
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                })
    }

    private fun getSeatUser(roomId: String) {
        HttpClient.getApi().getRowSeatUser(roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<MutableList<RowSeatResult>>() {
                    override fun success(bean: MutableList<RowSeatResult>?, response: BaseResponse<MutableList<RowSeatResult>>?) {
                        userList = bean

                        setRowSeatResult(bean)
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                })
    }

    private fun setRowSeatResult(bean: MutableList<RowSeatResult>?) {


        if (mKind == 3) {

            val selfUserId = SPUtils.getInstance().userInfo.id

            val mine = getMine(selfUserId, bean)

            setAudienceResult(mine, audienceList = bean)
        } else if (mKind == 1 || mKind == 2) {
            setManagerResult(bean)
        }


    }

    private fun setManagerResult(audienceList: MutableList<RowSeatResult>?) {


        mTvSingUp.visibility = View.GONE

        if (audienceList != null && audienceList.size > 0) {
            mTvTis.text = "??????????????????:"
            mTvTis.visibility = View.VISIBLE
            mTvTis2.text = audienceList.size.toString()
            mTvTis2.visibility = View.VISIBLE

            mAdapter?.data = audienceList
            mAdapter?.notifyDataSetChanged()

        } else {
            mTvTis.visibility = View.GONE
            mTvTis2.visibility = View.GONE
            mAdapter?.setEmptyView(R.layout.empty_row_manager_mike)
            mAdapter?.data = audienceList!!
            mAdapter?.notifyDataSetChanged()
        }
    }

    private fun setAudienceResult(mine: RowSeatResult?, audienceList: MutableList<RowSeatResult>?) {
        mTvSingUp.visibility = View.VISIBLE
        if (audienceList != null && audienceList.size > 0) {

            if (mine == null) {
                mTvTis.text = "?????????????????????~"
                mTvTis.visibility = View.VISIBLE
                mTvTis2.visibility = View.GONE

                mTvSingUp.text = "????????????"
                mTvSingUp.setBackgroundResource(R.drawable.shape_p_r20_bg)
            } else {
                mTvTis.text = "???????????????????????????:"
                mTvTis2.text = mine?.index.toString()
                mTvTis.visibility = View.VISIBLE
                mTvTis2.visibility = View.VISIBLE

                mTvSingUp.text = "????????????"
                mTvSingUp.setBackgroundResource(R.drawable.shape_g_r20_bg)
            }

            mAdapter?.data = audienceList
            mAdapter?.notifyDataSetChanged()

        } else {
            mTvTis.text = "?????????????????????~"
            mTvTis.visibility = View.VISIBLE
            mTvTis2.visibility = View.GONE
            mAdapter?.setEmptyView(R.layout.empty_row_mike)
            mTvSingUp.text = "????????????"
            mTvSingUp.setBackgroundResource(R.drawable.shape_p_r20_bg)
            mAdapter?.data = audienceList!!
            mAdapter?.notifyDataSetChanged()

        }
    }


    private fun getMine(selfUserId: String, rowSeat: MutableList<RowSeatResult>?): RowSeatResult? {
        var selfRowSeat: RowSeatResult? = null
        if (rowSeat == null || rowSeat.size <= 0) {
            return selfRowSeat
        }
        for (i in rowSeat!!.indices) {
            if (rowSeat[i].userId == selfUserId) {
                rowSeat[i].index = i + 1
                selfRowSeat = rowSeat[i]
                break
            }
        }

        return selfRowSeat
    }

    override fun onStart() {
        super.onStart()
        //??????dialog??????
        val dialog = dialog as BottomSheetDialog?
        dialog?.window?.findViewById<View>(R.id.design_bottom_sheet)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        //??????diglog????????????
        val bottomSheet = dialog!!.delegate.findViewById<FrameLayout>(R.id.design_bottom_sheet)

        if (bottomSheet != null) {
            //??????????????????LayoutParams??????
            val layoutParams = bottomSheet.layoutParams as CoordinatorLayout.LayoutParams
            layoutParams.height = getPeekHeight()
            //?????????????????????????????????????????????????????????????????????
            bottomSheet.layoutParams = layoutParams
            val behavior = BottomSheetBehavior.from(bottomSheet)
            //peekHeight????????????????????????
            behavior.peekHeight = getPeekHeight()
            // ?????????????????????
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isHideable = false
        }

    }

    private fun getPeekHeight(): Int {
        //????????????????????????????????????3/4
        return dp2px(484f)
    }


    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.tv_sing_up -> {

                if (mKind == 3) {
                    val mine = getMine(SPUtils.getInstance().userInfo.id, userList)
                    if (mine == null) {
                        ToastUtils.showShort("??????")
                        singUp(SPUtils.getInstance().userInfo.id)
                    } else {
                        ToastUtils.showShort("????????????")
                        removeSing(mine)
                    }
                }
            }
        }

    }

    private fun singUp(userId: String) {
        HttpClient.getApi().singUp(userId, mRoomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<Int>() {
                    override fun success(bean: Int?, response: BaseResponse<Int>?) {
                        if (bean == 1) {
                            getSeatUser(mRoomId!!)
                        } else {
                            ToastUtils.showShort("????????????")
                        }
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }


                })
    }

    private fun removeSing(rowSeat: RowSeatResult?) {
        HttpClient.getApi().removeSing(rowSeat?.id, rowSeat?.userId, rowSeat?.roomId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : HttpObserver<String>() {
                    override fun success(bean: String?, response: BaseResponse<String>?) {
                        if (bean == "1") {
                            getSeatUser(mRoomId!!)
                        } else {
                            ToastUtils.showShort("??????????????????")
                        }
                    }

                    override fun error(msg: String?) {
                        ToastUtils.showShort(msg)
                    }

                })
    }


}