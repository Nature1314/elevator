package com.fdmgroup;

public class Elevator implements Runnable{

	
	private int peopleNumber;
	private int floorNumber;
	private boolean isUp;
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + floorNumber;
		result = prime * result + (isUp ? 1231 : 1237);
		result = prime * result + peopleNumber;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Elevator other = (Elevator) obj;
		if (floorNumber != other.floorNumber)
			return false;
		if (isUp != other.isUp)
			return false;
		if (peopleNumber != other.peopleNumber)
			return false;
		return true;
	}



	public int getPeopleNumber() {
		return peopleNumber;
	}



	public void setPeopleNumber(int peopleNumber) {
		this.peopleNumber = peopleNumber;
	}



	public int getFloorNumber() {
		return floorNumber;
	}



	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}



	public boolean isUp() {
		return isUp;
	}



	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}



	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
