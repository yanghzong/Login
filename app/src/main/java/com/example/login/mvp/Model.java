package com.example.login.mvp;

import com.example.login.inter.MyCallBack;

import java.util.Map;

public interface Model {
    void requestData(String url, Map<String, String> params, Class clazz, MyCallBack callBack);
}
