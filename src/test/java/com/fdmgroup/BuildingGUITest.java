package com.fdmgroup;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mockito;

public class BuildingGUITest {

	@Test
	public void test_buildingInit(){
		System.out.println("Test initializing building GUI");
		BuildingGUI buildingGUI= Mockito.spy(new BuildingGUI());
		buildingGUI.buildingInit();
		verify(buildingGUI).listerner();
	}

}
