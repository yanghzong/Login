package com.example.login.inter;

public interface MyCallBack <T>{
    void onSuccess(T data);
    void onFail(String error);
}
