package com.fdmgroup;

import static org.junit.Assert.*;
import org.junit.Test;
//import static org.mockito.Mockito.*;

import java.util.TreeMap;

public class ElevatorTest {

	@Test
	public void test_getFloorNumber() {
		System.out.println("Test getting Floor Number");
        Elevator lift = new Elevator();
        int expResult = 0;
        int result = lift.getFloorNumber();
        assertEquals(expResult, result);
	}
	
	@Test
	public void test_changePersonNumber() {
		System.out.println("Test changing person Number");
        Elevator lift = new Elevator();
        int expResult = 12;
        lift.changePeopleNumber(12);
        int result = lift.getCurrentPeopleNumber();
        assertEquals(expResult, result);
	}
	
	@Test
	public void test_getCurrentPeopleNumber() {
		System.out.println("Test get current person Number");
        Elevator lift = new Elevator();
        int expResult = 0;
        int result = lift.getCurrentPeopleNumber();
        assertEquals(expResult, result);
	}
	
	
	@Test
	public void test_addTask_addOnePersonWhenPersonComeIn() {
		System.out.println("Test adding Task map and add one person on the floor that the person comes in");
        Elevator lift = new Elevator();
        int floorNumberOfPersonComeIn = 0;
        int floorNumberOfPersonLeaveOff = 3;
        lift.addTasks(floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOff);
        TreeMap<Integer,Integer> myTask = lift.getRiseTaskMap();
        int expResult = 1;
        int result = myTask.get(0);
        assertEquals(expResult, result);   
	}
	
	@Test
	public void test_addTask_decreaseOnePersonWhenPersonleaveout() {
		System.out.println("Test adding Task map and decrease one person on the floor that the person leaves out");
        Elevator lift = new Elevator();
        int floorNumberOfPersonComeIn = 0;
        int floorNumberOfPersonLeaveOut = 3;
        lift.addTasks(floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut);
        TreeMap<Integer,Integer> myTask = lift.getRiseTaskMap();
        int expResult = -1;
        int result = myTask.get(3);
        assertEquals(expResult, result);   
	}
	
	
	@Test
	public void test_changeFloorNumber_liftStayAtGroundFloor() {
		System.out.println("Test changing floor Number At Ground Floor");
        Elevator lift = new Elevator();
        int expResult = 0;
        lift.changeFloorNumberAndRemoveTasks();
        int result = lift.getFloorNumber();
        assertEquals(expResult, result);
		
	}

	@Test
	public void test_changeFloorNumber_LiftGoUP() {
		System.out.println("Test changing floor Number LiftGoUP");
        Elevator lift = new Elevator();
        int expResult = 1;
        int floorNumberOfPersonComeIn = 0;
        int floorNumberOfPersonLeaveOut = 3;
        lift.addTasks(floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut);
        lift.changeFloorNumberAndRemoveTasks();
        int result = lift.getFloorNumber();
        assertEquals(expResult, result);
		
	}
	
	@Test
	public void test_changeFloorNumber_LiftGoDown() {
		System.out.println("Test changing floor Number LiftGoDown");
        Elevator lift = new Elevator();
        int expResult = 1;
        int floorNumberOfPersonComeIn = 0;
        int floorNumberOfPersonLeaveOut = 3;
        lift.addTasks(floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut);
        lift.addTasks(floorNumberOfPersonLeaveOut, 2);
        lift.changeFloorNumberAndRemoveTasks();
        int result = lift.getFloorNumber();
        assertEquals(expResult, result);
	}
	
	@Test
	public void test_changeFloorNumber_LiftGoDownAndPeopleGoDown() {
		System.out.println("Test changing floor Number LiftGoDown");
        Elevator lift = new Elevator();
        int expResult = 1;
        int floorNumberOfPersonComeIn = 3;
        int floorNumberOfPersonLeaveOut = 2;
        lift.addTasks(floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut);
        lift.changeFloorNumberAndRemoveTasks();
        int result = lift.getFloorNumber();
        assertEquals(expResult, result);
	}
	
	@Test
	public void test_changeFloorNumber_dropTask() {
		System.out.println("Test changing floor Number LiftGoDown");
        Elevator lift = new Elevator();
        int expResult = 0;
        int floorNumberOfPersonComeIn = 1;
        int floorNumberOfPersonLeaveOut = 0;
        lift.addTasks(floorNumberOfPersonComeIn, floorNumberOfPersonLeaveOut);
        lift.setFloorNumber(1);
        lift.setLiftGoUPSign(false);
        lift.changeFloorNumberAndRemoveTasks();
        int result = lift.getFloorNumber();
        assertEquals(expResult, result);
	}
	
	
}
