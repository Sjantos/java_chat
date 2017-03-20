package client;

//Doda�em GUI dla serwera, okienet czatu u�ytkownik�w, zamkni�cie okienka uzytkownika wypisuje go z serwera, zamkni�cie okienka serwera
//wy��cza go. Konsola nie b�dzie ju� potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomo�ci odbywa si� domy�lnie do wszystkich, dopiero po wybraniu kogo� z listy po prawej,
//wiadomo�� zostanie wys�ana tylko do niego.
//Ilo�� uzytkownik�w to tak naprawd� ilo�� uruchomionych program�w ChatClient, z tym �e najpierw trzeba uruchomi� Server.
//Je�li nowy ChatClient b�dzie mia� tak� sam� jak jaki� ju� istniej�cy, czat si� nie w�aczy

import java.lang.invoke.MethodHandles.Lookup;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.IntSummaryStatistics;
import java.util.Scanner;

import javax.swing.JOptionPane;

import server.Server;
import server.ServerInterface;

public class ChatClient extends UnicastRemoteObject implements KInterface
{
	private ServerInterface server;
	private String name = null;
	
	private RamkaKlienta okno;

	protected ChatClient(ServerInterface serv) throws RemoteException 
	{
		server=serv;
		name=JOptionPane.showInputDialog(null, "Enter your nickname:", "Log In", JOptionPane.PLAIN_MESSAGE);
		okno = new RamkaKlienta(name, this, server);
		server.zapisz(this, name);
		server.updateStatus("Po��czono u�ytkownika ("+name+").\n");
	}
	
	@Override
	public String getUserName()
	{
		return name;
	}

	@Override
	public void odbierz(String s) throws RemoteException {
		try {
			okno.append(s);
		} catch (Exception e) {
			server.updateStatus("B��d odebrania wiadomo�ci przez:"+name+"\n");
		}
	}
	
	@Override
	public void updateUserList()
	{
		okno.updateUserList();
	}
	
	@Override
	public void zamknij()
	{
		okno.zamkniecie();
	}
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException
	{
		String serverURL = "rmi://localhost/RMIServer";
		ServerInterface chatServer = (ServerInterface) Naming.lookup(serverURL);
		new ChatClient(chatServer);
	}
}
