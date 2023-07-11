package src;

import java.math.BigInteger;
import javax.swing.JFrame;

import encryption.RSAEncryption;

// Written by Sommos //
public class Main {
	public static void main(String[] args) {
		// saves the current system time to a variable // 
		// long start = System.currentTimeMillis();
		// BasicMethods bsc = new BasicMethods();
		assignValues();
		demonstration();
		// prints the time to run out to the console //
		// bsc.println("\nTime to run the demonstration (Including user input time) = " + (System.currentTimeMillis() - start) + "ms.");
		
		client();
	}
	
	// method for giving values to each variable needed for the encryption algorithm //
	static void assignValues() {
		RSAEncryption rsa = new RSAEncryption();
		// prompts the user for their desired bit length of keys //
		rsa.setBitLengthDefault();
		// sets the values of the two prime keys that will be used for our encryption //
	   	rsa.setPrimeP(rsa.getBitLength());
	   	rsa.setPrimeQ(rsa.getBitLength());
	   	// sets the value of prime p totient //
		rsa.setPrimePTotient(rsa.getPrimeP());
		// sets the value of prime q totient //
		rsa.setPrimeQTotient(rsa.getPrimeQ());
		// sets the value of e //
		rsa.setE(); 
		// sets the value of d //
		rsa.setD(rsa.getE(), rsa.getPrimeP(), rsa.getPrimeQ());     
		// sets the value of pq //
		rsa.setPQ(rsa.getPrimeP(), rsa.getPrimeQ());    
	}
	
	// method for showing a demonstration of the encryption algorithm //
	private static void demonstration() {
		// this is the start of the basic RSA encryption demonstration //
		BasicMethods bsc = new BasicMethods();
		bsc.println("Written by Sommos");
		bsc.println("|°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`°º¤ø,¸|- Demonstration Program - Build 0.2.0 -|°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`°º¤ø,¸|");
		// instantiates the RSAEncryption class //
		RSAEncryption rsa = new RSAEncryption();
		// gets the message from the user //
		rsa.setUserInput();
		// prompts the user for their desired bit length of keys //
		rsa.setBitLength();
		// prints original user input before encryption
		bsc.println("Original user input = " + rsa.getUserInput());
		// sets the values of the two prime keys that will be used for our encryption //
		rsa.setPrimeP(rsa.getBitLength());
		rsa.setPrimeQ(rsa.getBitLength());
		// gets the values of primes and assigns memory addresses //
		BigInteger p = rsa.getPrimeP();
		BigInteger q = rsa.getPrimeQ();   
		// sets the value of prime p totient //
		rsa.setPrimePTotient(p);        
		// sets the value of prime q totient //
		rsa.setPrimeQTotient(q);        
		// sets the value of e //
		rsa.setE();        
		// sets the value of d //
		rsa.setD(rsa.getE(), p, q);     
		// sets the value of pq //
		rsa.setPQ(p, q);    
       
		// prints the generated keys to the console as proof of concept //
		bsc.println("\nThese are the original RSA encryption keys (using " + rsa.getBitLength() + " encryption):\n");
		bsc.println("-----BEGIN RSA PRIVATE KEY-----");
		bsc.println("Prime p = " + p);
		bsc.println("Prime q = " + q);
		bsc.println("-----END RSA PRIVATE KEY-----\n");
        
		// takes the values generated and the users original message, encrypts it and returns it as a byte array //
		byte[] encrypted = rsa.returnEncryptedMessage(rsa.getUserInput().getBytes(), p, q, rsa.getE(), rsa.getPQ());        
		// may change this back to decrypted if byte values fail but we should be good //
		bsc.println("Byte value of user input (ENCRYPTED) = " + rsa.bytesToString(encrypted));        

		// decrypts the values from the keys given (p and q) //
		byte[] decrypted = rsa.returnDecryptedMessage(encrypted, rsa.getD(), rsa.getPQ());        
		// prints out the simplistic byte value to the console - May be useful later //
		bsc.println("Byte value of user input (DECRYPTED) = " + rsa.bytesToString(decrypted) + "\n");        

		// converts the byte array to a String //
		String finalDecryptedString = new String(decrypted);        
		// prints the decrypted String out to the console //
		bsc.println("Decrypted user input = " + finalDecryptedString);
		// checks if original input is equal to the decrypted value //
		if (finalDecryptedString.equals(rsa.getUserInput())) {
			bsc.println("Equal check : TRUE.");
		} else if (!finalDecryptedString.equals(rsa.getUserInput())) {
			bsc.println("Equal check : FALSE.");
		} else {
			bsc.println("ERROR CODE : 0");
		} 
		// prints exit code out to the console - More for error checking than anything else //
		bsc.println("\nDemonstration ran without any issues - GG WP.\n   _\n _(.)< EXIT - FINAL TRANSMISSION. (M83 - INTRO).\n/___)");
	}

	// method that connects to the server and launches a client JFrame //
	private static void client() {
		// instantiates the Client class, with the local IP address (This should be changed when in actual use) //
		Client client = new Client("127.0.0.1");
		// sets up the default close situation (Which is when the JFrame is closed by the user) //
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// starts the running of the Client class as a thread //
		client.startRunning();
	}
}
