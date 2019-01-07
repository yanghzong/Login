package com.example.login.mvp;

import java.util.Map;

public interface Presenter {
    void startRequest(String url, Map<String, String> params, Class clazz);
}
