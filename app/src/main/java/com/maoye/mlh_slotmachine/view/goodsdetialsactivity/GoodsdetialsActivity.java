package com.maoye.mlh_slotmachine.view.goodsdetialsactivity;


import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.adapter.GoodsDetialsGoodsAdapter;
import com.maoye.mlh_slotmachine.adapter.SpecAdapter;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.CacheBean;
import com.maoye.mlh_slotmachine.bean.GoodsBean;
import com.maoye.mlh_slotmachine.bean.GoodsDetialsBean;
import com.maoye.mlh_slotmachine.bean.SpecBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.mvp.MVPBaseActivity;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.DateUtils;
import com.maoye.mlh_slotmachine.util.DensityUtil;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.httputil.ImgGlideUtil;
import com.maoye.mlh_slotmachine.util.httputil.cache.CacheUtil;
import com.maoye.mlh_slotmachine.view.cartactivity.CartActivity;
import com.maoye.mlh_slotmachine.view.imgactivity.ImgActivity;
import com.maoye.mlh_slotmachine.view.loginactivity.LoginActivity;
import com.maoye.mlh_slotmachine.webservice.EnvConfig;
import com.maoye.mlh_slotmachine.widget.BadgeView;
import com.maoye.mlh_slotmachine.widget.CodePop;
import com.maoye.mlh_slotmachine.widget.GlideImageLoaderCenter;
import com.maoye.mlh_slotmachine.widget.MyScrollView;
import com.maoye.mlh_slotmachine.widget.NoLineSpaceTextView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoodsdetialsActivity extends MVPBaseActivity<GoodsdetialsContract.View, GoodsdetialsPresenter> implements GoodsdetialsContract.View {
    @BindView(R.id.banner)
    Banner banner;
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
    @BindView(R.id.hint_bt)
    TextView hintBt;//不能购买按钮
    @BindView(R.id.hint_bottom_bt)
    TextView hintBottomBt;//不能购买按钮
    @BindView(R.id.top_imgbt)
    ImageButton topImgbt;
    @BindView(R.id.cart_img)
    ImageView cartImg;

    @BindView(R.id.address_tv)
    TextView addressTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.discription_wb)
    WebView discriptionWb;
    @BindView(R.id.address_ll)
    LinearLayout addressLl;
    @BindView(R.id.warmhint_bt)
    TextView warmhintBt;
    @BindView(R.id.address_bt)
    TextView addressBt;
    @BindView(R.id.detial_bt)
    TextView detialBt;
    @BindView(R.id.back_imgbt)
    ImageView backImgbt;
    @BindView(R.id.bottom_rl)
    RelativeLayout bottomRl;
    @BindView(R.id.scrollview)
    MyScrollView scrollview;
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
    public static final String START_TIME_HINT = "距离开始时间还有:%s";
    public static final String END_TIME_HINT = "还剩:%s";
    public static final String IMMDL_START = "即将开始";
    public static final String ALREAD_SALE = "已售罄";
    public static final String STOCK_NUM = "(库存还剩%s件)";
    public static final String INTEGRAL = "本商品购买预计可积%s~%s分";
    public static final String INTEGRAL_2 = "本商品购买预计可积%s分";
    private BadgeView badgeView;
    private int cartNum;//购物车商品数量
    private int goodsId;
    private ArrayList<String> resultList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_goodsdetials);
        goodsId = getIntent().getIntExtra(Constant.GOODS_ID, 0);
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        banner.setImageLoader(new GlideImageLoaderCenter());
        banner.start();
        badgeView = new BadgeView(this);
        badgeView.setStyle(1);
        badgeView.setTargetView(cartImg);
        badgeView.setBadgeMargin(10, 0, 0, 10);

        initWebSetting();
        goodsPicAdapter = new GoodsDetialsGoodsAdapter();
        goodsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        goodsRecycler.setAdapter(goodsPicAdapter);
        goodsPicAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object data) {
                picPisition = position;
                for (int i = 0; i < image_list.size(); i++) {
                    if (picPisition == i) {
                        image_list.get(i).setIs_default(1);
                    } else {
                        image_list.get(i).setIs_default(0);
                    }
                }
                goodsPicAdapter.addDatas(image_list);
                upDataBanner(position);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.specId = 0;
        specAdapter = new SpecAdapter();
        specificationRecycler.setLayoutManager(new LinearLayoutManager(this));
        specificationRecycler.setAdapter(specAdapter);
        mPresenter.getGoodsDetialsData(goodsId);
        Object query = CacheUtil.query(CacheUtil.GOODS_DETIALS + goodsId, GoodsDetialsBean.class);
        if (query != null) {
            bean = (GoodsDetialsBean) query;
            handleData();
        }
        specAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onChildItemClick(View view, int type, int position, Object data) {
                int num = mPresenter.getStockNum(specList, type, position, bean, bean.getStock());
                stockNum = num;
                stockNumTv.setText(String.format(STOCK_NUM, stockNum + ""));
                switchStockState(stockNum);
                salepriceTv.setText(String.format(Constant.PRICE_FORMAT, mPresenter.getPrice(bean.getSpec_list())));
                // specAdapter.addDatas(specList);
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

        WebSettings goodsDetialsWebSettings = discriptionWb.getSettings();
        //  goodsDetialsWebSettings.setSupportZoom(true); // 支持缩放
        goodsDetialsWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        goodsDetialsWebSettings.setLoadWithOverviewMode(true);//设置webview加载的页面的模式
        goodsDetialsWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        goodsDetialsWebSettings.setDomStorageEnabled(true);
        goodsDetialsWebSettings.setJavaScriptEnabled(true);
        goodsDetialsWebSettings.setBlockNetworkImage(false); // 解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            goodsDetialsWebSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }


    @Override
    public void onSuccess(Object o) {
        bean = (GoodsDetialsBean) o;
        CacheBean cacheBean = new CacheBean();
       // cacheBean.setId(CacheUtil.GOODS_DETIAL_ACTIVITY_ID);
        cacheBean.setJsonUrl(new Gson().toJson(bean));
        cacheBean.setName(CacheUtil.GOODS_DETIALS + goodsId);
        CacheUtil.put(cacheBean);
        handleData();

    }

    private void handleData() {
        cartNum = bean.getCartNum();
        salednumTv.setText(String.format("%s件已售", bean.getSales()+""));
        badgeView.setBadgeCount(cartNum);
        String WEB_STYLE = "<style>*{padding:0;margin:0;  color:#505050 !important; font-size:8px !important;} p {font-size:8px;}</style>";
        String warmHintWebContent = bean.getNotice().replace("<img", "<img style='max-width:100%;height:auto;'");
        String CONTENT_STYLE = "<style>*{padding:0;margin:0; } img {line-height:0px;}</style>";
        String goodsDetialsWebContent = bean.getDescription().replace("<img", "<img style='max-width:100%;height:auto;margin:0;padding:0;'");
        if (warmHintWebContent != null)
            hintWebview.loadDataWithBaseURL(null, WEB_STYLE + warmHintWebContent, "text/html", "utf-8", null);
        discriptionWb.loadDataWithBaseURL(null, CONTENT_STYLE + goodsDetialsWebContent, "text/html", "utf-8", null);
        goodTitleTv.setText(bean.getName() + "");
        timehandler();
        salepriceTv.setText(String.format(Constant.PRICE_FORMAT, bean.getPrice() + ""));
        SpannableString spannableString = new SpannableString(String.format(Constant.PRICE_FORMAT, bean.getOld_price() + ""));
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        oldpriceTv.setText(spannableString);
        stockNum = bean.getStock();
        addressTv.setText(String.format("地址：%s", bean.getService_address() + ""));
        phoneTv.setText(String.format("电话：%s", bean.getService_phone() + ""));
        stockNumTv.setText(String.format(STOCK_NUM, bean.getStock() + ""));
        if (bean.getDelivery_type() == 1) {//自提
            expressTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.unselect_hui_icon), null, null, null);
            addressLl.setVisibility(View.GONE);
        } else if (bean.getDelivery_type() == 2) {//快递
            pickupTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.unselect_hui_icon), null, null, null);
        }
        if (bean.getMax_points() == 0) {
            integralTv.setVisibility(View.GONE);
        } else if (bean.getMax_points() == bean.getMax_points()) {
            integralTv.setText(String.format(INTEGRAL_2, bean.getMax_points() + ""));
        } else {
            integralTv.setText(String.format(INTEGRAL, bean.getMin_points() + "", bean.getMax_points() + ""));
        }
        specList = mPresenter.handleSpecifi(bean.getSpec_name_list(), bean.getSpec_list());
        specAdapter.addDatas(specList);
        if(bean.getSpec_name_list()!=null) {
            mPresenter.getStockNum(specList,bean,bean.getStock());
        }
        image_list = bean.getImage_list();
        resultList = new ArrayList<>();
        for (GoodsDetialsBean.ImageListBean imageListBean : image_list) {
            resultList.add(imageListBean.getImage_url());
        }
        banner.update(resultList);
        goodsPicAdapter.addDatas(image_list);
/*        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                 Intent intent = new Intent(GoodsdetialsActivity.this, ImgActivity.class);
                 intent.putExtra(Constant.POSITION,position);
                 intent.putExtra(Constant.KEY, resultList);
                 intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 startActivity(intent);
            }
        });*/

        final int heigth = DensityUtil.dip2px(this, 270 + 106);
        scrollview.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (oldy >= heigth) {
                    topImgbt.setVisibility(View.VISIBLE);
                    backImgbt.setVisibility(View.GONE);
                    bottomRl.setVisibility(View.VISIBLE);
                } else {
                    topImgbt.setVisibility(View.INVISIBLE);
                    backImgbt.setVisibility(View.VISIBLE);
                    bottomRl.setVisibility(View.GONE);
                }
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
        } else if (bean.getActivity_type() == 1 || bean.getActivity_type() == 2) {
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
     * 活动未开始倒计时
     *
     * @param secTime
     */
    private void unStartTime(long secTime) {
        timer = new CountDownTimer(secTime - 10, 1000) {
            @Override
            public void onTick(long l) {
                titmeTv.setText(String.format(START_TIME_HINT, mPresenter.formatTime(l)));
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
                titmeTv.setText(String.format(END_TIME_HINT, mPresenter.formatTime(l)));
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


    @OnClick({R.id.subtract_tv, R.id.add_tv, R.id.left_scroll_img, R.id.right_scroll_img, R.id.addcart_tv, R.id.immdl_buy_tv, R.id.phonebuy_tv, R.id.back, R.id.addcart_bottom_tv, R.id.immdl_buy_bottom_tv, R.id.top_imgbt, R.id.cart_img, R.id.warmhint_bt, R.id.address_bt, R.id.detial_bt, R.id.back_imgbt})
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
                if (bean.getSpec_name_list().size() == 0) {
                    addCart();
                } else if (bean.getSpec_name_list().size() > 0 && mPresenter.specId != 0) {
                    addCart();
                } else {
                    scrollview.smoothScrollTo(0, 0);
                    Toast.getInstance().toast(this, "请先选择规格", 2);
                }
                break;
            case R.id.addcart_tv://加入购物车
                if (bean.getSpec_name_list().size() == 0) {
                    addCart();
                } else if (bean.getSpec_name_list().size() > 0 && mPresenter.specId != 0) {
                    addCart();
                } else {
                    Toast.getInstance().toast(this, "请先选择规格", 2);
                }
                break;
            case R.id.left_scroll_img:
                int i = leftScroll();
                if (i >= 0) upDataBanner(i);
                //  goodsRecycler.scrollTo(DensityUtil.dip2px(this,60)*(picPisition-1),0);
                break;
            case R.id.right_scroll_img:
                int position = rightscroll();
                if (position <= image_list.size() - 1)
                    upDataBanner(position);

                break;
            case R.id.phonebuy_tv:
                codePop = new CodePop(this, EnvConfig.instance().getWebServiceBaseUrl() + "goods?id=" + bean.getId());
                codePop.showAsDropDown(phonebuyTv);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.immdl_buy_tv:
                if (bean.getSpec_name_list().size() == 0) {
                    SkipConfirmActivity();
                } else if (bean.getSpec_name_list().size() > 0 && mPresenter.specId != 0) {
                    SkipConfirmActivity();
                } else {
                    Toast.getInstance().toast(this, "请先选择规格", 2);
                }
                break;
            case R.id.immdl_buy_bottom_tv:
                if (bean.getSpec_name_list().size() == 0) {
                    SkipConfirmActivity();
                } else if (bean.getSpec_name_list().size() > 0 && mPresenter.specId != 0) {
                    SkipConfirmActivity();
                } else {
                    scrollview.smoothScrollTo(0, 0);
                    Toast.getInstance().toast(this, "请先选择规格", 2);
                }

                break;
            case R.id.top_imgbt:
                //返回顶部
                scrollview.smoothScrollTo(0, 0);
                break;
            case R.id.cart_img:
                openActivity(CartActivity.class);
                break;

            case R.id.warmhint_bt:
                //滑到温馨提示
                scrollview.smoothScrollTo(0, DensityUtil.dip2px(this, 270 + 106));
                break;
            case R.id.address_bt:
                //滑到取货地址
                int height = DensityUtil.dip2px(this, 270 + 106 + 15 + 40) + DensityUtil.getViewHeight(hintWebview);
                scrollview.smoothScrollTo(0, height);
                break;
            case R.id.detial_bt:
                //滑到详情
                int i2 = DensityUtil.dip2px(this, 270 + 106 + 15 + 40 + 18) + DensityUtil.getViewHeight(hintWebview) + DensityUtil.getViewHeight(addressLl);
                scrollview.smoothScrollTo(0, i2);
                break;
            case R.id.back_imgbt:
                finish();
                break;
        }
    }


    private int leftScroll() {
        int position = 0;
        for (int i = 0; i < image_list.size(); i++) {
            if (image_list.get(i).getIs_default() == 1) {
                position = i;
                if (position != 0) image_list.get(i).setIs_default(0);
            }
        }
        position = position - 1;
        if (position >= 0) {
            image_list.get(position).setIs_default(1);
            goodsPicAdapter.addDatas(image_list);
            goodsRecycler.smoothScrollToPosition(position);
        }
        return position;
    }

    private int rightscroll() {
        int position = 0;
        for (int i = 0; i < image_list.size(); i++) {
            if (image_list.get(i).getIs_default() == 1) {
                position = i;
                if (position != image_list.size() - 1)
                    image_list.get(i).setIs_default(0);
            }

        }
        position = position + 1;
        if (position < image_list.size()) {
            image_list.get(position).setIs_default(1);
            goodsPicAdapter.addDatas(image_list);
            goodsRecycler.smoothScrollToPosition(position);
        }
        return position;
    }

    private void upDataBanner(int position) {
        List<String> list = new ArrayList<>();
        list.add(image_list.get(position).getImage_url());
        for (GoodsDetialsBean.ImageListBean imageListBean : image_list) {
            if (imageListBean.getIs_default() == 0) {
                list.add(imageListBean.getImage_url());
            }
        }
        banner.update(list);
    }

    private void addCart() {
        if (Integer.parseInt(selectGoodsNumTv.getText().toString()) > stockNum) {
            Toast.getInstance().toast(this, "商品不能超过库存数", 2);
            return;
        }
        mPresenter.addCart(bean.getId(), mPresenter.getSpecId(bean.getSpec_list()), Integer.parseInt(selectGoodsNumTv.getText().toString()));
    }

    private void SkipConfirmActivity() {
        ArrayList<GoodsBean> list = new ArrayList<>();
        GoodsBean paramsBean = new GoodsBean();
        paramsBean.setNum(Integer.valueOf(selectGoodsNumTv.getText() + ""));
        paramsBean.setPrice(mPresenter.getPrice(bean.getSpec_list()));
        paramsBean.setProduct_id(bean.getId());
        paramsBean.setOld_price(bean.getOld_price());
        paramsBean.setProduct_image(bean.getDefault_image());
        paramsBean.setSpec_id(mPresenter.getSpecId(bean.getSpec_list()));
        paramsBean.setProduct_name(bean.getName());
        paramsBean.setSpec_vals(mPresenter.getSpec_vals(bean.getSpec_list()));
        paramsBean.setDelivery_type(bean.getDelivery_type());
        list.add(paramsBean);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constant.FROM, Constant.FROM_BUY);
        intent.putExtra(Constant.KEY, list);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void addCardSucc(BaseResult baseResult) {
        int i = Integer.parseInt(selectGoodsNumTv.getText().toString());
        cartNum = cartNum + i;
        badgeView.setBadgeCount(cartNum);
        Toast.getInstance().toast(this, "已成功加入购物车", 2);
    }

}
