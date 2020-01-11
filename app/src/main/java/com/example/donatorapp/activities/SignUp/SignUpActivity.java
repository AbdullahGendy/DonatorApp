package com.example.donatorapp.activities.SignUp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.donatorapp.Models.Registration.RegistrationRequestModel;
import com.example.donatorapp.Models.Registration.RegistrationResponseModel;
import com.example.donatorapp.Network.NetworkUtil;
import com.example.donatorapp.R;
import com.example.donatorapp.Utills.Constant;
import com.example.donatorapp.Utills.Validation;
import com.example.donatorapp.activities.Home.HomeActivity;
import com.example.donatorapp.activities.NoInternet.NoInternetActivity;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.view.View.GONE;
import static com.example.donatorapp.Utills.Constant.ErrorDialog;

public class SignUpActivity extends AppCompatActivity {
    String date;
    Button SignUpButton;
    Button done;
    EditText nameTextView;
    EditText passwordTextView;
    EditText confirmPasswordTextView;
    EditText mailTextView;
    EditText mobileTextView;
    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;
    Spinner spinnerCities, spinnerBloodType;
    private SharedPreferences mSharedPreferences;
    private CompositeSubscription mSubscriptions;
    String gender;
    EditText Birthday;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mSharedPreferences = getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        mSubscriptions = new CompositeSubscription();

        datePicker = findViewById(R.id.datePicker);
        done = findViewById(R.id.done);
        Birthday = findViewById(R.id.Birthday);
        nameTextView = findViewById(R.id.name_text_view);
        passwordTextView = findViewById(R.id.password_text_view);
        confirmPasswordTextView = findViewById(R.id.confirm_password_text_view);
        mailTextView = findViewById(R.id.mail_text_view);
        mobileTextView = findViewById(R.id.mobile_text_view);
        maleRadioButton = findViewById(R.id.male_radio_button);
        femaleRadioButton = findViewById(R.id.female_radio_button);
        SignUpButton = findViewById(R.id.sign_up_button);
        spinnerBloodType = findViewById(R.id.spinner_blood_type);
        spinnerCities = findViewById(R.id.spinner_governorate);

        datePicker.setVisibility(GONE);
        done.setVisibility(GONE);


        ArrayList<String> bloodTypesSpinnerArray = Constant.bloodTypeArrayList;
        ArrayList<String> CitiesSpinnerArray = Constant.CitiesArrayList;
        if (bloodTypesSpinnerArray == null || CitiesSpinnerArray == null) {
            Intent intent = new Intent(this, NoInternetActivity.class);
            finish();
            startActivity(intent);

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, bloodTypesSpinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodType.setAdapter(adapter);


        ArrayAdapter<String> CitiesAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, CitiesSpinnerArray);
        CitiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(CitiesAdapter);


        Birthday.setOnClickListener(view -> {

            datePicker.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
        });
        done.setOnClickListener(view -> {
            date = datePicker.getMonth() + 1 + "-" + datePicker.getDayOfMonth() + "-" + datePicker.getYear();
            Birthday.setText(date);
            done.setVisibility(GONE);
            datePicker.setVisibility(GONE);
        });
        SignUpButton.setOnClickListener(v -> {

            if (maleRadioButton.isChecked()) {
                gender = "Male";
            } else {
                gender = "Female";
            }

            validation(nameTextView.getText().toString(), passwordTextView.getText().toString(), Birthday.getText().toString()
                    , confirmPasswordTextView.getText().toString(), mailTextView.getText().toString(), gender, mobileTextView.getText().toString()
            );


        });
    }

    void validation(String name, String password, String birthdate,
                    String confirmPassword, String mail, String gender, String mobile) {
        if (!Validation.validateFields(name)) {
            ErrorDialog(this, "Name is Empty");
        } else if (!Validation.validateFields(password)) {
            ErrorDialog(this, "Password is Empty");
        } else if (!Validation.validateBirthDate(birthdate)) {
            ErrorDialog(this, "BirthDate is Empty");
        } else if (!Validation.validatePassword(password)) {
            ErrorDialog(this, "Password must be greater than 6");
        } else if (!Validation.validateFields(confirmPassword)) {
            ErrorDialog(this, "confirmed Password is Empty");
        } else if (!Validation.validatePassword(confirmPassword)) {
            ErrorDialog(this, "confirmed Password must be greater than 6");
        } else if (!password.equals(confirmPassword)) {
            ErrorDialog(this, "password not match");
        } else if (!Validation.validateFields(mail)) {
            ErrorDialog(this, "mail is Empty");
        } else if (!Validation.validateEmail(mail)) {
            ErrorDialog(this, "mail format incorrect");
        } else if (!Validation.validateFields(mobile)) {
            ErrorDialog(this, "phone is Empty");
        } else if (!Validation.validatePhone(mobile)) {
            ErrorDialog(this, "please check phone");
        } else {

            sendData();


        }
    }

    private void sendData() {
        RegistrationRequestModel registrationRequestModel = new RegistrationRequestModel();
        registrationRequestModel.setEmail(mailTextView.getText().toString());
        registrationRequestModel.setName(nameTextView.getText().toString());
        registrationRequestModel.setMobileNumber(mobileTextView.getText().toString());
        registrationRequestModel.setGender(this.gender);
        registrationRequestModel.setPassword(passwordTextView.getText().toString());
        registrationRequestModel.setCityId(Constant.GetCityId(spinnerCities.getSelectedItem().toString()));
        registrationRequestModel.setBirthdate(Birthday.getText().toString());
        registrationRequestModel.setBloodTypeId(Constant.GetBloodTypeId(spinnerBloodType.getSelectedItem().toString()));
        registrationRequestModel.setDeviceToken(mSharedPreferences.getString(Constant.notificationToken, ""));


        if (Validation.isConnected(this)) {
            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .Registration(registrationRequestModel)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(this);
        }
    }

    private void handleError(Throwable throwable) {
       // Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("RegistrationThrowable", throwable.getMessage(), throwable);
    }

    private void handleResponse(RegistrationResponseModel registrationResponseModel) {
        switch (registrationResponseModel.getResultCode()) {
            case "1":
                SharedPreferences.Editor edit = mSharedPreferences.edit();
                edit.putString(Constant.accessToken, registrationResponseModel.getData());
                edit.apply();
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                finish();
                startActivity(intent);
                break;
            case "0":
            case "2":
               // Toast.makeText(this, registrationResponseModel.getResultMessageEn(), Toast.LENGTH_SHORT).show();
                Log.e("RegistrationServerError", registrationResponseModel.getResultCode() + "-" + registrationResponseModel.getResultMessageEn());
                break;
        }
    }
}
