package com.example.login.mvp;

import com.example.login.inter.MyCallBack;

import java.util.Map;

public class PresenterImpl  implements Presenter{
    private ModeImpl model;
    private View iView;

    public PresenterImpl(View iView) {
        this.iView = iView;
        model = new ModeImpl();
    }

    @Override
    public void startRequest(String url, Map<String, String> params, Class clazz) {
        model.requestData(url, params, clazz, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {
                iView.getDataSuccess(data);
            }

            @Override
            public void onFail(String error) {
                iView.getDataFail(error);
            }
        });
    }

    public void onDetach() {
        if (model != null) {
            model = null;
        }
        if (iView != null) {
            iView = null;
        }
    }
}
