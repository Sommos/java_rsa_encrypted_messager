package encryption;

import javax.swing.JFrame;
import java.math.BigInteger;

// Written by Sommos //
public class Main {
	public static void main(String[] args) {
		// Saves the current system time to a variable // 
		long start = System.currentTimeMillis();
		BasicMethods bsc = new BasicMethods();
		
		assignValues();
		
		demonstration();
		// Prints the time to run out to the console //
		bsc.println("\nTime to run the demonstration (Including user input time) = " + (System.currentTimeMillis() - start) + "ms.");
		
		client();
	}
	// Methods for the Main class //
	
	// Method for giving values to each variable needed for the encryption algorithm //
	static void assignValues() {
		RSAEncryption rsa = new RSAEncryption();
		// Prompts the user for their desired bit length of keys //
		rsa.setBitLengthDefault();
		// Sets the values of the two prime keys that will be used for our encryption //
	    rsa.setPrimeP(rsa.getBitLength());
	    rsa.setPrimeQ(rsa.getBitLength());
	    // Sets the value of prime p totient //
		rsa.setPrimePTotient(rsa.getPrimeP());
		// Sets the value of prime q totient //
		rsa.setPrimeQTotient(rsa.getPrimeQ());
		// Sets the value of e //
		rsa.setE(); 
		// Sets the value of d //
		rsa.setD(rsa.getE(), rsa.getPrimeP(), rsa.getPrimeQ());     
		// Sets the value of pq //
		rsa.setPQ(rsa.getPrimeP(), rsa.getPrimeQ());    
	}
	
	// Method for showing a demonstration of the encryption algorithm //
	private static void demonstration() {
		// This is the start of the basic RSA encryption demonstration //
		BasicMethods bsc = new BasicMethods();
		bsc.println("Written by Sommos (Discord pm me @ Sommos#1256 (Always on nowadays)");
		bsc.println("|°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`°º¤ø,¸|- Demonstration Program - Build 0.1.79 -|°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`°º¤ø,¸|");
		// Instantiates the RSAEncryption class //
		RSAEncryption rsa = new RSAEncryption();
		
		// Gets the message from the user //
		rsa.setUserInput();
		// Prompts the user for their desired bit length of keys //
		rsa.setBitLength();
	
		bsc.println("Original user input = " + rsa.getUserInput());
		
		// Sets the values of the two prime keys that will be used for our encryption //
        rsa.setPrimeP(rsa.getBitLength());
        rsa.setPrimeQ(rsa.getBitLength());
        // Gets the values of primes and assigns memory addresses //
        BigInteger p = rsa.getPrimeP();
        BigInteger q = rsa.getPrimeQ();   
        // Sets the value of prime p totient //
        rsa.setPrimePTotient(p);        
        // Sets the value of prime q totient //
        rsa.setPrimeQTotient(q);        
        // Sets the value of e //
        rsa.setE();        
        // Sets the value of d //
        rsa.setD(rsa.getE(), p, q);     
        // Sets the value of pq //
        rsa.setPQ(p, q);    
       
        // Prints the generated keys to the console as proof of concept //
        bsc.println("\nThese are the original RSA encryption keys (using " + rsa.getBitLength() + " encryption):\n");
        bsc.println("-----BEGIN RSA PRIVATE KEY-----");
        bsc.println("Prime p = " + p);
        bsc.println("Prime q = " + q);
        bsc.println("-----END RSA PRIVATE KEY-----\n");
        
        // Takes the values generated and the users original message, encrypts it and returns it as a byte array //
        byte[] encrypted = rsa.returnEncryptedMessage(rsa.getUserInput().getBytes(), p, q, rsa.getE(), rsa.getPQ());        
        // May change this back to decrypted if byte values fail but we should be good //
        bsc.println("Byte value of user input (ENCRYPTED) = " + rsa.bytesToString(encrypted));        
        
        // Decrypts the values from the keys given (p and q) //
        byte[] decrypted = rsa.returnDecryptedMessage(encrypted, rsa.getD(), rsa.getPQ());        
        // Prints out the simplistic byte value to the console - May be useful later //
        bsc.println("Byte value of user input (DECRYPTED) = " + rsa.bytesToString(decrypted) + "\n");        
        
        // Converts the byte array to a String //
        String finalDecryptedString = new String(decrypted);        
        // Prints the decrypted String out to the console //
        bsc.println("Decrypted user input = " + finalDecryptedString);
        // Checks if original input is equal to the decrypted value //
        if (finalDecryptedString.equals(rsa.getUserInput())) {
        	bsc.println("Equal check : TRUE.");
        } else if (!finalDecryptedString.equals(rsa.getUserInput())) {
        	bsc.println("Equal check : FALSE.");
        } else {
        	bsc.println("ERROR CODE : 0");
        } 
		// Prints exit code out to the console - More for error checking than anything else //
        bsc.println("\nDemonstration ran without any issues - GG WP.\n   _\n _(.)< EXIT - FINAL TRANSMISSION. (M83 - INTRO).\n/___)");
	}

	// Method that connects to the server and launches a client JFrame //
	private static void client() {
		// Instantiates the Client class, with the local IP address (This should be changed when in actual use) //
		Client client = new Client("127.0.0.1");
    
		// Sets up the default close situation (Which is when the JFrame is closed by the user) //
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		// Starts the running of the Client class as a thread //
		client.startRunning();
	}
}
