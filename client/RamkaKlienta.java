package client;

//Doda³em GUI dla serwera, okienet czatu u¿ytkowników, zamkniêcie okienka uzytkownika wypisuje go z serwera, zamkniêcie okienka serwera
//wy³¹cza go. Konsola nie bêdzie ju¿ potrzebna, rejestr RMI jest tworzony w kodzie. 
//Nadawanie wiadomoœci odbywa siê domyœlnie do wszystkich, dopiero po wybraniu kogoœ z listy po prawej,
//wiadomoœæ zostanie wys³ana tylko do niego.
//Iloœæ uzytkowników to tak naprawdê iloœæ uruchomionych programów ChatClient, z tym ¿e najpierw trzeba uruchomiæ Server.
//Jeœli nowy ChatClient bêdzie mia³ tak¹ samê jak jakiœ ju¿ istniej¹cy, czat siê nie w³aczy

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import server.ServerInterface;

public class RamkaKlienta extends JFrame
{
	private ServerInterface server;
	private ChatClient user;
	private JList<String> userList;
	private String message = "";
	private String userName = "";
	private String odbiorca = "GLOBAL";
	
	private JTextField input;
	private JTextArea chat = new JTextArea();
	private JPanel container = new JPanel(), chatcontainer = new JPanel(), listContainer = new JPanel();;
	private JScrollPane chatscroll = new JScrollPane(chat), listscroll = new JScrollPane(listContainer);
	private JButton sendButton, connectButton;
	
	//Akcja nadania wiadomoœci
	ActionListener sendMsgAction = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!input.getText().trim().equals(""))
			{
				message=input.getText()+"\n";
				try {
					append("TY: "+message);
					server.wyslijWiadomosc(userName+": "+message, odbiorca);
				} catch (RemoteException e1) {
					try {
						server.updateStatus("B³¹d w sendMsgActionListener ("+userName+")\n");
					} catch (RemoteException e2) {	}
				}
				input.setText("");
			}
		}
	};
	
	//Akcja zmiany odbiorcy na liœcie
	ListSelectionListener wybranyUser = new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			odbiorca = userList.getSelectedValue();
		}
	};
	
	public RamkaKlienta(String s, ChatClient u, ServerInterface serv)
	{
		super(s+" - Chat");
		userName = s;
		server=serv;
		user=u;
		setSize(800, 400);
		chatcontainer.setSize(600, 400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				zamkniecie();//Zamkniêcie z wczeœniejszym wypisaniem z serwera uzytkownika
			}
		});
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		chatcontainer.setLayout(new BoxLayout(chatcontainer, BoxLayout.Y_AXIS));
		
		chat.setEditable(false);
		chatscroll.setMinimumSize(new Dimension(600, 300));
		chatcontainer.add(chatscroll);
		
		input = new JTextField(40);
		input.setText("");
		input.addActionListener(sendMsgAction);
		sendButton = new JButton("Send");
		sendButton.addActionListener(sendMsgAction);
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(input);
		panel.add(sendButton);
		panel.setMaximumSize(new Dimension(600, 100));
		chatcontainer.add(panel);
		
		try {
			userList = new JList<>(server.listuj());
		} catch (RemoteException e1) {
			try {
				server.updateStatus("B³¹d listowania userów. ("+userName+")\n");
			} catch (RemoteException e2) {	}
		}
		listContainer.add(userList);
		
		container.add(chatcontainer);
		container.add(listscroll);
		add(container);
		setVisible(true);
	} 
	public void updateUserList()
	{
		try {
			container.remove(listscroll);
			listContainer = new JPanel();
			listscroll = new JScrollPane(listContainer);
			userList = new JList<>(server.listuj());
			userList.addListSelectionListener(wybranyUser);
			System.gc();
			listContainer.add(userList);
			container.add(listscroll);
			revalidate();
		} catch (RemoteException e) {
			try {
				server.updateStatus("B³¹d w aktualizacji listy userów ("+userName+")\n");
			} catch (RemoteException e1) {	}
		}
	}
	public void append(String s)
	{
		chat.append(s);
	}
	public int zamkniecie()
	{
		try {
			server.wypisz(userName);
			server.updateStatus("Od³aczono  ("+userName+")\n");
			System.exit(0);
		} catch (RemoteException e1) {
			try {
				server.updateStatus("B³¹d w zamkniêciu chatu.  ("+userName+")\n");
			} catch (RemoteException e) { }
		}
		return 3;
	}
}
