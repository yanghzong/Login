package com.example.login.mvp;

public interface View<T> {
    void getDataSuccess(T data);
    void getDataFail(String error);
}
