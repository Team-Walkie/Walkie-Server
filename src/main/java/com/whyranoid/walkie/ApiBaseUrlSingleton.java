package com.whyranoid.walkie;

public class ApiBaseUrlSingleton {
    private static ApiBaseUrlSingleton instance;
    private String baseUrl;

    private ApiBaseUrlSingleton() {
        // private 생성자로 외부에서 인스턴스를 생성하지 못하도록 함
    }

    public static ApiBaseUrlSingleton getInstance() {
        if (instance == null) {
            instance = new ApiBaseUrlSingleton();
        }
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
