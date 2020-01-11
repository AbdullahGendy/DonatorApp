package com.example.donatorapp.Models.BloodTypes;

public class BloodTypesDataResponse {
    private String BloodTypeId;

    private String Name;

    public String getBloodTypeId ()
    {
        return BloodTypeId;
    }

    public void setBloodTypeId (String BloodTypeId)
    {
        this.BloodTypeId = BloodTypeId;
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
