package com.maoye.mlh_slotmachine.mvp;



public interface  BasePresenter <V extends BaseView>{
    void attachView(V view);
    void detachView();
}
