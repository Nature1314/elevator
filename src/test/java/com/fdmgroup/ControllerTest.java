package com.fdmgroup;

import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

public class ControllerTest {
	@Test
	public void test_addTasksAndDistributeThem() {
		System.out.println("Test add tasks and distribute task");
		Controller controller = Mockito.spy(new Controller(2,2,2));
		People person = new People(2,3);
		controller.addTasksAndDistributeThem(person);
		verify(controller).chooseElevator(person);
	}
	
	@Test
	public void test_chooseElevator() {
		System.out.println("Test choose elevator");
		Controller controller = Mockito.spy(new Controller(2,2,2));
		People person = new People(2,3);
		controller.chooseElevator(person);
		verify(controller).getAvalibleElevatorList(2, 3);
	}
	
	@Test
	public void test_getAvalibleElevatorList() {
		System.out.println("Test get avalible elevator list");
		Controller controller = new Controller(5, 1, 5);
		ArrayList<Elevator> myTastList = controller.getAvalibleElevatorList(0,5);
		assertTrue(myTastList.size()==1);
	}
	
	@Test
	public void test_calculateTimeAndselectMin() {
		System.out.println("Test calculate time and select min");
		Controller controller = new Controller(5, 1, 5);
		People person = new People(2,3);
		ArrayList<Elevator> myTastList = controller.getAvalibleElevatorList(2,3);
		Elevator expResult = myTastList.get(0);
	    Elevator result = controller.calculateTimeAndselectMin(myTastList, person);
	    assertEquals(expResult, result);
	}
	
	@Test
	public void test_taskBetweenPeopleAndLift() {
		System.out.println("Test task between people and lift");
		Controller controller = new Controller(5, 1, 5);
		//People person = new People(2,3);
		ArrayList<Elevator> myTastList = controller.getAvalibleElevatorList(2,3);
		int expResult = 0;
	    int result = controller.taskBetweenPeopleAndLift(myTastList.get(0), 2);
	    assertEquals(expResult, result);
	}

}
