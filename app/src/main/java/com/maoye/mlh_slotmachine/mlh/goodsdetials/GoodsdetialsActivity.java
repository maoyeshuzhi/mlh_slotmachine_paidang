package com.maoye.mlh_slotmachine.mlh.goodsdetials;


import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.GoodsDetialsGoodsAdapter;
import com.maoye.mlh_slotmachine.adapter.SpecAdapter;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.bean.SpecBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mlh.login.LoginActivity;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DateUtils;
import com.maoye.mlh_slotmachine.util.DeviceInfoUtil;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;
import com.maoye.mlh_slotmachine.widget.CodePop;
import com.maoye.mlh_slotmachine.widget.NoLineSpaceTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoodsdetialsActivity extends MVPBaseActivity<GoodsdetialsContract.View, GoodsdetialsPresenter> implements GoodsdetialsContract.View {

    @BindView(R.id.goods_img)
    ImageView goodsImg;
    @BindView(R.id.good_title_tv)
    TextView goodTitleTv;
    @BindView(R.id.titme_tv)
    TextView titmeTv;
    @BindView(R.id.salednum_tv)
    TextView salednumTv;
    @BindView(R.id.saleprice_tv)
    NoLineSpaceTextView salepriceTv;
    @BindView(R.id.oldprice_tv)
    NoLineSpaceTextView oldpriceTv;
    @BindView(R.id.deliverway_tv)
    TextView deliverwayTv;
    @BindView(R.id.pickup_tv)
    TextView pickupTv;
    @BindView(R.id.express_tv)
    TextView expressTv;
    @BindView(R.id.integral_tv)
    TextView integralTv;
    @BindView(R.id.specificationRecycler)
    RecyclerView specificationRecycler;
    @BindView(R.id.subtract_tv)
    TextView subtractTv;
    @BindView(R.id.select_goods_num_tv)
    TextView selectGoodsNumTv;
    @BindView(R.id.add_tv)
    TextView addTv;
    @BindView(R.id.stock_num_tv)
    TextView stockNumTv;
    @BindView(R.id.left_scroll_img)
    ImageView leftScrollImg;
    @BindView(R.id.goods_recycler)
    RecyclerView goodsRecycler;
    @BindView(R.id.right_scroll_img)
    ImageView rightScrollImg;
    @BindView(R.id.goods_img_rl)
    RelativeLayout goodsImgRl;
    @BindView(R.id.addcart_tv)
    TextView addcartTv;
    @BindView(R.id.immdl_buy_tv)
    TextView immdlBuyTv;
    @BindView(R.id.phonebuy_tv)
    TextView phonebuyTv;
    @BindView(R.id.hint_webview)
    WebView hintWebview;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.addcart_bottom_tv)
    TextView addcartBottomTv;
    @BindView(R.id.immdl_buy_bottom_tv)
    TextView immdlBuyBottomTv;
    public static final String START_TIME_HINT = "距离开始时间还有:%s";
    public static final String END_TIME_HINT = "还剩:%s";
    public static final String IMMDL_START = "即将开始";
    public static final String ALREAD_SALE = "已售罄";
    public static final String STOCK_NUM = "(库存还剩%s件)";
    public static final String INTEGRAL = "本商品购买预计可积%s~%s分";
    @BindView(R.id.hint_bt)
    TextView hintBt;//不能购买按钮
    @BindView(R.id.hint_bottom_bt)
    TextView hintBottomBt;//不能购买按钮
    @BindView(R.id.top_imgbt)
    ImageButton topImgbt;
    @BindView(R.id.cart_img)
    ImageView cartImg;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    private CountDownTimer timer;
    private int timeState;//时间状态
    public static final int UN_START = 0;//未开始
    public static final int STARTING = 1;//进行中
    public static final int ENDTIME = 2;//已结束
    private GoodsDetialsBean bean;
    private List<GoodsDetialsBean.ImageListBean> image_list = new ArrayList<>();
    private GoodsDetialsGoodsAdapter goodsPicAdapter;
    private int stockNum;
    private List<SpecBean> specList;
    private SpecAdapter specAdapter;
    private int selectGoodsNum = 1;
    private PopupWindow codePop;
    private int picPisition;//商品图片位置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsdetials);
        int goodsId = getIntent().getIntExtra(Constant.GOODS_ID, 0);
        mPresenter.getGoodsDetialsData(goodsId);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        initWebSetting();
        goodsPicAdapter = new GoodsDetialsGoodsAdapter();
        goodsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goodsRecycler.setAdapter(goodsPicAdapter);
        specAdapter = new SpecAdapter();
        specificationRecycler.setLayoutManager(new LinearLayoutManager(this));
        specificationRecycler.setAdapter(specAdapter);
        goodsPicAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                picPisition = position;
                ImgGlideUtil.displayImage(image_list.get(position).getImage_url(), goodsImg, true);
            }
        });
    }


    private void initWebSetting() {
        WebSettings webSetting = hintWebview.getSettings();
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }


    @Override
    public void onSuccess(Object o) {
        bean = (GoodsDetialsBean) o;
        String WEB_STYLE = "<style>*p{padding:0px;}</style>";
        String warmHintWebContent = bean.getDescription().replace("<img", "<img style='max-width:100%;height:auto;'");
        if (warmHintWebContent != null)
            hintWebview.loadDataWithBaseURL(null, WEB_STYLE + warmHintWebContent, "text/html", "utf-8", null);

        ImgGlideUtil.displayImage(bean.getDefault_image(), goodsImg, true);
        goodTitleTv.setText(bean.getName() + "");
        timehandler();
        salepriceTv.setText(String.format(Constant.PRICE_FORMAT, bean.getPrice() + ""));
        SpannableString spannableString = new SpannableString(String.format(Constant.PRICE_FORMAT, bean.getOld_price() + ""));
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        oldpriceTv.setText(spannableString);
        stockNum = bean.getStock();
        stockNumTv.setText(String.format(STOCK_NUM, bean.getStock() + ""));
        if (bean.getDelivery_type() == 1) {
            expressTv.setVisibility(View.INVISIBLE);
        } else if (bean.getDelivery_type() == 2) {
            expressTv.setVisibility(View.INVISIBLE);
        }
        integralTv.setText(String.format(INTEGRAL, bean.getMin_points() + "", bean.getMax_points() + ""));
        specList = mPresenter.handleSpecifi(bean.getSpec_name_list(), bean.getSpec_list());
        specAdapter.addDatas(specList);
        image_list = bean.getImage_list();
        goodsPicAdapter.addDatas(image_list);

        specAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                int num = mPresenter.getStockNum(specList, type, position, bean, bean.getStock());
                stockNum = num;
                stockNumTv.setText(String.format(STOCK_NUM, stockNum + ""));
                switchStockState(stockNum);
            }
        });
    }

    @Override
    public void onFail(Throwable throwable) {

    }

    /**
     * 处理库存状态
     */
    public void switchStockState(int stockNum) {
        if (stockNum == 0) {
            hintBt.setText(ALREAD_SALE);
            hintBottomBt.setText(ALREAD_SALE);
            hintBottomBt.setVisibility(View.VISIBLE);
            hintBt.setVisibility(View.VISIBLE);
            addcartTv.setVisibility(View.GONE);
            immdlBuyTv.setVisibility(View.GONE);
            addcartBottomTv.setVisibility(View.GONE);
            immdlBuyBottomTv.setVisibility(View.GONE);
        } else {
            hintBottomBt.setVisibility(View.GONE);
            hintBt.setVisibility(View.GONE);
            addcartTv.setVisibility(View.VISIBLE);
            immdlBuyTv.setVisibility(View.VISIBLE);
            addcartBottomTv.setVisibility(View.VISIBLE);
            immdlBuyBottomTv.setVisibility(View.VISIBLE);
        }
    }

    private void timehandler() {
        if (bean.getActivity_type() == 0) {
            titmeTv.setVisibility(View.GONE);
        } else if (bean.getActivity_type() == 1) {
            titmeTv.setVisibility(View.VISIBLE);
            if (DateUtils.compareNowTime(bean.getStart_time())) {
                //活动未开始
                timeState = UN_START;
                if (timeState == UN_START) {
                    hintBt.setText(IMMDL_START);
                    hintBottomBt.setText(IMMDL_START);
                    hintBottomBt.setVisibility(View.VISIBLE);
                    hintBt.setVisibility(View.VISIBLE);
                    addcartTv.setVisibility(View.GONE);
                    immdlBuyTv.setVisibility(View.GONE);
                    addcartBottomTv.setVisibility(View.GONE);
                    immdlBuyBottomTv.setVisibility(View.GONE);
                }
                long l = DateUtils.differentDay(bean.getStart_time(), System.currentTimeMillis());
                unStartTime(l);
            } else if (DateUtils.compareNowTime(bean.getEnd_time())) {
                //活动已结束
                timeState = ENDTIME;
                titmeTv.setVisibility(View.GONE);
            } else {//活动开始中
                titmeTv.setVisibility(View.VISIBLE);
                timeState = STARTING;
                long l = DateUtils.differentDay(bean.getEnd_time(), System.currentTimeMillis());
                startingTime(l);
            }
        }
    }


    /**
     * 毫秒转化
     *
     * @param ms
     * @return
     */
    public static String formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strDay + "天" + strHour + "小时" + strMinute + " 分钟 " + strSecond + " 秒";
    }

    /**
     * 活动未开始倒计时
     *
     * @param secTime
     */
    private void unStartTime(long secTime) {
        timer = new CountDownTimer(secTime - 10, 1000) {
            @Override
            public void onTick(long l) {
                titmeTv.setText(String.format(START_TIME_HINT, formatTime(l)));
            }

            @Override
            public void onFinish() {
                if (timeState == UN_START) {
                    timeState = STARTING;
                    hintBottomBt.setVisibility(View.GONE);
                    hintBt.setVisibility(View.GONE);
                    addcartTv.setVisibility(View.VISIBLE);
                    immdlBuyTv.setVisibility(View.VISIBLE);
                    addcartBottomTv.setVisibility(View.VISIBLE);
                    immdlBuyBottomTv.setVisibility(View.VISIBLE);
                    timer = null;
                    long l = DateUtils.differentDay(bean.getEnd_time(), System.currentTimeMillis());
                    startingTime(l);
                } else if (timeState == STARTING) {
                    titmeTv.setVisibility(View.GONE);
                }


            }
        }.start();

    }

    /**
     * 活动进行中的倒计时
     */
    private void startingTime(long secTime) {
        timer = new CountDownTimer(secTime - 10, 1000) {
            @Override
            public void onTick(long l) {
                titmeTv.setText(String.format(END_TIME_HINT, formatTime(l)));
            }

            @Override
            public void onFinish() {
                if (timeState == STARTING) {
                    titmeTv.setVisibility(View.GONE);
                }
            }
        }.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.onFinish();
        }
    }


    @OnClick({R.id.subtract_tv, R.id.add_tv, R.id.left_scroll_img, R.id.right_scroll_img, R.id.addcart_tv, R.id.immdl_buy_tv, R.id.phonebuy_tv, R.id.back, R.id.addcart_bottom_tv, R.id.immdl_buy_bottom_tv, R.id.top_imgbt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.subtract_tv:
                //减
                int goodsnum1 = Integer.parseInt(selectGoodsNumTv.getText().toString());
                if (goodsnum1 <= 1) {
                    Toast.getInstance().toast(this, "至少选择一件商品", 2);
                    return;
                }
                selectGoodsNumTv.setText(goodsnum1 - 1 + "");

                break;
            case R.id.add_tv:
                //加
                int goodsnum = Integer.parseInt(selectGoodsNumTv.getText().toString());
                if (goodsnum >= stockNum) {
                    Toast.getInstance().toast(this, Constant.NOOVER_STOCK, 2);
                    return;
                }
                selectGoodsNumTv.setText(goodsnum + 1 + "");

                break;
            case R.id.addcart_bottom_tv:
            case R.id.addcart_tv:
                //加入购物车
                if (Integer.parseInt(selectGoodsNumTv.getText().toString()) > stockNum) {
                    Toast.getInstance().toast(this, "商品不能超过库存数", 2);
                    return;
                }
                mPresenter.addCart(DeviceInfoUtil.getDeviceId(this), bean.getId(), 0, Integer.parseInt(selectGoodsNumTv.getText().toString()));

                break;

            case R.id.left_scroll_img:
                if (picPisition != 0) {
                    goodsRecycler.scrollToPosition(picPisition - 1);
                    picPisition = picPisition - 1;
                }
                break;
            case R.id.right_scroll_img:
                if (picPisition != image_list.size() - 1) {
                    goodsRecycler.scrollToPosition(picPisition + 1);
                    picPisition = picPisition + 1;
                }

                break;
            case R.id.phonebuy_tv:
                if (codePop == null) {
                    codePop = new CodePop(this, EnvConfig.instance().getWebServiceBaseUrl() + "goods?id=" + bean.getId());
                }
                codePop.showAsDropDown(phonebuyTv);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.immdl_buy_tv:
            case R.id.immdl_buy_bottom_tv:
                openActivity(LoginActivity.class);
                break;
            case R.id.top_imgbt:
                //返回顶部
                scrollview.scrollBy(0, 0);
                break;
        }
    }

    @Override
    public void addCardSucc(BaseResult baseResult) {
        Toast.getInstance().toast(this, "添加成功！购物车等您亲", 2);
    }

}
