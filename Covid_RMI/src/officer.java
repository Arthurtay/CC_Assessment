import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;

/*
 * A remote 'officer' interface that hosts the method signatures for the officer terminal access
 * */
public interface officer extends java.rmi.Remote {
	
	/**
	 * Function's signature to be implemented in the server
	 * @param mohClient
	 * @param locationCovid
	 * @param dateAlert
	 * @param datePost
	 * @throws java.rmi.RemoteException
	 */
	public void insertLog(OfficerRMIClientInf mohClient,String locationCovid, String dateAlert, String datePost) throws java.rmi.RemoteException;
	
	/**
	 * Function's signature to be implemented in the server
	 * @param IL 
	 * @throws java.rmi.RemoteException, IOException
	 */
	public void storeTextToArray(InfectedLocation IL) throws java.rmi.RemoteException, IOException;
	
	/**
	 * Function's signature to be implemented in the server
	 * @param locationInput
	 * @throws java.rmi.RemoteException
	 */
	public String getLatestLocationDate(String locationInput) throws java.rmi.RemoteException;
	
	/**
	 * Function's signature to be implemented in the server
	 * @param firstTime
	 * @param secondTime
	 * @throws java.rmi.RemoteException
	 */
	public long time_diff(Date firstTime, Date secondTime) throws java.rmi.RemoteException;
	
	/**
	 * Function's signature to be implemented in the server
	 * @throws java.rmi.RemoteException
	 */
	public void DisplayInfectedLocation() throws java.rmi.RemoteException;
	
}
