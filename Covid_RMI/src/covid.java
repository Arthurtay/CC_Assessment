import java.util.ArrayList;

public interface covid extends java.rmi.Remote {	


// add takes two long values, adds them together and returns the resulting
// long value

public void checkIn(Person p) throws java.rmi.RemoteException;


public void checkOut(Person p) throws java.rmi.RemoteException;



public void multipleCheckIn(ArrayList<Person> p)throws java.rmi.RemoteException;

public void multipleCheckOut(Person p)throws java.rmi.RemoteException;

}