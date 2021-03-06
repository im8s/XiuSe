package com.xsvideoLive.www.mvp.ui.fragment.mine;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.constant.CacheConstant;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.MineContract;
import com.xsvideoLive.www.mvp.presenter.MinePresenter;
import com.xsvideoLive.www.mvp.ui.activity.mine.AttentionMineAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.FamilyDiamondAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.GradeAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.HeadgearAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.HomePageAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyChiliAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyDiamondAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyFansAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyGiftAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.MyGoldAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.NobleWebActivity;
import com.xsvideoLive.www.mvp.ui.activity.mine.SettingAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.TeensAct;
import com.xsvideoLive.www.mvp.ui.activity.mine.VerifiedAct;
import com.xsvideoLive.www.mvp.ui.activity.square.FamilyMemberAct;
import com.xsvideoLive.www.mvp.ui.view.AvatarView;
import com.xsvideoLive.www.mvp.ui.view.ConstellationView;
import com.xsvideoLive.www.mvp.ui.view.UserLevelView;
import com.xsvideoLive.www.net.bean.CustomerServiceEntity;
import com.xsvideoLive.www.net.bean.Decks;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.net.bean.UserBean;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.utils.UserManagerUtils;
import com.xsvideoLive.www.view.TipsDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineContract.View, OnRefreshListener {
    private static final String TAG = "MineFragment";

    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.fl_avater)
    AvatarView flAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_sex)
    ImageView mIvSex;
    @BindView(R.id.clv_lv)
    UserLevelView mClvLv;
    @BindView(R.id.tv_start)
    ConstellationView mTvStart;
    @BindView(R.id.tv_user_id)
    TextView mTvUserId;
    @BindView(R.id.tv_attention_num)
    TextView mTvAttentionNum;
    @BindView(R.id.tv_fan_num)
    TextView mTvFanNum;
    @BindView(R.id.tv_lv_user_num)
    TextView mTvUserLv;
    @BindView(R.id.tv_charm_num)
    TextView mTvCharmLv;
    @BindView(R.id.iv_lh)
    ImageView mIvLh;
    @BindView(R.id.iv_noble)
    ImageView mIvNoble;

    private MineReslut mResult;

    private CustomerServiceEntity mEntity;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }


    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        refresh.setOnRefreshListener(this);

    }

    @Override
    public void MineSuccess(MineReslut result) {
        mResult = result;
        CacheConstant.result = result;
        setMineResult(CacheConstant.result);

    }

    @Override
    public void familyOnSuccess(String status, String clanId, String userId) {
        if (status.equals("0")) {
            ToastUtils.showShort("??????????????????");
        } else if (status.equals("1")) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.USER_ID, userId);
            ActStartUtils.startAct(getActivity(), FamilyDiamondAct.class);
        } else if (status.equals("2")) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.FAMILY_ID, clanId);
            ActStartUtils.startAct(getActivity(), FamilyMemberAct.class, bundle);
        }
    }

    @Override
    public void onServiceSuccess(CustomerServiceEntity entity) {
        mEntity = entity;
    }

    @Override
    protected void onLazyLoad() {

        mPresenter.getMine(null, "");

    }

    @Override
    public void onResume() {
        super.onResume();


        if (CacheConstant.result != null) {
            mResult = CacheConstant.result;
            setMineResult(CacheConstant.result);
        } else {
            mPresenter.getMine(null, "");
        }

        mPresenter.getService();

    }

    @SuppressLint("SetTextI18n")
    private void setMineResult(MineReslut result) {

        if (result == null) return;
        //??????
        Decks headwear = result.getHeadwear();
        String deckUrl = "";
        if (headwear != null) {
            deckUrl = headwear.getDeckUrl();
        }
        flAvatar.setAvatarAndHeadear(result.getUserPicture(), deckUrl);

        mTvName.setText(result.getUserName());

        int noble = UserManagerUtils.nobleImage(Integer.valueOf(result.getUserNoble()));
        if (noble == 0) {
            mIvNoble.setVisibility(View.GONE);
        } else {
            mIvNoble.setVisibility(View.VISIBLE);
            mIvNoble.setImageResource(noble);
        }


        if (result.getUserSex() == 0) {
            mIvSex.setImageResource(R.mipmap.icon_gender_female);
        } else if (result.getUserSex() == 1) {
            mIvSex.setImageResource(R.mipmap.icon_gender_male);
        }

        int userLevel = result.getUserLevel();

        mClvLv.setUserLevel(userLevel);

        String userBirthday = result.getUserBirthday();

        mTvStart.setStart(userBirthday);

        if (result.getIsBeautiful() == 1) {
            mIvLh.setVisibility(View.VISIBLE);
        } else {
            mIvLh.setVisibility(View.GONE);
        }

        mTvUserId.setText("ID:" + result.getUserNumber());

        //??????
        mTvAttentionNum.setText(String.valueOf(result.getFollowTotal()));
        //?????? fanTotal
        mTvFanNum.setText(String.valueOf(result.getFanTotal()));
        //????????????
        mTvUserLv.setText("Lv." + result.getUserLevel());
        //????????????
        mTvCharmLv.setText("Lv." + result.getCharmLevel());

    }

    @OnClick({R.id.tv_user_info, R.id.rl_attention, R.id.rl_my_fans, R.id.rl_gold, R.id.rl_diamond,
            R.id.rl_chili, R.id.rl_gift, R.id.rl_vehicle, R.id.rl_vip, R.id.rl_teenagers, R.id.rl_my_name,
            R.id.tv_menu, R.id.rl_user_lv, R.id.rl_charm_num, R.id.rl_family, R.id.tv_kf})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_user_info:
                Bundle bundle = new Bundle();
                bundle.putString(Constant.USER_ID, SPUtils.getInstance().getUserInfo().getId());
                ActStartUtils.startAct(getActivity(), HomePageAct.class, bundle);
                break;
            case R.id.rl_attention:
                ActStartUtils.startAct(getActivity(), AttentionMineAct.class);
                break;
            case R.id.rl_my_fans:
                ActStartUtils.startAct(getActivity(), MyFansAct.class);
                break;
            case R.id.rl_gold: //????????????
                ActStartUtils.startAct(getActivity(), MyGoldAct.class);
                break;
            case R.id.rl_diamond://????????????
                ActStartUtils.startAct(getActivity(), MyDiamondAct.class);
                break;
            case R.id.rl_chili:
                ActStartUtils.startAct(getActivity(), MyChiliAct.class);
                break;
            case R.id.rl_gift:
                ActStartUtils.startAct(getActivity(), MyGiftAct.class);
                break;
            case R.id.rl_vehicle:
                if (mResult != null) {
                    Bundle headgearBundle = new Bundle();
                    headgearBundle.putSerializable(Constant.MINE_RESLUT, mResult);
                    headgearBundle.putBoolean(Constant.IS_SELF, true);
                    ActStartUtils.startAct(getContext(), HeadgearAct.class, headgearBundle);
                }
                break;
            case R.id.rl_vip:
                ActStartUtils.startAct(getActivity(), NobleWebActivity.class);
                break;
            case R.id.rl_teenagers://???????????????
                ActStartUtils.startAct(getActivity(), TeensAct.class);
                break;
            case R.id.rl_my_name:
                ActStartUtils.startAct(getActivity(), VerifiedAct.class);
                break;
            case R.id.tv_menu:
                ActStartUtils.startAct(getActivity(), SettingAct.class);
                break;
            case R.id.rl_user_lv:
                Bundle gradeBundle = new Bundle();
                gradeBundle.putString(Constant.GRADE_KEY, Constant.GRADE_USER_LV);
                ActStartUtils.startAct(getActivity(), GradeAct.class, gradeBundle);
                break;
            case R.id.rl_charm_num:
                Bundle gradeBundle2 = new Bundle();
                gradeBundle2.putString(Constant.GRADE_KEY, Constant.GRADE_CHARM_LV);
                ActStartUtils.startAct(getActivity(), GradeAct.class, gradeBundle2);
                break;

            case R.id.rl_family:
                UserBean userInfo = SPUtils.getInstance().getUserInfo();
                mPresenter.family(userInfo.getIsClanElder(), userInfo.getId());
                break;
            case R.id.tv_kf:
                if (mEntity != null) {
                    TipsDialog.createDialog(getActivity(), R.layout.dialog_kf)
                            .setText(R.id.tv_kf_phone, mEntity.getServePhone())
                            .setText(R.id.tv_kf_wx_num, mEntity.getServeWechat())
                            .bindClick(R.id.iv_copy_phone, (v, dialog) -> {
                                //???????????????????????????
                                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                // ?????????????????????ClipData
                                ClipData mClipData = ClipData.newPlainText("Label", mEntity.getServePhone());
                                // ???ClipData?????????????????????????????????
                                cm.setPrimaryClip(mClipData);
                                dialog.dismiss();
                            })
                            .bindClick(R.id.iv_copy_wx, (v, dialog) -> {
                                //???????????????????????????
                                ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                                // ?????????????????????ClipData
                                ClipData mClipData = ClipData.newPlainText("Label", mEntity.getServeWechat());
                                // ???ClipData?????????????????????????????????
                                cm.setPrimaryClip(mClipData);
                                dialog.dismiss();
                            })
                            .show();
                }
                break;
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getMine(refreshLayout, "");
    }
}
