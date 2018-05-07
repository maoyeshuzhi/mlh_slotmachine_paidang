package com.maoye.mlh_slotmachine.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maoye.mlh_slotmachine.R;
import com.maoye.mlh_slotmachine.bean.BaseResult;
import com.maoye.mlh_slotmachine.bean.ProvinceBean;
import com.maoye.mlh_slotmachine.listener.OnItemChildClickListener;
import com.maoye.mlh_slotmachine.listener.OnItemClickListener;
import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.LogUtils;
import com.maoye.mlh_slotmachine.util.TextUtil;
import com.maoye.mlh_slotmachine.util.Toast;
import com.maoye.mlh_slotmachine.util.httputil.BaseRetrofit;
import com.maoye.mlh_slotmachine.util.httputil.HttpResultFunc;
import com.maoye.mlh_slotmachine.util.httputil.subscribers.BaseObserver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rs on 2018/4/21.
 */

public class DialogAddLocalAddress extends AlertDialog {
    @BindView(R.id.detialaddress_et)
    EditText detialaddressEt;
    @BindView(R.id.name_et)
    EditText nameEt;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.close_tv)
    TextView closeTv;
    @BindView(R.id.save_tv)
    TextView saveTv;
    @BindView(R.id.province_tv)
    TextView provinceTv;
    @BindView(R.id.city_tv)
    TextView cityTv;
    @BindView(R.id.district_tv)
    TextView districtTv;
    @BindView(R.id.address_ll)
    LinearLayout addressLl;
    private ProvinceBean provinceBean, cityBean, districtBean;

    private ProvincePopWindow provincePop;
    public static final int PROVINCRE = 0;
    public static final int CITY = 1;
    public static final int DISTRICT = 2;
    private OnItemChildClickListener onItemChildClickListener;

    public void  setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener){
        this.onItemChildClickListener = onItemChildClickListener;
    }



    public DialogAddLocalAddress(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_addlocaladdress);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(false);


    }

    @OnClick({R.id.close_tv, R.id.save_tv, R.id.province_tv, R.id.city_tv, R.id.district_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_tv:
                dismiss();
                break;
            case R.id.save_tv:
                if(judge()){
                    Observable observable = BaseRetrofit.getInstance()
                            .apiService.addAddress(nameEt.getText().toString(),phoneEt.getText().toString(),provinceBean.getRegion_id(),cityBean.getRegion_id(),districtBean.getRegion_id(),detialaddressEt.getText().toString())
                            .map(new HttpResultFunc());
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                }

                break;
            case R.id.province_tv:
                provinceTv.setClickable(false);
                provinceTv.setEnabled(false);
                Observable observable = BaseRetrofit.getInstance().apiService.province().map(new HttpResultFunc<List<ProvinceBean>>());
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(provinceObserver);
                break;
            case R.id.city_tv:
                if (provinceBean == null) {
                    return;
                }
                cityTv.setEnabled(false);
                cityTv.setClickable(false);
                Observable cityObservable = BaseRetrofit.getInstance().apiService.city(provinceBean.getRegion_id()).map(new HttpResultFunc<List<ProvinceBean>>());
                cityObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(cityObserver);
                break;
            case R.id.district_tv:
                if (cityBean == null) return;
                districtTv.setClickable(false);
                districtTv.setEnabled(false);
                Observable districtObservable = BaseRetrofit.getInstance().apiService.city(cityBean.getRegion_id()).map(new HttpResultFunc<List<ProvinceBean>>());
                districtObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(districtObserver);
                break;
        }
    }

    private boolean judge() {
        if(provinceBean==null ||cityBean ==null ||districtBean ==null){
            Toast.getInstance().toast(getContext(),"省市区不能为空",2);
            return false;
        }

        if(detialaddressEt.getText()==null||detialaddressEt.getText().toString().trim().length()==0){
            Toast.getInstance().toast(getContext(),"详细地址不能为空",2);
            return false;
        }
        if(nameEt.getText()==null ||nameEt.getText().toString().trim().length()==0){
            Toast.getInstance().toast(getContext(),"姓名不能为空",2);
            return false;
        }
        if(!TextUtil.isMobile(phoneEt.getText()+"")){
            Toast.getInstance().toast(getContext(),  Constant.PLEASE_INPUT_RIGHT_PHONE,2);
            return false;
        }
        return true;
    }

    Observer<BaseResult<List<ProvinceBean>>> provinceObserver = new BaseObserver<BaseResult<List<ProvinceBean>>>(getContext(),true) {
        @Override
        protected void onBaseNext(BaseResult<List<ProvinceBean>> data) {
            provincePop = new ProvincePopWindow(getContext(), data.getData(), provinceBean,PROVINCRE);
            provincePop.showAsDropDown(addressLl);

            provinceTv.setClickable(true);
            provinceTv.setEnabled(true);
            provincePop.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Object data) {
                    if(position == PROVINCRE) {
                        if(provinceBean!=null&&provinceBean.getRegion_id() == ((ProvinceBean) data).getRegion_id()){
                        }else {
                            cityBean =null;
                            districtBean =null;
                            cityTv.setText("");
                            districtTv.setText("");
                        }
                        provinceBean = (ProvinceBean) data;
                        provinceTv.setText(provinceBean.getRegion_name() + "");

                    }
                }
            });
        }

        @Override
        protected void onBaseError(Throwable t) {
            provinceTv.setClickable(true);
            provinceTv.setEnabled(true);
           // LogUtils.e(t.getMessage());
        }
    };


    Observer<BaseResult<List<ProvinceBean>>> cityObserver = new BaseObserver<BaseResult<List<ProvinceBean>>>(getContext(),true) {
        @Override
        protected void onBaseNext(BaseResult<List<ProvinceBean>> data) {
            provincePop = new ProvincePopWindow(getContext(), data.getData(), cityBean,CITY);
            provincePop.showAsDropDown(addressLl);
            cityTv.setEnabled(true);
            cityTv.setClickable(true);
            provincePop.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Object data) {
                    if(position ==CITY) {
                        if(!(cityBean!=null &&cityBean.getRegion_id() ==((ProvinceBean)data).getRegion_id())){
                            districtBean =null;
                            districtTv.setText("");
                        }
                        cityBean = (ProvinceBean) data;
                        cityTv.setText(cityBean.getRegion_name() + "");
                    }
                }
            });
        }

        @Override
        protected void onBaseError(Throwable t) {
            cityTv.setEnabled(true);
            cityTv.setClickable(true);
        }
    };

    Observer<BaseResult<List<ProvinceBean>>> districtObserver = new BaseObserver<BaseResult<List<ProvinceBean>>>(getContext(),true) {
        @Override
        protected void onBaseNext(BaseResult<List<ProvinceBean>> data) {
            provincePop = new ProvincePopWindow(getContext(), data.getData(), districtBean,DISTRICT);
            provincePop.showAsDropDown(addressLl);
            districtTv.setClickable(true);
            districtTv.setEnabled(true);
            provincePop.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, Object data) {
                    if(position ==DISTRICT){
                        districtBean = (ProvinceBean) data;
                        districtTv.setText(((ProvinceBean) data).getRegion_name() + "");
                    }

                }
            });
        }

        @Override
        protected void onBaseError(Throwable t) {
            districtTv.setClickable(true);
            districtTv.setEnabled(true);
        }
    };

    Observer<BaseResult> observer = new BaseObserver<BaseResult>(getContext(),true) {
        @Override
        protected void onBaseNext(BaseResult data) {
            if(data.isState()){
                dismiss();
                if(onItemChildClickListener!=null){
                    onItemChildClickListener.onChildItemClick(null,0,0,null);
                }
            }
        }

        @Override
        protected void onBaseError(Throwable t) {

        }
    };

}
