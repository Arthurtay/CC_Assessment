
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
  			  System.out.println("Name: "+tokens[0]);
  			  System.out.println("NRIC: "+tokens[1]);
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
    
    

    /**
     * 1.Writes the Person's check-in information into a text file(perm storage) and append into the  temporary database storage(arraylist)
     * 2.Each Invokation of method creates a thread to process the task
     * 3.Thread will only process task after obtaining the lock and unlocks after finishing the task  
     * @param  p- A Person object that contains the user's information to be check in
     * @param  client- used to do callback for the client
     * @throws RemoteException
     */
	public void checkIn(CovidRMIClientInf client,Person p) throws RemoteException {
	
	
			 Thread checkInThread = new Thread(new Runnable() {

					@Override
					public void run() {
						Random rg = new Random();
						int timer = rg.nextInt(1000);  
										
						try {							
							
						  	
							  client.confirmation("Processing Check In");
							  lock.lock();
							   
							  File f  = new File("Database.txt");
							  PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
							  write.append("\n"+p.name +","+ p.nric +","+ p.location +","+ p.time);
							  write.close();  
					  		  
							  Person ppl = new Person(p.name,p.nric,p.location,p.time); 
					  		  Database.add(ppl); 
					  		  
					  		  client.confirmation("Check in Sucessfully");
					  		  Thread.sleep(timer);
							
						} catch (java.rmi.RemoteException e) {
							e.printStackTrace();
						} catch(InterruptedException ee) {
							ee.printStackTrace();
						}catch(FileNotFoundException eee) {
							eee.printStackTrace();
						}finally {
							//unlocks once task is completed
							lock.unlock();
						}
						
					}    
					});
			
			 checkInThread.start();		
			 
	}

    /**
     * 1.Check if there is a valid Check-In information for today based on NRIC,Location 
     * before allowing checkout to process
     * 2.Writes the Person's check-out information into a text file(perm storage) and append into the temporary database storage(arraylist)
     * 3.Each Invokation of method creates a thread to process the task
     * 4.Thread will only process task after obtaining the lock and unlocks after finishing the task  
     * @param  p- A Person object that contains the user's information to be check out
     * @param  client- used to do callback for the client
     * @throws RemoteException
     */
	public void checkOut(CovidRMIClientInf client,Person p) throws RemoteException {

		
		 Thread checkOutThread = new Thread(new Runnable() {
			 boolean checkout = false;
				@Override
				public void run() {
					Random rg = new Random();
					int timer = rg.nextInt(1000);  
									
					try {							
						SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					        
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
							  write.append("\n"+p.name +","+ p.nric +","+ p.location +","+ p.time);
							  write.close();  
							  System.out.println();
					  		  Person ppl = new Person(p.name,p.nric,p.location,p.time); 
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

	
    /**
     * 1.Iterating through an arraylist from the client and writes the Person's check-out information into a text file 
     * and append into the temporary database storage(arraylist)
     * 2.Writes the Person's check-out information into a text file and append into the arraylist
     * 3.Each Invocation of method creates a thread to process the task
     * 4.Thread will only process task after obtaining the lock and unlocks after finishing the task  
     * @param  listofPeople- An Arraylist containing Person object that contains the user's information to be check in
     * @param  client- used to do callback for the client
     * @throws RemoteException
     */
	
	public void multipleCheckIn(CovidRMIClientInf client,ArrayList<Person> listofPeople) throws RemoteException {
			
			 Thread checkInThread = new Thread(new Runnable() {

					@Override
					public void run() {
						Random rg = new Random();
						int timer = rg.nextInt(1000);  				
						try {							
							
						    client.confirmation("Processing check in");
						    lock.lock();
						    File f  = new File("Database.txt");
							PrintWriter write =   new PrintWriter(new FileOutputStream(f,true));
					
							  listofPeople.forEach((ppl) -> {
							  write.append("\n"+ppl.name +","+ ppl.nric +","+ ppl.location +","+ ppl.time);  
							  });
							  
							  write.close();
							  client.confirmation("Check in Sucessfully");
							 Thread.sleep(timer);
							
						} catch (java.rmi.RemoteException e) {
							e.printStackTrace();
						} catch(InterruptedException ee) {
							ee.printStackTrace();
						}catch(FileNotFoundException eee) {
							eee.printStackTrace();
						}finally {
							lock.unlock();
				
						}
						
					}    
					});
			
			 checkInThread.start();	
		
	}

    /**
     * 1.Iterating through an arraylist of people's record and covid location log to determine if a suspected individual has covid
     * Based on nric,location,date. 
     * @param  nric- used as a primary key to uniquely identify the records belonging to a person.
     * @param  client- used to do callback for the client
     * @throws RemoteException
     */
	@Override
	public void checkCovid(CovidRMIClientInf client, String nric) throws RemoteException {
		
		
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			// can return result as different string also
			for (Person p : Database) {
				if (p.getNric().equals(nric)) {
					for (InfectedLocation Log: LocationLog) {
						if(p.getLocation().equals(Log.getLocation())) {
							Date clientDate = dateFormat.parse(p.getTime());
							Date covidDate = dateFormat.parse(Log.getDateInfection());
							Date covidEndDate = dateFormat.parse(Log.getDatePost());
							if (clientDate.before(covidDate)) {
								// can return some other place also
								System.out.println(p.getNric() + " is not exposed to covid at " + Log.getLocation());
							}
							else if (clientDate.after(covidDate) && (time_diff(covidEndDate, clientDate)< 0)) {
								// can return some other place also
								System.out.print(p.getNric() + " is not exposed to covid at " + Log.getLocation());
							}
							else {
								SuspectedCovid sc = new SuspectedCovid(p.getNric(), Log.getLocation(), p.getTime());
								suspectC.add(sc);
							}
						}
					}
				}
			}
			client.NotifyCovid(suspectC, nric);
		}
		catch(ParseException e) {
			System.out.println(e);
		}
			
		
	}
	
	/**
	 * Clears the suspectC arrayList to avoid any duplication of records everytime user starts up client program
	 * @throws java.rmi.RemoteException
	 * */
	public void clear() throws java.rmi.RemoteException{
		suspectC.clear();
	}


    //================================================================================
    // 2. Covid Client END
    //================================================================================
	

    //================================================================================
    // 3.Moh Officer Client Function implemention  in Server START
    //================================================================================
	

	/**
	 * 1. Get datetime difference in days to check if user is within time of infection
	 * @param firstTime- Date variable passed in from the end date of infected location
	 * @param secondTime - Date variable passed in from the day of check in for the user
	 * @return primitive long date
	 * */
	@Override
	public long time_diff(Date firstTime, Date secondTime) {
		long diff_time = firstTime.getTime() - secondTime.getTime();
		long difference = (diff_time
                   / (1000 * 60 * 60 * 24))
                  % 365;
		return difference;
	}


	
	/**
	 * 1. Inserts new infected location and date of declaration as well as the end date of infected location
	 * @param mohClient- used to do callback for the officer client
	 * @param locationCovid- location entered by the MoH officer
	 * @param dateAlert- date entered by the MoH officer, date input is either date Now or other dates
	 * @param datePost- end of date for the covid infected location, +14 days after dateAlert
	 * @throws RemoteException
	 * */
	@Override
	public void insertLog(OfficerRMIClientInf mohClient,String locationCovid, String dateAlert, String datePost) throws RemoteException {
		
		
	
		 Thread MohThread = new Thread(new Runnable() {
			 
				String result = "Empty";
				File f =new File("covid_location.txt");
				PrintWriter pw;
				
				@Override
				public void run() {
					Random rg = new Random();
		
					try {							
						
	
						 	mohLock.lock();
							pw = new PrintWriter(new FileOutputStream(f,true));
							pw.append("\n"+locationCovid +","+ dateAlert +"," +datePost);
							
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
						e.printStackTrace();
					} catch (RemoteException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						mohLock.unlock();
						System.out.print("Moh Lock is free");
					}
					
				}    
			});
		
		MohThread.start();
	
	}

	/**
	 * 1. Stores updated information of the newly added location, date of declaration and end of date of infection of location into the array list instantiated
	 * @param InfectedLocation IL- Arraylist that stores all InfectedLocation
	 * @throws RemoteException, IOException
	 * */
	@Override
	public void storeTextToArray(InfectedLocation IL) throws RemoteException, IOException {
			  System.out.println("Updated Infected Location Log");
			   
	  		  InfectedLocation InfectedLog = new InfectedLocation(IL.location, IL.dateInfection, IL.datePostInfection); 
	  		  LocationLog.add(InfectedLog); 
		 
	}


	

	/**
	 * 1. Displays updated infected location by using getter method to print out
	 * @throws RemoteException
	 * */
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













