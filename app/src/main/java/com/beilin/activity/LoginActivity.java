package com.beilin.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.beilin.App;
import com.beilin.R;
import com.beilin.request.LoginRequest;
import com.beilin.request.SignInRequest;
import com.beilin.utils.Tools;

import java.util.List;

/**
 * 注册界面
 * Created by ChengTao on 2016-07-21.
 */
@SuppressWarnings("ALL")
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText etUser;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignIn;
    //请求
    private LoginRequest loginRequest;
    private SignInRequest signInRequest;
    //请求ID
    private final static int LOGIN_REQUEST = 1;
    private final static int SIGN_IN_REQUEST = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etUser = obtainView(R.id.login_et_user);
        etPassword = obtainView(R.id.login_et_password);
        btnLogin = obtainView(R.id.login_btn_login);
        btnSignIn = obtainView(R.id.login_btn_sign_in);
    }

    @Override
    protected void setListener() {
        btnSignIn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login://登录
                login();
                break;
            case R.id.login_btn_sign_in://注册
                signIn();
                break;
        }
    }

    /**
     * 注册
     */
    private void signIn() {
        signInRequest = new SignInRequest(mContext,etUser.getText().toString(),etPassword.getText().toString());
        signInRequest.setRequestId(SIGN_IN_REQUEST);
        leanColud(signInRequest);
    }

    /**
     * 登录
     */
    private void login() {
        loginRequest = new LoginRequest(mContext,etUser.getText().toString(),etPassword.getText().toString());
        loginRequest.setRequestId(LOGIN_REQUEST);
        leanColud(loginRequest);
    }

    @Override
    public void onStart(int requestId) {
        super.onStart(requestId);
        switch (requestId){
            case LOGIN_REQUEST:
                Tools.printLog("----onStart-----LOGIN_REQUEST---------");
                break;
            case SIGN_IN_REQUEST:
                Tools.printLog("----onStart-----SIGN_IN_REQUEST---------");
                break;
        }
    }

    @Override
    public void onSuccess(int requestId, List<AVObject> list) {
        super.onSuccess(requestId, list);
        switch (requestId){
            case LOGIN_REQUEST:
                showToast("登录成功");
                Tools.printLog("----onSuccess-----LOGIN_REQUEST---------");
                MainActivity.invoke(mContext);
                break;
            case SIGN_IN_REQUEST:
                showToast("注册成功");
                Tools.printLog("----onSuccess-----SIGN_IN_REQUEST---------");
                Tools.printLog("----onSuccess-----App.currentUser---------"+App.currentUser.toString());
                break;
        }
    }

    @Override
    public void onFailure(int requestId, AVException e) {
        super.onFailure(requestId, e);
        switch (requestId){
            case LOGIN_REQUEST:
                Tools.printLog("----onFailure-----LOGIN_REQUEST---------"+e.toString());
                showToast("登录失败");
                break;
            case SIGN_IN_REQUEST:
                Tools.printLog("----onFailure-----SIGN_IN_REQUEST---------"+e.toString());
                showToast("注册失败");
                break;
        }
    }
}
