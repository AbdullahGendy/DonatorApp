package com.example.donatorapp.Models.Cities;

public class GetCitiesDataResponseModel {
    private String CityId;

    private String Name;

    public String getCityId ()
    {
        return CityId;
    }

    public void setCityId (String CityId)
    {
        this.CityId = CityId;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }
}
