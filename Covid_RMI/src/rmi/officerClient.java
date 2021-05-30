package rmi;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class officerClient {

	public static void main(String[] args) throws IOException {
		
		// get date time
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
		String dateCheckIn = dateFormat.format(date);
		
		// date after 14 days
		// instantiate calendar
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 14);
		String datePost = dateFormat.format(c.getTime());
		
		
		// check difference in date if lesser than 14 days
		try {
			Date firstDate = dateFormat.parse(dateCheckIn);
			Date secondDate = dateFormat.parse(datePost);
			long diff_time = secondDate.getTime() - firstDate.getTime();
			long diff_day = (diff_time
	                   / (1000 * 60 * 60 * 24))
	                  % 365;
//						System.out.println(diff_day);
		
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// request for officer input
		// columns include: location, date time of covid, end time of covid (14 days)
		Scanner sc=new Scanner(System.in);
		System.out.println("Welcome to the MOH Officer Client");
		 
		System.out.println("Enter Location of Covid Case");
		String locationCovid = sc.nextLine();
		
		System.out.println("Enter your NRIC");
		String nric = sc.nextLine();
		 
		System.out.println("Enter your Location");
		String location =  sc.nextLine();
		 
		String time = dateFormat.format(date);
		System.out.println(time);
		
	}
	
}
