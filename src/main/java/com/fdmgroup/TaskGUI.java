package com.fdmgroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;

public class TaskGUI {
	
	private JFrame jFrameTasks = new JFrame("Setting task");
	private Container cTask = jFrameTasks.getContentPane();
	
	private JLabel lPeopleCurrentFloor = new JLabel("Which floor am I now?");
	private JFormattedTextField TPeopleCurrentFloor= new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JLabel lFloorToGo = new JLabel("Which floor I want to go?");
	private JFormattedTextField TFloorToGo = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JButton okbtn = new JButton("OK");
	private JButton cancelbtn = new JButton("Exit");
	private Controller myController;
	
	 public TaskGUI(Controller myController) {
		 
		 jFrameTasks.setBounds(300, 200, 600, 300);	 
		 cTask.setLayout(new BorderLayout()); 
		 jFrameTasks.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 taskInit();
		 jFrameTasks.setVisible(true);
		 this.myController = myController;
	 }
	 
	 
	 public void taskInit() {
		 
		 JPanel titlePanel = new JPanel();
		 titlePanel.setLayout(new FlowLayout());
		 titlePanel.add(new JLabel("Adding Tasks"));
		 cTask.add(titlePanel, "North");
		 JPanel fieldPanel = new JPanel();
		 fieldPanel.setLayout(null);
		 lPeopleCurrentFloor.setBounds(30, 20, 200, 20);
		 lFloorToGo.setBounds(30, 40, 200, 20);
		 fieldPanel.add(lPeopleCurrentFloor);
		 fieldPanel.add(lFloorToGo);
		 TPeopleCurrentFloor.setBounds(250, 20, 40, 20);
		 TFloorToGo.setBounds(250, 40, 40, 20);
		 fieldPanel.add(TPeopleCurrentFloor);
		 fieldPanel.add(TFloorToGo); 
		 cTask.add(fieldPanel, "Center");
		 JPanel buttonPanel = new JPanel();
		 buttonPanel.setLayout(new FlowLayout());
		 buttonPanel.add(okbtn);
		 buttonPanel.add(cancelbtn);
		 cTask.add(buttonPanel, "South");
		 listerner(); 
	 }
	 
	 public void listerner() {
	        okbtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String currentFloor = TPeopleCurrentFloor.getText();
	                String tofloor = TFloorToGo.getText() ;
	                int peopleCurrentFloor =Integer.parseInt(currentFloor);  
	                int floorToGo =Integer.parseInt(tofloor); 
	                if(peopleCurrentFloor>=0 && peopleCurrentFloor!=floorToGo && floorToGo>=0) {
		                People people = new People(peopleCurrentFloor, floorToGo);
		                myController.addTasksAndDistributeThem(people);
	                }else {
	                	JOptionPane.showMessageDialog(null, "Opps! The two input should Bigger than zero and not same!");  
	                }
	            }
	        });
	        
	        cancelbtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	System.exit(0);
	            }
	        });
	    }
	 
}
