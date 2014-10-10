package com.local.courier.model;


import android.os.Parcel;
import android.os.Parcelable;

public class CreditCardVO implements Parcelable{
	
	
	private String creditCard;
	private String mMonth;
	private String mYear;
	private String cvv;
	private String zipCode;
	

 
    public CreditCardVO(String creditCard, String mMonth, String mYear, String cvv, String zipCode) {
		this.creditCard = creditCard;
		this.mMonth = mMonth;
		this.mYear = mYear;
		this.cvv = cvv;
		this.zipCode = zipCode;
	}

	private CreditCardVO(Parcel in){
		creditCard = in.readString();
		mMonth = in.readString();
		mYear = in.readString();
		cvv = in.readString();
		zipCode = in.readString();
    }
 


	public String getCreditCard() {
		return creditCard;
	}

	public String getMonth() {
		return mMonth;
	}

	public String getYear() {
		return mYear;
	}

	public String getCvv() {
		return cvv;
	}

	public String getZipCode() {
		return zipCode;
	}

	public static Parcelable.Creator<CreditCardVO> getCreator() {
		return CREATOR;
	}



	public static final Parcelable.Creator<CreditCardVO> CREATOR =
            new Parcelable.Creator<CreditCardVO>() {
 
        @Override
        public CreditCardVO createFromParcel(Parcel source) {
            return new CreditCardVO(source);
        }
 
        @Override
        public CreditCardVO[] newArray(int size) {
            return new CreditCardVO[size];
        }
 
    };
 
    @Override
    public int describeContents() {
        return 0;
    }
 
    @Override
    public void writeToParcel(Parcel dest, int flags) {
    	
        dest.writeString(creditCard);
        dest.writeString(mMonth);
        dest.writeString(mYear);
        dest.writeString(cvv);
        dest.writeString(zipCode);
    }
	
	
}

