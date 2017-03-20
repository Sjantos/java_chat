package server;

//Doda³em GUI dla serwera, okienet czatu u¿ytkowników, zamkniêcie okienka uzytkownika wypisuje go z serwera, zamkniêcie okienka serwera
//wy³¹cza go. Konsola nie bêdzie ju¿ potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomoœci odbywa siê domyœlnie do wszystkich, dopiero po wybraniu kogoœ z listy po prawej,
//wiadomoœæ zostanie wys³ana tylko do niego.
//Iloœæ uzytkowników to tak naprawdê iloœæ uruchomionych programów ChatClient, z tym ¿e najpierw trzeba uruchomiæ Server.
//Jeœli nowy ChatClient bêdzie mia³ tak¹ samê jak jakiœ ju¿ istniej¹cy, czat siê nie w³aczy

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RamkaSerwera extends JFrame
{
	private Server server;
	private JTextArea status = new JTextArea("Serwer wystartowa³, podawane bêd¹ tu komunikaty o b³edach itp.\n");
	private JScrollPane scroll = new JScrollPane(status);
	private JPanel container = new JPanel(new GridLayout(1, 1));
	public RamkaSerwera(Server s)
	{
		super("Serwer");
		server = s;
		setSize(500, 800);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screen.width-500, 0);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				zamkniecie();//Zamkniêcie najpierw wszystkich klientów a potem serwera jeœli serwer zostanie wy³aczony z po³¹czonymi klientami
			}
		});
		status.setEditable(false);
		status.setLineWrap(true);
		status.setWrapStyleWord(true);
		container.add(scroll);
		add(container);
		
		setVisible(true);
	}
	public void append(String s)
	{
		status.append(s);
	}
	private void zamkniecie()
	{
		server.zamknijWszystko();
		System.exit(0);
	}
}
