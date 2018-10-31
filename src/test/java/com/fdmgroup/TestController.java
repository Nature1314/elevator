package com.fdmgroup;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TestController {

	@Test
	public void Test_IfAvaliable_Returnavaliableelevator() {

		Controller controller = new Controller(5, 5, 5);
		Building building = new Building();
		Elevator elevator = new Elevator();
		Elevator[] list1 = new Elevator[1];
		list1[0] = controller.getElevatorList(-1,-2).get(0);
		Elevator[] list2 = new Elevator[1];
		list2[0]=elevator;
		assertTrue(Arrays.equals(list1, list2));

	}
	
	@Test
	public void Test_IfAvaliable_Returnavaliable_2nd_elevator() {

		Controller controller = new Controller(5, 5, 5);
		Building building = new Building();
		Elevator elevator = new Elevator();
		Elevator[] list1 = new Elevator[1];
		list1[0] = controller.getElevatorList(-2,-4).get(1);
		Elevator[] list2 = new Elevator[1];
		list2[0]=elevator;
		assertTrue(Arrays.equals(list1, list2));

	}
	
	@Test
	public void TestThat_chooseElevator_return_Elevator() {
		Controller controller = new Controller(5,5,5);
		Elevator elevator = controller.chooseElevator(-11, -10);
		Elevator elevator1 = new Elevator();
		assertEquals(elevator, elevator1);
	}
	
	@Test
	public void TestThat_getElevator_return_emptyList_WhenNOElevatorIsvailable() {
		Controller controller = new Controller(5,5,5);
		Elevator elevator = controller.chooseElevator(1, 2);
		assertEquals(null, elevator);
	}
	
	
	
	
	
}