package com.fdmgroup;


import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Elevator implements Runnable{
	
	Logger log = LogManager.getLogger(Elevator.class);
	
	private int currentPeopleNum =0;//The number of people in the lift
	private int currentFloorNum =0 ;
	private boolean liftGoUp = true;//This vari
	//private boolean keepRunning = false;
	
	private TreeMap<Integer,Integer> riseTask = new TreeMap<Integer,Integer>();
	//This map records the floor number (key) and the person in and out (Values).
	private TreeMap<Integer,Integer> dropTask = new TreeMap<Integer,Integer>();
	//This map records the floor number (key) and the person in and out (Values).
	//The negative value of Values means the person leaves. The positive number means the people comes in.
	
	private long threadID;

	public int getFloorNumber() {
		return currentFloorNum;	
	}
	
	protected void setFloorNumber(int currentFloorNum) {
		this.currentFloorNum= currentFloorNum;	
	}
	
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
	
	protected void setLiftGoUPSign(boolean liftGoUp) {
		this.liftGoUp = liftGoUp;
	}
	
	public boolean isLiftGoUp() {
		return liftGoUp;
	}

	protected void changeFloorNumberAndRemoveTasks() {
		/**The method has two tasks
		 * 1.change the floor number each time
		 * 2.check if we need to remove the task and remove it.
		 * The lift floor number increase only if the lift goes up and we have tasks to go up. 
		 * Other condition the floor number decreases unless we are on ground floor*/
		
		log.info("the elevator ID "+ threadID+" now reaching " + currentFloorNum + " floor");

		if(!riseTask.isEmpty()&&liftGoUp) {
			//This part will remove the task from the riseTask treeMap and call the method changePeopleNumber to change the number of currentPeopleNum
			if(riseTask.containsKey(currentFloorNum)) {
				changePeopleNumber(riseTask.get(currentFloorNum));
				if(riseTask.get(currentFloorNum)>0) {
					log.info("ID "+threadID +" elevator reach people at " + currentFloorNum + " floor");
					//log.info("ID "+threadID +" elevator remove task (" + currentFloorNum + " ," + riseTask.get(currentFloorNum) + ")");
				}else if(riseTask.get(currentFloorNum)<0) {
					log.info("ID "+threadID +" elevator send people at " + currentFloorNum + " floor");
				}
				//System.out.printf(threadID +" Revome task (%d,%d)  \n",currentFloorNum, riseTask.get(currentFloorNum));
				currentPeopleNum-=riseTask.get(currentFloorNum);
				riseTask.remove(currentFloorNum);
			}
			if(!riseTask.isEmpty()&&riseTask.descendingKeySet().first()>currentFloorNum)
				currentFloorNum++;
			else 
				liftGoUp=false;
		}else if(riseTask.isEmpty()&&liftGoUp) {		
			liftGoUp=false;			
		}else if (!dropTask.isEmpty() && !liftGoUp && currentFloorNum>=0) {
			//This part will remove the task from the dropTask treeMap and call the method changePeopleNumber to change the number of currentPeopleNum
			if(dropTask.containsKey(currentFloorNum)) {
				changePeopleNumber(dropTask.get(currentFloorNum));
				
				if(dropTask.get(currentFloorNum)>0) {
					log.info("ID "+threadID +" elevator reach people at " + currentFloorNum + " floor");
					//log.info("ID "+threadID +" elevator remove task (" + currentFloorNum + " ," + riseTask.get(currentFloorNum) + ")");
				}else if(dropTask.get(currentFloorNum)<0) {
					log.info("ID "+threadID +" elevator send people at " + currentFloorNum + " floor");
				}
				
				//log.info(String.format(threadID +" Revome task (%d,%d)  \n",currentFloorNum, dropTask.get(currentFloorNum)));
				
				//System.out.printf(threadID +" Revome task (%d,%d)  \n",currentFloorNum, dropTask.get(currentFloorNum));
				currentPeopleNum-=dropTask.get(currentFloorNum);
				dropTask.remove(currentFloorNum);
			}
			if(currentFloorNum>0)
				currentFloorNum-=1;
			//Because we verify if we have task at ground floor before(and remove it), so we don't need worry about if the lift goes to -1.
		}else if (currentFloorNum>0 && dropTask.isEmpty() && riseTask.isEmpty()) {
			liftGoUp=false;//If this is the last rising task, then we change the sigh to lift go down
			currentFloorNum-=1;//Finish last go up task.
			//changeFloorNumberAndRemoveTasks();
		}else {
			liftGoUp=true;//We don't have task, and we stop at ground floor. 
		}
	}
	
	
	protected void addTasks(int floorNumberOfPersonComeIn, int floorNumberOfPersonLeaveOut) {
		/**The method add tasks for the current elevator.
		 * The inputs are the floor number that the people comes in and the people leaves out*/
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
			
			log.info(String.format(threadID + " elevator add go down task that person comes in on floor %d and leave out on floor %d \n", 
					floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut));
			
		}else if (floorNumberOfPersonComeIn<floorNumberOfPersonLeaveOut) {
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
			if(currentFloorNum>floorNumberOfPersonComeIn) {
				dropTask.put(this.currentFloorNum, 0);
				dropTask.put(floorNumberOfPersonComeIn, 0);
			}else
				currentPeopleNum++;
			log.info(String.format(threadID + " elevator add go up task that person comes in on floor %d and leave out on floor %d \n", 
					floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut));
		}
		
//		log.info(String.format(threadID + " elevator add task (%d,%d) and (%d,%d) \n", 
//				floorNumberOfPersonComeIn, numberAfterANewPersonEnter, floorNumberOfPersonLeaveOut, numberAfterAPersonExit));
//		
		//System.out.printf(threadID + " elevator add task (%d,%d) and (%d,%d) \n", 
			//	floorNumberOfPersonComeIn, numberAfterANewPersonEnter, floorNumberOfPersonLeaveOut, numberAfterAPersonExit);
	}
	
	
	public void run() {
		/**The method implement the interface Runnable method run()
		 * The method call the changeFloorNumberAndRemoveTasks() method each period and return the thread ID as elevator name*/
		// Getting thread's ID
        threadID =Thread.currentThread().getId();
       while(true) {
    	   if(!riseTask.isEmpty()||!dropTask.isEmpty()||(riseTask.isEmpty()&&dropTask.isEmpty()&&liftGoUp==false))
    		   changeFloorNumberAndRemoveTasks();
        	try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }
	}
	
}
