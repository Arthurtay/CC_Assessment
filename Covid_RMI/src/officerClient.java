

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

public class officerClient  extends java.rmi.server.UnicastRemoteObject {

	public officerClient() throws RemoteException {
		
	}
	
	
	
	public static void main(String[] args) throws IOException, NotBoundException {
		
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
		
		
		officer o = (officer) Naming.lookup("rmi://" + reg_host + ":" + reg_port + "/OfficerLocationService");
		
		
		
		
		// check difference in date if lesser than 14 days
		// datePost : dayAlert + 14 days
//		try {
//			Date firstDate = dateFormat.parse(dateAlert);
//			Date secondDate = dateFormat.parse(datePost);
//			long diff_time = secondDate.getTime() - firstDate.getTime();
//			long diff_day = (diff_time
//	                   / (1000 * 60 * 60 * 24))
//	                  % 365;
////						System.out.println(diff_day);
//		
//			
//		} catch (ParseException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		// request for officer input
		// columns include: location, date time of covid, end time of covid (14 days)
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome to the MOH Officer Client");
		 
		System.out.println("Enter Location of Covid Case");
		String locationCovid = sc.nextLine();
				 
		System.out.println("Declare Location at risk now? (y/n)");
		String dateState = sc.nextLine();
		
		// test String for location Input
		String testLocation = "JEM";
		
		InsertLocation(locationCovid, dateState);
		o.getLatestLocationDate(testLocation, o.storeTextToArray());
//		oc.getLatestLocationDate(testLocation, oc.storeTextToArray());
		
	}
//	
//	public String getLatestLocationDate(String locationInput, ArrayList<String> array){
//		
//		List<String> endOfInfection = new ArrayList<String>();
//		
//		// iterates through list and returns list with specified string value
//		for (String i: array) {
//			if (i.trim().contains(locationInput)) {
////				System.out.println(i);
//				while (true) {
//					endOfInfection.add(i.substring(i.indexOf("[") +1, i.indexOf("]")));
//					break;
//				}
//				
//			}
//		}
//		
//		String latestLocationDate = endOfInfection.get(endOfInfection.size()-1);
//		
//		System.out.println("\nLatest time: " + latestLocationDate);
//		
//		return latestLocationDate;
//	}
//	
//	public ArrayList<String> storeTextToArray() throws FileNotFoundException{
//		BufferedReader in = new BufferedReader(new FileReader("covid_location.txt"));
//		String str=null;
//		
//		ArrayList<String> arrayOfInfectedLocations = new ArrayList<String>();
//		try {
//			
//			
//			while((str = in.readLine()) != null) {
//				arrayOfInfectedLocations.add(str);
//			}
//			
//			// display array
////			for (String i: arrayOfInfectedLocations) {
////				System.out.println(i);
////			}
//			
//		}
//		catch (IOException e){
//			e.printStackTrace();
//		}
//		return arrayOfInfectedLocations;
//	}
//	
	public static void InsertLocation(String locationCovid, String dateState) {
		// get date time
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String dateAlert = dateFormat.format(date);
		
		Scanner sc= new Scanner(System.in);
		// date after 14 days
		// instantiate calendar
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 14);
		String datePost = dateFormat.format(c.getTime());
		
		if (dateState.toLowerCase().equals("y")){
				
				System.out.println(dateAlert);
				File f =new File("covid_location.txt");
				PrintWriter pw;
				try {
					pw = new PrintWriter(new FileOutputStream(f,true));
					pw.append(locationCovid +","+ dateAlert +","+ "[" +datePost + "]" +"\n");
					pw.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				System.out.println("Updated Record");
				System.out.println("Location: " + locationCovid + " is at risk from " + dateAlert + " to "+ datePost);
			}
			else if (dateState.toLowerCase().equals("n")) {
				
				try {
					System.out.println("Please enter date and time in this format: dd/MM/yyyy");
					String dateInput = sc.nextLine();
					Date newDateAlert = new SimpleDateFormat("dd/MM/yyyy").parse(dateInput);
					File f =new File("covid_location.txt");
					PrintWriter pw = new PrintWriter(new FileOutputStream(f,true));
					
					String printedDateAlert = dateFormat.format(newDateAlert);
					
					Calendar d = Calendar.getInstance();
					d.setTime(newDateAlert);
					
					d.add(Calendar.DAY_OF_MONTH, 14);
					String newDatePost = dateFormat.format(d.getTime());
					
					pw.append(locationCovid +","+ printedDateAlert +","+ "[" +newDatePost + "]" + "\n");
					pw.close();
					
					System.out.println("Updated Record");
					System.out.println("Location: " + locationCovid + " is at risk from " + printedDateAlert + " to "+ newDatePost);
				
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    
		
	}
	
}
