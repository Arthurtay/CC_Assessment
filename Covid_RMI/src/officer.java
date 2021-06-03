import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface officer extends java.rmi.Remote {
	
	public String insertLog(String locationCovid, String dateAlert, String datePost) throws java.rmi.RemoteException;
	
	public void storeTextToArray(InfectedLocation IL) throws java.rmi.RemoteException, IOException;
	
	public String getLatestLocationDate(String locationInput) throws java.rmi.RemoteException;

	
}
