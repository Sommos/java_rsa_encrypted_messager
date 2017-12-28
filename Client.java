package encryption;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Client extends JFrame {
	private JTextField userText;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message;
	private String serverIP;
	private Socket connection;
	
	// Constructor for the Client sub-class //
	public Client(String host) {
		// Sets the title of the window //
		setTitle("Samuels Encryption Client Service");
		
		// Sets the IP address of the server to connect to //
		serverIP = host;
		
		// Generates a new JTextField //
		userText = new JTextField();
		
		// Sets the JTextField to be un-editable //
		userText.setEditable(false);
		
		// Adds an action listener //
		userText.addActionListener(new ActionListener() {
			
			// Detects if the action is performed //
			public void actionPerformed(ActionEvent event) {
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
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		
// 		Use this to gain the full dimensions of the users screen //		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
		
		// Sets the size of the whole window //
		setSize(325,375);
		
		// Sets visibility of the Swing components to true //
		setVisible(true);
	}
	
	// Starts the Client class //
	public void startRunning() {
		try {
			// Connects to the server //
			connectToServer();
			
			// Sets up the i/o streams //
			setupStreams();
			
			// Sets up the GUI to read the input stream //
			whileChatting();
		} catch(EOFException eOfException) {
			// If an EOFException is given to the program, then this message is shown //
			showMessage("\nClient terminated connection.");
			eOfException.printStackTrace();			
		} catch(IOException ioException) {
			// If an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
		} finally {
			// Closes the streams and makes sure the text fields are un-editable //
			closeEverything();
		}
	}
	
	// Method that connects to the server using an IP and port //
	private void connectToServer() throws IOException {
		showMessage("Attempting connection...");
		
		// Sets up the connection using the IP and port of the server machine //
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		
		showMessage("\nConnected to : " + connection.getInetAddress().getHostName());
	}
	
	// Sets the streams up in preparation for data to be sent //
	private void setupStreams() throws IOException {
		// Sets up the output stream //
		output = new ObjectOutputStream(connection.getOutputStream());
		
		// Flushes spare bytes out of the Client machine //
		output.flush();
		
		// Sets up the input stream //
		input = new ObjectInputStream(connection.getInputStream());
		
		showMessage("\nYour streams are good to go.");
	}
	
	// Method that is run whilst the users are connected to each other //
	private void whileChatting() throws IOException {
		// Sets the chat box to be editable //
		ableToType(true);
		do {
			try {
				BigInteger d = Server.d;
				BigInteger pq = Server.pq;
		
				// Casts the input.readObject to a String //
				message = (String) input.readObject();
				
				byte[] messageAsByteArray = message.getBytes();
				
				byte[] bigIntegerModPow = new BigInteger(messageAsByteArray).modPow(d, pq).toByteArray();
				
				String finalDecryptedMessage = new String(bigIntegerModPow);

//				byte[] bigIntegerModPowAsByteArray = new BigInteger(messageAsByteValue).modPow(d, pq).toByteArray();
				// Prints the input.readObject to the console //
				showMessage("\n" + finalDecryptedMessage);
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");
				classNotFoundException.printStackTrace();
			}
		} while(!message.equals("Server : END CONNECTION"));
	}
	
	// Method that sends a message using the output stream //
	private void sendMessage(String message) throws NullPointerException {
		try {
			if(message.length() < 1 || message.length() >= 50) {
				
			} else {			
				showMessage("\nClient : " + message);
			
				output.writeObject("Client : " + message);
				// Flushes the output stream //
				output.flush();
			}
		} catch(IOException ioException) {
			// If an IOException is given to the program, then this message is printed to the console //
			chatWindow.append("\nClient had an issue with sending that message.");
			
			ioException.printStackTrace();
		}
	}
	
	// Method that does not allow the user to type to the text box and closes the i/o streams //
	private void closeEverything() {
		showMessage("\nClosing all connections...");
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
	private void showMessage(final String message) {
		// Invokes a new runnable //
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Appends the message to the chat window //
				chatWindow.append(message);
			}
		});
	}
	
	// Method that deals with permissions of the text field //
	private void ableToType(final boolean tof) {
		// Invokes a new runnable //
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Sets the userText field to be editable (depending on the input of the function //
				userText.setEditable(tof);
			}
		});
	}
}
