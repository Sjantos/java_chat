package client;

//Doda�em GUI dla serwera, okienet czatu u�ytkownik�w, zamkni�cie okienka uzytkownika wypisuje go z serwera, zamkni�cie okienka serwera
//wy��cza go. Konsola nie b�dzie ju� potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomo�ci odbywa si� domy�lnie do wszystkich, dopiero po wybraniu kogo� z listy po prawej,
//wiadomo�� zostanie wys�ana tylko do niego.
//Ilo�� uzytkownik�w to tak naprawd� ilo�� uruchomionych program�w ChatClient, z tym �e najpierw trzeba uruchomi� Server.
//Je�li nowy ChatClient b�dzie mia� tak� sam� jak jaki� ju� istniej�cy, czat si� nie w�aczy

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KInterface extends Remote
{
	void odbierz(String s) throws RemoteException;//Odbiera wiadomo�� z serwera i wy�wietla w czacie
	String getUserName() throws RemoteException;//Zwraca nazw� u�ytkownika
	void updateUserList() throws RemoteException;//Aktualizuje list� u�ytkownik�w, do kt�rych mo�na wysy�a� wiadomo�ci
	void zamknij() throws RemoteException;//Zamyka okno u�ytkownika (w przypadku gdy serwer zostanie wy��czony)
}
