package com.fdmgroup;

import java.util.TreeMap;

public class Elevator implements Runnable {
	
	private int currentPeopleNum =0;//The number of people in the lift
	private int currentFloorNum =0 ;
	private boolean liftGoUp = true;//This vari
	//private boolean keepRunning = false;
	
	private TreeMap<Integer,Integer> riseTask = new TreeMap<Integer,Integer>();//This map records the floor number (key) and the person in and out (Values).
	private TreeMap<Integer,Integer> dropTask = new TreeMap<Integer,Integer>();//This map records the floor number (key) and the person in and out (Values).
	//private 
	//The negative value of Values means the person leaves. The positive number   
	private long threadID;

	public int getFloorNumber() {
		return currentFloorNum;	
	}
	
//	public void setKeepRunning(boolean keepRunning) {
//		this.keepRunning = keepRunning;
//	}
	
	public void changePeopleNumber(int changeNumOfPerson) {
		
		currentPeopleNum+=changeNumOfPerson;
		
	}
	
	public int getCurrentPeopleNumber() {
		return currentPeopleNum;
	}
	
	public TreeMap<Integer, Integer> getDropTaskMap(){
		return dropTask;
	}
	
	public TreeMap<Integer, Integer> getRiseTaskMap(){
		return riseTask;
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
		System.out.println(threadID+ " elevator is at "+ currentFloorNum );
		
		if(!riseTask.isEmpty()&&liftGoUp) {
			//This part will remove the task from the riseTask treeMap and call the method changePeopleNumber to change the number of currentPeopleNum
			if(riseTask.containsKey(currentFloorNum)) {
				changePeopleNumber(riseTask.get(currentFloorNum));
				System.out.printf(threadID +" Revome task (%d,%d)  \n",currentFloorNum, riseTask.get(currentFloorNum));
				currentPeopleNum-=riseTask.get(currentFloorNum);
				riseTask.remove(currentFloorNum);
			}
			
			currentFloorNum++;
			changeFloorNumberAndRemoveTasks();
//			if(keepRunning){
//				changeFloorNumberAndRemoveTasks();
//				//keepRunning = false;
//				}
		}else if(riseTask.isEmpty()&&liftGoUp) {		
			liftGoUp=false;
			changeFloorNumberAndRemoveTasks();			
		}else if (!dropTask.isEmpty() && !liftGoUp && currentFloorNum>=0) {
			//This part will remove the task from the dropTask treeMap and call the method changePeopleNumber to change the number of currentPeopleNum
			if(dropTask.containsKey(currentFloorNum)) {
				changePeopleNumber(dropTask.get(currentFloorNum));
				System.out.printf(threadID +" Revome task (%d,%d)  \n",currentFloorNum, dropTask.get(currentFloorNum));
				currentPeopleNum-=dropTask.get(currentFloorNum);
				dropTask.remove(currentFloorNum);
			}
			if(currentFloorNum>0)
				currentFloorNum-=1;
			//Because we verify if we have task at ground floor before(and remove it), so we don't need worry about if the lift goes to -1.
//			if(keepRunning) {
//				changeFloorNumberAndRemoveTasks();
//				//keepRunning = false;
//				}
			changeFloorNumberAndRemoveTasks();
		}else if (currentFloorNum>0 && dropTask.isEmpty() && riseTask.isEmpty()) {
			liftGoUp=false;//If this is the last rising task, then we change the sigh to lift go down
			currentFloorNum-=1;//Finish last go up task.
			changeFloorNumberAndRemoveTasks();
		}else {
			liftGoUp=true;//We don't have task, and we stop at ground floor. 
		}
	}
	
	
	protected void addTasks(int floorNumberOfPersonComeIn, int floorNumberOfPersonLeaveOut) {
		int numberAfterANewPersonEnter=0;
		int numberAfterAPersonExit =0;
		
		if(floorNumberOfPersonComeIn>floorNumberOfPersonLeaveOut) {
			//The elevator go down and the the people wants to go down
			//Add one person at the floor that the person leaves.		
			if(dropTask.containsKey(floorNumberOfPersonComeIn)) {
				numberAfterANewPersonEnter = dropTask.get(floorNumberOfPersonComeIn)+1;
			}else {
				numberAfterANewPersonEnter =1;
			}
			
			//Decrease one person at the floor that the person leaves.
			if(dropTask.containsKey(floorNumberOfPersonLeaveOut)) {
				numberAfterAPersonExit = dropTask.get(floorNumberOfPersonLeaveOut)-1;
			}else {
				numberAfterAPersonExit=-1;
			}
			
			//Update the map
			dropTask.put(floorNumberOfPersonComeIn, numberAfterANewPersonEnter);
			dropTask.put(floorNumberOfPersonLeaveOut, numberAfterAPersonExit);
			//update current people number
			//if(((floorNumberOfPersonLeaveOut-floorNumberOfPersonComeIn)>0) == this.liftGoUp)
			
			if(this.isLiftGoUp()) {
				riseTask.put(this.currentFloorNum, 0);
				riseTask.put(floorNumberOfPersonComeIn, 0);
			}else
			    currentPeopleNum++;
			
		}else if (floorNumberOfPersonComeIn<floorNumberOfPersonLeaveOut&& this.isLiftGoUp()) {
			//The elevator go up and the the people wants to go up
			if(riseTask.containsKey(floorNumberOfPersonComeIn)) {
				numberAfterANewPersonEnter = riseTask.get(floorNumberOfPersonComeIn)+1;
			}else {
				numberAfterANewPersonEnter =1;
			}
			
			//Decrease one person at the floor that the person leaves.
			if(riseTask.containsKey(floorNumberOfPersonLeaveOut)) {
				numberAfterAPersonExit = riseTask.get(floorNumberOfPersonLeaveOut)-1;
			}else {
				numberAfterAPersonExit=-1;
			}
			
			//Update the map
			riseTask.put(floorNumberOfPersonComeIn, numberAfterANewPersonEnter);
			riseTask.put(floorNumberOfPersonLeaveOut, numberAfterAPersonExit);
			//update current people number
			if(this.isLiftGoUp()) {
				dropTask.put(this.currentFloorNum, 0);
				dropTask.put(floorNumberOfPersonComeIn, 0);
			}else
				currentPeopleNum++;
		}
		
		System.out.printf(threadID + " elevator add task (%d,%d) and (%d,%d) \n", 
				floorNumberOfPersonComeIn, numberAfterANewPersonEnter, floorNumberOfPersonLeaveOut, numberAfterAPersonExit);
	}
	
	
	
	public void runElevator(int floorNumberOfPersonComeIn, int floorNumberOfPersonLeaveOut) {
		addTasks(floorNumberOfPersonComeIn,floorNumberOfPersonLeaveOut);
		//keepRunning = true;//false;
		changeFloorNumberAndRemoveTasks();
		//keepRunning = false;//true;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		// Getting thread's ID
        threadID =Thread.currentThread().getId();
	}
	
}
