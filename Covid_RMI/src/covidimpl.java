
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class covidimpl extends java.rmi.server.UnicastRemoteObject  implements covid, officer {

	
	public ArrayList<Person> Database = new ArrayList<Person>() ;
	
	public ArrayList<InfectedLocation> LocationLog = new ArrayList<InfectedLocation>();
	
	public ArrayList<SuspectedCovid> suspectC = new ArrayList<SuspectedCovid>();
	
	private ReentrantLock lock = new ReentrantLock();
	
	private ReentrantLock mohLock = new ReentrantLock();
	
    public covidimpl() throws java.rmi.RemoteException {
        super();
  	  File file = new File("Database.txt");
  	  File fileLocation = new File("covid_location.txt");
  	  
  	  try {
  		  BufferedReader br = new BufferedReader(new FileReader(file));
  		  String st;
  		  System.out.println("\nActivity Record\n");
  		  System.out.println("--------------------------------");
  	  
  	  //continuous read file 
  		  while ((st = br.readLine()) != null) 
  		  {
  		 //For each line being read. split the text by delimiter comma.
  			  String[] tokens = st.split(","); 
  			  System.out.println("NRIC: "+tokens[0]);
  			  System.out.println("Name: "+tokens[1]);
  			  System.out.println("Location: "+tokens[2]);
  			  System.out.println("Date & Time of Check In/Out: " + tokens[3]);
  			  System.out.println("\n");
  			  Person ppl = new Person(tokens[0],tokens[1],tokens[2],tokens[3]); 
  		 
  			  Database.add(ppl); 
  		  }
  		System.out.println("--------------------------------" );
  	  	System.out.println("\nLocations that are infected are as follows: \n" );
  		  br = new BufferedReader(new FileReader(fileLocation));
  		 while ((st = br.readLine()) != null) 
 		  {
 		 //For each line being read. split the text by delimiter comma.
 			  String[] tokens = st.split(","); 
 			  System.out.println(tokens[0] +" is infected from, "+ tokens[1] + " till "+ tokens[2] +"\n");
 			  InfectedLocation InfectedLog = new InfectedLocation(tokens[0],tokens[1],tokens[2]); 
 		 
 			  LocationLog.add(InfectedLog); 
 		 
 		  }
  		System.out.println("--------------------------------" );
  	  }
  	  catch(Exception e) {
  		  System.out.println(e);
  	  }
    }
    

    

	public void checkIn(CovidRMIClientInf client,Person p) throws RemoteException {
		// TODO Auto-generated method stub
	 // Person test = (Person) p; 
			 Thread checkInThread = new Thread(new Runnable() {

					@Override
					public void run() {
						Random rg = new Random();
						int timer = 5000;
								//rg.nextInt(10000);  				
						try {							
							
						  	//Thread.sleep(timer);
							  System.out.println("Getting lock please wait");
							  client.confirmation("Getting lock please wait");
							  lock.lock();
							  System.out.println("I got the lock");
							  client.confirmation("I got the lock");
							  File f  = new File("Database.txt");
							  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
							  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
							  write.close();  
							  System.out.println("check in sucessfully");
							  
					  		  Person ppl = new Person(p.nric,p.name,p.location,p.time); 
					  		  Database.add(ppl); 
					  		  client.confirmation("Check in Sucessfully");
					  		 Thread.sleep(timer);
							
						} catch (java.rmi.RemoteException e) {
							e.printStackTrace();
						} catch(InterruptedException ee) {
							
						}catch(FileNotFoundException eee) {
							
						}finally {
							lock.unlock();
							System.out.print("Lock is free");
						}
						
					}    
					});
			
			 checkInThread.start();		
			 
	}


	public void checkOut(CovidRMIClientInf client,Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
		 try {
		      lock.lock();      
		      File f  = new File("Database.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
			  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
			  write.close();  
			  System.out.println("check out sucessfully");
			  
	  		  Person ppl = new Person(p.nric,p.name,p.location,p.time); 
	  		  Database.add(ppl); 
	  		  
		 	}catch(Exception e) {
		 		System.out.println(e);
		 
		 	}finally {
		 		
		 		lock.unlock();
		 		
		 	}
		
	}


	
	public void multipleCheckIn(CovidRMIClientInf client,ArrayList<Person> listofPeople) throws RemoteException {
			
			 Thread checkInThread = new Thread(new Runnable() {

					@Override
					public void run() {
						Random rg = new Random();
						int timer = rg.nextInt(10000);  				
						try {							
							
						  	//Thread.sleep(timer);
						    System.out.println("Getting lock please wait");
						    client.confirmation("Getting lock please wait");
						     lock.lock();
						     client.confirmation("Processing check in");
						     System.out.println("I got the lock");
						      File f  = new File("Database.txt");
							  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
					
							  listofPeople.forEach((ppl) -> {
							  write.append(ppl.nric +","+ ppl.name +","+ ppl.location +","+ ppl.time + "\n");  
							  });
							  
							  write.close();
							  client.confirmation("Check in Sucessfully");
							 Thread.sleep(timer);
							
						} catch (java.rmi.RemoteException e) {
							e.printStackTrace();
						} catch(InterruptedException ee) {
							
						}catch(FileNotFoundException eee) {
							
						}finally {
							lock.unlock();
							System.out.print("Lock is free");
						}
						
					}    
					});
			
			 checkInThread.start();	
		
	}






	@Override
	public void multipleCheckOut(Person p) throws RemoteException {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void storeTextToArray(InfectedLocation IL) throws RemoteException, IOException {
		try {
		      mohLock.lock();
			  File f  = new File("covid_location.txt");
			  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
			   
			  System.out.println("Updated Infected Location Log");
			  
	  		  InfectedLocation InfectedLog = new InfectedLocation(IL.location, IL.dateInfection, IL.datePostInfection); 
	  		  LocationLog.add(InfectedLog); 
		 }catch(Exception e) {
			  System.out.println(e);
			 
		 }finally {
			 mohLock.unlock();
		 }
	}




	@Override
	public String getLatestLocationDate(String locationInput) throws RemoteException {
		
		String latestLocationDate = null;
		File fileLocation = new File("covid_location.txt");
	  	 ArrayList<String> latestTime = new ArrayList<String>();
	  	  try {
	  		  BufferedReader br = new BufferedReader(new FileReader(fileLocation));
	  		  String st;
	  	  
	  	  //continuous read file 
	  		  while ((st = br.readLine()) != null) 
	  		  {
	  		 //For each line being read. split the text by delimiter comma.
	  			  String[] tokens = st.split(","); 
	  		 
	  			  if (tokens[0].equals(locationInput)) {
	  				  latestTime.add(tokens[2]);
	  				  
	  			  }
	  		
	  		  }
	  		  
	  		latestLocationDate = latestTime.get(latestTime.size()-1);
	  		System.out.println(locationInput + " is suspected of Covid until " + latestLocationDate);
	  	  }
	  	  catch(Exception e) {
	  		  System.out.println(e);
	  	  }
	  	  
		return latestLocationDate;
	}




	@Override
	public void checkCovid(CovidRMIClientInf client, String nric) throws RemoteException {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String result = null;

			for (Person p : Database) {
				if (p.getNric().equals(nric)) {
					for (InfectedLocation Log: LocationLog) {
						if(p.getLocation().equals(Log.getLocation())) {
							Date clientDate = dateFormat.parse(p.getTime());
							Date covidDate = dateFormat.parse(Log.getDateInfection());
							Date covidEndDate = dateFormat.parse(Log.getDatePost());
							if (clientDate.before(covidDate)) {
								System.out.println("safe");
							}
							else if (clientDate.after(covidDate) && (time_diff(covidEndDate, clientDate)< 0)) {
								System.out.print("Damn safe");
							}
							else {
								System.out.println(result);
								SuspectedCovid sc = new SuspectedCovid(p.getNric(), Log.getLocation(), p.getTime());
								suspectC.add(sc);
								client.NotifyCovid(suspectC, nric);
							}
						}
					}
				}
			}
			
//			ArrayList<InfectedLocation> exposure = new ArrayList<InfectedLocation>();
//			for(InfectedLocation log : LocationLog ) {
//				Date covidDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(log.getDateInfection()); 
//				for (Person person : Database) {
//					Date visitDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(person.getTime()); 
//					if(log.getLocation().equals(person.getLocation()) && covidDate.before(visitDate) &&  nric.equals(person.getNric())) 
//					{
//						exposure.add(log);
//					}
//				}
//			}
			//Set<InfectedLocation> uniqueLocation =  exposure;
//			client.NotifyCovid(exposure);	
		}
		catch(Exception e) {
			System.out.println(e);
		}
			
		
	}

	
	@Override
	public long time_diff(Date firstTime, Date secondTime) {
		long diff_time = firstTime.getTime() - secondTime.getTime();
		long difference = (diff_time
                   / (1000 * 60 * 60 * 24))
                  % 365;
		return difference;
	}


	@Override
	public String insertLog(String locationCovid, String dateAlert, String datePost) {
		// TODO Auto-generated method stub
		String result = null;
		File f =new File("covid_location.txt");
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(f,true));
			pw.append(locationCovid +","+ dateAlert +"," +datePost +"\n");
			
			pw.close();
			
			InfectedLocation IL = new InfectedLocation(locationCovid, dateAlert, datePost);
			
			storeTextToArray(IL);
			result = "\n"+ "Location: " + locationCovid + " is at risk from " + dateAlert + " to "+ datePost;
			System.out.println(result +"\n"+ "- Declared by MOH officer.");
			System.out.println("--------------------------------");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}




	@Override
	public void DisplayInfectedLocation() throws RemoteException {
		System.out.println("--------------------------------\n");
		System.out.println("Updated Infected Location");
		System.out.println("--------------------------------");
		for (InfectedLocation i: LocationLog) {
			System.out.println(i.getLocation() +" is infected from, "+ i.getDateInfection() + " till "+ i.getDatePost() +"\n");
		}
		System.out.println("--------------------------------\n");
	}




	@Override
	public ArrayList<InfectedLocation> IL() throws RemoteException {
		ArrayList<InfectedLocation> IL = new ArrayList<InfectedLocation>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("covid_location.txt"));
			
			 String st = null;
			 while ((st = br.readLine()) != null) 
	 		  {
	 		 //For each line being read. split the text by delimiter comma.
	 			  String[] tokens = st.split(","); 
	 			  System.out.println(tokens[0] +" is infected from, "+ tokens[1] + " till "+ tokens[2] +"\n");
	 			  InfectedLocation InfectedLog = new InfectedLocation(tokens[0],tokens[1],tokens[2]); 
	 		 
	 			 IL.add(InfectedLog);
	 		 
	 		  }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return(IL);
	}
	
	

}













