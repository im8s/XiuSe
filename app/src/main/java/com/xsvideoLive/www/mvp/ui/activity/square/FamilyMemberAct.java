package com.xsvideoLive.www.mvp.ui.activity.square;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpActivity;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.FamilyMemberContract;
import com.xsvideoLive.www.mvp.model.FamilyMemberPresenter;
import com.xsvideoLive.www.mvp.ui.activity.mine.HomePageAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyBuddyAct;
import com.xsvideoLive.www.mvp.ui.adapter.FamilyMemberAdapter;
import com.xsvideoLive.www.mvp.ui.view.FamilyMemberHeader;
import com.xsvideoLive.www.net.bean.ClanResult;
import com.xsvideoLive.www.net.bean.FamilyMemberResult;
import com.xsvideoLive.www.net.bean.MyFamilyResult;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.PopupWindowUtil;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.TipsDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FamilyMemberAct extends BaseMvpActivity<FamilyMemberPresenter> implements FamilyMemberContract.View {


    @BindView(R.id.iv_black)
    ImageView ivBlack;
    @BindView(R.id.tv_title_name)
    TextView tvTitleName;
    @BindView(R.id.tv_share)
    ImageView tvShare;
    @BindView(R.id.tv_more)
    ImageView tvMore;
    @BindView(R.id.rc_family_member)
    RecyclerView rcFamilyMember;
    @BindView(R.id.tv_join)
    TextView tvJoin;


    private String familyId;


    private int patriarch = -1;

    private FamilyMemberAdapter mAdapter;

    private FamilyMemberHeader header;

    private PopupWindow mPopupWindow;

    private MyFamilyResult myFamily;

    private ClanResult mResult;

    @Override
    public int setLayoutId() {
        return R.layout.activity_family_member;
    }


    @Override
    protected FamilyMemberPresenter createPresenter() {
        return new FamilyMemberPresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        Bundle extras = getIntent().getExtras();
        familyId = extras.getString(Constant.FAMILY_ID);
        myFamily = (MyFamilyResult) extras.getSerializable(Constant.MY_FAMILY);

        header = new FamilyMemberHeader(this);

        mAdapter = new FamilyMemberAdapter();
        rcFamilyMember.setLayoutManager(new LinearLayoutManager(this));
        rcFamilyMember.setAdapter(mAdapter);

        mAdapter.setHeaderView(header);

        if (!TextUtils.isEmpty(familyId)) {
            mPresenter.getClanInfo(familyId);
        }

        mAdapter.setOnItemClickListener((adapter, view, position) -> {

            MultiItemEntity item = mAdapter.getItem(position);
            String userId = null;
            int itemType = item.getItemType();
            //TODO ????????????????????????
            if (itemType == 0) {
                userId = ((ClanResult) item).getUserId();
            } else if (itemType == 1) {
                userId = ((FamilyMemberResult) item).getId();
            }

            if (!TextUtils.isEmpty(userId)) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.USER_ID, userId);
                ActStartUtils.startAct(this, HomePageAct.class, bundle);
            }


        });

    }

    @Override
    public void clanInfoSuccess(ClanResult result) {

        if (result != null) {
            this.mResult = result;
//            String patriarchID = result.getClanId();

            if (myFamily == null && result.getIsClanMember() != 1) {         //???????????????
                patriarch = 0;
                showView(patriarch, result);
                ArrayList<ClanResult> list = new ArrayList<>();
                list.add(result);
                mAdapter.setList(list);
            } else {

//                if (myFamily.getClanId().equals(patriarchID)) {  //????????????
                if (result.getIsClanMember() == 1) {  //????????????
                    patriarch = 1;
                    showView(patriarch, result);
                    mPresenter.familyMembers(familyId);

                } else {        //???????????????

                    patriarch = 0;
                    showView(patriarch, result);
                    ArrayList<ClanResult> list = new ArrayList<>();
                    list.add(result);
                    mAdapter.setList(list);

                }
            }

        }
    }

    @Override
    public void familyMembersSuccess(List<FamilyMemberResult> results) {

        mAdapter.setList(results);
    }

    @Override
    public void joinSuccess(String data) {

        //	0??????????????????????????????????????????1????????????
        if ("0".equals(data)) {
            ToastUtils.showShort("???????????????????????????????????????");

        } else if ("1".equals(data)) {

            ToastUtils.showShort("????????????");

            patriarch = 1;
            showView(patriarch, mResult);
            mPresenter.familyMembers(familyId);

        } else if ("2".equals(data)){
            ToastUtils.showShort("??????????????????????????????????????????????????????????????????~");
        }
    }

    @Override
    public void outSuccess(String data) {
        //	0??????????????????2??????7????????????1????????????
        if ("0".equals(data)) {
            ToastUtils.showShort("???????????????");
        } else if ("1".equals(data)) {
            ToastUtils.showShort("????????????");
            finish();
        } else if ("2".equals(data)) {
            ToastUtils.showShort("??????7????????????");
        }
    }

    /**
     * ??????????????????view
     *
     * @param status ?????? 0??? ??????????????? 1???????????????
     * @param result ????????????
     */
    private void showView(int status, ClanResult result) {
        if (status == 0) {
            tvMore.setVisibility(View.GONE);
            tvShare.setVisibility(View.VISIBLE);
            if (result != null) {
                tvTitleName.setText(result.getClanName());
            }
            tvJoin.setVisibility(View.VISIBLE);

        } else if (status == 1) {
            tvMore.setVisibility(View.VISIBLE);
            tvShare.setVisibility(View.VISIBLE);
            tvTitleName.setText(getResources().getString(R.string.my_famliy));
            tvJoin.setVisibility(View.GONE);
        }
        header.upData(result, status);

    }


    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick({R.id.iv_black, R.id.tv_share, R.id.tv_more, R.id.tv_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_black:
                finish();
                break;
            case R.id.tv_share:
                TipsDialog.createDialog(this, R.layout.dialog_share)
                        .bindClick(R.id.tv_cancel, (v, dialog) -> {
                            dialog.dismiss();
                        })
                        .bindClick(R.id.tv_share_buddy, (v, dialog) -> {
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.BUDDY_KEY, Constant.CLAN);
                            bundle.putSerializable(Constant.CLAN, mResult);
                            ActStartUtils.startAct(this, MyBuddyAct.class, bundle);
                        })
                        .show();
                break;
            case R.id.tv_more:
                showPopupWindow(tvMore);
                break;
            case R.id.tv_join:
                mPresenter.joinFamily(SPUtils.getInstance().getUserInfo().getId(), familyId);

                break;
        }
    }

    private void showPopupWindow(View anchorView) {
        View contentView = getPopupWindowContentView();
        mPopupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // ???????????????PopupWindow????????????????????????????????????????????????????????????????????????????????????Back????????????dismiss??????
//        mPopupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // ????????????????????????show
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(anchorView, contentView);
        int xOff = 20; // ????????????????????????
        windowPos[0] -= xOff;

        backgroundAlpha((float) 0.4);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((float) 1);
            }
        });
        mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
    }

    private View getPopupWindowContentView() {
        // ????????????????????????????????????????????????
        int layoutId = R.layout.popup_content_layout;   // ??????ID
        View contentView = LayoutInflater.from(this).inflate(layoutId, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mPresenter.outFamily(SPUtils.getInstance().getUserInfo().getId(), familyId);

            }
        };
        contentView.findViewById(R.id.ll_out_family).setOnClickListener(menuItemOnClickListener);
        return contentView;
    }

    // ?????????????????????
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        getWindow().setAttributes(lp); //act ????????????context

    }
}
