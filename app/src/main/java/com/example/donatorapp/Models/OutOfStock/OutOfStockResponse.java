package com.example.donatorapp.Models.OutOfStock;

import java.util.ArrayList;

public class OutOfStockResponse {
    private String ResultMessageEn;

    private ArrayList<OutOfStockDataResponse> Data;

    private String ResultCode;

    public String getResultMessageEn ()
    {
        return ResultMessageEn;
    }

    public void setResultMessageEn (String ResultMessageEn)
    {
        this.ResultMessageEn = ResultMessageEn;
    }

    public ArrayList<OutOfStockDataResponse> getData ()
    {
        return Data;
    }

    public void setData (ArrayList<OutOfStockDataResponse> Data)
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
