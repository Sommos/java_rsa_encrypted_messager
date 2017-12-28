package encryption;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Server extends JFrame {
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message;
	private ServerSocket server;
	private Socket connection;
	static BigInteger pq;
	static BigInteger d;
	
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
		
// 		Use this to gain the full dimensions of the users screen //
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
				} catch(EOFException eOfException) {
					// If an EOFException is given to the program, then this message is shown //
					eOfException.printStackTrace();
					showMessage("\nThe server has ended the connection.");
				} finally {
					// Closes the streams and makes sure the text fields are un-editable //
					closeEverything();
				}
			}
		} catch(IOException ioException) {
			// If an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
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
		message = "You are now connected!\n";
		sendMessage(message);
		// Sets the chat box to be editable //
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
//				RSAEncryption rsa = new RSAEncryption();
//				
//				BigInteger bigIntegerModPow = new BigInteger(message).modPow(rsa.getD(), rsa.getPQ());
				
				showMessage("\n" + new String(message));
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");	
				
				classNotFoundException.printStackTrace();
			}
		} while(!message.equals("Client : END CONNECTION"));
	}
	
	// Method that sends a message using the output stream //
	private void sendMessage(String message) {
		try {
			if (message.length() < 1 || message.length() >= 50) {
				
			} else {
				showMessage("\nServer : " + message);
				
				RSAEncryption rsa = new RSAEncryption();
				
				rsa.setPrimeP(1024);
				rsa.setPrimeQ(1024);
			
				BigInteger p = rsa.getPrimeP();
				BigInteger q = rsa.getPrimeQ();
				
				rsa.setE();
				rsa.setPQ(p, q);
				rsa.setD(rsa.getE(), p, q);
				
				BigInteger e = rsa.getE();
				pq = rsa.getPQ();
				d = rsa.getD();
				
				byte[] messageAsBytes = message.getBytes();
			
				BigInteger bigIntegerModPow = new BigInteger(messageAsBytes).modPow(e, pq);
				
				output.writeObject("Server : " + bigIntegerModPow);
				// Flushes the output stream //
				output.flush();
			}
		} catch(IOException ioException) {
			// If an IOException is given to the program, then this message is printed to the console //
			chatWindow.append("\nERROR CODE : 0");
			
			ioException.printStackTrace();
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
