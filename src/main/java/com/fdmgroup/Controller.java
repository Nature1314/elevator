package com.fdmgroup;

import java.util.ArrayList;
import java.util.TreeMap;

public class Controller {

	private int totalNumOfFloor;//number of floor in building
	private int numOfElevator;//number of elevator in building
	private int maxPeople;//max people for each elevator
	//private People people;
	private ArrayList<Elevator> listOfElevator = new ArrayList<Elevator>();
	private ArrayList<People> listOfWaitingTask = new ArrayList<People>();
	//This list store the task that has not been distributed by any of lift. 
	//The chooseElevator method will check this list every floor and tries to distribute these tasks to lift. 
	
	
	
	public Controller(int totalNumOfFloor, int numOfElevator, int maxPeople) {
		//The number of this three inputs must be nature number and greater than zero(>0) 
		this.totalNumOfFloor = totalNumOfFloor;
		this.numOfElevator = numOfElevator;
		this.maxPeople = maxPeople;
		this.createThreadAndElevator();
	}

	public void createThreadAndElevator() {
		/**This method is used to initialize the elevator and each thread only hold one elevator.*/
		for (int counter = 1; counter <= numOfElevator; counter++) {
			Elevator elevator = new Elevator();
			Thread thread = new Thread(elevator);
			thread.start();
			listOfElevator.add(elevator);
		}
	}
	
	public void addTasksAndDistributeThem(People... persons) {
		/** This method tries to add new person task to the waiting list. 
		*If the waiting is not empty, this method will try to solve the person in waiting list continuously.*/
		for(People people: persons) {
			listOfWaitingTask.add(people);
		}//add task part
		
		while(!listOfWaitingTask.isEmpty()) {
			for(People people: persons) {
				chooseElevator(people);
			}
		}//distribute part
		
	}
	
	// choose one elevator
	protected void chooseElevator(People people) {
	/**This method selects an elevator having the minimal moving time to get people*/
		int peopleCurrentfloor = people.getFloorEnterNum();
		int toFloor = people.getFloorExitNum();
		
		ArrayList<Elevator> avaliableElevator = getAvalibleElevatorList(peopleCurrentfloor, toFloor);
		//return the elevator that can been used
		
		if(!avaliableElevator.isEmpty()) {
			//If there is any lift servicing for this person, then remove this person in waiting list.
			Elevator elevator = calculateTimeAndselectMin(avaliableElevator, people);
			//elevator.setKeepRunning(true);
			elevator.addTasks(people.getFloorEnterNum(), people.getFloorExitNum());
			//elevator.setKeepRunning(false);
			listOfWaitingTask.remove(people);
		}
		
	}

	protected ArrayList<Elevator> getAvalibleElevatorList(int peopleCurrentfloor, int toFloor) {
		/**This method is used to choose the elevator which is available (The number of people in the elevator less than max number  of people)
		*The input 'floor' must >= zero and be the nature number 
		*The input of 'toFloor' must >= zero and <= max building floors*/
		
		ArrayList<Elevator> AvaliableElevator = new ArrayList<Elevator>();
		
		for (Elevator eachElevator : listOfElevator) {
			if (eachElevator.getCurrentPeopleNumber() < maxPeople) {
				if(eachElevator.isLiftGoUp() && peopleCurrentfloor > eachElevator.getFloorNumber() && toFloor > peopleCurrentfloor ) {
					AvaliableElevator.add(eachElevator);
			//If the lift go up, then it will only allow the people, who goes up, comes in and the current floor number of  this people must >= the floor number of lift 
				} else if (!eachElevator.isLiftGoUp() && peopleCurrentfloor < eachElevator.getFloorNumber() && toFloor < peopleCurrentfloor) {
					AvaliableElevator.add(eachElevator);
			//If the lift go down, then it will only allow the people, who goes down, comes in and the current floor number of  this people must <= the floor number of lift 		
				}else if(eachElevator.getDropTaskMap().isEmpty()&& eachElevator.getRiseTaskMap().isEmpty()) {
					AvaliableElevator.add(eachElevator);
				}
			}
		}
		return AvaliableElevator;
	}
	
	private Elevator calculateTimeAndselectMin(ArrayList<Elevator> availableElevator, People people) {
		/**This method return one elevator that use the minimal time
		 * The first input is an ArrayList with the available elevator
		 * The second input is a people object*/
		int floorNumOfPeople = people.getFloorEnterNum();
		
		TreeMap<Integer,Elevator> theTimeArrivePeople = new TreeMap<Integer,Elevator>();
		// The key of this map is the total time that reach the person
		// The value of this map is one elevator with this time to raech people
		for(Elevator elevator : availableElevator) {
			//accelerateTime = 1;
			//stopTime = 2;
			//decelerateTime = 1;
			// movingTime = 1;
			int taskBetweenPeopleAndLift = taskBetweenPeopleAndLift(elevator,floorNumOfPeople);
			int theTotalTimeArrivePeople = taskBetweenPeopleAndLift*4 + Math.abs(floorNumOfPeople - elevator.getFloorNumber()) +2 ;
			theTimeArrivePeople.put(theTotalTimeArrivePeople, elevator);
		}
		
		return theTimeArrivePeople.firstEntry().getValue();
	}
	
	private int taskBetweenPeopleAndLift(Elevator elevator, int floorNumOfPeople) {
		/**This method returns the number of tasks between the floor that lift on 
		 * and the floor that the people on*/
		int floorNumOfLiftNow = elevator.getFloorNumber();
		int taskBetweenPeopleAndLift = 0;
		
		if(elevator.isLiftGoUp()) {
			for(int key : elevator.getDropTaskMap().keySet()) {
				if(key>floorNumOfLiftNow && key<floorNumOfPeople) {
					taskBetweenPeopleAndLift+=1;
				}
			}
		}else {
				for(int key : elevator.getDropTaskMap().keySet()) {
					if(key<floorNumOfLiftNow && key>floorNumOfPeople) {
						taskBetweenPeopleAndLift+=1;
						}
					}
				}
		return taskBetweenPeopleAndLift;
	}

}
