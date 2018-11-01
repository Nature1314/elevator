package com.fdmgroup;

import java.util.TreeMap;

public class Elevator implements Runnable {
	
	private int currentPeopleNum =0;//The number of people in the lift
	private int currentFloorNum =0 ;
	private boolean liftGoUp = true;//This vari
	private boolean keepRunning = false;
	//private Building building;
	private TreeMap<Integer,Integer> task = new TreeMap<Integer,Integer>();//This map records the floor number (key) and the person in and out (Values).
	//The negative value of Values means the person leaves. The positive number    

	public int getFloorNumber() {
		return currentFloorNum;	
	}
	
	public void ifKeepRunning(boolean keepRunning) {
		this.keepRunning = keepRunning;
	}
	
	public void changePeopleNumber(int changeNumOfPerson) {
		
		currentPeopleNum+=changeNumOfPerson;
		
	}
	
	public int getCurrentPeopleNumber() {
		return currentPeopleNum;
	}
	
	public TreeMap<Integer, Integer> getTaskMap(){
		return task;
	}
	
	
	public boolean isLiftGoUp() {
		return liftGoUp;
	}

	public void setLiftGoUp(boolean liftGoUp) {
		this.liftGoUp = liftGoUp;
	}

	protected void changeFloorNumberAndRemoveTasks() {
		//The lift floor number increase only if the lift goes up and we have tasks to go up. 
		//Other condition the floor number decreases unless we are on ground floor
		System.out.println(currentFloorNum);
		//This part will remove the task from the task treeMap and call the method changePeopleNumber to change the number of currentPeopleNum
		if(task.containsKey(currentFloorNum)) {
			changePeopleNumber(task.get(currentFloorNum));
			System.out.printf("Revome task (%d,%d)  \n",currentFloorNum, task.get(currentFloorNum));
			task.remove(currentFloorNum);
		}

		//This part will control the lift to move.
		if(!task.isEmpty()) {
			if(liftGoUp) 
				currentFloorNum++;
			else
				currentFloorNum--;//Because we verify if we have task at ground floor before(and remove it), so we don't need worry about if the lift goes to -1.
			if(keepRunning) changeFloorNumberAndRemoveTasks();
		}else if (currentFloorNum!=0 && task.isEmpty()) {
			liftGoUp=false;//If this is the last rising task, then we change the sigh to lift go down
			currentFloorNum-=1;//Finish last go up task.
			if(keepRunning) changeFloorNumberAndRemoveTasks();
		}else{
			liftGoUp=true;//We don't have task, and we stop at ground floor. 
			keepRunning = false;
		}
		//System.out.println(currentFloorNum);
	}
	
	protected void changeFloorNumberAndRemoveTasks1() {
		//The lift floor number increase only if the lift goes up and we have tasks to go up. 
		//Other condition the floor number decreases unless we are on ground floor
		System.out.println(currentFloorNum);
		//This part will remove the task from the task treeMap and call the method changePeopleNumber to change the number of currentPeopleNum
		if(task.containsKey(currentFloorNum)) {
			changePeopleNumber(task.get(currentFloorNum));
			System.out.printf("Revome task (%d,%d)  \n",currentFloorNum, task.get(currentFloorNum));
			task.remove(currentFloorNum);
		}

		//This part will control the lift to move.
		if(!task.isEmpty()) {
			if(liftGoUp) 
				currentFloorNum++;
			else
				currentFloorNum--;//Because we verify if we have task at ground floor before(and remove it), so we don't need worry about if the lift goes to -1.
			changeFloorNumberAndRemoveTasks();//Continuous go down
		}else if (currentFloorNum!=0 && task.isEmpty()) {
			liftGoUp=false;//If this is the last rising task, then we change the sigh to lift go down
			currentFloorNum-=1;//Finish last go up task.
			changeFloorNumberAndRemoveTasks();//Continuous go down
		}else{
			liftGoUp=true;//We don't have task, and we stop at ground floor. 
		}
		//System.out.println(currentFloorNum);
	}
	
	protected void addTasks(int floorNumberOfPersonComeIn, int floorNumberOfPersonLeaveOut) {
		
		//Add one person at the floor that the person leaves.		
		int numberAfterANewPersonEnter=0;
		if(task.containsKey(floorNumberOfPersonComeIn)) {
			numberAfterANewPersonEnter = task.get(floorNumberOfPersonComeIn)+1;
		}else {
			numberAfterANewPersonEnter =1;
		}
		
		//Decrease one person at the floor that the person leaves.
		int numberAfterAPersonExit =0;
		if(task.containsKey(floorNumberOfPersonLeaveOut)) {
			numberAfterAPersonExit = task.get(floorNumberOfPersonLeaveOut)-1;
		}else {
			numberAfterAPersonExit=-1;
		}


		//Update the map
		task.put(floorNumberOfPersonComeIn, numberAfterANewPersonEnter);
		task.put(floorNumberOfPersonLeaveOut, numberAfterAPersonExit);
		System.out.printf("Add task (%d,%d) and (%d,%d) \n", floorNumberOfPersonComeIn, numberAfterANewPersonEnter, floorNumberOfPersonLeaveOut, numberAfterAPersonExit);
	}
	
	
	public void runElevator(int floorNumberOfPersonComeIn, int floorNumberOfPersonLeaveOut) {
			
			addTasks(floorNumberOfPersonComeIn,floorNumberOfPersonLeaveOut);
			changeFloorNumberAndRemoveTasks();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
