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
	
	// constructor for the client sub-class //
	public Client(String host) {
		// sets the title of the window //
		setTitle("Samuels Encryption Client Service");
		
		// sets the IP address of the server to connect to //
		serverIP = host;
		
		// generates a new JTextField //
		userText = new JTextField();
		
		// sets the JTextField to be un-editable //
		userText.setEditable(false);
		
		// adds an action listener //
		userText.addActionListener(new ActionListener() {
			
			// detects if the action is performed //
			public void actionPerformed(ActionEvent event) {
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
		add(new JScrollPane(chatWindow), BorderLayout.CENTER);
		
// 		Use this to gain the full dimensions of the users screen //		
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
		
		// sets the size of the whole window //
		setSize(325,375);
		
		// sets visibility of the Swing components to true //
		setVisible(true);
	}
	
	// starts the Client class //
	public void startRunning() {
		try {
			// connects to the server //
			connectToServer();
			
			// sets up the i/o streams //
			setupStreams();
			
			// sets up the GUI to read the input stream //
			whileChatting();
		} catch(EOFException eOfException) {
			// if an EOFException is given to the program, then this message is shown //
			showMessage("\nClient terminated connection.");
			eOfException.printStackTrace();			
		} catch(IOException ioException) {
			// if an IOException is given to the program, then the stack trace is printed to the console //
			ioException.printStackTrace();
		} finally {
			// closes the streams and makes sure the text fields are un-editable //
			closeEverything();
		}
	}
	
	// method that connects to the server using an IP and port //
	private void connectToServer() throws IOException {
		showMessage("Attempting connection...");
		
		// sets up the connection using the IP and port of the server machine //
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		
		showMessage("\nConnected to : " + connection.getInetAddress().getHostName());
	}
	
	// sets the streams up in preparation for data to be sent //
	private void setupStreams() throws IOException {
		// sets up the output stream //
		output = new ObjectOutputStream(connection.getOutputStream());
		
		// flushes spare bytes out of the client machine //
		output.flush();
		
		// sets up the input stream //
		input = new ObjectInputStream(connection.getInputStream());
		
		showMessage("\nYour streams are good to go.");
	}
	
	// method that is run whilst the users are connected to each other //
	private void whileChatting() throws IOException {
		// sets the chat box to be editable //
		ableToType(true);
		do {
			try {
//				BigInteger d = Server.d;
//				BigInteger pq = Server.pq;
		
				// casts the input.readObject to a String //
				message = (String) input.readObject();
				
//				byte[] messageAsByteArray = message.getBytes();
//				
//				byte[] bigIntegerModPow = new BigInteger(messageAsByteArray).modPow(d, pq).toByteArray();
//				
//				String finalDecryptedMessage = new String(bigIntegerModPow);

//				byte[] bigIntegerModPowAsByteArray = new BigInteger(messageAsByteValue).modPow(d, pq).toByteArray();
				
				// prints the input.readObject to the console //
				showMessage("\n" + message);
			} catch(ClassNotFoundException classNotFoundException) {
				showMessage("\nThe server is unable to understand that String.");
				classNotFoundException.printStackTrace();
			}
		} while(!message.equals("Server : END CONNECTION"));
	}
	
	// method that sends a message using the output stream //
	private void sendMessage(String message) throws NullPointerException {
		try {
			if(message.length() < 1 || message.length() >= 50) {
				
			} else {			
				showMessage("\nClient : " + message);
			
				output.writeObject("Client : " + message);
				// flushes the output stream //
				output.flush();
			}
		} catch(IOException ioException) {
			// if an IOException is given to the program, then this message is printed to the console //
			chatWindow.append("\nClient had an issue with sending that message.");
			
			ioException.printStackTrace();
		}
	}
	
	// method that does not allow the user to type to the text box and closes the i/o streams //
	private void closeEverything() {
		showMessage("\nClosing all connections...");
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
	private void showMessage(final String message) {
		// invokes a new runnable //
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// appends the message to the chat window //
				chatWindow.append(message);
			}
		});
	}
	
	// method that deals with permissions of the text field //
	private void ableToType(final boolean tof) {
		// invokes a new runnable //
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// sets the userText field to be editable (depending on the input of the function //
				userText.setEditable(tof);
			}
		});
	}
}
