import java.rmi.RemoteException;
import java.util.ArrayList;

public interface covid extends java.rmi.Remote {	


/**
 * Function's signature to be implemented in the server
 * @param client
 * @param p  
 * @throws java.rmi.RemoteException
 */
public void checkIn(CovidRMIClientInf client, Person p) throws java.rmi.RemoteException;


/**
 * Function's signature to be implemented in the server
 * @param client
 * @param p
 * @throws java.rmi.RemoteException
 */
public void checkOut(CovidRMIClientInf client, Person p) throws java.rmi.RemoteException;


/**
 * Function's signature to be implemented in the server
 * @param client
 * @param p
 * @throws java.rmi.RemoteException
 */
public void multipleCheckIn(CovidRMIClientInf client, ArrayList<Person> p)throws java.rmi.RemoteException;

/**
 * Function's signature to be implemented in the server
 * @param client
 * @param nric
 * @throws java.rmi.RemoteException
 */
public void checkCovid(CovidRMIClientInf client, String nric) throws java.rmi.RemoteException;

/**
 * Function's signature to be implemented in the server
 * @throws java.rmi.RemoteException
 */
public void clear() throws java.rmi.RemoteException;
 
}