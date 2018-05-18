package com.maoye.mlh_slotmachine.view.goodsdetialsactivity;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.Drawable;
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
import android.view.animation.LinearInterpolator;
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
import com.maoye.mlh_slotmachine.util.Toast;
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
import com.maoye.mlh_slotmachine.widget.banner.Banner;
import com.maoye.mlh_slotmachine.widget.banner.listener.OnBannerListener;

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
    @BindView(R.id.left_center_rl)
    RelativeLayout leftCenterRl;
    @BindView(R.id.parent_rl)
    RelativeLayout parentRl;
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
    private BadgeView badgeView, animBadgeView;
    private int cartNum;//购物车商品数量
    private int goodsId;
    private ArrayList<String> resultList;
    private boolean isAddCartBottom;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];//塞尔曲线中间过程的点的坐标

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
        animBadgeView = new BadgeView(this);
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

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                ArrayList<String> arrayList = new ArrayList<>();
                for (GoodsDetialsBean.ImageListBean imageListBean : image_list) {
                    arrayList.add(imageListBean.getImage_url());
                }
                Intent intent = new Intent(GoodsdetialsActivity.this, ImgActivity.class);
                intent.putExtra(Constant.KEY, arrayList);
                intent.putExtra(Constant.POSITION, position);
                startActivity(intent);
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


    public static Bitmap createPic(Context mContext, View view) {
        Bitmap screenshot = null;
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        int w = view.getWidth();
        int h = view.getHeight();
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        screenshot = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        view.draw(c);
        return screenshot;
    }


    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     *
     * @param view   移动的起始view
     * @param bitmap 移动的图片
     */
    private void addCart(View view, Bitmap bitmap) {
        final ImageView goods = new ImageView(GoodsdetialsActivity.this);
        goods.setImageBitmap(bitmap);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(40, 40);
        parentRl.addView(goods, params);
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        parentRl.getLocationInWindow(parentLocation);

        int startLoc[] = new int[2];
        view.getLocationInWindow(startLoc);
        int endLoc[] = new int[2];
        cartImg.getLocationInWindow(endLoc);
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + view.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + view.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片
        float toX = endLoc[0] - parentLocation[0] + cartImg.getWidth() - 20;
        float toY = endLoc[1] - parentLocation[1] - 10;

//    四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//   五、 开始执行动画
        valueAnimator.start();
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                badgeView.setBadgeCount(cartNum);
                // 把移动的图片imageview从父布局里移除
                parentRl.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
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
        salednumTv.setText(String.format("%s件已售", bean.getSales() + ""));
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
        if (bean.getSpec_name_list() != null) {
            mPresenter.getStockNum(specList, bean, bean.getStock());
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
                isAddCartBottom = false;
                int goodsnum = Integer.parseInt(selectGoodsNumTv.getText().toString());
                if (goodsnum >= stockNum) {
                    Toast.getInstance().toast(this, Constant.NOOVER_STOCK, 2);
                    return;
                }
                selectGoodsNumTv.setText(goodsnum + 1 + "");

                break;
            case R.id.addcart_bottom_tv:
                isAddCartBottom = true;
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
    /*    if(position!=0)
        banner.setCurrentPosition(position-1);*/
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
        int num = Integer.parseInt(selectGoodsNumTv.getText().toString());
        animBadgeView.setBadgeCount(num);
        cartNum = cartNum + num;
        addCart(isAddCartBottom ? addcartBottomTv : addTv, createPic(this, animBadgeView));
        // badgeView.setBadgeCount(cartNum);
        Toast.getInstance().toast(this, "已成功加入购物车", 2);
    }

}
