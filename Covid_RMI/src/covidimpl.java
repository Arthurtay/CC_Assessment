
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class covidimpl extends java.rmi.server.UnicastRemoteObject  implements covid, officer {

	
	public ArrayList<Person> Database = new ArrayList<Person>() ;
	public ArrayList<InfectedLocation> LocationLog = new ArrayList<InfectedLocation>();
	public ArrayList<SuspectedCovid> suspectC = new ArrayList<SuspectedCovid>();
	private ReentrantLock lock = new ReentrantLock();
	private ReentrantLock mohLock = new ReentrantLock();
	
	

    //================================================================================
    // 1. Constructor 
    //================================================================================

    public covidimpl() throws java.rmi.RemoteException {
        super();
       
      System.out.println("\n=============== Welcome to the Server Terminal ===============");
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
    
    //================================================================================
    // 1. Constructor END
    //================================================================================
    
    
    

    //================================================================================
    // 2. Covid Client Function implemention  in Server START
    //================================================================================
    

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
							
						  	
							  client.confirmation("Processing Check In");
							  lock.lock();
							   
							  File f  = new File("Database.txt");
							  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
							  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
							  write.close();  
					  		  
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
		
		
		 Thread checkOutThread = new Thread(new Runnable() {
			 boolean checkout = false;
				@Override
				public void run() {
					Random rg = new Random();
					int timer = 5000;
							//rg.nextInt(10000);  				
					try {							
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					  	//Thread.sleep(timer);
					
					        
					      for(Person record: Database) {
					    	  
							  Date recordDate = dateFormat.parse(record.time);
							  Date currentDate = dateFormat.parse(p.time);
					    	  //Check if there is existing check in record for the current day
					    	  if( record.location.equals(p.location) && record.nric.equals(p.nric) && record.location.equals(p.location)  ) {
					    		  checkout = true;
					    		  
					    	  }  
					    	  
					      }
					      if(checkout) {
						      lock.lock();  
						      File f  = new File("Database.txt");
							  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
							  write.append(p.nric +","+ p.name +","+ p.location +","+ p.time + "\n");
							  write.close();  
							  System.out.println();
					  		  Person ppl = new Person(p.nric,p.name,p.location,p.time); 
					  		  Database.add(ppl); 
					  		  client.confirmation("Check out sucessfully");
					  	   	  Thread.sleep(timer);
					      }
					      else {
					    	  client.confirmation("Unable to checkout,there is no existing checkin record today ");
					      }
	
						
					} catch (java.rmi.RemoteException e) {
						e.printStackTrace();
					} catch(InterruptedException e) {
						e.printStackTrace();
					}catch(FileNotFoundException e) {
						e.printStackTrace();
					}catch(ParseException e) {
						e.printStackTrace();
					}
					finally {
					 //only attempts to unlock if checkout was true 
						if(checkout) {
							lock.unlock();
						
						}
					 
					}
					
				}    
				});
		
		 checkOutThread.start();	

		
	}


	
	public void multipleCheckIn(CovidRMIClientInf client,ArrayList<Person> listofPeople) throws RemoteException {
			
			 Thread checkInThread = new Thread(new Runnable() {

					@Override
					public void run() {
						Random rg = new Random();
						int timer = rg.nextInt(10000);  				
						try {							
							
						    client.confirmation("Processing check in");
						    lock.lock();
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
				
						}
						
					}    
					});
			
			 checkInThread.start();	
		
	}

	
	@Override
	public void checkCovid(CovidRMIClientInf client, String nric) throws RemoteException {
		
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// can return result as different string also
			String result = null;

			for (Person p : Database) {
				if (p.getNric().equals(nric)) {
					for (InfectedLocation Log: LocationLog) {
						if(p.getLocation().equals(Log.getLocation())) {
							Date clientDate = dateFormat.parse(p.getTime());
							Date covidDate = dateFormat.parse(Log.getDateInfection());
							Date covidEndDate = dateFormat.parse(Log.getDatePost());
							if (clientDate.before(covidDate)) {
								// can return some other place also
								System.out.println("safe");
							}
							else if (clientDate.after(covidDate) && (time_diff(covidEndDate, clientDate)< 0)) {
								// can return some other place also
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
			

		}
		catch(ParseException e) {
			System.out.println(e);
		}
			
		
	}


    //================================================================================
    // 2. Covid Client END
    //================================================================================
	

    //================================================================================
    // 3.Moh Officer Client Function implemention  in Server START
    //================================================================================
	
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






	/*
	 * This method will take in the check-in datetime of the user as 'firstTime'
	 * 'secondTime' will be the last dateTime of when an infected location is assumed as having risk of covid*/
	@Override
	public long time_diff(Date firstTime, Date secondTime) {
		long diff_time = firstTime.getTime() - secondTime.getTime();
		long difference = (diff_time
                   / (1000 * 60 * 60 * 24))
                  % 365;
		return difference;
	}


	

	@Override
	public void insertLog(OfficerRMIClientInf mohClient,String locationCovid, String dateAlert, String datePost) throws RemoteException {
		// TODO Auto-generated method stub
		
		System.out.println("This is called");
		 Thread MohThread = new Thread(new Runnable() {
			 
				String result = "Empty";
				File f =new File("covid_location.txt");
				PrintWriter pw;
				
				@Override
				public void run() {
					Random rg = new Random();
				//	int timer = rg.nextInt(10000);  				
					try {							
						
	
						 	mohLock.lock();
							pw = new PrintWriter(new FileOutputStream(f,true));
							pw.append(locationCovid +","+ dateAlert +"," +datePost +"\n");
							
							pw.close();
							
							InfectedLocation IL = new InfectedLocation(locationCovid, dateAlert, datePost);
							
							storeTextToArray(IL);
							result = "\n"+ "Location: " + locationCovid + " is at risk from " + dateAlert + " to "+ datePost;
							System.out.println(result +"\n"+ "- Declared by MOH officer.");
							System.out.println("--------------------------------");
					        mohClient.confirmation(result);
					        mohClient.retrieveLatestLocation(LocationLog);
							Thread.sleep(10);
							
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						mohLock.unlock();
						System.out.print("Moh Lock is free");
					}
					
				}    
			});
		
		MohThread.start();
	
	}

	@Override
	public void storeTextToArray(InfectedLocation IL) throws RemoteException, IOException {
			  System.out.println("Updated Infected Location Log");
			   
	  		  InfectedLocation InfectedLog = new InfectedLocation(IL.location, IL.dateInfection, IL.datePostInfection); 
	  		  LocationLog.add(InfectedLog); 
		 
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

	
    //================================================================================
    // 3.Moh Officer Client Function implemention  in Server END
    //================================================================================
	
	
	

}













