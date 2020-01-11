package com.example.donatorapp.Models.DonatorInfo;

public class DonatorInfoResponse {
    private String ResultMessageEn;

    private DonatorInfoDataResponse Data;

    private String ResultCode;

    public String getResultMessageEn ()
    {
        return ResultMessageEn;
    }

    public void setResultMessageEn (String ResultMessageEn)
    {
        this.ResultMessageEn = ResultMessageEn;
    }

    public DonatorInfoDataResponse getData ()
    {
        return Data;
    }

    public void setData (DonatorInfoDataResponse Data)
    {
        this.Data = Data;
    }

    public String getResultCode ()
    {
        return ResultCode;
    }

    public void setResultCode (String ResultCode)
    {
        this.ResultCode = ResultCode;
    }

}
