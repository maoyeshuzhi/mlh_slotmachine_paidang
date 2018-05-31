package com.maoye.mlh_slotmachine.view.quickpayactivity.quickpayorderfragment;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.QuickOrderAdapter;
import com.maoye.mlh_slotmachine.bean.QuickOrderBean;
import com.maoye.mlh_slotmachine.bean.QuickPayWXBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.mvp.MVPBaseFragment;
import com.maoye.mlh_slotmachine.util.CodeUtils;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.Poputils;
import com.maoye.mlh_slotmachine.util.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class QuickpayorderFragment extends MVPBaseFragment<QuickpayorderContract.View, QuickpayorderPresenter> implements QuickpayorderContract.View, View.OnClickListener {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.back_imgbt)
    ImageButton backImgbt;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.isdelivery_tv)
    TextView isdeliveryTv;
    @BindView(R.id.submit_bt)
    Button submitBt;
    Unbinder unbinder;
    private QuickOrderAdapter adapter;
    private List<QuickOrderBean> list = new ArrayList<>();
    private PopupWindow payPopWindow;
    private View popView;
    private ImageView ali_code_img, wechatpay_code_img;
    private CountDownTimer countDownTimer;
    private TextView pop_time_tv;
    private QuickOrderBean selectOrder;
    private boolean isIntercept;
    private  boolean isQueryBill;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quickpay_order, null);
        unbinder = ButterKnife.bind(this, view);
        initPopView();
        initData();
        return view;
    }

    private void initPopView() {
        popView = LayoutInflater.from(getContext()).inflate(R.layout.pop_quickpay, null);
        ali_code_img = popView.findViewById(R.id.ali_code_img);
        wechatpay_code_img = popView.findViewById(R.id.wechatpay_code_img);
        pop_time_tv = popView.findViewById(R.id.pop_time_tv);
        popView.findViewById(R.id.pop_giveup_bt).setOnClickListener(this);
        popView.findViewById(R.id.confirm_bt).setOnClickListener(this);
    }

    private void initData() {
        initCounterTimer();
        adapter = new QuickOrderAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler.setAdapter(adapter);
        list = (List<QuickOrderBean>) getArguments().getSerializable(Constant.KEY);
        adapter.addDatas(list);

        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        list.get(position).setSelect(true);
                    } else {
                        list.get(i).setSelect(false);
                    }
                }
                adapter.addDatas(list);
            }
        });
    }

    private void initCounterTimer() {
        countDownTimer = new CountDownTimer(Constant.quickPaycountDownTime, 1000) {
            @Override
            public void onTick(long l) {
                pop_time_tv.setText(String.format(Constant.countDownTimeFormat, l / 1000 + ""));
            }

            @Override
            public void onFinish() {
                isIntercept = true;
            }
        };

    }


    @Override
    public void onSuccess(Object o) {

    }

    @Override
    public void onFail(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isIntercept = true;
        countDownTimer.cancel();
    }

    @OnClick({R.id.back_imgbt, R.id.submit_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_imgbt:
                getActivity().finish();
                break;
            case R.id.submit_bt:
                selectOrder = mPresenter.getSelectOrder(list);
                if (selectOrder == null) {
                    Toast.getInstance().toast(getContext(), "请选择需要付款的订单", 2);
                } else {
                    mPresenter.getWXCode(selectOrder.getSaleNo(), mPresenter.getOrderPrice(selectOrder));
                    mPresenter.getAliCode(selectOrder.getSaleNo(), mPresenter.getOrderPrice(selectOrder));
                }
                break;
            case R.id.pop_giveup_bt:
                isQueryBill = false;
                payPopWindow.dismiss();
                break;
            case R.id.confirm_bt:
                isQueryBill = false;
                break;

        }
    }

    @Override
    public void getWXCode(QuickPayWXBean wxBean) {
        wechatpay_code_img.setImageBitmap(CodeUtils.createQRCode(wxBean.getCode(), 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.wxcode_icon),0));
    }

    @Override
    public void getAliCode(QuickPayWXBean wxBean) {
        ali_code_img.setImageBitmap(CodeUtils.createQRCode(wxBean.getCode(), 300, 300, BitmapFactory.decodeResource(getResources(), R.mipmap.alicode_icon),0));
        payPopWindow = Poputils.getPop(popView, R.layout.fragment_quickpay_order, getActivity());
        countDownTimer.start();
        isQueryBill = true;
        mPresenter.billQuery(selectOrder.getSaleNo(), isQueryBill);
    }

    @Override
    public void getWXCodeFial(Throwable t) {
        mPresenter.getWXCode(selectOrder.getSaleNo(), mPresenter.getOrderPrice(selectOrder));
    }

    @Override
    public void getAliCodeFial(Throwable t) {
        mPresenter.getAliCode(selectOrder.getSaleNo(), mPresenter.getOrderPrice(selectOrder));
    }

    @Override
    public void queryBillResult(String jsonStr) {

    }

    @Override
    public void quiryBillFail(Throwable t, boolean isQueryBill) {
        if (isQueryBill && isIntercept) {
            mPresenter.billQuery(selectOrder.getSaleNo(), isQueryBill);
        } else if (!isQueryBill) {//支付失败

        }
    }
}
