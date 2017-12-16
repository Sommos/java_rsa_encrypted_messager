package encryption;

import java.io.*;
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
		setSize(700,700);
		setVisible(true);
	}
	
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
		}
	}
	
	private void connectToServer() throws IOException {
		showMessage("\nAttempting connection...");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage("\nConnected to : " + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nYour streams are good to go.");
	}
	
	private void whileChatting() throws IOException {
		ableToType(true);
		do {
			try {
//				EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
				message = (String) input.readObject();
//				byte[] messageInBytes =  message.getBytes(StandardCharsets.UTF_8);
//				String decryptedUserMessage = encDecMeth.decrypt(message);
				showMessage("\n" + message);
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");
			}
		} while(!message.equals("Server : END"));
	}
	
	private void closeEverything() {
		showMessage("\nClosing all connections...");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		} catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
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
	
	private void showMessage(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(message);
			}
		});
	}
	
	private void showMessageReal(final String message) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
				String decryptedUserMessage = encDecMeth.decrypt(message.getBytes());
				chatWindow.append(decryptedUserMessage);
			}
		});
	}
	
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				userText.setEditable(tof);
			}
		});
	}
}
