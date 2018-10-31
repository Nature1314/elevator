package com.fdmgroup;

import java.util.ArrayList;

public class Controller {

	private int numOfFloor;
	private int numOfElevator;
	private int maxPeople;
	private ArrayList<Elevator> listOfElevator = new ArrayList<Elevator>();

	public Controller(int numOfFloor, int numOfElevator, int maxPeople) {
		super();
		this.numOfFloor = numOfFloor;
		this.numOfElevator = numOfElevator;
		this.maxPeople = maxPeople;
		this.setElevator();
	}

	public void setElevator() {
		for (int counter = 1; counter <= numOfElevator; counter++) {
			Elevator elevator = new Elevator();
			Thread thread = new Thread(elevator);
			thread.start();
			listOfElevator.add(elevator);
		}
	}

	// choose one elevator
	public Elevator chooseElevator(int floor, int toFloor) {
		ArrayList<Elevator> AvaliableElevator = this.getElevatorList(floor, toFloor);
		
		if(AvaliableElevator.isEmpty()) {
			return null;
		}
		
		
		return AvaliableElevator.get(0);
	}

	public ArrayList<Elevator> getElevatorList(int floor, int toFloor) {
		ArrayList<Elevator> AvaliableElevator = new ArrayList<Elevator>();
		Building building = new Building();
		for (Elevator eachElevator : listOfElevator) {
			if (eachElevator.getPeopleNumber() < building.getMaxPeople()) {
				if(eachElevator.isUp() && floor > eachElevator.getFloorNumber() && toFloor > eachElevator.getFloorNumber()) {
					AvaliableElevator.add(eachElevator);
				} else if (!eachElevator.isUp() && floor < eachElevator.getFloorNumber() && toFloor < eachElevator.getFloorNumber()) {
					AvaliableElevator.add(eachElevator);
				}
				
			}
		}
		return AvaliableElevator;
	}

}
