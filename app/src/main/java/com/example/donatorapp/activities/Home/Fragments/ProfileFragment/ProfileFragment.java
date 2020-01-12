package com.example.donatorapp.activities.Home.Fragments.ProfileFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.donatorapp.Models.DonatorInfo.DonatorInfoResponse;
import com.example.donatorapp.Network.NetworkUtil;
import com.example.donatorapp.R;
import com.example.donatorapp.Utills.Constant;
import com.example.donatorapp.Utills.Validation;
import com.example.donatorapp.activities.Login.LoginActivity;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {
    private TextView textViewAge;
    private TextView textViewName;
    private TextView textViewDateOfBirth;
    private TextView textViewBloodType;
    private TextView textViewPhoneNumber;
    private TextView textViewAddress;
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        textViewAge = view.findViewById(R.id.text_view_age);
        textViewName = view.findViewById(R.id.text_view_name);
        textViewDateOfBirth = view.findViewById(R.id.text_view_date_of_birth);
        textViewBloodType = view.findViewById(R.id.text_view_blood_type);
        textViewAddress = view.findViewById(R.id.text_view_address);
        textViewPhoneNumber = view.findViewById(R.id.text_view_phone_number);
        TextView textViewLogOut = view.findViewById(R.id.text_view_log_out);
        mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MySharedPreference", MODE_PRIVATE);

        textViewLogOut.setOnClickListener(v -> {
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString(Constant.accessToken, "");
            editor.putString(Constant.notificationToken, "");
            editor.putBoolean(Constant.rememberMe, false);
            editor.putString(Constant.logoutNotificationTitle, "");
            editor.putString(Constant.logoutNotificationBody, "");
            editor.apply();
            Objects.requireNonNull(getActivity()).finish();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
        });
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        String mAccessToken = mSharedPreferences.getString(Constant.accessToken, "");
        if (Validation.isConnected(Objects.requireNonNull(getActivity()))) {

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetDonatorInfo(mAccessToken)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(getActivity());
        }
        return view;
    }

    private void handleError(Throwable throwable) {

    }

    @SuppressLint("SetTextI18n")
    private void handleResponse(DonatorInfoResponse donatorInfoResponse) {
        textViewDateOfBirth.setText(donatorInfoResponse.getData().getBirthdate().substring(0, 10));
        textViewName.setText(donatorInfoResponse.getData().getName());

        textViewAge.setText(calcAge(donatorInfoResponse.getData().getBirthdate().substring(0, 10)) + " Years");
        textViewAddress.setText(donatorInfoResponse.getData().getCity() + ", Egypt");
        textViewPhoneNumber.setText(donatorInfoResponse.getData().getMobileNumber());
        textViewBloodType.setText(donatorInfoResponse.getData().getBloodType());


    }

    private String calcAge(String DateOfBirth) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1;
            try {
                date1 = simpleDateFormat.parse(DateOfBirth);
            } catch (ParseException e) {
                e.printStackTrace();
                return "1";
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int date = calendar.get(Calendar.DATE);
            LocalDate birthDate = LocalDate.of(year, month, date);
            LocalDate now = LocalDate.now();
            Period age;

            age = Period.between(birthDate, now);

            return String.valueOf(age.getYears());
        } else return String.valueOf(2020 - Integer.valueOf(DateOfBirth.substring(0, 4)));
    }
}