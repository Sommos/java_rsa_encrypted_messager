package encryption;
 
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class Server extends JFrame {
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	// CONSTRUCTOR FOR SERVER SUB-CLASS //
	public Server() {
		setTitle("Samuels Encryption Server Service");
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				sendMessage(event.getActionCommand());
				userText.setText("");
			}
		});
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow));
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
		setSize(700,700);
		setVisible(true);
	}
	
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 100);
			while(true) {
				try {
					waitForConnection();
					setupStreams();
					whileChatting();
				} catch(EOFException eofException) {
					showMessage("\n The server has ended the connection.");
				} finally {
					closeEverything();
				}
			}
		} catch(IOException ioException) {
			ioException.printStackTrace();
			Main.exit(0);
		}
	}
	
	// METHOD THAT WAITS FOR A CONNECTION //
	private void waitForConnection() throws IOException {
		showMessage("Waiting for another user to connect...\n");
		connection = server.accept();
		showMessage("You have connected to " + connection.getInetAddress().getHostName());
	}
	
	// SETS STREAMS TO SEND AND RECIEVE DATA //
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream()); 
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup! \n");
	}
	
	// METHOD THAT IS RUN WHILST THE USERS ARE CONNECTED TO EACH OTHER //
	private void whileChatting() throws IOException {
		String message = "You are now connected!";
		sendMessage(message);
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\n The server is unable to understand that String.");
			}
			
		} while(!message.equals("CLIENT END"));
	}
	
	// METHOD THAT CLOSES SOCKETS AND STREAMS ONCE FINISHED //
	private void closeEverything() {
		showMessage("\n Closing connections...\n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	// METHOD THAT SENDS THE MESSAGE TO CLIENT //
	private void sendMessage(String message) {
		try {
//			EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
//			String encryptedUserMessage = encDecMeth.encrypt(message);
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nServer - " + message);
		} catch(IOException ioException) {
			chatWindow.append("\n ERROR CODE : 0");
		}
	}
	
	// METHOD THAT SHOWS THE MESSAGE TO CLIENT //
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(text);
			}
		});
	}
	
	// METHOD THAT ALLOWS / DISALLOWS THE USER TO TYPE IN THE CHAT BOX //
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(tof);
			}
		});
	}
}
