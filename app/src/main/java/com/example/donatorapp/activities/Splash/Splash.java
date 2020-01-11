package com.example.donatorapp.activities.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.donatorapp.Models.BloodTypes.BloodTypesDataResponse;
import com.example.donatorapp.Models.BloodTypes.BloodTypesResponse;
import com.example.donatorapp.Models.Cities.GetCitiesDataResponseModel;
import com.example.donatorapp.Models.Cities.GetCitiesResponseModel;
import com.example.donatorapp.Network.NetworkUtil;
import com.example.donatorapp.R;
import com.example.donatorapp.Utills.Constant;
import com.example.donatorapp.Utills.Validation;
import com.example.donatorapp.activities.Home.HomeActivity;
import com.example.donatorapp.activities.Login.LoginActivity;
import com.example.donatorapp.activities.NoInternet.NoInternetActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.donatorapp.Utills.Constant.CitiesArrayList;
import static com.example.donatorapp.Utills.Constant.CitiesIdArrayList;
import static com.example.donatorapp.Utills.Constant.bloodTypeArrayList;
import static com.example.donatorapp.Utills.Constant.bloodTypeIdArrayList;


public class Splash extends AppCompatActivity {

    SharedPreferences mSharedPreferences;
    CompositeSubscription mSubscriptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       mSubscriptions = new CompositeSubscription();
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        setContentView(R.layout.activity_splash);
        if (Validation.isConnected(this)) {
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetCities()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetBloodTypes()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponseBloodTypes, this::handleError));
            if (mSharedPreferences.getString(Constant.accessToken, "").equals("")) {
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, instanceIdResult -> {
                    String newToken = instanceIdResult.getToken();
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString(Constant.notificationToken, newToken);
                    editor.apply();
                });
            }

            new Handler().postDelayed(() -> {
                Intent intent;
                if ((mSharedPreferences.getBoolean(Constant.rememberMe, false))) {
                    intent = new Intent(Splash.this, HomeActivity.class);

                } else {
                    intent = new Intent(Splash.this, LoginActivity.class);
                }

                finish();
                startActivity(intent);
            }, 3000);
        } else {
            Intent intent = new Intent(Splash.this, NoInternetActivity.class);
            finish();
            startActivity(intent);
        }


    }

    private void handleResponseBloodTypes(BloodTypesResponse bloodTypesResponse) {
        switch (bloodTypesResponse.getResultCode()) {
            case "1":
                ArrayList<BloodTypesDataResponse> bloodTypesDataResponses = bloodTypesResponse.getData();
                bloodTypeArrayList = new ArrayList<String>();
                bloodTypeIdArrayList = new ArrayList<String>();

                for (BloodTypesDataResponse BloodType :
                        bloodTypesDataResponses) {
                    bloodTypeArrayList.add(BloodType.getName());
                    bloodTypeIdArrayList.add(BloodType.getBloodTypeId());
                }
                break;
            case "0":
            case "2":
               // Toast.makeText(this, bloodTypesResponse.getResultMessageEn(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void handleError(Throwable throwable) {
       // Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    private void handleResponse(GetCitiesResponseModel getCitiesResponseModel) {
        switch (getCitiesResponseModel.getResultCode()) {
            case "1":
                ArrayList<GetCitiesDataResponseModel> getCitiesDataResponseModel = getCitiesResponseModel.getData();
                CitiesArrayList = new ArrayList<String>();
                CitiesIdArrayList = new ArrayList<String>();

                for (GetCitiesDataResponseModel city :
                        getCitiesDataResponseModel) {
                    CitiesArrayList.add(city.getName());
                    CitiesIdArrayList.add(city.getCityId());
                }
               // Toast.makeText(this, CitiesArrayList.size() + "," + CitiesIdArrayList.size(), Toast.LENGTH_LONG).show();
                break;
            case "0":
            case "2":
               // Toast.makeText(this, getCitiesResponseModel.getResultMessageEn(), Toast.LENGTH_LONG).show();
                break;
        }
    }
}
