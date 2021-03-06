package com.xsvideoLive.www.mvp.ui.view

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.xsvideoLive.www.R
import com.xsvideoLive.www.callback.OnSeatClickCallback
import com.xsvideoLive.www.constant.Constant
import com.xsvideoLive.www.constant.RoomSeatConstant
import com.xsvideoLive.www.net.bean.RoomSeat

class ManagerSeatView private constructor() : BottomSheetDialogFragment(), View.OnClickListener {

    private var mTvOnSeat: TextView? = null

    private var mTvPickSeat: TextView? = null

    private var mTvCloseMike: TextView? = null

    private var mTvLockSeat: TextView? = null

    private var mTvCancel: TextView? = null

    private var userSeat: RoomSeat? = null

    private var mOnSeatClickCallback: OnSeatClickCallback? = null

    companion object {
        fun newInstance(userSeat: RoomSeat): ManagerSeatView {
            var mManagerSeatView = ManagerSeatView()
            val args = Bundle()
//            args.putInt(Constant.ROOM_SELF_KIND, selfKind)
            args.putSerializable(Constant.ROOM_USER_SEAT, userSeat)
            mManagerSeatView.arguments = args
            return mManagerSeatView
        }
    }

    fun setOnSeatClickCallback(onSeatClick: OnSeatClickCallback?) {
        this.mOnSeatClickCallback = onSeatClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userSeat = arguments?.getSerializable(Constant.ROOM_USER_SEAT) as RoomSeat?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mView = inflater.inflate(R.layout.view_manager_seat, container, false)
        initView(mView)
        return mView
    }

    private fun initView(mView: View?) {
        mTvOnSeat = mView?.findViewById(R.id.tv_onSeat)
        mTvPickSeat = mView?.findViewById(R.id.tv_pick_seat)
        mTvCloseMike = mView?.findViewById(R.id.tv_close_mike)
        mTvLockSeat = mView?.findViewById(R.id.tv_lock_seat)
        mTvCancel = mView?.findViewById(R.id.tv_cancel)

        mTvOnSeat?.setOnClickListener(this)
        mTvPickSeat?.setOnClickListener(this)
        mTvCloseMike?.setOnClickListener(this)
        mTvLockSeat?.setOnClickListener(this)
        mTvCancel?.setOnClickListener(this)

        initData(userSeat)
    }

    private fun initData(userSeat: RoomSeat?) {
        mTvCloseMike?.text = if (userSeat?.isMute!!) "??????" else "??????"
        mTvLockSeat?.text = if (userSeat?.isClose!!) "?????????" else "??????"
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
        }

    }

    private fun getPeekHeight(): Int {
        //????????????????????????????????????3/4
        return dp2px(328f)
    }


    private fun dp2px(dpValue: Float): Int {
        val scale: Float = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    override fun onClick(view: View?) {

        when (view?.id) {

            R.id.tv_onSeat -> {     //??????
                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UP_SEAT, null, userSeat)
                dismiss()
            }

            R.id.tv_pick_seat -> {
                mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.PICK_SEAT, null, userSeat)
                dismiss()
            }

            R.id.tv_close_mike -> {
                if (userSeat?.isMute!!) {       //???????????????????????? ???????????????
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.OPEN_MIKE, null, userSeat)
                } else {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.CLOSE_MIKE, null, userSeat)
                }
                dismiss()
            }

            R.id.tv_lock_seat -> {
                if (userSeat?.isClose!!) {       //???????????????????????? ????????????
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.UNLOCK_MIKE, null, userSeat)
                } else {
                    mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.LOCK_MIKE, null, userSeat)
                }
                dismiss()
            }

            R.id.tv_cancel -> {
                dismiss()
            }
        }

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mOnSeatClickCallback?.onSeatItemClick(RoomSeatConstant.MANAGER_VIEW_DISMISS, null, null)
    }
}