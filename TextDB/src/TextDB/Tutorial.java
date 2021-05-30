package TextDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tutorial {

	public static void main(String arg[]) {
		Tutorial t = new Tutorial();
//		t.insertDB();
//		t.insertGroupDB();
//		t.displayDB();
		t.storeDBtoArray();
		
		
	}
	
	
	// display db from text file
	public void displayDB() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Database.txt"));
			String s = "";
			while((s=br.readLine()) != null) {
				String[] data = new String[4];
				data=s.split(",");
				for (int i=0;i<4;i++) {
					System.out.print(data[i]+" ");
				}
				System.out.println();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// insert to text file db
	public void insertDB(){	
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter ID: ");
		String id = sc.nextLine();
		System.out.println("Enter Name: ");
		String name = sc.nextLine();
		System.out.println("Enter Location: ");
		String location = sc.nextLine();
		System.out.println("Check in with group? (y/n)");
		String groupStateCheckIn = sc.nextLine();
		
		// get date
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
		String dateCheckIn = dateFormat.format(date);
		
		//insertGroupDB();
		
		
		
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
//				System.out.println(diff_day);
			
			
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		try {
			while (true) {
				File f =new File("Database.txt");
				PrintWriter pw = new PrintWriter(new FileOutputStream(f,true));
				pw.append("\n"+ id +","+name+","+location+","+dateCheckIn);
				pw.close();
				
				if (groupStateCheckIn.equals("y")) {
					insertDB();
				}
				break;
			}
			
			
		}
		catch(Exception e) {}

		
	}
	
	public void insertGroupDB() {
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Check in with group (y/n)");
		String groupCheckInState = sc.nextLine();
		while (groupCheckInState != null) {
			// java uses .equals instead of "=="
			if (groupCheckInState.equals("y")) {
				// need to place insertDB in better area
				insertDB();
				break;
			}
			else  {
				break;
			}
		}
		
	}

		
		
	
	// read text file and store in array list and display
	public void storeDBtoArray() {
		
		try {
			BufferedReader in = new BufferedReader(new FileReader("Database.txt"));
			String str=null;
			List<String> lines = new ArrayList<String>();
			while((str = in.readLine()) != null){
			    lines.add(str);
			}

			String infected = "Tampines Mall";
			String user = "haadee";
			
			// iterates through list and returns list with specified string value
			for (String i: lines) {
				if (i.trim().contains(user)) {
					System.out.println(i);
				}
			}
			
//			for (String i: lines) {
//				System.out.println(i);
//			}
		    
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
