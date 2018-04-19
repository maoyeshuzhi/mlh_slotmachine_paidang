package com.maoye.mlh_slotmachine.webservice;

/**
 * Created by Rs on 2018/4/9.
 */

public abstract class EnvConfig {
    private static Stage stage = Stage.DEV;

    public static EnvConfig instance() {
        switch (stage) {
            case DEV:
                return new DevConfig();
            case PROD:
                return new ProdConfig();
            default:
                return null;
        }
    }

    public abstract String getWebServiceBaseUrl();

    public abstract int getEnvType();

    public enum Stage {
        DEV, PROD
    }
}
