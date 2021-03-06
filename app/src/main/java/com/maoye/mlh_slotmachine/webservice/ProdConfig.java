package com.maoye.mlh_slotmachine.webservice;

/**
 * 生产环境
 */
public class ProdConfig extends EnvConfig{

    @Override
    public String getWebServiceBaseUrl() {
        return "http://itao.maoye.cn/";
    }

    @Override
    public String getQuickPayWebServiceBaseUrl() {
        return "http://172.29.100.105:8081/";
    }

    @Override
    public String getBaseUkfUrl() {
        return "https://uke.maoye.cn/";
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
