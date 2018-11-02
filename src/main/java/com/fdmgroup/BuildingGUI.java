package com.fdmgroup;



import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.*;

public class BuildingGUI {
	private JFrame jFrameBuilding = new JFrame("Building initialization");	
	private Container cBuilding = jFrameBuilding.getContentPane();
	
	private JLabel lBuildTotalFloor = new JLabel("How many floors in this building?");
	private JFormattedTextField TBuildTotalFloor = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JLabel lTotalNumOfLift = new JLabel("How many elevators in this building?");
	private JFormattedTextField TTotalNumOfLift = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JLabel lMaxPeoson = new JLabel("What is the max people you want in one elevator?");
	private JFormattedTextField TMaxPeople = new JFormattedTextField(NumberFormat.getIntegerInstance());
	private JButton okbtn = new JButton("OK");
	private JButton cancelbtn = new JButton("Cancel");
	
	 public BuildingGUI() {
		 jFrameBuilding.setBounds(300, 200, 600, 200);
		 cBuilding.setLayout(new BorderLayout()); 
		 buildingInit();
		 jFrameBuilding.setVisible(true);
	 }
	 
	 public void buildingInit() {
		 JPanel titlePanel = new JPanel();
		 titlePanel.setLayout(new FlowLayout());
		 titlePanel.add(new JLabel("Create your building"));
		 cBuilding.add(titlePanel, "North");
		 
		 
		 JPanel fieldPanel = new JPanel();
		 fieldPanel.setLayout(null);
		 lBuildTotalFloor.setBounds(30, 20, 300, 20);
		 lTotalNumOfLift.setBounds(30, 40,300, 20);
		 lMaxPeoson.setBounds(30, 60, 300, 20);
		 fieldPanel.add(lBuildTotalFloor);
		 fieldPanel.add(lTotalNumOfLift);
		 fieldPanel.add(lMaxPeoson);
		 TBuildTotalFloor.setBounds(350, 20, 50, 20);
		 TTotalNumOfLift.setBounds(350, 40, 50, 20);
		 TMaxPeople.setBounds(350, 60, 50, 20);
		 fieldPanel.add(TBuildTotalFloor);
		 fieldPanel.add(TTotalNumOfLift);
		 fieldPanel.add(TMaxPeople); 
		 cBuilding.add(fieldPanel, "Center");
		 
		 
		 JPanel buttonPanel = new JPanel();
		 buttonPanel.setLayout(new FlowLayout());
		 buttonPanel.add(okbtn);
		 buttonPanel.add(cancelbtn);
		 cBuilding.add(buttonPanel, "South");
		 listerner();
	 }
	 
	 
	 public void listerner() {
	        okbtn.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                String buildTotalFloor = TBuildTotalFloor.getText();
	                String totalNumOfLift = TTotalNumOfLift.getText() ;
	                String maxPeople = TMaxPeople.getText();
	                int totalNumOfFloor=Integer.parseInt(buildTotalFloor);  
	                int numOfElevator=Integer.parseInt(totalNumOfLift);  
	                int maxPeopleInOneLift=Integer.parseInt(maxPeople); 
	                if(totalNumOfFloor>1 && numOfElevator>0&&maxPeopleInOneLift>0) {
		                Controller myController=  new  Controller(totalNumOfFloor, numOfElevator, maxPeopleInOneLift);
		                jFrameBuilding.dispose();
		                new TaskGUI(myController);
	                }else if(totalNumOfFloor<1 && numOfElevator>0&&maxPeopleInOneLift>0){
	                	JOptionPane.showMessageDialog(null, "Opps! total number of floor should more than 1 or you don't need elevator!");
	                }else if(totalNumOfFloor>1&&(numOfElevator<0||maxPeopleInOneLift<0)) {
	                	JOptionPane.showMessageDialog(null, "number Of elevator  and max people in one lift input should Bigger than zero");
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