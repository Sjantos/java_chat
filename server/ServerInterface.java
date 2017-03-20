package server;

//Doda�em GUI dla serwera, okienet czatu u�ytkownik�w, zamkni�cie okienka uzytkownika wypisuje go z serwera, zamkni�cie okienka serwera
//wy��cza go. Konsola nie b�dzie ju� potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomo�ci odbywa si� domy�lnie do wszystkich, dopiero po wybraniu kogo� z listy po prawej,
//wiadomo�� zostanie wys�ana tylko do niego.
//Ilo�� uzytkownik�w to tak naprawd� ilo�� uruchomionych program�w ChatClient, z tym �e najpierw trzeba uruchomi� Server.
//Je�li nowy ChatClient b�dzie mia� tak� sam� jak jaki� ju� istniej�cy, czat si� nie w�aczy

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.KInterface;

public interface ServerInterface extends Remote
{
	int zapisz(KInterface u, String n) throws RemoteException;//Wpisuje nowego uzytkownika do serwera
	int wypisz(String n) throws RemoteException;//Wypisuje u�ytkownika z serwera
	String[] listuj() throws RemoteException;//Zwraca tablic� nazw uzytkownik�w (do UserList gdzie wybiera si� odbiorc� wiadomosci)
	KInterface pobierz(String n) throws RemoteException;//Zwraca u�ytkownika o nazwie (n) z puli zapisanych
	void wyslijWiadomosc(String s, String o) throws RemoteException;//Wysy�a wiadomo�� (s) do odbiorcy (o)
	void updateUserList() throws RemoteException;//Aktualizuje list� u�ytkownik�w, do kt�rych mo�na wysy�a� wiadomo�ci
	void updateStatus(String s) throws RemoteException;//Wpisuje notk� (s) na serwer (komunikaty po�acze�, ewentualnie b��d�w)
	void zamknijWszystko() throws RemoteException;//Zamyka wszystkich u�ytkownik�w (Przypadek gdy serwer zostanie wy��czony z po�aczonymi u�ytkownikami)
}
