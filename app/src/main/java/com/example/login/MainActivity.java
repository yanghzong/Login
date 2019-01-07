package com.example.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.bean.LoginBean;
import com.example.login.bean.RegistBean;
import com.example.login.inter.APIS;
import com.example.login.mvp.PresenterImpl;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, com.example.login.mvp.View {
    private PresenterImpl mPresenter;
    private EditText mEtName, mEtPw;

    private final int TYPE_LOGIN = 0;
    private final int TYPE_REGISTER = TYPE_LOGIN + 1;
    private final int TYPE_GET_USER_INFO = TYPE_REGISTER + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPresenter = new PresenterImpl(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.button_main_login).setOnClickListener(this);;
        findViewById(R.id.button_main_register).setOnClickListener(this);;
        findViewById(R.id.button_main_get_user_info).setOnClickListener(this);;

        mEtName = findViewById(R.id.et_main_name);
        mEtPw = findViewById(R.id.et_main_pw);

        mEtName.setText("13800138000");
        mEtPw.setText("123456");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetach();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_main_login:
                checkPermission(TYPE_LOGIN);
                break;
            case R.id.button_main_register:
                checkPermission(TYPE_REGISTER);
                break;
            case R.id.button_main_get_user_info:
                checkPermission(TYPE_GET_USER_INFO);
                break;
            default:
                break;
        }
    }


    private void checkPermission(int type) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, type);
            } else {
                startRequest(type);
            }
        } else {
            startRequest(type);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startRequest(requestCode);
        }
    }

    private void startRequest(int type) {
        switch (type) {
            case TYPE_LOGIN:
                Map<String, String> params = new HashMap<>();
                params.put(Cantant.POST_BODY_KEY_LOGIN_PHONE, mEtName.getText().toString());
                params.put(Cantant.POST_BODY_KEY_LOGIN_PASSWORD, mEtPw.getText().toString());
                mPresenter.startRequest(APIS.URL_LOGIN_POST, params, LoginBean.class);
                break;
            case TYPE_REGISTER:
                Map<String , String > map = new HashMap<>();
                map.put(Cantant.POST_BODY_KEY_REGISTER_PHONE, mEtName.getText().toString());
                map.put(Cantant.POST_BODY_KEY_REGISTER_PASSWORD, mEtPw.getText().toString());
                mPresenter.startRequest(APIS.URL_REGISTER_POST, map, RegistBean.class);
                break;
            case TYPE_GET_USER_INFO:
                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {
        if(data instanceof LoginBean) {
            LoginBean loginBean = (LoginBean) data;
            Intent intent=new Intent(MainActivity.this,Main2Activity.class);
            startActivity(intent);


        }else if(data instanceof RegistBean){
            RegistBean registerBean = (RegistBean) data;
            Toast.makeText(this, registerBean.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
