package com.example.donatorapp.Models.DonatorInfo;

public class DonatorInfoDataResponse {
    private String MobileNumber;

    private String Email;

    private String ImageUrl;

    private String Gender;

    private String City;

    private String Birthdate;

    private String DonatorId;

    private String BloodType;

    private String Name;

    public String getMobileNumber ()
    {
        return MobileNumber;
    }

    public void setMobileNumber (String MobileNumber)
    {
        this.MobileNumber = MobileNumber;
    }

    public String getEmail ()
    {
        return Email;
    }

    public void setEmail (String Email)
    {
        this.Email = Email;
    }

    public String getImageUrl ()
    {
        return ImageUrl;
    }

    public void setImageUrl (String ImageUrl)
    {
        this.ImageUrl = ImageUrl;
    }

    public String getGender ()
    {
        return Gender;
    }

    public void setGender (String Gender)
    {
        this.Gender = Gender;
    }

    public String getCity ()
    {
        return City;
    }

    public void setCity (String City)
    {
        this.City = City;
    }

    public String getBirthdate ()
    {
        return Birthdate;
    }

    public void setBirthdate (String Birthdate)
    {
        this.Birthdate = Birthdate;
    }

    public String getDonatorId ()
    {
        return DonatorId;
    }

    public void setDonatorId (String DonatorId)
    {
        this.DonatorId = DonatorId;
    }

    public String getBloodType ()
    {
        return BloodType;
    }

    public void setBloodType (String BloodType)
    {
        this.BloodType = BloodType;
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
