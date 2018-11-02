package com.fdmgroup;

import static org.junit.Assert.*;

import org.junit.Test;

public class PeopleTest {

	@Test
	public void testGetFloorEnterNum() {
		People people = new People(2,3);
		int result = people.getFloorEnterNum();
		int expResult = 2;
		assertEquals(expResult, result);
	}
	
	@Test
	public void testSetFloorEnterNum() {
		People people = new People();
		people.setFloorEnterNum(2);
		int result = people.getFloorEnterNum();
		int expResult = 2;
		assertEquals(expResult, result);
	}


	@Test
	public void testSetFloorExitNum() {
		People people = new People();
		people.setFloorExitNum(3);
		int result = people.getFloorExitNum();
		int expResult = 3;
		assertEquals(expResult, result);
	}

	@Test
	public void testGetFloorExitNum() {
		People people = new People(2,3);
		int result = people.getFloorExitNum();
		int expResult = 3;
		assertEquals(expResult, result);
	}

}
