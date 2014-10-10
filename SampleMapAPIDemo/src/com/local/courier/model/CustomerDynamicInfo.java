package com.local.courier.model;

import java.util.ArrayList;

public class CustomerDynamicInfo extends Status{
	
	private ArrayList<Courier> courierList;

	// private ArrayList<Package> packageList;

	
	public ArrayList<Courier> getCourierList() {
		return courierList;
	}

	public void setCourierList(ArrayList<Courier> courierList) {
		this.courierList = courierList;
	}

}
