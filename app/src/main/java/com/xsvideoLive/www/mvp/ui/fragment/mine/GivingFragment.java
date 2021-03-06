package com.xsvideoLive.www.mvp.ui.fragment.mine;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xsvideoLive.www.R;
import com.xsvideoLive.www.base.BaseMvpFragment;
import com.xsvideoLive.www.constant.Constant;
import com.xsvideoLive.www.mvp.contract.GivingContract;
import com.xsvideoLive.www.mvp.presenter.GivingPresenter;
import com.xsvideoLive.www.mvp.ui.adapter.GiftRecordAdapter;
import com.xsvideoLive.www.net.bean.GiftRecordResult;
import com.xsvideoLive.www.net.bean.GiftsResult;
import com.xsvideoLive.www.net.bean.MineReslut;
import com.xsvideoLive.www.net.bean.MyGiftTypeResult;
import com.xsvideoLive.www.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GivingFragment extends BaseMvpFragment<GivingPresenter> implements GivingContract.View {

    @BindView(R.id.rcy_view)
    RecyclerView mRecyclerView;

    private MineReslut mResult;

    private boolean isSelf = false;

    private GiftRecordAdapter mAdapter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_giving;
    }

    public static GivingFragment newInstance() {
        return new GivingFragment();
    }

    private void initData() {
        Bundle arguments = getArguments();
        mResult = (MineReslut) arguments.getSerializable(Constant.MINE_RESLUT);
        if (mResult.getId().equals(SPUtils.getInstance().getUserInfo().getId())) {
            isSelf = true;
        } else {
            isSelf = false;
        }

        mAdapter = new GiftRecordAdapter();
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected GivingPresenter createPresenter() {
        return new GivingPresenter();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mPresenter.attachView(this);
        initData();

        mPresenter.giftRecord(mResult.getId());
    }


    @Override
    public void onSuccess(GiftRecordResult result) {
        if (result != null) {

            if (result.getCoinGifts().size() > 0 || result.getPepperGifts().size() > 0) {

                ArrayList<MyGiftTypeResult> list = new ArrayList<>();

                if (result.getPepperGifts().size() > 0) {

                    list.add(new MyGiftTypeResult(true, "?????????????????????", result.getPepperGiftTotal(), null));
                    List<GiftsResult> pepperGifts = result.getPepperGifts();
                    for (GiftsResult pepperGift : pepperGifts) {
                        list.add(new MyGiftTypeResult(false, "", 0, pepperGift));
                    }
                }

                if (result.getCoinGifts().size() > 0) {
                    list.add(new MyGiftTypeResult(true, "?????????????????????", result.getCoinGiftTotal(), null));
                    List<GiftsResult> coinGifts = result.getCoinGifts();
                    for (GiftsResult coinGift : coinGifts) {
                        list.add(new MyGiftTypeResult(false, "", 0, coinGift));
                    }
                }

                mAdapter.setList(list);
            } else {

                if (isSelf) {
                    mAdapter.setList(null);
                    mAdapter.setEmptyView(R.layout.gift_empty);
                } else {
                    mAdapter.setList(null);
                    mAdapter.setEmptyView(R.layout.gift_home_page_empty);
                }

            }


        } else {
            if (isSelf) {
                mAdapter.setEmptyView(R.layout.gift_empty);
            } else {
                mAdapter.setEmptyView(R.layout.gift_home_page_empty);
            }
        }


    }

    @Override
    public void onError(String msg) {

    }
}
