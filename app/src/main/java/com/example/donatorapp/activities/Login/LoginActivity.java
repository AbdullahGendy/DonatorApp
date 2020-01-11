package com.example.donatorapp.activities.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.donatorapp.Models.DeviceToken.UpdateDeviceTokenRequest;
import com.example.donatorapp.Models.DeviceToken.UpdateDeviceTokenResponse;
import com.example.donatorapp.Models.Login.LoginRequestModel;
import com.example.donatorapp.Models.Login.LoginResponseModel;
import com.example.donatorapp.Network.NetworkUtil;
import com.example.donatorapp.R;
import com.example.donatorapp.Utills.Constant;
import com.example.donatorapp.Utills.Validation;
import com.example.donatorapp.activities.Home.HomeActivity;
import com.example.donatorapp.activities.SignUp.SignUpActivity;

import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.donatorapp.Utills.Constant.ErrorDialog;

public class LoginActivity extends AppCompatActivity {
    TextView signUpTextView;
    Button loginButton;
    SharedPreferences mSharedPreferences;

    AppCompatEditText username_text_view;
    AppCompatEditText password_text_view;
    CheckBox rememberMeCheckBox;
    String mAccessToken;
    private CompositeSubscription mSubscriptions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mSubscriptions = new CompositeSubscription();

        rememberMeCheckBox = findViewById(R.id.remember_me_check_box);
        signUpTextView = findViewById(R.id.sign_up_text_view);
        loginButton = findViewById(R.id.login_button);
        username_text_view = findViewById(R.id.username_text_view);
        password_text_view = findViewById(R.id.password_text_view);
        loginButton = findViewById(R.id.login_button);
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        signUpTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            finish();
            startActivity(intent);
        });
        loginButton.setOnClickListener(v ->
                validation(Objects.requireNonNull(username_text_view.getText()).toString(),
                        Objects.requireNonNull(password_text_view.getText()).toString()));


    }

    void validation(String userName, String password) {
        if (!Validation.validateFields(userName)) {
            ErrorDialog(this, "username is Empty");
        } else if (!Validation.validateFields(password)) {
            ErrorDialog(this, "Password is Empty");
        } else {
            if (Validation.isConnected(this)) {
                LoginRequestModel loginRequestModel = new LoginRequestModel();
                loginRequestModel.setName(userName);
                loginRequestModel.setPassword(password);
                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .Login(loginRequestModel)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse, this::handleErrorLogin));
            } else {
                Constant.buildDialog(this);
            }
        }
    }

    private void handleErrorLogin(Throwable throwable) {
        //Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("LoginThrowable", throwable.getMessage(), throwable);
    }

    private void handleError(Throwable throwable) {
       // Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("NewTokenThrowable", throwable.getMessage(), throwable);
    }

    private void handleResponse(LoginResponseModel loginResponseModel) {
        switch (loginResponseModel.getResultCode()) {
            case "1":
                UpdateDeviceTokenRequest updateDeviceTokenRequest = new UpdateDeviceTokenRequest();
                updateDeviceTokenRequest.setDonatorId(loginResponseModel.getData());
                mAccessToken = loginResponseModel.getData();
                updateDeviceTokenRequest.setDeviceToken(mSharedPreferences.getString(Constant.notificationToken, ""));
                mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                        .UpdateDonatorDeviceToken(updateDeviceTokenRequest)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponseNewToken, this::handleError));
                break;
            case "0":
                Constant.ErrorDialog(this, "Username or password is incorrect, Please try again.");
                Log.e("LoginServerError(0)", loginResponseModel.getResultMessageEn());

                break;
            case "2":
                Log.e("LoginServerError(2)", loginResponseModel.getResultMessageEn());
                break;
        }
    }

    private void handleResponseNewToken(UpdateDeviceTokenResponse updateDeviceTokenResponse) {

        switch (updateDeviceTokenResponse.getResultCode()) {
            case "1":
                //Toast.makeText(this, updateDeviceTokenResponse.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor edit = mSharedPreferences.edit();
                edit.putBoolean(Constant.rememberMe, rememberMeCheckBox.isChecked());
                edit.putString(Constant.accessToken, mAccessToken);
                edit.apply();
                Intent intent = new Intent(this, HomeActivity.class);
                finish();
                startActivity(intent);
                break;
            case "0":
                Constant.ErrorDialog(this, "Username or password is incorrect, Please try again.");
                Log.e("NewTokenServerError(0)", updateDeviceTokenResponse.getResultMessageEn());

                break;
            case "2":
                Log.e("NewTokenServerError(2)", updateDeviceTokenResponse.getResultMessageEn());
                break;
        }
    }

}
