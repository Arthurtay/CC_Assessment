package rmi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class officerClient {

	public static void main(String[] args) throws IOException {
		
		officerClient oc = new officerClient();
		
		// get date time
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
	    String dateAlert = dateFormat.format(date);
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
		
		oc.InsertLocation(locationCovid, dateState, dateAlert);
	}
	
	
	
	public String InsertLocation(String locationCovid, String dateState, String dateAlert) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
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
					pw.append(locationCovid +","+ dateAlert +","+datePost + "\n");
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
					System.out.println("Please enter date and time in this format: dd/MM/yyyy HH:mm:ss");
					String dateInput = sc.nextLine();
					Date newDateAlert = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateInput);
					File f =new File("covid_location.txt");
					PrintWriter pw = new PrintWriter(new FileOutputStream(f,true));
					
					Calendar d = Calendar.getInstance();
					d.setTime(newDateAlert);
					
					d.add(Calendar.DAY_OF_MONTH, 14);
					String newDatePost = dateFormat.format(d.getTime());
					
					pw.append(locationCovid +","+ newDateAlert +","+newDatePost + "\n");
					pw.close();
					
					System.out.println("Updated Record");
					System.out.println("Location: " + locationCovid + " is at risk from " + newDateAlert + " to "+ newDatePost);
				
				} catch (IOException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    
		return dateAlert;
	}
	
}
