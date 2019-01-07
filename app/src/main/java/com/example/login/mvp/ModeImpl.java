package com.example.login.mvp;

import com.example.login.inter.MyCallBack;
import com.example.login.utils.RefetfitMessage;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public  class ModeImpl<T> implements Model {
    @Override
    public void requestData(String url, Map<String, String> params, final Class clazz, final MyCallBack callBack) {
        Map<String, RequestBody> map = RefetfitMessage.getInstance().generateRequestBody(params);
        RefetfitMessage.getInstance().postFormBody(url, map).result(new RefetfitMessage.HttpListener() {
            @Override
            public void onSuccess(String data) {
                try{
                    Object o = new Gson().fromJson(data, clazz);
                    if(callBack != null){
                        callBack.onSuccess(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(callBack != null){
                        callBack.onFail(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
                if(callBack != null){
                    callBack.onFail(error);
                }
            }
        });
    }

}
