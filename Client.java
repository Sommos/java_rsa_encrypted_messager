package encryption;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Client extends JFrame {
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	// CONSTRUCTOR FOR CLIENT SUB-CLASS //
	public Client(String host) {
		setTitle("Samuels Encryption Client Service");
		serverIP = host;
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendMessage(event.getActionCommand());
				userText.setText("");
			} 
		});
		add(userText, BorderLayout.NORTH);
		chatWindow = new JTextArea();
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
		setSize(325,375);
		setVisible(true);
	}
	
	// STARTS THE CLIENT //
	public void startRunning() {
		try {
			connectToServer();
			setupStreams();
			whileChatting();
		} catch(EOFException eofException) {
			showMessage("\nClient terminated connection.");
		} catch(IOException ioException) {
			ioException.printStackTrace();
		} finally {
			closeEverything();
			Main.exit(0);
		}
	}
	
	// METHOD THAT SETS A CONNECTION UP //
	private void connectToServer() throws IOException {
		showMessage("Attempting connection...");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage("\nConnected to : " + connection.getInetAddress().getHostName());
	}
	
	// SETS STREAMS TO SEND AND RECIEVE DATA //
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nYour streams are good to go.");
	}
	
	// METHOD THAT IS RUN WHILST THE USERS ARE CONNECTED TO EACH OTHER //
	private void whileChatting() throws IOException {
		ableToType(true);
		do {
			try {
				Object message = input.readObject();
				EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
				RSAEncryption rsa = new RSAEncryption();
				BigInteger p = rsa.getPrimeP(1024);
				BigInteger q = rsa.getPrimeQ(1024);
				BigInteger pq = rsa.getPQ(p, q);
				BigInteger e = rsa.getE();
				byte[] messageAsByteArray = encDecMeth.toByteArray(message);
				Main.println(messageAsByteArray);
				Main.println(encDecMeth.toByteArray(message));
				byte[] decrypted = encDecMeth.returnDecryptedMessage(messageAsByteArray,rsa.getD(e, p, q), pq);
				Main.println(decrypted);
				Main.println(encDecMeth.returnDecryptedMessage(messageAsByteArray,rsa.getD(e, p, q), pq));
				String messageBytesToString = encDecMeth.bytesToString(decrypted);
				Main.println(messageBytesToString);
				Main.println(encDecMeth.bytesToString(decrypted));
				String finalDecryptedString = new String(messageBytesToString);
				Main.println(finalDecryptedString);
				showMessage("\n" + finalDecryptedString);
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");
			}
		} while(!message.equals("Server : END CONNECTION"));
	}
	
	// METHOD THAT CLOSES SOCKETS AND STREAMS ONCE FINISHED //
	private void closeEverything() {
		showMessage("\nClosing all connections...");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch(IOException ioException) {
			ioException.printStackTrace();
			Main.exit(0);
		}
	}
	
	// METHOD THAT SENDS THE MESSAGE TO CLIENT //
	private void sendMessage(String message) {
		try {
			showMessage("\nClient : " + message);
//			EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
//			String startEncryptionMessage = message;
//			String encryptedUserMessage = encDecMeth.encrypt(startEncryptionMessage);
			output.writeObject("Client : " + message);
			output.flush();
		} catch(IOException ioException) {
			chatWindow.append("\nClient had an issue with sending that message.");
		}
	}
	
	// METHOD THAT SHOWS THE MESSAGE TO CLIENT //
	private void showMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(message);
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
