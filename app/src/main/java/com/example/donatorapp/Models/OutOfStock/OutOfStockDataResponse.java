package com.example.donatorapp.Models.OutOfStock;

public class OutOfStockDataResponse {
    private String MobileNumber;

    private String Address;

    private String HospitalId;

    private String Name;

    public String getMobileNumber ()
    {
        return MobileNumber;
    }

    public void setMobileNumber (String MobileNumber)
    {
        this.MobileNumber = MobileNumber;
    }

    public String getAddress ()
    {
        return Address;
    }
   public void setAddress (String Address)
    {
        this.Address = Address;
    }

    public String getHospitalId ()
    {
        return HospitalId;
    }

    public void setHospitalId (String HospitalId)
    {
        this.HospitalId = HospitalId;
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
