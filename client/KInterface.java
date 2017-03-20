package client;

//Doda³em GUI dla serwera, okienet czatu u¿ytkowników, zamkniêcie okienka uzytkownika wypisuje go z serwera, zamkniêcie okienka serwera
//wy³¹cza go. Konsola nie bêdzie ju¿ potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomoœci odbywa siê domyœlnie do wszystkich, dopiero po wybraniu kogoœ z listy po prawej,
//wiadomoœæ zostanie wys³ana tylko do niego.
//Iloœæ uzytkowników to tak naprawdê iloœæ uruchomionych programów ChatClient, z tym ¿e najpierw trzeba uruchomiæ Server.
//Jeœli nowy ChatClient bêdzie mia³ tak¹ samê jak jakiœ ju¿ istniej¹cy, czat siê nie w³aczy

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface KInterface extends Remote
{
	void odbierz(String s) throws RemoteException;//Odbiera wiadomoœæ z serwera i wyœwietla w czacie
	String getUserName() throws RemoteException;//Zwraca nazwê u¿ytkownika
	void updateUserList() throws RemoteException;//Aktualizuje listê u¿ytkowników, do których mo¿na wysy³aæ wiadomoœci
	void zamknij() throws RemoteException;//Zamyka okno u¿ytkownika (w przypadku gdy serwer zostanie wy³¹czony)
}
