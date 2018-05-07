package com.maoye.mlh_slotmachine.webservice;

/**
 * 生产环境
 */

public class ProdConfig extends EnvConfig{
    @Override
    public String getWebServiceBaseUrl() {
        return null;
    }

    @Override
    public String getH5BaseUrl() {
        return "http://172.31.10.3:8899/";
    }

    @Override
    public int getEnvType() {
        return 1;
    }
}
