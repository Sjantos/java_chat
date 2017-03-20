package client;

//Doda³em GUI dla serwera, okienet czatu u¿ytkowników, zamkniêcie okienka uzytkownika wypisuje go z serwera, zamkniêcie okienka serwera
//wy³¹cza go. Konsola nie bêdzie ju¿ potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomoœci odbywa siê domyœlnie do wszystkich, dopiero po wybraniu kogoœ z listy po prawej,
//wiadomoœæ zostanie wys³ana tylko do niego.
//Iloœæ uzytkowników to tak naprawdê iloœæ uruchomionych programów ChatClient, z tym ¿e najpierw trzeba uruchomiæ Server.
//Jeœli nowy ChatClient bêdzie mia³ tak¹ samê jak jakiœ ju¿ istniej¹cy, czat siê nie w³aczy

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
		server.updateStatus("Po³¹czono u¿ytkownika ("+name+").\n");
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
			server.updateStatus("B³¹d odebrania wiadomoœci przez:"+name+"\n");
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
