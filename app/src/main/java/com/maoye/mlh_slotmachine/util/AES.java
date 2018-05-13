package com.maoye.mlh_slotmachine.util;

/**
 * Created by Rs on 2018/5/11.
 */


import android.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Rs on 2017/8/28.
 */

public class AES {
    public static final String KEY = "maoye_mlhj";
    private static final int NUM = 16;

    public static String getNonce(){
        String nonce = "";
        for (int i = 0; i < NUM; i++) {
            char temp = 0;
            int key = (int) (Math.random() * 2);
            switch (key) {
                case 0:
                    temp = (char) (Math.random() * 10 + 48);//产生随机数字
                    break;
                case 1:
                    temp = (char) (Math.random()*6 + 'a');//产生a-f
                    break;
                default:
                    break;
            }
            nonce = nonce + temp;
        }
        return nonce;
    }


    /**
     * 加密
     * @param data
     */
    public static String encrypt(String data) throws Exception {
        if(data ==null){
            return null;
        }
        byte[] raw = KEY.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));
        return Base64.encodeToString(encrypted,Base64.NO_WRAP);
      //  return Base64.encodeToString(encrypted,Base64.NO_WRAP).replace("+","%2B");
    }

    public static String encryptUrl(String data) throws Exception {
        if(data ==null){
            return null;
        }
        byte[] raw = KEY.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));
        return Base64.encodeToString(encrypted,Base64.NO_WRAP).replace("+","%2B");
    }



    /**
     * 解密
     * @param data
     */
    public static String decrypt(String data) throws Exception {
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 =Base64.decode(data,Base64.NO_WRAP);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
