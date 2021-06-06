import java.rmi.RemoteException;
import java.util.ArrayList;

public interface covid extends java.rmi.Remote {	


public void checkIn(CovidRMIClientInf client, Person p) throws java.rmi.RemoteException;


public void checkOut(CovidRMIClientInf client, Person p) throws java.rmi.RemoteException;



public void multipleCheckIn(CovidRMIClientInf client, ArrayList<Person> p)throws java.rmi.RemoteException;



public void checkCovid(CovidRMIClientInf client, String nric) throws java.rmi.RemoteException;
 
}