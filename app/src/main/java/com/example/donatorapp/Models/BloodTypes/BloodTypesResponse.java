package com.example.donatorapp.Models.BloodTypes;

import java.util.ArrayList;

public class BloodTypesResponse {
    private String ResultMessageEn;

    private ArrayList<BloodTypesDataResponse> Data;

    private String ResultCode;

    public String getResultMessageEn() {
        return ResultMessageEn;
    }

    public void setResultMessageEn(String ResultMessageEn) {
        this.ResultMessageEn = ResultMessageEn;
    }

    public ArrayList<BloodTypesDataResponse> getData() {
        return Data;
    }

    public void setData(ArrayList<BloodTypesDataResponse> Data) {
        this.Data = Data;
    }

    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String ResultCode) {
        this.ResultCode = ResultCode;
    }
}
