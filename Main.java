package encryption;

import java.math.BigInteger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// Don't get mad at this code - it was written by an idiot //
public class Main {
	public static void main(String[] args) {
		// Saves the current system time to a variable // 
		long start = System.currentTimeMillis();
		
		// This is the start of the basic RSA encryption demonstration //
		println("--------------------| Demonstration Program - Build 0.1.57 |--------------------");
		
		// Gets the message from the user //
		String userInput = JOptionPane.showInputDialog("Enter your message");
		
		// Gets the bit length of the keys that the user wants to use //
		String userInputBitLengthString = JOptionPane.showInputDialog("Enter the bit length of the encryption keys (Binary Multiples)");
		
		// Parses the resulting String to an int //
		int userInputBitLengthInt = Integer.parseInt(userInputBitLengthString);
		
		// Instantiates the RSAEncryption class //
		RSAEncryption rsa = new RSAEncryption();
	
		println("Original String = " + userInput);
		
		// Generates the two primes keys that will be used for our encryption //
        BigInteger p = rsa.getPrimeP(userInputBitLengthInt);
        BigInteger q = rsa.getPrimeQ(userInputBitLengthInt);
   
        // Prints the generated keys to the console as proof of concept //
        println("\nThese are the original RSA encryption keys (using " + userInputBitLengthInt + " encryption):\n");
        println("-----BEGIN RSA PRIVATE KEY-----");
        println("Prime P = " + p);
        println("Prime Q = " + q);
        println("-----END RSA PRIVATE KEY-----\n");
   
        // Instantiates the EncryptionDecryptionMethods class //
        EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
        
        // Gets the value of e //
        BigInteger e = rsa.getE();
        
        // Gets the value of pq from our entered values of p and q (Our original keys) //
        BigInteger pq = rsa.getPQ(p, q);
        
        // Takes the values generated and the users original message, encrypts it and returns it as a byte array //
        byte[] encrypted = encDecMeth.returnEncryptedMessage(userInput.getBytes(), p, q, e, pq);
        
        // May change this back to decrypted if byte values fail but we should be good //
        println("Byte Value (ENCRYPTED) = " + encDecMeth.bytesToString(encrypted));
        
        // Decrypts the values from the keys given (p and q) //
        byte[] decrypted = encDecMeth.returnDecryptedMessage(encrypted,rsa.getD(e, p, q), pq);
        
        // Prints out the simplistic byte value to the console - May be useful later //
        println("Byte Value (DECRYPTED) = " + encDecMeth.bytesToString(decrypted) + "\n");
        
        // Converts the byte array to a String //
        String finalDecryptedString = new String(decrypted);
        
        // Prints the decrypted String out to the c onsole //
        println("Decrypted String = " + finalDecryptedString);
        
        // Prints the time to run out to the console //
		println("\nTime to run = " + (System.currentTimeMillis() - start) + "ms.");
		
		// Prints exit code out to the console - More for error checking than anything else //
        println("\nDemonstration ran without any issues - GG WP. EXIT CODE: 0.\nEXIT - FINAL TRANSMISSION. (M83 - INTRO).");
		
        // That is the end of the demonstration of my RSA encryption //
        
        // Start of the actual instant messaging application //
        
        // Instantiates the Client class, with the local IP address (This should be changed when in actual use) //
        Client client = new Client("127.0.0.1");
        
        // Sets up the default close situation (Which is when the JFrame is closed by the user) //
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Starts the running of the Client class as a thread //
		client.startRunning();
	}
	
	// Methods for the Main class //
    
    // Method to print to the console in white text // 
	public static void println(Object x) {
		System.out.println(x);
	}
	
	// Method to print to the console in red text //
	public static void errorPrintln(Object x) {
		System.err.println(x);
	}
	
	// Method to exit the program and return 0 // 
	public static void exit(int x) {
		System.exit(x);
	}
}
