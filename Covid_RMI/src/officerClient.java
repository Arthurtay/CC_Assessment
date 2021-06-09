

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class officerClient  extends java.rmi.server.UnicastRemoteObject implements OfficerRMIClientInf {

	
	
	public void confirmation(String Message ) throws java.rmi.RemoteException{
		
		System.out.println(Message);
		
	}
	
	
    /** 
     * 1. Retrieves latest updated infected location by calling callback o.IL method
     * @param newIL- arrayList instantiated from the main implementation class to callback here
     * @throws java.rmi.RemoteException
     * 
	*/  
	
	public void retrieveLatestLocation(ArrayList<InfectedLocation> newIL) throws java.rmi.RemoteException{
		System.out.println("--------------------------------\n");
		System.out.println("Updated Infected Location");
		System.out.println("--------------------------------");
		for (InfectedLocation i: newIL) {
			System.out.println(i.getLocation() +" is infected from, "+ i.getDateInfection() + " till "+ i.getDatePost() +"\n");
		}
		System.out.println("--------------------------------\n");
		
	}
	
	public officerClient() throws RemoteException {
		
	}
	
	public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {
		
		String reg_host = "localhost";
		int reg_port = 1099;
		
		if (args.length == 1)
	    {
	    	reg_port = Integer.parseInt(args[0]);
	    } 
	    else if (args.length == 2) 
	    {
	    	reg_host = args[0];
	    	reg_port = Integer.parseInt(args[1]);
	    }
		
		officerClient mohClient = new officerClient();
		
		officer o = (officer) Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/CovidCheckInService");
		
		/** Request for officer input. Inputs include:
		 * 1. Infected location
		 * 2. Date state: Declare location today(NOW) or other day
		 * 
		 * */
		Scanner sc=new Scanner(System.in);
		System.out.println("-----------------Welcome to the MOH Officer Client-----------------\n");
		 
		System.out.println("Enter Location of Covid Case");
		String locationCovid = sc.nextLine();
				 
		System.out.println("Declare Location at risk with the current time? (y/n)");
		String dateState = sc.nextLine();
		
		
		
		System.out.println("\n");
		
		/**
		 * @dateAlert : current datetime
		 * @datePost : (dateAlert + 14 days) to indicate timeframe of infected location and set a date for when location is covid free
		 * @printedDateAlert : officer input own datetime
		 * @newDatePost : (printedDateAlert + 14 days)
		 * */

		
		// Set dateFormat to hold a default datetime format to be used throughout
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String dateAlert = dateFormat.format(date);
	    
		/** If officer typed yes to declaring location NOW, then current datetime will be taken
		* instantiate calendar to begin process of adding in 14 days to get datetime of when location will be Covid Free
		*/
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 14);
		String datePost = dateFormat.format(c.getTime());
		
		if (dateState.toLowerCase().equals("y")){
				
				System.out.println(dateAlert);
				
				// Invoke remote method insertLog to place inputs into database("covid_locations.txt") directly
				o.insertLog(mohClient,locationCovid, dateAlert, datePost);
				
			}
			else if (dateState.toLowerCase().equals("n")) {
				
				try {
					System.out.println("Please enter date and time in this format: dd/MM/yyyy");
					String dateInput = sc.nextLine();
					Date newDateAlert = new SimpleDateFormat("dd/MM/yyyy").parse(dateInput);
					String printedDateAlert = dateFormat.format(newDateAlert);
					
					Calendar d = Calendar.getInstance();
					d.setTime(newDateAlert);
					
					d.add(Calendar.DAY_OF_MONTH, 14);
					String newDatePost = dateFormat.format(d.getTime());
					
					o.insertLog(mohClient,locationCovid, printedDateAlert, newDatePost);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

		
	}
	

	
}
