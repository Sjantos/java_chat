package server;

//Doda³em GUI dla serwera, okienet czatu u¿ytkowników, zamkniêcie okienka uzytkownika wypisuje go z serwera, zamkniêcie okienka serwera
//wy³¹cza go. Konsola nie bêdzie ju¿ potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomoœci odbywa siê domyœlnie do wszystkich, dopiero po wybraniu kogoœ z listy po prawej,
//wiadomoœæ zostanie wys³ana tylko do niego.
//Iloœæ uzytkowników to tak naprawdê iloœæ uruchomionych programów ChatClient, z tym ¿e najpierw trzeba uruchomiæ Server.
//Jeœli nowy ChatClient bêdzie mia³ tak¹ samê jak jakiœ ju¿ istniej¹cy, czat siê nie w³aczy

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.KInterface;

public interface ServerInterface extends Remote
{
	int zapisz(KInterface u, String n) throws RemoteException;//Wpisuje nowego uzytkownika do serwera
	int wypisz(String n) throws RemoteException;//Wypisuje u¿ytkownika z serwera
	String[] listuj() throws RemoteException;//Zwraca tablicê nazw uzytkowników (do UserList gdzie wybiera siê odbiorcê wiadomosci)
	KInterface pobierz(String n) throws RemoteException;//Zwraca u¿ytkownika o nazwie (n) z puli zapisanych
	void wyslijWiadomosc(String s, String o) throws RemoteException;//Wysy³a wiadomoœæ (s) do odbiorcy (o)
	void updateUserList() throws RemoteException;//Aktualizuje listê u¿ytkowników, do których mo¿na wysy³aæ wiadomoœci
	void updateStatus(String s) throws RemoteException;//Wpisuje notkê (s) na serwer (komunikaty po³aczeñ, ewentualnie b³êdów)
	void zamknijWszystko() throws RemoteException;//Zamyka wszystkich u¿ytkowników (Przypadek gdy serwer zostanie wy³¹czony z po³aczonymi u¿ytkownikami)
}
