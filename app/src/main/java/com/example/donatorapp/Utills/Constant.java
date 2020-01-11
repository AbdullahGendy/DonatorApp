package com.example.donatorapp.Utills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.donatorapp.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Constant {
    public static final String BASE_URL_HTTP = "http://bloodbank.somee.com";
    public static String accessToken = "accessToken";
    public static String userName = "userName";
    public static String name = "name";
    public static String password = "password";
    public static String mail = "mail";
    public static String mobile = "mobile";
    public static String gender = "gender";
    public static String bloodType = "bloodType";
    public static String city = "city";
    public static String rememberMe = "rememberMe";
    public static ArrayList<String> bloodTypeIdArrayList;
    public static ArrayList<String> bloodTypeArrayList;
    public static ArrayList<String> CitiesArrayList;
    public static ArrayList<String> CitiesIdArrayList;
    public static String notificationToken = "notificationToken";
    public static String logoutNotificationTitle = "logoutNotificationTitle";
    public static String logoutNotificationBody = "logoutNotificationBody";

    public static AlertDialog.Builder buildDialog(Activity c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet connection.");
        builder.setMessage("You have no internet connection");
        builder.setMessage("please check internet connection");
        builder.setIcon(R.drawable.ic_warning);
        builder.setCancelable(false);

        builder.setPositiveButton("Reload", (dialog, which) -> {
            c.finish();
            c.startActivities(new Intent[]{c.getIntent()});
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> c.finish());
        return builder;
    }

    public static String GetCityId(String cityName) {

        int index = CitiesArrayList.indexOf(cityName);
        return CitiesIdArrayList.get(index);
    }

    public static String GetBloodTypeId(String cityName) {
        int index = bloodTypeArrayList.indexOf(cityName);
        return bloodTypeIdArrayList.get(index);
    }


    public static AlertDialog.Builder ErrorDialog(Activity c, String errorMessage) {

        AlertDialog.Builder builder;
        AlertDialog dialog;
        builder = new AlertDialog.Builder(c);

        @SuppressLint("InflateParams")
        View mview = c.getLayoutInflater().inflate(R.layout.dialog_error, null);
        builder.setView(mview);
        dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        TextView message = mview.findViewById(R.id.message);
        TextView cancel = mview.findViewById(R.id.cancel);
        TextView ok = mview.findViewById(R.id.ok);
        message.setText(errorMessage);
        cancel.setOnClickListener(v2 -> {
            dialog.dismiss();
        });
        ok.setOnClickListener(v2 -> {
            dialog.dismiss();
        });
        return builder;
    }

    public static void errorBuildDialog(Activity c, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setIcon(R.drawable.ic_warning);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> c.finish());
    }


}
