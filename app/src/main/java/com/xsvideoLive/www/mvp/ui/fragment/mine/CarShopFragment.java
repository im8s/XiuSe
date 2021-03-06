package com.xsvideoLive.www.mvp.ui.fragment.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.callback.OnCarTestDive;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.CarShopContract;
import com.xsvideoLive.www.mvp.presenter.CarShopPresenter;
import com.xsvideoLive.www.mvp.ui.activity.mine.FriendsSelectionAct;
import com.xsvideoLive.www.mvp.ui.adapter.StoreCarAdapter;
import com.xsvideoLive.www.net.bean.Decks;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.utils.ActStartUtils;
import com.xsvideoLive.www.utils.SPUtils;
import com.xsvideoLive.www.utils.ToastUtils;
import com.xsvideoLive.www.view.GridDecoration;
import com.xsvideoLive.www.view.TipsDialog;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfo;
import com.tencent.qcloud.tim.uikit.modules.message.MessageInfoUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CarShopFragment extends BaseMvpFragment<CarShopPresenter> implements CarShopContract.View {

    @BindView(R.id.rcy_car)
    RecyclerView rcyCar;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_give_away)
    TextView tvGiveAway;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.ll_buy)
    LinearLayout llBuy;
    //    @BindView(R.id.tv_my_buy)
//    TextView tvMyBuy;
    @BindView(R.id.ll_car)
    LinearLayout llCar;
    @BindView(R.id.fl_car_info)
    FrameLayout flCarInfo;
    @BindView(R.id.tv_stint)
    TextView tvStint;

    private MineReslut mResult;

    private OnCarTestDive onCarTestDive;

    private StoreCarAdapter mAdapter;

    private boolean isSelf;

    private Decks decks;

    private String mChatType;

    private String mGroupId;

    public static CarShopFragment newInstance() {
        return new CarShopFragment();
    }

    public void setOnCarTestDive(OnCarTestDive onCarTestDive) {
        this.onCarTestDive = onCarTestDive;

    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_car_shop;
    }

    @Override
    protected CarShopPresenter createPresenter() {
        return new CarShopPresenter();
    }

    private void init() {
        Bundle arguments = getArguments();
        mResult = (MineReslut) arguments.getSerializable(Constant.MINE_RESLUT);

        mChatType = arguments.getString(Constant.CHAT_TYPE);
        mGroupId = arguments.getString(Constant.PUBLIC_CHAT_GROUP);
        if (mResult.getId().equals(SPUtils.getInstance().getUserInfo().getId())) {
            isSelf = true;
        } else {
            isSelf = false;
        }

    }


    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        init();
        mAdapter = new StoreCarAdapter();
        mAdapter.setOnCarTestDive(this.onCarTestDive);
        rcyCar.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rcyCar.addItemDecoration(new GridDecoration(20, 10, 17, 17, 2));
        rcyCar.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                decks.setSelect(false);
                Decks item = mAdapter.getItem(position);
                item.setSelect(true);
                decks = item;
                mAdapter.notifyDataSetChanged();
                setBuyInfo(decks, isSelf);

            }
        });

        mPresenter.getStoreCar();
    }

    @Override
    public void onSuccess(List<Decks> decks) {
        if (decks.size() > 0) {
            this.decks = decks.get(0);
            this.decks.setSelect(true);
            mAdapter.setList(decks);
            setBuyInfo(this.decks, isSelf);
        }
    }

    @Override
    public void buyDeckSuccess(Integer status) {

    }

    @Override
    public void giveAwaySuccess(Integer status, Decks decks, MineReslut mResult) {
        if (status == 1) {
            ToastUtils.showShort("????????????");

            if (mChatType.equals(Constant.CHAT_USER)) {

                Gson gson = new Gson();

                Map<String, String> map = new HashMap<>();
                map.put("type", "Handsel");
                map.put("title", decks.getDeckName() + "(" + decks.getUseDay() + "???)");
                map.put("detail", "??????" + mResult.getUserName());
                map.put("img", decks.getDeckStaticUrl());

                String data = gson.toJson(map);
                MessageInfo info = MessageInfoUtil.buildCustomMessage(data);

                V2TIMMessage v2TIMMessage = info.getTimMessage();

                V2TIMManager.getMessageManager().sendMessage(v2TIMMessage, mResult.getId(), null,
                        V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, null);
            } else if (mChatType.equals(Constant.CHAT_GROUP)) {

                Gson gson = new Gson();

                Map<String, String> map = new HashMap<>();
                map.put("type", "Handsel");
                map.put("title", decks.getDeckName() + "(" + decks.getUseDay() + "???)");
                map.put("detail", "??????" + mResult.getUserName());
                map.put("img", decks.getDeckStaticUrl());
                String data = gson.toJson(map);
                V2TIMMessage groupMessage = MessageInfoUtil.buildGroupCustomMessage(data);
                V2TIMManager.getMessageManager().sendMessage(groupMessage, null, mGroupId, V2TIMMessage.V2TIM_PRIORITY_DEFAULT, false, null, null);
            }
        } else {
            ToastUtils.showShort("????????????");
        }
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @OnClick({R.id.tv_give_away, R.id.tv_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_give_away:

                if (isSelf) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.DECKS, decks);
                    ActStartUtils.startAct(getActivity(), FriendsSelectionAct.class, bundle);
                } else {
                    if (mResult == null || decks == null) return;
                    TipsDialog.createDialog(getActivity(), R.layout.dialog_headwear_store)
                            .setText(R.id.tv_title, "????????????")
                            .setText(R.id.tv_gift_info, "?????? " + mResult.getUserName() + " " + decks.getDeckName() + "(" + decks.getUseDay() + "???)")
                            .setText(R.id.tv_price, decks.getCostNumber() + (decks.getCostType() == 1 ? "??????" : decks.getCostType() == 2 ? "??????" : ""))
                            .bindClick(R.id.tv_cancel, (v, dialog) -> {
                                dialog.dismiss();
                            })
                            .bindClick(R.id.tv_comit, (v, dialog) -> {
                                mPresenter.giveAway(decks.getId(), mResult.getId(), decks, mResult);

                            })
                            .show();
                }

                break;
            case R.id.tv_buy:

                if (decks == null) return;
                TipsDialog.createDialog(getActivity(), R.layout.dialog_headwear_store)
                        .setText(R.id.tv_title, "????????????")
                        .setText(R.id.tv_gift_info, "?????? " + decks.getDeckName() + "(" + decks.getUseDay() + "???)")
                        .setText(R.id.tv_price, decks.getCostNumber() + (decks.getCostType() == 1 ? "??????" : decks.getCostType() == 2 ? "??????" : ""))
                        .bindClick(R.id.tv_cancel, (v, dialog) -> {
                            dialog.dismiss();
                        })
                        .bindClick(R.id.tv_comit, (v, dialog) -> {

                            mPresenter.buyDeck(decks.getId());

                        })
                        .show();

                break;
        }
    }

    private void setBuyInfo(Decks decks, boolean self) {
        if (decks.isSelect()) {
            flCarInfo.setVisibility(View.VISIBLE);
            if (decks.getCostType() == 0) {
                tvStint.setVisibility(View.VISIBLE);
                llCar.setVisibility(View.GONE);
            } else {
                llCar.setVisibility(View.VISIBLE);
                tvStint.setVisibility(View.GONE);

                tvPrice.setText(String.valueOf(decks.getCostNumber()));
                tvUnit.setText(decks.getCostType() == 1 ? "??????" : decks.getCostType() == 2 ? "??????" : "");
                tvDate.setText(String.valueOf(decks.getUseDay()));

                llBuy.setVisibility(View.VISIBLE);

            }

        }

    }
}
