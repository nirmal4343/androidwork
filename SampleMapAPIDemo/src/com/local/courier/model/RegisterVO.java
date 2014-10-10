package com.local.courier.model;


import android.os.Parcel;
import android.os.Parcelable;

public class RegisterVO implements Parcelable{
	
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNo;
	private String password;
	
	private double longitude;
	private double latitude;


	public RegisterVO(String firstName, String lastName, String emailId, String phoneNo, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.password = password;
    }
 
    private RegisterVO(Parcel in){
    	firstName = in.readString();
    	lastName = in.readString();
    	emailId = in.readString();
    	phoneNo = in.readString();
    	password = in.readString();
    }
 
    public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	

	public String getPassword() {
		return password;
	}


	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public static final Parcelable.Creator<RegisterVO> CREATOR =
            new Parcelable.Creator<RegisterVO>() {
 
        @Override
        public RegisterVO createFromParcel(Parcel source) {
            return new RegisterVO(source);
        }
 
        @Override
        public RegisterVO[] newArray(int size) {
            return new RegisterVO[size];
        }
 
    };
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(emailId);
        dest.writeString(phoneNo);
        dest.writeString(password);
    }
	
	
}

