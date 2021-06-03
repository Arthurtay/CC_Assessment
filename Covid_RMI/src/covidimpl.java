
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

	
	private ArrayList<Person> Database = new ArrayList<Person>() ;
	
	private ArrayList<InfectedLocation> LocationLog = new ArrayList<InfectedLocation>();
	
	private ReentrantLock lock = new ReentrantLock();
	
	private ReentrantLock mohLock = new ReentrantLock();
	
    public covidimpl() throws java.rmi.RemoteException {
        super();
       
    
  	  File file = new File("Database.txt");
  	  
  	  File fileLocation = new File("covid_location.txt");
  	  
  	  try {
  		  BufferedReader br = new BufferedReader(new FileReader(file));
  		  String st;
  	  
  	  //continuous read file 
  		  while ((st = br.readLine()) != null) 
  		  {
  		 //For each line being read. split the text by delimiter comma.
  			  String[] tokens = st.split(","); 
  			  System.out.println(tokens[0]);
  			  System.out.println(tokens[1]);
  			  System.out.println(tokens[2]);
  			  Person ppl = new Person(tokens[0],tokens[1],tokens[2],tokens[3]); 
  		 
  			  Database.add(ppl); 
  		 
  			  System.out.println("--------------------------------" );
  		
  		  }
  	  }
  	  catch(Exception e) {
  		  System.out.println(e);
  	  }
  	  
  	  try {
  		  BufferedReader br = new BufferedReader(new FileReader(fileLocation));
  		  String st;
  		 while ((st = br.readLine()) != null) 
 		  {
 		 //For each line being read. split the text by delimiter comma.
 			  String[] tokens = st.split(","); 
 			  System.out.println(tokens[0]);
 			  System.out.println(tokens[1]);
 			  System.out.println(tokens[2]);
 			  InfectedLocation InfectedLog = new InfectedLocation(tokens[0],tokens[1],tokens[2]); 
 		 
 			  LocationLog.add(InfectedLog); 
 		 
 			  System.out.println("--------------------------------" );
 		
 		  }
  		  
  		  
  	  }catch(Exception e) {
  		  System.out.println(e);
  	  }
        
        //Implement and load data from text file
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

			ArrayList<InfectedLocation> exposure = new ArrayList<InfectedLocation>();
		
			for(InfectedLocation log : LocationLog ) {
				Date covidDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(log.dateInfection); 
			
				for (Person person : Database) {
					Date visitDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(person.time); 
				    
					if(log.location.equals(person.location) && covidDate.before(visitDate) &&  nric.equals(person.nric)) 
					{
	
						exposure.add(log);
						
					}
							
				}
			
				
			}

			//Set<InfectedLocation> uniqueLocation =  exposure;
			client.NotifyCovid(exposure);	
		}
		catch(Exception e) {
			System.out.println(e);
		}
			
		
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
			result = "Update Record \n"+ "Location: " + locationCovid + " is at risk from " + dateAlert + " to "+ datePost;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}













