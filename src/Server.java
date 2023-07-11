package src;

import java.math.BigInteger;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.awt.*;
import java.io.*;

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
	
	// constructor for the Server sub-class //
	public Server() {
		// sets the title of the window //
		setTitle("Samuels Encryption Server Service");
		// generates a new JTextField //
		userText = new JTextField();
		// sets the JTextField to be un-editable //
		userText.setEditable(false);
		// adds an action listener //
		userText.addActionListener(new ActionListener(){
			// detects if the action is performed //
			public void actionPerformed(ActionEvent event){
				// sends message with the contents of the action command //
				sendMessage(event.getActionCommand());
				// sets the text field to empty //
				userText.setText("");
			}
		});
		// adds the userText and sets the border layout to north of the screen //
		add(userText, BorderLayout.NORTH);
		// a new JTextArea is now instantiated //
		chatWindow = new JTextArea();
		// adds the new JScrollPane, using the chatWindow variable and setting the border layout to center of the screen //
		add(new JScrollPane(chatWindow));
		
		// use this to gain the full dimensions of the users screen //
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
		
		// sets the size of the whole window //
		setSize(325,375);
		// sets visibility of the Swing components to true //
		setVisible(true);
	}
	
	// starts the Server class // 
	public void startRunning() {
		try {
			// instantiates the server with port '6789' and allows 100 users to queue //
			server = new ServerSocket(6789, 100);
			while(true) {
				try {
					// waits for a user to connect //
					waitForConnection();
					// sets up the i/o streams //
					setupStreams();
					// sets up the GUI to read the input stream //
					whileChatting();
				} catch(EOFException eOfException) {
					// if an EOFException is given to the program, then this message is shown //
					eOfException.printStackTrace();
					showMessage("\nThe server has ended the connection.");
				} finally {
					// closes the streams and makes sure the text fields are un-editable //
					closeEverything();
				}
			}
		} catch(IOException ioException) {
			// if an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
		}
	}
	
	// method that waits for a user to connect to the server //
	private void waitForConnection() throws IOException {
		showMessage("Waiting for another user to connect...");
		// accepts any connection that the server receives //
		connection = server.accept();
		showMessage("\nYou have connected to " + connection.getInetAddress().getHostName());
	}
	
	// sets the streams up in preparation for data to be sent //
	private void setupStreams() throws IOException {
		// sets up the output stream //
		output = new ObjectOutputStream(connection.getOutputStream()); 
		// flushes spare bytes out of the Client machine //
		output.flush();
		// sets up the input stream //
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\nStreams are now setup!");
	}

	// method that is run whilst the users are connected to each other //
	private void whileChatting() throws IOException {
		message = "You are now connected!\n";
		sendMessage(message);
		// sets the chat box to be editable //
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();		
				// deprecated //
//				RSAEncryption rsa = new RSAEncryption();
//				BigInteger bigIntegerModPow = new BigInteger(message).modPow(rsa.getD(), rsa.getPQ());
				showMessage("\n" + new String(message));
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");	
				classNotFoundException.printStackTrace();
			}
		} while(!message.equals("Client : END CONNECTION"));
	}
	// method that sends a message using the output stream //
	private void sendMessage(String message) {
		try {
			if (message.length() < 1 || message.length() >= 50) {
				
			} else {
				showMessage("\nServer : " + message);	
//				RSAEncryption rsa = new RSAEncryption();
//				rsa.setPrimeP(1024);
//				rsa.setPrimeQ(1024);		
//				BigInteger p = rsa.getPrimeP();
//				BigInteger q = rsa.getPrimeQ();		
//				rsa.setE();
//				rsa.setPQ(p, q);
//				rsa.setD(rsa.getE(), p, q);	
//				BigInteger e = rsa.getE();
//				pq = rsa.getPQ();
//				d = rsa.getD();				
//				byte[] messageAsBytes = message.getBytes();			
//				BigInteger bigIntegerModPow = new BigInteger(messageAsBytes).modPow(e, pq);
				output.writeObject("Server : " + message);
				// flushes the output stream //
				output.flush();
			}
		} catch(IOException ioException) {
			// if an IOException is given to the program, then this message is printed to the console //
			chatWindow.append("\nERROR CODE : 0");
			
			ioException.printStackTrace();
		} 
	}
    		
	// method that does not allow the user to type to the text box and closes the i/o streams //
	private void closeEverything() {
		showMessage("\nClosing connections...\n");
		// sets the text box to be un-editable //
		ableToType(false);
		try {
			// closes the output stream //
			output.close();
			// closes the input stream //
			input.close();
			// closes the connection between Client and Server //
			connection.close();
		} catch(IOException ioException) {
			// if an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
		}
	}
	
	// method that prints a message to the text field //
	private void showMessage(final String text) {
		// invokes a new runnable //
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// appends the message to the chat window //
				chatWindow.append(text);
			}
		});
	}
	
	// method that deals with permissions of the text field //
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(new Runnable() {
			// invokes a new runnable //
			public void run() {
				// sets the userText field to be editable (depending on the input of the function //
				userText.setEditable(tof);
			}
		});
	}
}
