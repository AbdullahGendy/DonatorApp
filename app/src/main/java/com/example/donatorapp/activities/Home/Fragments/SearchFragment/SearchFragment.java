package com.example.donatorapp.activities.Home.Fragments.SearchFragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donatorapp.Models.OutOfStock.OutOfStockDataResponse;
import com.example.donatorapp.Models.OutOfStock.OutOfStockResponse;
import com.example.donatorapp.Network.NetworkUtil;
import com.example.donatorapp.R;
import com.example.donatorapp.Utills.Constant;
import com.example.donatorapp.Utills.Validation;
import com.example.donatorapp.adapters.Hospital.Hospital;
import com.example.donatorapp.adapters.Hospital.HospitalAdapter;

import java.util.ArrayList;
import java.util.Objects;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static android.content.Context.MODE_PRIVATE;


public class SearchFragment extends Fragment {
    private View view;
    private TextView textViewNoContent;
    private RecyclerView HospitalsRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        textViewNoContent = view.findViewById(R.id.text_view_no_content);
        textViewNoContent.setVisibility(View.GONE);
        CompositeSubscription mSubscriptions = new CompositeSubscription();
        SharedPreferences mSharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("MySharedPreference", MODE_PRIVATE);
        String mAccessToken = mSharedPreferences.getString(Constant.accessToken, "");
        if (Validation.isConnected(getActivity())) {

            mSubscriptions.add(NetworkUtil.getRetrofitNoHeader()
                    .GetOutOfStock(mAccessToken)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError));
        } else {
            Constant.buildDialog(getActivity());
        }
        return view;
    }

    private void handleError(Throwable throwable) {
        //Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_LONG).show();

    }

    private void handleResponse(OutOfStockResponse outOfStockResponse) {
        switch (outOfStockResponse.getResultCode()) {
            case "1":
                ArrayList<Hospital> hospitals = new ArrayList<>();
                if (outOfStockResponse.getData() != null) {
                    for (OutOfStockDataResponse response : outOfStockResponse.getData()) {
                        if (response.getAddress() == null) {
                            hospitals.add(new Hospital(response.getName(), response.getMobileNumber(), "No Address"));
                        } else {
                            hospitals.add(new Hospital(response.getName(), response.getMobileNumber(), response.getAddress()));
                        }
                    }
                    HospitalAdapter hospitalAdapter = new HospitalAdapter(getContext(), hospitals);
                    HospitalsRecyclerView = view.findViewById(R.id.Hospitals_Recycler_view);
                    HospitalsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    HospitalsRecyclerView.setAdapter(hospitalAdapter);
                } else {
                    HospitalsRecyclerView.setVisibility(View.GONE);
                    textViewNoContent.setVisibility(View.VISIBLE);
                }

                break;
            case "0":
               // Toast.makeText(getActivity(), outOfStockResponse.getResultMessageEn() + "0", Toast.LENGTH_LONG).show();

                break;
            case "2":
                //Toast.makeText(getActivity(), outOfStockResponse.getResultMessageEn() + "2", Toast.LENGTH_LONG).show();
                break;
        }
    }


}
