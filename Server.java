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
	
	// Constructor for the Server sub-class //
	public Server() {
		// Sets the title of the window //
		setTitle("Samuels Encryption Server Service");
		
		// Generates a new JTextField //
		userText = new JTextField();
		
		// Sets the JTextField to be un-editable //
		userText.setEditable(false);
		
		// Adds an action listener //
		userText.addActionListener(new ActionListener(){
			// Detects if the action is performed //
			public void actionPerformed(ActionEvent event){
				// Sends message with the contents of the action command //
				sendMessage(event.getActionCommand());
				
				// Sets the text field to empty //
				userText.setText("");
			}
		});
		// Adds the userText and sets the border layout to north of the screen //
		add(userText, BorderLayout.NORTH);
		
		// A new JTextArea is now instantiated //
		chatWindow = new JTextArea();
		
		// Adds the new JScrollPane, using the chatWindow variable and setting the border layout to center of the screen //
		add(new JScrollPane(chatWindow));
		
// 		Use this to gain the dimensions of the users screen //
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
		
		// Sets the size of the whole window //
		setSize(325,375);
		
		// Sets visibility of the Swing components to true //
		setVisible(true);
	}
	
	// Starts the Server class // 
	public void startRunning() {
		try {
			// Instantiates the server with port '6789' and allows 100 users to queue //
			server = new ServerSocket(6789, 100);
			while(true) {
				try {
					// Waits for a user to connect //
					waitForConnection();
					
					// Sets up the i/o streams //
					setupStreams();
					
					// Sets up the GUI to read the input stream //
					whileChatting();
				} catch(EOFException eofException) {
					// If an EOFException is given to the program, then this message is shown //
					showMessage("\nThe server has ended the connection.");
				} finally {
					// Closes the streams and makes sure the text fields are un-editable //
					closeEverything();
					
					// Exits the program with exit code 0 //
					Main.exit(0);
				}
			}
		} catch(IOException ioException) {
			// If an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
			
			// Exits the program with exit code 0 //
			Main.exit(0);
		}
	}
	
	// Method that waits for a user to connect to the server //
	private void waitForConnection() throws IOException {
		showMessage("Waiting for another user to connect...");
		// Accepts any connection that the server receives //
		connection = server.accept();
		showMessage("\nYou have connected to " + connection.getInetAddress().getHostName());
	}
	
	// Sets the streams up in preparation for data to be sent //
	private void setupStreams() throws IOException {
		// Sets up the output stream //
		output = new ObjectOutputStream(connection.getOutputStream()); 
		
		// Flushes spare bytes out of the Client machine //
		output.flush();
		
		// Sets up the input stream //
		input = new ObjectInputStream(connection.getInputStream());
		
		showMessage("\nStreams are now setup!");
	}
	
	// Method that is run whilst the users are connected to each other //
	private void whileChatting() throws IOException {
		String message = "You are now connected!\n";
		sendMessage(message);
		// Sets the chat box to be editable //
		ableToType(true);
		do {
			try {
//				RSAEncryption rsa = new RSAEncryption();
				// Casts the input.readObject to a String //
				message = (String) input.readObject();
//				byte[] source = message.getBytes();
				
//				String finalEncryptedString = new String(source);
//				message = rsa.dec(message);
				
				showMessage("\n" + message);
//				RSAEncryption rsa = new RSAEncryption();
				
//				byte[] source = message.getBytes();
//				
//				ByteArrayInputStream bis = new ByteArrayInputStream(source);
//				ByteArrayOutputStream bos = new ByteArrayOutputStream();
//				
//				byte[] sourceAsByteArray = bos.toByteArray();

//				byte[] decrypted = rsa.rsaDecrypt(source);
//				String finalDecryptedString = new String(decrypted);
//				String finalEncryptedString = new String(source);
//				showMessage("\n" + finalEncryptedString);
				
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");
			}
		} while(!message.equals("Client : END CONNECTION"));
	}
	
	// Method that sends a message using the output stream //
	private void sendMessage(String message) {
		try {
//			RSAEncryption rsa = new RSAEncryption();
			showMessage("\nServer : " + message);
	        // Takes the values generated and the users original message, encrypts it and returns it as a byte array //
	        // May change this back to decrypted if byte values fail but we should be good //
			
//			BigInteger messageAsBigInteger = new BigInteger(message);
//			BigInteger e = rsa.getE();
//			BigInteger pq = rsa.getPQ();
			
//			BigInteger finalEncryptedMessage = messageAsBigInteger.modPow(e, pq);
//			message = rsa.enc(message);
//			String finalEncryptedString = new String(message);
			output.writeObject("Server : " + message);
			
			// Flushes the output stream //
			output.flush();
			
//			byte[] source = message.getBytes();
//			
//			ByteArrayInputStream bis = new ByteArrayInputStream(source);
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			
//			byte[] sourceAsByteArray = bos.toByteArray();
//			
//			byte[] encrypted = rsaEncrypt(sourceAsByteArray);
//			String finalEncryptedString = new String(encrypted);
			
			
//			byte[] source = message.getBytes();
//			String finalEncryptedString = new String(source);
//			
//			output.writeObject("Server : " + finalEncryptedString);
//			
//			// Flushes the output stream //
//			output.flush();
			
//			bis.close();
//			bos.close();
		} catch(IOException ioException) {
			// If an IOException is given to the program, then this message is printed to the console //
			chatWindow.append("\nERROR CODE : 0");
			
			ioException.printStackTrace();
			// Exits the program with exit code 0 //
			Main.exit(0);
		}
	}
    		
	// Method that does not allow the user to type to the text box and closes the i/o streams //
	private void closeEverything() {
		showMessage("\nClosing connections...\n");
		// Sets the text box to be un-editable //
		ableToType(false);
		try {
			// Closes the output stream //
			output.close();
			
			// Closes the input stream //
			input.close();
			
			// Closes the connection between Client and Server //
			connection.close();
		} catch(IOException ioException) {
			// If an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
			
			Main.println("ERROR CODE : 2");
			// Exits the program with exit code 0 //
			Main.exit(0);
		}
	}
	
	// Method that prints a message to the text field //
	private void showMessage(final String text) {
		// Invokes a new runnable //
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Appends the message to the chat window //
				chatWindow.append(text);
			}
		});
	}
	
	// Method that deals with permissions of the text field //
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			// Invokes a new runnable //
			public void run() {
				// Sets the userText field to be editable (depending on the input of the function //
				userText.setEditable(tof);
			}
		});
	}
}
