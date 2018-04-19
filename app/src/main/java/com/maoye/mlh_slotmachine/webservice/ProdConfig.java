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
    public int getEnvType() {
        return 1;
    }
}
