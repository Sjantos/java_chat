package server;

//	Doda�em GUI dla serwera, okienet czatu u�ytkownik�w, zamkni�cie okienka uzytkownika wypisuje go z serwera, zamkni�cie okienka serwera
//	wy��cza go. Konsola nie b�dzie ju� potrzebna, rejestr RMI jest tworzony w kodzie. 
//	Nadawanie wiadomo�ci odbywa si� domy�lnie do wszystkich, dopiero po wybraniu kogo� z listy po prawej,
//	wiadomo�� zostanie wys�ana tylko do niego.
//	Ilo�� uzytkownik�w to tak naprawd� ilo�� uruchomionych program�w ChatClient, z tym �e najpierw trzeba uruchomi� Server.
//	Je�li nowy ChatClient b�dzie mia� tak� sam� jak jaki� ju� istniej�cy, czat si� nie w�aczy
	

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import client.KInterface;

public class Server extends UnicastRemoteObject implements ServerInterface
{
	private List<KInterface> uzytkownicy;
	private static RamkaSerwera oknoSerwera;
	
	public Server() throws RemoteException
	{
		uzytkownicy = Collections.synchronizedList(new ArrayList<KInterface>());
	}
	private String uzytkownicyToString()
	{
		String tmp="[ ";
		for(int i=0; i<uzytkownicy.size(); i++)
		{
			try {
				tmp+=uzytkownicy.get(i).getUserName()+" ";
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return tmp+" ]";
	}
	
	public void updateUserList()
	{
		try {
			for(int i=0; i<uzytkownicy.size(); i++)
			{
				if(i==0) updateStatus("UPDATE list u�ytkownik�w.\n");
				uzytkownicy.get(i).updateUserList();
			}
		} catch (RemoteException e) {
			updateStatus("B��d aktualizacji listy u�ytkownik�w.\n");
		}
	}
	
	@Override
	public int zapisz(KInterface u, String n) throws RemoteException 
	{
		for(int i=0; i<uzytkownicy.size(); i++)
		{
			if(uzytkownicy.get(i).getUserName().equals(n)) return 0;
		}
		uzytkownicy.add(u);
		updateUserList();
		return 1;
	}

	@Override
	public int wypisz(String n) throws RemoteException 
	{
		for(int i=0; i<uzytkownicy.size(); i++)
		{
			if(uzytkownicy.get(i).getUserName().equals(n))
			{
				uzytkownicy.remove(i);
				updateUserList();
				return 1;
			}
		}
		return 0;
	}

	@Override
	public String[] listuj() throws RemoteException {
		String[] tab = new String[uzytkownicy.size()+1];
		tab[0] = "GLOBAL";
		for(int i=1; i<tab.length; i++) tab[i]=uzytkownicy.get(i-1).getUserName();
		return tab;
	}

	@Override
	public KInterface pobierz(String n) throws RemoteException {
		for(int i=0; i<uzytkownicy.size(); i++)
		{
			if(uzytkownicy.get(i).getUserName().equals(n)) return uzytkownicy.get(i);
		}
		return null;
	}

	@Override
	public void wyslijWiadomosc(String s, String o) throws RemoteException {
		if(o.equals("GLOBAL"))
		{
			for(int i=0; i<uzytkownicy.size(); i++)
			{
				try {
					uzytkownicy.get(i).odbierz(s);
				} catch (Exception e) {
					updateStatus("B��d odebrania wiadomo�ci");
				}
			}
		}
		else pobierz(o).odbierz(s);
	}
	
	@Override
	public void updateStatus(String s)
	{
		oknoSerwera.append(s);
	}
	
	@Override
	public void zamknijWszystko()
	{
		for(int i=0; i<uzytkownicy.size(); i++)
		{
			try {
				uzytkownicy.get(i).zamknij();
			} catch (RemoteException e) {
				updateStatus("B��d w zamkni�ciu wszystkiego.");
				zamknijWszystko();
			}
		}
	}
	
	public static void main(String[] args) throws RemoteException, MalformedURLException
	{
		LocateRegistry.createRegistry(1099);
		Server s = new Server();
		Naming.rebind("RMIServer", s);
		oknoSerwera = new RamkaSerwera(s);
	}
}
