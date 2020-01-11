package com.example.donatorapp.Models.Cities;

import java.util.ArrayList;

public class GetCitiesResponseModel {
        private String ResultMessageEn;

        private ArrayList<GetCitiesDataResponseModel> Data;

        private String ResultCode;

        public String getResultMessageEn ()
        {
            return ResultMessageEn;
        }

        public void setResultMessageEn (String ResultMessageEn)
        {
            this.ResultMessageEn = ResultMessageEn;
        }

        public ArrayList<GetCitiesDataResponseModel> getData ()
        {
            return Data;
        }

        public void setData (ArrayList<GetCitiesDataResponseModel> Data)
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
