package com.example.donatorapp.Network;


import com.example.donatorapp.Models.BloodTypes.BloodTypesResponse;
import com.example.donatorapp.Models.Cities.GetCitiesResponseModel;
import com.example.donatorapp.Models.DeviceToken.UpdateDeviceTokenRequest;
import com.example.donatorapp.Models.DeviceToken.UpdateDeviceTokenResponse;
import com.example.donatorapp.Models.DonatorInfo.DonatorInfoResponse;
import com.example.donatorapp.Models.Login.LoginRequestModel;
import com.example.donatorapp.Models.Login.LoginResponseModel;
import com.example.donatorapp.Models.OutOfStock.OutOfStockResponse;
import com.example.donatorapp.Models.Registration.RegistrationRequestModel;
import com.example.donatorapp.Models.Registration.RegistrationResponseModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {


    @GET("/api/City/GetCities")
    Observable<GetCitiesResponseModel> GetCities();

    @POST("/api/DonatorAccount/Registration")
    Observable<RegistrationResponseModel> Registration(@Body RegistrationRequestModel registrationRequestModel);

    @POST("/api/DonatorAccount/Login")
    Observable<LoginResponseModel> Login(@Body LoginRequestModel loginRequestModel);

    @GET("/api/DonatorAccount/GetDonatorInfoByDonatorId/{id}")
    Observable<DonatorInfoResponse> GetDonatorInfo(@Path("id") String id);

    @GET("/api/HospitalAccount/GetOutOfStockHospitalsByUserId/{id}")
    Observable<OutOfStockResponse> GetOutOfStock(@Path("id") String id);

    @GET("api/BloodType/GetBloodTypes")
    Observable<BloodTypesResponse> GetBloodTypes();

    @PUT("/api/DonatorAccount/UpdateDonatorDeviceToken")
    Observable<UpdateDeviceTokenResponse> UpdateDonatorDeviceToken(@Body UpdateDeviceTokenRequest updateDeviceTokenRequest);

}