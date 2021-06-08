import java.rmi.RemoteException;
import java.util.ArrayList;

public interface covid extends java.rmi.Remote {	


/**
 * 
 * @param client
 * @param p - 
 * @throws java.rmi.RemoteException
 */
public void checkIn(CovidRMIClientInf client, Person p) throws java.rmi.RemoteException;


/**
 * 
 * @param client
 * @param p
 * @throws java.rmi.RemoteException
 */
public void checkOut(CovidRMIClientInf client, Person p) throws java.rmi.RemoteException;


/**
 * 
 * @param client
 * @param p
 * @throws java.rmi.RemoteException
 */
public void multipleCheckIn(CovidRMIClientInf client, ArrayList<Person> p)throws java.rmi.RemoteException;

/**
 * 
 * @param client
 * @param nric
 * @throws java.rmi.RemoteException
 */
public void checkCovid(CovidRMIClientInf client, String nric) throws java.rmi.RemoteException;
 
}