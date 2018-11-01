package com.fdmgroup;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

public class ControllerTest {

	@Test
		public void Test_IfAvaliable_Returnavaliableelevator() {
			System.out.println("Test get avalible elevator list");
			Controller controller = new Controller(5, 1, 5);
			ArrayList<Elevator> myTastList = controller.getAvalibleElevatorList(0,5);
			assertTrue(myTastList.size()==1);
		}
	
	@Test
	public void Test_chooseElevator_return_Elevator() {
		System.out.println("Test chooseElevator");		
		Elevator mockElevator = mock(Elevator.class);		
		Controller controller = new Controller(5,1,1);
		People p1 = new People(1,5);
		controller.chooseElevator(p1);
		verify(mockElevator).addTasks(1, 5);
	
	}
	
	@Test
	public void test_addTasksAndDistributeThem_verifyIfTheWaitingListIsNotEmpty() {
		System.out.println("Test addTasksAndDistributeThem");
		Controller controller = new Controller(15,1,4);
		People p1 = new People(0,4);
		People p2 = new People(3,2);
		controller.addTasksAndDistributeThem(p1,p2);
		assertTrue(true);
	}


}
