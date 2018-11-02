package com.fdmgroup;

import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import org.junit.Test;

public class TaskGUITest {


	@Test
	public void test_taskInit() {
		
		System.out.println("Test initializing task GUI");
		Controller controller = new Controller(2,2,2);		
		TaskGUI taskGUI= Mockito.spy(new TaskGUI(controller));
		taskGUI.taskInit();
		verify(taskGUI).listerner();
		
	}
	

}
