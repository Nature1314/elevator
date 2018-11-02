package com.fdmgroup;

import static org.junit.Assert.*;

import org.junit.Test;

public class PeopleTest {

	@Test
	public void testSetGetFloorEnterNum() {
		People people = new People(2,3);
		int result = people.getFloorEnterNum();
		int expResult = 2;
		assertEquals(expResult, result);
	}


	@Test
	public void testSetGetFloorExitNum() {
		People people = new People(2,3);
		int result = people.getFloorExitNum();
		int expResult = 3;
		assertEquals(expResult, result);
	}

}
