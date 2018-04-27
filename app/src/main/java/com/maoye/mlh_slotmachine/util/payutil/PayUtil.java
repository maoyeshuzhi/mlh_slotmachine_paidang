package com.maoye.mlh_slotmachine.util.payutil;

import android.app.Activity;
import android.content.Intent;

import com.maoye.mlh_slotmachine.util.Constant;
import com.maoye.mlh_slotmachine.util.Toast;

import java.util.Map;

/**
 * Created by Rs on 2018/4/26.
 */

public class PayUtil {
    /**
     * 支付宝支付
     * @param context
     * @param resultMap  支付宝返回结果
     * @param orderNo   订单号
     * @param orderId  订单id
     * @param orderAmount  总资金（包括邮费）
     */
    public void aliPayResult(Activity context, Map<String, String> resultMap, String orderNo, int orderId, String orderAmount) {
        @SuppressWarnings("unchecked")
        PayResult payResult = new PayResult(resultMap);
        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
        String resultStatus = payResult.getResultStatus();
        if (resultStatus.equals("6001")) {
            //取消
        } else if (resultStatus.equals("6002")) {
            Toast.getInstance().toast(context,"网络连接错误",2);
        } else if (resultStatus.equals("5000")) {
            Toast.getInstance().toast(context,"请勿重复操作",2);
        } else if (resultStatus.equals("8000")) {
            Toast.getInstance().toast(context,"正在处理中",2);
        } else if (resultStatus.equals("9000")) {
            //支付成功
        }
    }

}
