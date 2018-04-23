package com.maoye.mlh_slotmachine.webservice;

/**
 * Created by Rs on 2018/4/9.
 */

public class DevConfig extends EnvConfig {
    @Override
    public String getWebServiceBaseUrl() {
        return "http://172.29.36.199/";
    }

    @Override
    public int getEnvType() {
        return 0;
    }
}
