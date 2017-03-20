package server;

//Doda�em GUI dla serwera, okienet czatu u�ytkownik�w, zamkni�cie okienka uzytkownika wypisuje go z serwera, zamkni�cie okienka serwera
//wy��cza go. Konsola nie b�dzie ju� potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomo�ci odbywa si� domy�lnie do wszystkich, dopiero po wybraniu kogo� z listy po prawej,
//wiadomo�� zostanie wys�ana tylko do niego.
//Ilo�� uzytkownik�w to tak naprawd� ilo�� uruchomionych program�w ChatClient, z tym �e najpierw trzeba uruchomi� Server.
//Je�li nowy ChatClient b�dzie mia� tak� sam� jak jaki� ju� istniej�cy, czat si� nie w�aczy

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
	private JTextArea status = new JTextArea("Serwer wystartowa�, podawane b�d� tu komunikaty o b�edach itp.\n");
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
				zamkniecie();//Zamkni�cie najpierw wszystkich klient�w a potem serwera je�li serwer zostanie wy�aczony z po��czonymi klientami
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
